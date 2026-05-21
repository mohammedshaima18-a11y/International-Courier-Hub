package com.mycompany.internationalcourierhub1.gui;

import com.mycompany.internationalcourierhub1.factory.Shipment;
import com.mycompany.internationalcourierhub1.factory.ShipmentFactory;
import com.mycompany.internationalcourierhub1.observer.Customer;
import com.mycompany.internationalcourierhub1.observer.ShipmentStatus;
import com.mycompany.internationalcourierhub1.singleton.CourierHubManager;
import com.mycompany.internationalcourierhub1.strategy.ExpressCost;
import com.mycompany.internationalcourierhub1.strategy.InternationalCost;
import com.mycompany.internationalcourierhub1.strategy.ShippingCostStrategy;
import com.mycompany.internationalcourierhub1.strategy.StandardCost;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

// كلاس التحكم الرئيسي للواجهات والذي يربط أحداث العناصر (UI Components) بالعمليات البرمجية
public class MainController implements Initializable {

    // عناصر واجهة لوحة التحكم (Dashboard UI Components)
    @FXML private Label statusLabel;
    @FXML private Label totalShipmentsLabel;
    @FXML private Label totalEventsLabel;
    @FXML private Label totalTrackingLabel;
    @FXML private Label systemStatusLabel;
    @FXML private TextArea reportArea;
    @FXML private Button reportBtn;

    // عناصر واجهة إنشاء الشحنات (Create Shipment UI Components)
    @FXML private ComboBox<String> shipmentTypeCombo;
    @FXML private TextField descriptionField;
    @FXML private TextField weightField;
    @FXML private TextField destinationField;
    @FXML private TextArea shipmentLogArea;

    // عناصر واجهة تتبع الشحنات ونظام المراقب (Tracking & Observer UI Components)
    @FXML private TextField trackingIdField;
    @FXML private TextField customerNameField;
    @FXML private ComboBox<String> statusCombo;
    @FXML private TextArea trackingLogArea;

    // عناصر واجهة حساب تكلفة الشحن ونمط الاستراتيجية (Cost Strategy UI Components)
    @FXML private TextField costWeightField;
    @FXML private TextField costDistanceField;
    @FXML private ComboBox<String> strategyCombo;
    @FXML private Label costResultLabel;
    @FXML private TextArea costLogArea;

    // حاويات الألواح البرمجية لتمكين نظام التنقل متعدد الشاشات في واجهة واحدة
    @FXML private VBox dashboardPanel;
    @FXML private VBox createPanel;
    @FXML private VBox trackPanel;
    @FXML private VBox costPanel;

    // أزرار القائمة الجانبية للتنقل (Sidebar Navigation)
    @FXML private Button navDashboard;
    @FXML private Button navCreate;
    @FXML private Button navTrack;
    @FXML private Button navCost;

    // تطبيق نمط الـ Singleton: استدعاء النسخة الوحيدة من مدير النظام
    private final CourierHubManager manager = CourierHubManager.getInstance();
    
    // هياكل بيانات محلية لتخزين ومطابقة الشحنات والعملاء في الذاكرة أثناء التشغيل
    private final Map<String, ShipmentStatus> trackingMap = new HashMap<>();
    private final Map<String, Customer> customerMap = new HashMap<>();
    
    // عدادات إحصائية لعرض البيانات الحية في لوحة التحكم
    private int shipmentCounter;
    private int trackingCounter;

