package com.obolonyk.springPrototype.epam;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {
    @Getter
    private Reflections scanner;
    private Map<Class, Class> ifcToImpl;

    public JavaConfig(String packageToScan, Map<Class, Class> ifcToImpl) {
        this.scanner = new Reflections(packageToScan);
        this.ifcToImpl = ifcToImpl;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        //if key is exist method return value, if not: it start the lambda, which returns the value
        //which is gonna be saved in map
        //we need this map to remember all the implementations of the interface
        //and avoid scanning the package
        return ifcToImpl.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            //if the interface has more than one or any implementations we cannot go further
            if (classes.size() != 1) {
                throw new RuntimeException(ifc + " has 0 or more than 1 implementations please update your config");
            }
            return classes.iterator().next();
        });
    }
}
