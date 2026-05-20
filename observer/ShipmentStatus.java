package com.mycompany.internationalcourierhub1.observer;

import java.util.ArrayList;
import java.util.List;

public class ShipmentStatus {

    private final String trackingId;
    private String status;
    private final List<Observer> observers;

    public ShipmentStatus(String trackingId) {
        this.trackingId = trackingId;
        this.status = "Pending";
        this.observers = new ArrayList<>();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
        notifyObservers();
    }

    public String getStatus() {
        return status;
    }

    public String getTrackingId() {
        return trackingId;
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(trackingId, status);
        }
    }
}
