package com.mycompany.internationalcourierhub1.strategy;

// استراتيجية الشحن الدولي - أعلى تكلفة مع رسوم إضافية
public class InternationalCost implements ShippingCostStrategy {

    @Override
    public double calculateCost(double weight, double distance) {
        // تكلفة = (الوزن × 3.0) + (المسافة × 1.2) + رسوم دولية
        return weight * 3.0 + distance * 1.2 + 25.0;
    }

    @Override
    public String getStrategyName() {
        return "International Shipping Cost";
    }
}