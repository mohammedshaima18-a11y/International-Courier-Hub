package com.mycompany.internationalcourierhub1.singleton;

import com.mycompany.internationalcourierhub1.factory.Shipment;
import java.util.ArrayList;
import java.util.List;

public class CourierHubManager {

    private static CourierHubManager instance;
    private final List<String> logs;
    private int totalShipments;

    private CourierHubManager() {
        logs = new ArrayList<>();
        totalShipments = 0;
    }

    public static synchronized CourierHubManager getInstance() {
        if (instance == null) {
            instance = new CourierHubManager();
        }
        return instance;
    }

    public void registerShipment(Shipment shipment) {
        totalShipments++;
        String log = "Shipment #" + totalShipments + " [" + shipment.getType()
                + "] registered: " + shipment.getDescription();
        logs.add(log);
        System.out.println("[Manager] " + log);
    }

    public void logEvent(String event) {
        logs.add(event);
        System.out.println("[Manager] " + event);
    }

    public void printReport() {
        System.out.println("\n===== Courier Hub Report =====");
        System.out.println("Total shipments: " + totalShipments);
        System.out.println("Events logged: " + logs.size());
        System.out.println("==============================\n");
    }
}
