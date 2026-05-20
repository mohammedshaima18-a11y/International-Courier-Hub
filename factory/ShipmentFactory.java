package com.mycompany.internationalcourierhub1.factory;

public class ShipmentFactory {

    public static Shipment createShipment(String type, String description, double weight) {
        return createShipment(type, description, weight, null);
    }

    public static Shipment createShipment(String type, String description,
                                          double weight, String destinationCountry) {
        switch (type.toLowerCase()) {
            case "standard":
                return new StandardShipment(description, weight);
            case "express":
                return new ExpressShipment(description, weight);
            case "international":
                if (destinationCountry == null || destinationCountry.isBlank()) {
                    throw new IllegalArgumentException(
                            "Destination country is required for international shipments");
                }
                return new InternationalShipment(description, weight, destinationCountry);
            default:
                throw new IllegalArgumentException("Unknown shipment type: " + type);
        }
    }
}
