package com.obolonyk.springPrototype.epam;


import lombok.Setter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;


/*Singleton - the core of our application, which will be
responsible for service's objects creation
*/
public class ObjectFactory {
    private final ApplicationContext context;
    //to fill the configurators we should scan the package, find the implementations
    //of the interface and put it to the list
    private List<ObjectConfigurator> configurators = new ArrayList<>();


    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        for (Class<? extends ObjectConfigurator> configurator : context.getConfig()
                .getScanner()
                .getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(configurator.getDeclaredConstructor().newInstance());

        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implClass) {

        T t = implClass.getDeclaredConstructor().newInstance();

        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t, context));

        return t;
    }
}
