package com.mycompany.internationalcourierhub1.observer;

import java.util.ArrayList;
import java.util.List;

// كلاس يمثل الموضوع أو الهدف المراد مراقبته (Subject) في نمط الـ Observer لإدارة وتحديث حالات التتبع
public class ShipmentStatus {

    private final String trackingId;   // معرف التتبع الفريد الخاص بالشحنة
    private String status;              // حالة الشحنة الحالية (مثال: Pending, Shipped...)
    private final List<Observer> observers; // قائمة برمجية لتخزين جميع المراقبين (الزبائن) المشتركين في تتبع هذه الشحنة

    // باني الكلاس (Constructor) لتهيئة كائن التتبع وتحديد الحالة الافتراضية "Pending"
    public ShipmentStatus(String trackingId) {
        this.trackingId = trackingId;
        this.status = "Pending";
        this.observers = new ArrayList<>(); // تهيئة مصفوفة ديناميكية فارغة للمستمعين
    }

    // دالة إضافة وتسجيل مراقب (العميل) جديد في قائمة المتابعين لهذه الشحنة (Attach)
    public void attach(Observer observer) {
        observers.add(observer);
    }

    // دالة حذف وإلغاء تسجيل مراقب من قائمة المتابعين (Detach)
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    // دالة تحديث حالة الشحنة؛ والضغط عليها يقوم فوراً باستدعاء دالة التنبيه الآلي (Notify)
    public void setStatus(String newStatus) {
        this.status = newStatus;
        notifyObservers(); // إطلاق إشعار فوري لجميع المشتركين لإعلامهم بالحالة الجديدة
    }

    // دالة جلب (Getter) لاسترجاع الحالة الحالية للشحنة
    public String getStatus() {
        return status;
    }

    // دالة جلب (Getter) لاسترجاع رقم تتبع الشحنة
    public String getTrackingId() {
        return trackingId;
    }

    // الدالة الداخلية المسؤولة عن المرور على كافة المراقبين المندمجين في القائمة وتنبيههم (Notify)
    private void notifyObservers() {
        // حلقة تكرار (For-each) تمر على كل مراقب مسجل وتستدعي دالة التحديث الخاصة به
        for (Observer observer : observers) {
            observer.update(trackingId, status);
        }
    }
}