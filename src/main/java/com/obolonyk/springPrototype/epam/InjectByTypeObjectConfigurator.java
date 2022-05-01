package com.obolonyk.springPrototype.epam;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectByTypeObjectConfigurator implements ObjectConfigurator {

    @SneakyThrows
    @Override
    public void configure(Object t) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(InjectByType.class)){
                field.setAccessible(true);
                Object object = ObjectFactory.getInstance().createObject(field.getType());
                field.set(t, object);
            }
        }
    }
}
