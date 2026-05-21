package com.mycompany.internationalcourierhub1.factory;

// الواجهة المشتركة (Interface) التي توحد خصائص جميع أنواع الشحنات في نظام المصنع
public interface Shipment {
    
    String getType();           // دالة مجردة لإجبار كل شحنة على تحديد نوعها (عادية، سريعة، دولية)
    
    String getDescription();    // دالة مجردة لإجبار كل شحنة على إرجاع الوصف والتفاصيل الخاصة بها
    
    double calculateWeight();   // دالة مجردة لإجبار كل شحنة على إرجاع وزنها لتسهيل الحساب المالي
}