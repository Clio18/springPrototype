package com.obolonyk.springPrototype.epam;

public class Main {
    public static void main(String[] args) {

        //before this we create the object by using new operator and make the adjustments
        //by our own, but now out Factory made this for us (Inversion of control)
        CoronaDesinfector coronaDesinfector = ObjectFactory.getInstance().createObject(CoronaDesinfector.class);
        coronaDesinfector.start(new Room());
    }
}