    // دالة التهيئة المبدئية عند تشغيل الواجهة لأول مرة وضبط قيم القوائم المنسدلة
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shipmentTypeCombo.getItems().addAll("Standard", "Express", "International");
        shipmentTypeCombo.getSelectionModel().selectFirst();
        statusCombo.getItems().addAll("Picked Up", "In Transit", "Out for Delivery", "Delivered");
        statusCombo.getSelectionModel().selectFirst();
        strategyCombo.getItems().addAll("Standard", "Express", "International");
        strategyCombo.getSelectionModel().selectFirst();
        statusLabel.setText("● System Ready");
        systemStatusLabel.setText("Online");
        updateDashboard();
        showPanel(dashboardPanel); // عرض لوحة التحكم كشاشة افتراضية عند الفتح
        setActiveNav(navDashboard); // تمييز زر لوحة التحكم في القائمة الجانبية
    }

    // دالة داخلية للتحكم في ظهور وإخفاء الألواح (الشاشات) بشكل ديناميكي
    private void showPanel(VBox panel) {
        dashboardPanel.setVisible(false);
        createPanel.setVisible(false);
        trackPanel.setVisible(false);
        costPanel.setVisible(false);
        dashboardPanel.setManaged(false);
        createPanel.setManaged(false);
        trackPanel.setManaged(false);
        costPanel.setManaged(false);
        panel.setVisible(true);
        panel.setManaged(true);
    }

    // دالة لتغيير مظهر الزر النشط حالياً في القائمة الجانبية باستخدام الـ CSS
    private void setActiveNav(Button active) {
        navDashboard.getStyleClass().remove("active");
        navCreate.getStyleClass().remove("active");
        navTrack.getStyleClass().remove("active");
        navCost.getStyleClass().remove("active");
        active.getStyleClass().add("active");
    }

    // أحداث أزرار التنقل (Navigation Actions)
    @FXML
    void showDashboard(ActionEvent event) {
        showPanel(dashboardPanel);
        setActiveNav(navDashboard);
        updateDashboard();
    }

    @FXML
    void showCreateShipment(ActionEvent event) {
        showPanel(createPanel);
        setActiveNav(navCreate);
    }

    @FXML
    void showTrackShipment(ActionEvent event) {
        showPanel(trackPanel);
        setActiveNav(navTrack);
    }

    @FXML
    void showCalculateCost(ActionEvent event) {
        showPanel(costPanel);
        setActiveNav(navCost);
    }

    // حدث توليد وإصدار التقارير الإحصائية للنظام
    @FXML
    void onReport(ActionEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("================================\n");
        sb.append("   COURIER HUB SYSTEM REPORT\n");
        sb.append("================================\n\n");
        sb.append("Total shipments created: ").append(shipmentCounter).append("\n");
        sb.append("Total events tracked:   ").append(trackingCounter).append("\n");
        sb.append("Active trackings:       ").append(trackingMap.size()).append("\n");
        sb.append("Registered customers:   ").append(customerMap.size()).append("\n\n");
        sb.append("================================\n");
        reportArea.setText(sb.toString());
        manager.logEvent("Report generated"); // تدوين الحدث في الـ Singleton الموحد
    }

    // حدث إنشاء شحنة جديدة (يتصل مباشرة بنمط الـ Factory Method)
    @FXML
    void onCreateShipment(ActionEvent event) {
        try {
            String type = shipmentTypeCombo.getValue();
            String desc = descriptionField.getText().trim();
            String weightStr = weightField.getText().trim();

            // الفحوصات الأمنية والمنطقية لمدخلات المستخدم البرمجية (Validation)
            if (desc.isEmpty() || weightStr.isEmpty()) {
                shipmentLogArea.appendText("⛔ Fill description and weight fields\n");
                return;
            }

            double weight = Double.parseDouble(weightStr);

            if (weight <= 0) {
                shipmentLogArea.appendText("⛔ Weight must be positive\n");
                return;
            }

            String dest = destinationField.getText().trim();

            if ("International".equals(type) && (dest == null || dest.isEmpty())) {
                shipmentLogArea.appendText("⛔ Destination required for International shipment\n");
                return;
            }

            // سحب كائن الشحنة من المصنع ديناميكياً بدون تشييد يدوي عبر الـ UI
            Shipment shipment = ShipmentFactory.createShipment(type, desc, weight,
                    "International".equals(type) ? dest : null);

            shipmentCounter++;
            manager.registerShipment(shipment); // تسجيل الشحنة الجديدة بداخل سجل الـ Singleton
            shipmentLogArea.appendText("✅ " + shipment.getType() + " | "
                    + shipment.getDescription() + " (" + weight + " kg)\n");

            // تنظيف الحقول لتسهيل الإدخال التالي
            descriptionField.clear();
            weightField.clear();
            destinationField.clear();
            updateDashboard();
        } catch (NumberFormatException e) {
            shipmentLogArea.appendText("⛔ Invalid weight value\n");
        } catch (IllegalArgumentException e) {
            shipmentLogArea.appendText("⛔ " + e.getMessage() + "\n");
        }
    }

    // زر لتفريغ حقول واجهة الإنشاء
    @FXML
    void onClearShipment(ActionEvent event) {
        descriptionField.clear();
        weightField.clear();
        destinationField.clear();
        shipmentLogArea.appendText("🧹 Fields cleared\n");
    }

    // إضافة معرف شحنة جديد يبدأ نظام التتبع الفوري
    @FXML
    void onAddTracking(ActionEvent event) {
        String id = trackingIdField.getText().trim();
        if (id.isEmpty()) {
            trackingLogArea.appendText("⛔ Enter a tracking ID\n");
            return;
        }
        if (trackingMap.containsKey(id)) {
            trackingLogArea.appendText("⛔ Tracking ID " + id + " already exists\n");
            return;
        }
        // إنشاء الكائن المراد مراقبته (Subject) في نمط الـ Observer
        ShipmentStatus status = new ShipmentStatus(id);
        trackingMap.put(id, status);
        trackingLogArea.appendText("📌 Tracking created for: " + id + "\n");
        trackingIdField.clear();
        updateDashboard();
    }

    // تسجيل واشتراك العميل لمراقبة شحنة محددة (نمط الـ Observer - Attach)
    @FXML
    void onSubscribe(ActionEvent event) {
        String id = trackingIdField.getText().trim();
        String name = customerNameField.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            trackingLogArea.appendText("⛔ Enter tracking ID and customer name\n");
            return;
        }

        ShipmentStatus status = trackingMap.get(id);
        if (status == null) {
            trackingLogArea.appendText("⛔ Tracking ID " + id + " not found. Add it first.\n");
            return;
        }

        // إنشاء كائن العميل كمراقب (Observer) وضمه لقائمة المستمعين لهوية التتبع الحالية
        Customer customer = customerMap.computeIfAbsent(name, Customer::new);
        status.attach(customer);
        trackingLogArea.appendText("🔔 " + name + " subscribed to " + id + "\n");
        customerNameField.clear();
    }

    // تحديث حالة الشحنة، والضغط هنا يطلق إشعارات فورية لكل المشتركين (نمط الـ Observer - Notify)
    @FXML
    void onUpdateStatus(ActionEvent event) {
        String id = trackingIdField.getText().trim();
        if (id.isEmpty()) {
            trackingLogArea.appendText("⛔ Enter a tracking ID\n");
            return;
        }

        ShipmentStatus status = trackingMap.get(id);
        if (status == null) {
            trackingLogArea.appendText("⛔ Tracking ID " + id + " not found\n");
            return;
        }

        String newStatus = statusCombo.getValue();
        status.setStatus(newStatus); // تعيين الحالة الجديدة مما يطلق دالة التنبيه الآلي داخلياً للمراقبين
        trackingCounter++;
        trackingLogArea.appendText("🔄 " + id + " → " + newStatus + "\n");
        updateDashboard();
    }

    // حساب التسعير التكافلي للشحن بالاعتماد بالكامل على تفويض خوارزميات نمط الـ Strategy ديناميكياً
    @FXML
    void onCalculateCost(ActionEvent event) {
        try {
            String weightStr = costWeightField.getText().trim();
            String distStr = costDistanceField.getText().trim();

            if (weightStr.isEmpty() || distStr.isEmpty()) {
                costLogArea.appendText("⛔ Fill weight and distance fields\n");
                return;
            }

            double weight = Double.parseDouble(weightStr);
            double distance = Double.parseDouble(distStr);

            if (weight <= 0 || distance <= 0) {
                costLogArea.appendText("⛔ Values must be positive\n");
                return;
            }

            String strategyType = strategyCombo.getValue();

            // بوليمورفيزم الاستراتيجية: تحديد ملف الحساب دون استخدام جمل شرطية لحساب التكلفة الفعلي
            ShippingCostStrategy strategy;
            switch (strategyType) {
                case "Standard" -> strategy = new StandardCost();
                case "Express" -> strategy = new ExpressCost();
                case "International" -> strategy = new InternationalCost();
                default -> throw new IllegalArgumentException("Unknown strategy");
            }

            // تنفيذ الحساب المستهدف بناءً على الكائن المختار حركياً
            double cost = strategy.calculateCost(weight, distance);
            String result = String.format("$%.2f", cost);
            costResultLabel.setText("💰 Total: " + result);
            costLogArea.appendText("📊 " + strategyType + " | " + weight + " kg, "
                    + distance + " km → " + result + "\n");
            manager.logEvent("Cost calculated: " + result + " (" + strategy.getStrategyName() + ")");
        } catch (NumberFormatException e) {
            costLogArea.appendText("⛔ Invalid number format\n");
        }
    }

    // دالة لتحديث قيم واجهة الـ Dashboard حياً عند حدوث أي تعديل في العدادات
    private void updateDashboard() {
        totalShipmentsLabel.setText(String.valueOf(shipmentCounter));
        totalEventsLabel.setText(String.valueOf(trackingCounter));
        totalTrackingLabel.setText(String.valueOf(trackingMap.size()));
    }
}