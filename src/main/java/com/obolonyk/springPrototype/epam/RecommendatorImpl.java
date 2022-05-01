package com.obolonyk.springPrototype.epam;


public class RecommendatorImpl implements Recommendator{

    @InjectProperty("tea")
    private String drinks;

    @Override
    public void recommend() {
        System.out.println("Drink " + drinks);
    }
}
