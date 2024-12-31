package com.example.java_project.measurementsexplorer;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.java_project.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class measurementsexplorerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button MEbtnFetch;

    @FXML
    private TextField MEcustomermob;

    @FXML
    private ComboBox<String> MEorderstatus;

    @FXML
    private TableView<OrderDetails> MEtblOrders;

    @FXML
    private ComboBox<String> MEworkers;

    private Connection con;
    private PreparedStatement stmt;

    @FXML
    void MEdoFetch(ActionEvent event) {
        ObservableList<OrderDetails> orderList = FXCollections.observableArrayList();
        StringBuilder query = new StringBuilder("SELECT orderid, mobile, dress, bill, worker, rstatus FROM measurements WHERE 1=1");

        // Add filters based on inputs
        if (!MEcustomermob.getText().isEmpty()) {
            query.append(" AND mobile = ?");
        }
        if (MEorderstatus.getValue() != null && !MEorderstatus.getValue().equals("All")) {
            query.append(" AND rstatus = ?");
        }
        if (MEworkers.getValue() != null && !MEworkers.getValue().isEmpty()) {
            query.append(" AND worker = ?");
        }

        try {
            stmt = con.prepareStatement(query.toString());

            int paramIndex = 1;

            // Set parameters for filters
            if (!MEcustomermob.getText().isEmpty()) {
                stmt.setString(paramIndex++, MEcustomermob.getText());
            }
            if (MEorderstatus.getValue() != null && !MEorderstatus.getValue().equals("All")) {
                stmt.setString(paramIndex++, MEorderstatus.getValue());
            }
            if (MEworkers.getValue() != null && !MEworkers.getValue().isEmpty()) {
                stmt.setString(paramIndex++, MEworkers.getValue());
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderid");
                String mobile = rs.getString("mobile");
                String dress = rs.getString("dress");
                int bill = rs.getInt("bill");
                String worker = rs.getString("worker");
                String rstatus = rs.getString("rstatus");

                orderList.add(new OrderDetails(orderId, mobile, dress, bill, worker, rstatus));
            }

            MEtblOrders.setItems(orderList);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching order details.");
        }
    }

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
    void MEdoClear(ActionEvent event) {
        MEorderstatus.getSelectionModel().clearSelection(); // Clear selected order status
        MEworkers.getSelectionModel().clearSelection();    // Clear selected worker
        MEcustomermob.clear();                             // Clear customer mobile text field
        MEtblOrders.getItems().clear();                    // Clear the table view
    }


    @FXML
    void initialize() {
        assert MEbtnFetch != null : "fx:id=\"MEbtnFetch\" was not injected: check your FXML file 'measurementsexplorerView.fxml'.";
        assert MEcustomermob != null : "fx:id=\"MEcustomermob\" was not injected: check your FXML file 'measurementsexplorerView.fxml'.";
        assert MEorderstatus != null : "fx:id=\"MEorderstatus\" was not injected: check your FXML file 'measurementsexplorerView.fxml'.";
        assert MEtblOrders != null : "fx:id=\"MEtblOrders\" was not injected: check your FXML file 'measurementsexplorerView.fxml'.";
        assert MEworkers != null : "fx:id=\"MEworkers\" was not injected: check your FXML file 'measurementsexplorerView.fxml'.";

        con = DatabaseConnection.doConnect();
        if (con == null) {
            System.out.println("Database connection failed.");
        } else {
            System.out.println("Database connection established.");
            populateWorkersComboBox();
        }

        populateOrderStatusComboBox();
        setupTableColumns();
    }

    private void populateWorkersComboBox() {
        ObservableList<String> workerList = FXCollections.observableArrayList();

        try {
            String query = "SELECT wname FROM workers";
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String workerName = rs.getString("wname");
                workerList.add(workerName);
            }

            MEworkers.setItems(workerList);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching worker names.");
        }
    }

    private void populateOrderStatusComboBox() {
        ObservableList<String> orderStatusList = FXCollections.observableArrayList("0", "1", "All");
        MEorderstatus.setItems(orderStatusList);
    }

    private void setupTableColumns() {
        TableColumn<OrderDetails, Integer> colOrderId = new TableColumn<>("Order ID");
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<OrderDetails, String> colMobile = new TableColumn<>("Mobile");
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));

        TableColumn<OrderDetails, String> colDress = new TableColumn<>("Dress");
        colDress.setCellValueFactory(new PropertyValueFactory<>("dress"));

        TableColumn<OrderDetails, Integer> colBill = new TableColumn<>("Bill");
        colBill.setCellValueFactory(new PropertyValueFactory<>("bill"));

        TableColumn<OrderDetails, String> colWorker = new TableColumn<>("Worker");
        colWorker.setCellValueFactory(new PropertyValueFactory<>("worker"));

        TableColumn<OrderDetails, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("rstatus"));

        MEtblOrders.getColumns().clear(); // Clear existing columns
        MEtblOrders.getColumns().addAll(colOrderId, colMobile, colDress, colBill, colWorker, colStatus);
    }
}
