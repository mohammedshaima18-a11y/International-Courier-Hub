package com.mycompany.internationalcourierhub1.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// الكلاس الرئيسي لتشغيل التطبيق والذي يرث من كلاس Application الخاص بـ JavaFX
public class MainApp extends Application {

    // دالة بدء التشغيل وبناء الواجهة الرسومية (GUI Life Cycle)
    @Override
    public void start(Stage stage) throws Exception {
        // تحميل ملف التصميم الخارجي (main-view.fxml) الذي يحتوي على هيكل الواجهة الرئيسية
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-view.fxml"));
        Parent root = loader.load();
        
        // إنشاء المشهد (Scene) وتحديد الأبعاد الافتراضية للعرض والارتفاع (1024x680)
        Scene scene = new Scene(root, 1024, 680);
        
        // إعداد خصائص نافذة البرنامج (العنوان، المشهد، والحد الأدنى لحجم النافذة)
        stage.setTitle("International Courier Hub");
        stage.setScene(scene);
        stage.setMinWidth(800); // منع تصغير النافذة لأقل من 800 عرضاً للحفاظ على اتساق الواجهة
        stage.setMinHeight(600); // منع تصغير النافذة لأقل من 600 ارتفاعاً
        
        // إظهار نافذة البرنامج الرسمية على الشاشة
        stage.show();
    }

    // دالة التشغيل القياسية لـ Java (Main Method) والتي تقوم باستدعاء دالة launch لتهيئة JavaFX
    public static void main(String[] args) {
        launch(args);
    }
}