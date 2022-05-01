package com.obolonyk.springPrototype.epam;


import lombok.Setter;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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

        //on this stage called constructor and when we do the calling of the
        //recommendator.getClass() in teh Policeman class we cannot have it yet
        //because we cannot make the adjustments before object creation. So in this case
        //we shouldn't use constructor for additional adjustments
        T t = create(implClass);

        configure(t);

        invokeInit(implClass, t);

        //what if we want to show user the log info in the case when for example
        //RecommedatorImpl became Deprecated? In this case we should use Proxy pattern and
        //return instead the object of RecommedatorImpl the object of another class which
        //will implements the same interface
        if (implClass.isAnnotationPresent(Deprecated.class)) {
            return (T) Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("***** This class is deprecated! *****");
                    return method.invoke(t);
                }
            });
        }
        return t;
    }

    private <T> void invokeInit(Class<T> implClass, T t) throws IllegalAccessException, InvocationTargetException {
        for (Method method : implClass.getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }

    private <T> void configure(T t) {
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t, context));
    }

    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }
}
