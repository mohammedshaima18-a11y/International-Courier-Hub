package com.mycompany.internationalcourierhub1;

import com.mycompany.internationalcourierhub1.factory.Shipment;
import com.mycompany.internationalcourierhub1.factory.ShipmentFactory;
import com.mycompany.internationalcourierhub1.observer.Customer;
import com.mycompany.internationalcourierhub1.observer.ShipmentStatus;
import com.mycompany.internationalcourierhub1.singleton.CourierHubManager;
import com.mycompany.internationalcourierhub1.strategy.ExpressCost;
import com.mycompany.internationalcourierhub1.strategy.InternationalCost;
import com.mycompany.internationalcourierhub1.strategy.ShippingCostStrategy;
import com.mycompany.internationalcourierhub1.strategy.StandardCost;

// النقطة الرئيسية لتشغيل نظام International Courier Hub
public class InternationalCourierHub1 {

    public static void main(String[] args) {

        // Singleton Pattern - مدير واحد فقط للنظام
        CourierHubManager manager = CourierHubManager.getInstance();

        // Factory Pattern - إنشاء أنواع مختلفة من الشحنات
        Shipment s1 = ShipmentFactory.createShipment("standard", "Documents", 2.5);
        Shipment s2 = ShipmentFactory.createShipment("express", "Electronics", 5.0);
        Shipment s3 = ShipmentFactory.createShipment("international",
                "Machinery Parts", 50.0, "Germany");

        // تسجيل الشحنات في النظام
        manager.registerShipment(s1);
        manager.registerShipment(s2);
        manager.registerShipment(s3);

        // Strategy Pattern - تحديد طريقة حساب التكلفة
        ShippingCostStrategy standardCost = new StandardCost();
        ShippingCostStrategy expressCost = new ExpressCost();
        ShippingCostStrategy internationalCost = new InternationalCost();

        // حساب تكلفة كل شحنة حسب استراتيجيتها
        System.out.println("\n--- Cost Calculation (Strategy Pattern) ---");
        double distance = 1000;
        System.out.println(s1.getType() + " cost: $"
                + standardCost.calculateCost(s1.calculateWeight(), distance)
                + " (" + standardCost.getStrategyName() + ")");
        System.out.println(s2.getType() + " cost: $"
                + expressCost.calculateCost(s2.calculateWeight(), distance)
                + " (" + expressCost.getStrategyName() + ")");
        System.out.println(s3.getType() + " cost: $"
                + internationalCost.calculateCost(s3.calculateWeight(), distance)
                + " (" + internationalCost.getStrategyName() + ")");

        // Observer Pattern - تتبع الشحنات وإشعار العملاء
        System.out.println("\n--- Shipment Tracking (Observer Pattern) ---");
        ShipmentStatus tracking1 = new ShipmentStatus("TRK-001");
        ShipmentStatus tracking2 = new ShipmentStatus("TRK-002");

        // تسجيل العملاء لاستقبال الإشعارات
        Customer alice = new Customer("Alice");
        Customer bob = new Customer("Bob");

        tracking1.attach(alice);
        tracking2.attach(bob);
        tracking2.attach(alice);

        // تحديث حالة الشحنات - يشعر العملاء تلقائياً
        tracking1.setStatus("Picked Up");
        tracking2.setStatus("In Transit");
        tracking1.setStatus("Out for Delivery");
        tracking2.setStatus("Delivered");

        // طباعة تقرير النظام
        manager.printReport();
    }
}