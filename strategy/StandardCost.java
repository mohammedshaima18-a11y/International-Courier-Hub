package com.mycompany.internationalcourierhub1.strategy;

// استراتيجية الشحن العادي - تكلفة منخفضة
public class StandardCost implements ShippingCostStrategy {

    @Override
    public double calculateCost(double weight, double distance) {
        // تكلفة = (الوزن × 0.5) + (المسافة × 0.1)
        return weight * 0.5 + distance * 0.1;
    }

    @Override
    public String getStrategyName() {
        return "Standard Shipping Cost";
    }
}