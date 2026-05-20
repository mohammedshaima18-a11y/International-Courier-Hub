package com.mycompany.internationalcourierhub1;

import com.mycompany.internationalcourierhub1.factory.Shipment;
import com.mycompany.internationalcourierhub1.factory.ShipmentFactory;
import com.mycompany.internationalcourierhub1.observer.Customer;
import com.mycompany.internationalcourierhub1.observer.ShipmentStatus;
import com.mycompany.internationalcourierhub1.singleton.CourierHubManager;
import com.mycompany.internationalcourierhub1.strategy.ExpressCost;
import com.mycompany.internationalcourierhub1.strategy.InternationalCost;
import com.mycompany.internationalcourierhub1.strategy.ShippingCostStrategy;
import com.mycompany.internationalcourierhub1.strategy.StandardCost;

public class InternationalCourierHub1 {

    public static void main(String[] args) {
        CourierHubManager manager = CourierHubManager.getInstance();

        Shipment s1 = ShipmentFactory.createShipment("standard", "Documents", 2.5);
        Shipment s2 = ShipmentFactory.createShipment("express", "Electronics", 5.0);
        Shipment s3 = ShipmentFactory.createShipment("international",
                "Machinery Parts", 50.0, "Germany");

        manager.registerShipment(s1);
        manager.registerShipment(s2);
        manager.registerShipment(s3);

        ShippingCostStrategy standardCost = new StandardCost();
        ShippingCostStrategy expressCost = new ExpressCost();
        ShippingCostStrategy internationalCost = new InternationalCost();

        System.out.println("\n--- Cost Calculation (Strategy Pattern) ---");
        double distance = 1000;
        System.out.println(s1.getType() + " cost: $"
                + standardCost.calculateCost(s1.calculateWeight(), distance)
                + " (" + standardCost.getStrategyName() + ")");
        System.out.println(s2.getType() + " cost: $"
                + expressCost.calculateCost(s2.calculateWeight(), distance)
                + " (" + expressCost.getStrategyName() + ")");
        System.out.println(s3.getType() + " cost: $"
                + internationalCost.calculateCost(s3.calculateWeight(), distance)
                + " (" + internationalCost.getStrategyName() + ")");

        System.out.println("\n--- Shipment Tracking (Observer Pattern) ---");
        ShipmentStatus tracking1 = new ShipmentStatus("TRK-001");
        ShipmentStatus tracking2 = new ShipmentStatus("TRK-002");

        Customer alice = new Customer("Alice");
        Customer bob = new Customer("Bob");

        tracking1.attach(alice);
        tracking2.attach(bob);
        tracking2.attach(alice);

        tracking1.setStatus("Picked Up");
        tracking2.setStatus("In Transit");
        tracking1.setStatus("Out for Delivery");
        tracking2.setStatus("Delivered");

        manager.printReport();
    }
}
