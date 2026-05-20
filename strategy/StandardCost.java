package com.mycompany.internationalcourierhub1.strategy;

public class StandardCost implements ShippingCostStrategy {

    @Override
    public double calculateCost(double weight, double distance) {
        return weight * 0.5 + distance * 0.1;
    }

    @Override
    public String getStrategyName() {
        return "Standard Shipping Cost";
    }
}
