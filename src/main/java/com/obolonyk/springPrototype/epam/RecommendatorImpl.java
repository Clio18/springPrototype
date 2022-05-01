package com.obolonyk.springPrototype.epam;

@Singleton
@Deprecated
public class RecommendatorImpl implements Recommendator{

    @InjectProperty("tea")
    private String drinks;

    public RecommendatorImpl() {
        System.out.println("Recommendator was created!");
    }

    @Override
    public void recommend() {
        System.out.println("Drink " + drinks);
    }
}
