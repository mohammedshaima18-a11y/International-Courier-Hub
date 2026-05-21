package com.mycompany.internationalcourierhub1.strategy;

// Strategy Pattern - واجهة تحديد طريقة حساب تكلفة الشحن
public interface ShippingCostStrategy {
    // حساب التكلفةع وزن ومسافة ً 
    double calculateCost(double weight, double distance);
    // إرجاع اسم الاستراتيجية
    String getStrategyName();
}