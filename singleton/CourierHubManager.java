package com.mycompany.internationalcourierhub1.singleton;

import com.mycompany.internationalcourierhub1.factory.Shipment;
import java.util.ArrayList;
import java.util.List;

// كلاس إدارة النظام المركزي المطبق لنمط الـ Singleton لضمان وجود نسخة واحدة موحدة في الذاكرة
public class CourierHubManager {

    // متغير ثابت (Static) يحمل النسخة الوحيدة من هذا الكلاس بداخل الذاكرة
    private static CourierHubManager instance;
    private final List<String> logs;    // قائمة لتخزين سجل الأحداث والعمليات للنظام بالكامل
    private int totalShipments;         // عداد إجمالي للشحنات المسجلة

    // مشيد الكلاس (Constructor) معرف كـ private لمنع أي كلاس خارجي من إنشاء كائن جديد باستخدام الكلمة new
    private CourierHubManager() {
        logs = new ArrayList<>();
        totalShipments = 0;
    }

    // دالة الوصول العامة والوحيدة للنسخة؛ مع ميزة synchronized لضمان الحماية عند استدعائها من خيوط متعددة (Thread-Safe)
    public static synchronized CourierHubManager getInstance() {
        // التحقق الذكي (Lazy Initialization): إذا لم تكن النسخة موجودة مسبقاً، يتم إنشاؤها لأول مرة فقط
        if (instance == null) {
            instance = new CourierHubManager();
        }
        return instance; // إرجاع النسخة الموحدة والثابتة دائماً
    }

    // دالة مركزية لتسجيل الشحنات الجديدة بداخل النظام وتحديث السجلات الحية
    public void registerShipment(Shipment shipment) {
        totalShipments++;
        String log = "Shipment #" + totalShipments + " [" + shipment.getType()
                + "] registered: " + shipment.getDescription();
        logs.add(log); // إضافة الحدث إلى قائمة السجلات المركزية
        System.out.println("[Manager] " + log);
    }

    // دالة عامة لتدوين وتوثيق أي حدث فرعي آخر يحدث داخل النظام
    public void logEvent(String event) {
        logs.add(event);
        System.out.println("[Manager] " + event);
    }

    // دالة لطباعة تقرير شامل ومختصر حول حالة النظام والعدادات بداخل الكونسول
    public void printReport() {
        System.out.println("\n===== Courier Hub Report =====");
        System.out.println("Total shipments: " + totalShipments);
        System.out.println("Events logged: " + logs.size());
        System.out.println("==============================\n");
    }
}