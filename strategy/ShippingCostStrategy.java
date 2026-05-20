package com.mycompany.internationalcourierhub1.strategy;

public interface ShippingCostStrategy {
    double calculateCost(double weight, double distance);
    String getStrategyName();
}
