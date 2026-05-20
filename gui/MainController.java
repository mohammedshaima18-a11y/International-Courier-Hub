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

public class MainController implements Initializable {

    @FXML private Label statusLabel;
    @FXML private Label totalShipmentsLabel;
    @FXML private Label totalEventsLabel;
    @FXML private Label totalTrackingLabel;
    @FXML private Label systemStatusLabel;
    @FXML private TextArea reportArea;
    @FXML private Button reportBtn;

    @FXML private ComboBox<String> shipmentTypeCombo;
    @FXML private TextField descriptionField;
    @FXML private TextField weightField;
    @FXML private TextField destinationField;
    @FXML private TextArea shipmentLogArea;

    @FXML private TextField trackingIdField;
    @FXML private TextField customerNameField;
    @FXML private ComboBox<String> statusCombo;
    @FXML private TextArea trackingLogArea;

    @FXML private TextField costWeightField;
    @FXML private TextField costDistanceField;
    @FXML private ComboBox<String> strategyCombo;
    @FXML private Label costResultLabel;
    @FXML private TextArea costLogArea;

    @FXML private VBox dashboardPanel;
    @FXML private VBox createPanel;
    @FXML private VBox trackPanel;
    @FXML private VBox costPanel;

    @FXML private Button navDashboard;
    @FXML private Button navCreate;
    @FXML private Button navTrack;
    @FXML private Button navCost;

    private final CourierHubManager manager = CourierHubManager.getInstance();
    private final Map<String, ShipmentStatus> trackingMap = new HashMap<>();
    private final Map<String, Customer> customerMap = new HashMap<>();
    private int shipmentCounter;
    private int trackingCounter;

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
        showPanel(dashboardPanel);
        setActiveNav(navDashboard);
    }

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

    private void setActiveNav(Button active) {
        navDashboard.getStyleClass().remove("active");
        navCreate.getStyleClass().remove("active");
        navTrack.getStyleClass().remove("active");
        navCost.getStyleClass().remove("active");
        active.getStyleClass().add("active");
    }

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
        manager.logEvent("Report generated");
    }

    @FXML
    void onCreateShipment(ActionEvent event) {
        try {
            String type = shipmentTypeCombo.getValue();
            String desc = descriptionField.getText().trim();
            String weightStr = weightField.getText().trim();

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

            Shipment shipment = ShipmentFactory.createShipment(type, desc, weight,
                    "International".equals(type) ? dest : null);

            shipmentCounter++;
            manager.registerShipment(shipment);
            shipmentLogArea.appendText("✅ " + shipment.getType() + " | "
                    + shipment.getDescription() + " (" + weight + " kg)\n");

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

    @FXML
    void onClearShipment(ActionEvent event) {
        descriptionField.clear();
        weightField.clear();
        destinationField.clear();
        shipmentLogArea.appendText("🧹 Fields cleared\n");
    }

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
        ShipmentStatus status = new ShipmentStatus(id);
        trackingMap.put(id, status);
        trackingLogArea.appendText("📌 Tracking created for: " + id + "\n");
        trackingIdField.clear();
        updateDashboard();
    }

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

        Customer customer = customerMap.computeIfAbsent(name, Customer::new);
        status.attach(customer);
        trackingLogArea.appendText("🔔 " + name + " subscribed to " + id + "\n");
        customerNameField.clear();
    }

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
        status.setStatus(newStatus);
        trackingCounter++;
        trackingLogArea.appendText("🔄 " + id + " → " + newStatus + "\n");
        updateDashboard();
    }

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

            ShippingCostStrategy strategy;
            switch (strategyType) {
                case "Standard" -> strategy = new StandardCost();
                case "Express" -> strategy = new ExpressCost();
                case "International" -> strategy = new InternationalCost();
                default -> throw new IllegalArgumentException("Unknown strategy");
            }

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

    private void updateDashboard() {
        totalShipmentsLabel.setText(String.valueOf(shipmentCounter));
        totalEventsLabel.setText(String.valueOf(trackingCounter));
        totalTrackingLabel.setText(String.valueOf(trackingMap.size()));
    }
}
