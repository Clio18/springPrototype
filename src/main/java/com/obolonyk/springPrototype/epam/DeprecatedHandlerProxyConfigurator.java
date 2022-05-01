package com.obolonyk.springPrototype.epam;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//this is a new configurator which will be responsible for Proxy configuration
//so when Announcer will call the recommend method the chain will be next in case
//of deprecated class: AnnouncerImpl.recommend()->Proxy.invoke()->InvocationHandler.recommend()->RecommendationImpl
public class DeprecatedHandlerProxyConfigurator implements ProxyConfigurator {
    @Override
    public Object replaceWithProxyIfNeeded(Object t, Class implClass) {
        if (implClass.isAnnotationPresent(Deprecated.class)) {
            return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("***** This class is deprecated *****");
                    return method.invoke(t);
                }
            });
        } else {
            return t;
        }
    }
}
