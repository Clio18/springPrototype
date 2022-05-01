package com.obolonyk.springPrototype.epam;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private ObjectFactory factory;
    private Map<Class, Object> cash = new ConcurrentHashMap<>();

    public <T> T getObject(Class<T> type){
        return null;
    };



}
