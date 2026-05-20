package com.mycompany.internationalcourierhub1.observer;

public class Customer implements Observer {

    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void update(String trackingId, String status) {
        System.out.println("[Notification for " + name + "] "
                + "Your shipment " + trackingId + " is now: " + status);
    }

    public String getName() {
        return name;
    }
}
