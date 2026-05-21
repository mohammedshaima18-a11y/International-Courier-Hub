package com.mycompany.internationalcourierhub1.factory;

// كلاس يمثل الشحنة العادية (المحلية) ويرث من الواجهة Shipment كجزء من نمط الـ Factory
public class StandardShipment implements Shipment {

    private final String description; // متغير لتخزين وصف الشحنة
    private final double weight;      // متغير لتخزين وزن الشحنة

    // باني الكلاس (Constructor) لتهيئة بيانات الشحنة العادية عند إنشائها
    public StandardShipment(String description, double weight) {
        this.description = description;
        this.weight = weight;
    }

    // دالة تعيد نوع الشحنة (عادية)
    @Override
    public String getType() {
        return "Standard";
    }

    // دالة تعيد الوصف الخاص بالشحنة
    @Override
    public String getDescription() {
        return description;
    }

    // دالة تعيد وزن الشحنة لاستخدامه في حساب التكلفة العادية
    @Override
    public double calculateWeight() {
        return weight;
    }
}