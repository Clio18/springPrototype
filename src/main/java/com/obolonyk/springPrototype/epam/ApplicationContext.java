package com.obolonyk.springPrototype.epam;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    @Setter
    private ObjectFactory factory;
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    //objectFactory will work with interfaces and applicationContext will return the implementstion
    public <T> T getObject(Class<T> type){
        Class<? extends T> implClass = type;
        if (cache.containsKey(type)){
            return (T) cache.get(type);
        }
        //if the type is interface we should return it's implementation
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = factory.createObject(implClass);
        if (implClass.isAnnotationPresent(Singleton.class)){
            cache.put(type, t);
        }
        return t;
    };



}
