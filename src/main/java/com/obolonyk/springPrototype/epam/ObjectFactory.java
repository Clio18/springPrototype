package com.obolonyk.springPrototype.epam;


import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/*Singleton - the core of our application, which will be
responsible for service's objects creation
*/
public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config;

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    private ObjectFactory() {
        config = new JavaConfig("com.obolonyk.springPrototype.epam",
                new HashMap<>(Map.of(Policeman.class, AngryPoliceman.class)));
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type;
        //if the type is interface we should return it's implementation
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = implClass.getDeclaredConstructor().newInstance();

        //before we return the object we will made the adjustments
        //to find the annotation injectProperty to insert drinks message to the announcer object
        for (Field field : implClass.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            //read from the property file to make the map
            String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
            Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
            Map<String, String> propertiesMap = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));

            if (annotation != null) {
                String value;
                //if the annotation has no value, we pick up the field name
                if (annotation.value().isEmpty()) {
                  value = propertiesMap.get(field.getName());
                } else {
                    //if its not - pick up the annotation value
                    value = propertiesMap.get(annotation.value());
                }
                field.setAccessible(true);
                field.set(t, value);
            }

        }

        return t;
    }
}
