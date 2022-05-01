package com.obolonyk.springPrototype.epam;

import javax.annotation.PostConstruct;

public class PolicemanImpl implements Policeman {
    @InjectByType
    private Recommendator recommendator;

    //this method is calling when the object is already adjusted
    @PostConstruct
    public void init() {
        System.out.println(recommendator.getClass());
    }

    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("Please, leave the room!!!");
    }
}
