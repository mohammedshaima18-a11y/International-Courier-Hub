package com.mycompany.internationalcourierhub1.strategy;

public class ExpressCost implements ShippingCostStrategy {

    @Override
    public double calculateCost(double weight, double distance) {
        return weight * 1.5 + distance * 0.5 + 10.0;
    }

    @Override
    public String getStrategyName() {
        return "Express Shipping Cost";
    }
}
