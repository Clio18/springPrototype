package com.obolonyk.springPrototype.epam;

public class CoronaDesinfector {
    private Announcer announcer = ObjectFactory.getInstance().createObject(Announcer.class);
    private Policeman policeman = ObjectFactory.getInstance().createObject(Policeman.class);

    public void start(Room room){
        announcer.announce("Starting the cleanup!");
        policeman.makePeopleLeaveRoom();
        desinfect(room);
        announcer.announce("Finish the cleanup!");
    }

    public void desinfect(Room room){
        System.out.println("Doing procedure for room desinfection");
    }
}
