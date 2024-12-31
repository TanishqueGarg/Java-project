package com.example.java_project.orderdeliverypanel;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.java_project.DatabaseConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class orderdeliverypanelformController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> ODPbilllist;

    @FXML
    private Button ODPbtndeliveredall;

    @FXML
    private Button ODPbtndofindorders;

    @FXML
    private ListView<String> ODPitemlist;

    @FXML
    private ListView<String> ODPorderidlist;

    @FXML
    private ListView<String> ODPstatuslist;

    @FXML
    private TextField ODPtotalbill;

    @FXML
    private TextField ODPtxtmobile;

    private Connection con;
    private PreparedStatement stmt;

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private Button btnBack;

    @FXML
    void doBack(ActionEvent event) {
        try {
            // Load the admin panel FXML file
            Parent adminPanel = FXMLLoader.load(getClass().getResource("/com/example/java_project/adminpanell/adminpanelView.fxml"));

            // Get the current stage
            Stage currentStage = (Stage) btnBack.getScene().getWindow();

            // Set the admin panel scene
            currentStage.setScene(new Scene(adminPanel));
            currentStage.setTitle("Darjee");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load the Admin Panel.");
        }
    }

    @FXML
    void doFindOrders(ActionEvent event) {
        String mobile = ODPtxtmobile.getText().trim();

        if (mobile.isEmpty()) {
            System.out.println("Please enter a mobile number.");
            return;
        }

        // Clear the existing data in ListViews
        ODPorderidlist.getItems().clear();
        ODPitemlist.getItems().clear();
        ODPbilllist.getItems().clear();
        ODPstatuslist.getItems().clear();

        try {
            String query = "SELECT orderid, dress, bill, rstatus FROM measurements WHERE mobile = ? AND dstatus = 0";
            stmt = con.prepareStatement(query);
            stmt.setString(1, mobile);

            ResultSet rs = stmt.executeQuery();

            int totalBill = 0; // To calculate total bill amount

            while (rs.next()) {
                String orderId = rs.getString("orderid");
                String item = rs.getString("dress");
                int bill = rs.getInt("bill");
                int rstatus = rs.getInt("rstatus");

                // Add data to respective ListViews
                ODPorderidlist.getItems().add(orderId);
                ODPitemlist.getItems().add(item);
                ODPbilllist.getItems().add(String.valueOf(bill));
                ODPstatuslist.getItems().add(rstatus == 1 ? "Ready" : "Pending");

                // Add to total bill
                totalBill += bill;
            }

            // Display the total bill in the TextField
            ODPtotalbill.setText(String.valueOf(totalBill));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching orders for mobile: " + mobile);
        }
    }

    @FXML
    void doDeliveredAll(ActionEvent event) {
        try {
            ObservableList<String> orderIds = ODPorderidlist.getItems();
            ObservableList<String> statuses = ODPstatuslist.getItems();

            for (int i = 0; i < orderIds.size(); i++) {
                String orderId = orderIds.get(i);
                String status = statuses.get(i);

                // Only update if the status is "Ready"
                if ("Ready".equals(status)) {
                    String query = "UPDATE measurements SET dstatus = 1 WHERE orderid = ?";
                    stmt = con.prepareStatement(query);
                    stmt.setString(1, orderId);
                    stmt.executeUpdate();
                }
            }

            // Refresh the view by removing all items and recalculating the total bill
            doFindOrders(null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error updating delivery status for all items.");
        }
    }

    @FXML
    void initialize() {
        assert ODPbilllist != null : "fx:id=\"ODPbilllist\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPbtndeliveredall != null : "fx:id=\"ODPbtndeliveredall\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPbtndofindorders != null : "fx:id=\"ODPbtndofindorders\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPitemlist != null : "fx:id=\"ODPitemlist\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPorderidlist != null : "fx:id=\"ODPorderidlist\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPstatuslist != null : "fx:id=\"ODPstatuslist\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPtotalbill != null : "fx:id=\"ODPtotalbill\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPtxtmobile != null : "fx:id=\"ODPtxtmobile\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";

        // Initialize database connection
        con = DatabaseConnection.doConnect();
        if (con == null) {
            System.out.println("Database connection failed.");
        } else {
            System.out.println("Database connection established.");
        }

        // Add double-click event listeners to all columns
        addDoubleClickListener(ODPorderidlist);
        addDoubleClickListener(ODPitemlist);
        addDoubleClickListener(ODPbilllist);
        addDoubleClickListener(ODPstatuslist);
    }

    private void addDoubleClickListener(ListView<String> listView) {
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedOrderId = ODPorderidlist.getItems().get(selectedIndex);
                    handleDoubleClick(selectedOrderId);
                }
            }
        });
    }

    private void handleDoubleClick(String orderId) {
        try {
            // Find the selected order's index
            int selectedIndex = ODPorderidlist.getItems().indexOf(orderId);

            // Get the corresponding status
            String status = ODPstatuslist.getItems().get(selectedIndex);

            // Only update if the status is "Ready"
            if ("Ready".equals(status)) {
                String query = "UPDATE measurements SET dstatus = 1 WHERE orderid = ?";
                stmt = con.prepareStatement(query);
                stmt.setString(1, orderId);
                stmt.executeUpdate();

                // Refresh the view by removing the selected item and recalculating the total bill
                doFindOrders(null);
            } else {
                System.out.println("Order ID " + orderId + " cannot be delivered. Status is not 'Ready'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error updating delivery status for order ID: " + orderId);
        }
    }
}
