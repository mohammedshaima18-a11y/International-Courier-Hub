package com.mycompany.internationalcourierhub1.strategy;

public class InternationalCost implements ShippingCostStrategy {

    @Override
    public double calculateCost(double weight, double distance) {
        return weight * 3.0 + distance * 1.2 + 25.0;
    }

    @Override
    public String getStrategyName() {
        return "International Shipping Cost";
    }
}
