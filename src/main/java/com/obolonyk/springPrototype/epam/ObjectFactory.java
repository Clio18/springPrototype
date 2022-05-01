package com.obolonyk.springPrototype.epam;


import lombok.SneakyThrows;

/*Singleton - the core of our application, which will be
responsible for service's objects creation
*/
public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config = new JavaConfig("com.obolonyk.springPrototype.epam");

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    private ObjectFactory() {
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type;
        //if the type is interface we should return it's implementation
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = implClass.getDeclaredConstructor().newInstance();

        //todo

        return t;
    }
}
