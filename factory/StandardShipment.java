package com.mycompany.internationalcourierhub1.factory;

public class StandardShipment implements Shipment {

    private final String description;
    private final double weight;

    public StandardShipment(String description, double weight) {
        this.description = description;
        this.weight = weight;
    }

    @Override
    public String getType() {
        return "Standard";
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
