package com.mycompany.internationalcourierhub1.observer;

// كلاس يمثل العميل الذي يرث من واجهة Observer لكي يستمع ويستقبل تحديثات حالة الشحنة
public class Customer implements Observer {

    private final String name; // متغير لتخزين اسم العميل المتابع للشحنة

    // باني الكلاس (Constructor) لتهيئة اسم العميل عند بدء الاشتراك في التتبع
    public Customer(String name) {
        this.name = name;
    }

    // الدالة الفعلية المستدعاة تلقائياً (Callback) من الـ Subject لإبلاغ العميل بتحديث الحالة الحية
    @Override
    public void update(String trackingId, String status) {
        // طباعة التنبيه الفوري في الكونسول/السجل بمجرد تغير حالة الشحنة
        System.out.println("[Notification for " + name + "] "
                + "Your shipment " + trackingId + " is now: " + status);
    }

    // دالة جلب (Getter) لاسترجاع اسم العميل عند الحاجة للتحقق منه في الواجهة
    public String getName() {
        return name;
    }
}