package com.mycompany.internationalcourierhub1.observer;

// الواجهة المشتركة (Interface) التي تمثل المستمع أو المراقب في نمط الـ Observer
public interface Observer {
    
    // الدالة التجريدية التي يتم استدعاؤها تلقائياً لإرسال بيانات التحديث (رقم الشحنة والحالة الجديدة) للمراقبين
    void update(String trackingId, String status);
}