package com.mycompany.internationalcourierhub1.factory;

public class InternationalShipment implements Shipment {

    private final String description;
    private final double weight;
    private final String destinationCountry;

    public InternationalShipment(String description, double weight, String destinationCountry) {
        this.description = description;
        this.weight = weight;
        this.destinationCountry = destinationCountry;
    }

    @Override
    public String getType() {
        return "International";
    }

    @Override
    public String getDescription() {
        return description + " to " + destinationCountry;
    }

    @Override
    public double calculateWeight() {
        return weight;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }
}
