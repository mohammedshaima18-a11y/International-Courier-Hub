package com.mycompany.internationalcourierhub1.strategy;

// استراتيجية الشحن السريع - أعلى تكلفة
public class ExpressCost implements ShippingCostStrategy {

    @Override
    public double calculateCost(double weight, double distance) {
        // تكلفة = (الوزن × 1.5) + (المسافة × 0.5) + رسوم ثابتة
        return weight * 1.5 + distance * 0.5 + 10.0;
    }

    @Override
    public String getStrategyName() {
        return "Express Shipping Cost";
    }
}