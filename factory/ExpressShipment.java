package com.mycompany.internationalcourierhub1.factory;

public class ExpressShipment implements Shipment {

    private final String description;
    private final double weight;

    public ExpressShipment(String description, double weight) {
        this.description = description;
        this.weight = weight;
    }

    @Override
    public String getType() {
        return "Express";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double calculateWeight() {
        return weight;
    }
}
