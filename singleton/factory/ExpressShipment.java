package com.mycompany.internationalcourierhub1.factory;

// كلاس يمثل الشحنة السريعة ويرث من الواجهة المشتركة Shipment كجزء من نمط الـ Factory
public class ExpressShipment implements Shipment {

    private final String description; // متغير لتخزين وصف الشحنة
    private final double weight;      // متغير لتخزين وزن الشحنة

    // باني الكلاس (Constructor) لتهيئة بيانات الشحنة عند إنشائها
    public ExpressShipment(String description, double weight) {
        this.description = description;
        this.weight = weight;
    }

    // دالة تعيد نوع الشحنة (سريعة)
    @Override
    public String getType() {
        return "Express";
    }

    // دالة تعيد الوصف الخاص بالشحنة
    @Override
    public String getDescription() {
        return description;
    }

    // دالة تعيد وزن الشحنة لحساب التكلفة لاحقاً
    @Override
    public double calculateWeight() {
        return weight;
    }
}