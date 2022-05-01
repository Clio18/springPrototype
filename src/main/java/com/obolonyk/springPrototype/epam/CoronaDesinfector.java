package com.obolonyk.springPrototype.epam;

public class CoronaDesinfector {
    public void start(Room room){
        //todo: alarm to go away from room
        //todo: push everyone from the room
        desinfect(room);
        //todo: alarm that desinfection is finished
    }

    public void desinfect(Room room){
        System.out.println("Doing procedure for room desinfection");
    }
}
