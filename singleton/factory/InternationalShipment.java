package com.mycompany.internationalcourierhub1.factory;

// كلاس يمثل الشحنة الدولية ويرث من الواجهة Shipment كجزء من تطبيق نمط الـ Factory
public class InternationalShipment implements Shipment {

    private final String description;        // متغير لتخزين وصف الشحنة
    private final double weight;             // متغير لتخزين وزن الشحنة
    private final String destinationCountry; // متغير إضافي خاص بالشحنات الدولية لتخزين الدولة المستهدفة

    // باني الكلاس (Constructor) لتهيئة بيانات الشحنة الدولية بما فيها دولة الوجهة
    public InternationalShipment(String description, double weight, String destinationCountry) {
        this.description = description;
        this.weight = weight;
        this.destinationCountry = destinationCountry;
    }

    // دالة تعيد نوع الشحنة (دولية)
    @Override
    public String getType() {
        return "International";
    }

    // دالة دمج تدمج الوصف مع دولة الوجهة لتعطي تفاصيل كاملة عن الشحنة
    @Override
    public String getDescription() {
        return description + " to " + destinationCountry;
    }

    // دالة تعيد وزن الشحنة لحساب التكلفة لاحقاً
    @Override
    public double calculateWeight() {
        return weight;
    }

    // دالة جلب (Getter) خاصة لاسترجاع اسم دولة الوجهة عند الحاجة إليها في النظام
    public String getDestinationCountry() {
        return destinationCountry;
    }
}