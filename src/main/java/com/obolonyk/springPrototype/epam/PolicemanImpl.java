package com.obolonyk.springPrototype.epam;

public class PolicemanImpl implements Policeman {
    @InjectByType
    private Recommendator recommendator;

    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("Please, leave the room!!!");
    }
}
