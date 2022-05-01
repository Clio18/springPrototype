package com.obolonyk.springPrototype.epam;

import org.reflections.Reflections;

import java.util.Set;

public class JavaConfig implements Config {
    private Reflections scanner;

    public JavaConfig(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
        //if the interface has more than one or any implementations we cannot go further
        if (classes.size() != 1) {
            throw new RuntimeException(ifc + " has 0 or more than 1 implementations");
        }
        return classes.iterator().next();
    }
}
