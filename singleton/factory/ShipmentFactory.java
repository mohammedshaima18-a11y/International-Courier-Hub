package com.mycompany.internationalcourierhub1.factory;

// كلاس المصنع المركزي المسؤل عن إدارة وإنشاء كائنات الشحنات دون كشف تفاصيل الإنشاء للواجهات
public class ShipmentFactory {

    // دالة إنشاء شحنة محلية (تستدعي الدالة الكبرى وتمرر الدولة بقيمة null تلقائياً) - Method Overloading
    public static Shipment createShipment(String type, String description, double weight) {
        return createShipment(type, description, weight, null);
    }

    // الدالة الرئيسية لإنشاء الشحنات بناءً على النوع الممرر (تتحكم في بناء الكائنات المناسبة)
    public static Shipment createShipment(String type, String description,
                                          double weight, String destinationCountry) {
        // فحص نوع الشحنة بعد تحويل النص لأحرف صغيرة لتفادي أخطاء حالة الأحرف
        switch (type.toLowerCase()) {
            case "standard":
                // إنشاء وإرجاع كائن شحنة عادية
                return new StandardShipment(description, weight);
                
            case "express":
                // إنشاء وإرجاع كائن شحنة سريعة
                return new ExpressShipment(description, weight);
                
            case "international":
                // التحقق من إدخال دولة الوجهة للشحنات الدولية، وإطلاق استثناء (خطأ) في حال عدم إدخالها
                if (destinationCountry == null || destinationCountry.isBlank()) {
                    throw new IllegalArgumentException(
                            "Destination country is required for international shipments");
                }
                // إنشاء وإرجاع كائن شحنة دولية مع تمرير دولة الوجهة
                return new InternationalShipment(description, weight, destinationCountry);
                
            default:
                // إطلاق استثناء في حال تمرير نوع شحنة غير معرف داخل النظام
                throw new IllegalArgumentException("Unknown shipment type: " + type);
        }
    }
}