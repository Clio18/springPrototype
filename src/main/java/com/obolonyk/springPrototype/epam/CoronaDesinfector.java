package com.obolonyk.springPrototype.epam;

public class CoronaDesinfector {
    @InjectByType
    private Announcer announcer;
    @InjectByType
    private Policeman policeman;

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
