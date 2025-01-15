package com.example.java_project.getreadyproducts;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class getreadyproductsformController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button GRPbtnrecieved;

    @FXML
    private Button GRPbtnsearch;

    @FXML
    private ComboBox<String> GRPcomboworkers;

    @FXML
    private ListView<String> GRPlistdod;

    @FXML
    private ListView<String> GRPlistorders;

    @FXML
    private ListView<String> GRPlistproducts;

    Connection con;
    PreparedStatement stmt;

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
            Parent adminPanel = FXMLLoader.load(getClass().getResource("/com/example/java_project/adminpanell/adminpanelView.fxml"));
            Stage currentStage = (Stage) btnBack.getScene().getWindow();
            currentStage.setScene(new Scene(adminPanel));
            currentStage.setTitle("Darjee");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load the Admin Panel.");
        }
    }

    @FXML
    void doSearch(ActionEvent event) {
        String selectedWorker = GRPcomboworkers.getValue();

        if (selectedWorker == null || selectedWorker.isEmpty()) {
            System.out.println("Please select a worker first.");
            return;
        }
        GRPlistorders.getItems().clear();
        GRPlistproducts.getItems().clear();
        GRPlistdod.getItems().clear();

        try {
            String query = "SELECT orderid, dress, dodel FROM measurements WHERE worker = ? AND rstatus = 0";
            stmt = con.prepareStatement(query);
            stmt.setString(1, selectedWorker);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String orderId = rs.getString("orderid");
                String dress = rs.getString("dress");
                String dod = rs.getString("dodel");
                GRPlistorders.getItems().add(orderId);
                GRPlistproducts.getItems().add(dress);
                GRPlistdod.getItems().add(dod);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching data for worker: " + selectedWorker);
        }
    }

    @FXML
    void doRecieveAll(ActionEvent event) {
        try {
            String selectedWorker = GRPcomboworkers.getValue();

            if (selectedWorker == null || selectedWorker.isEmpty()) {
                System.out.println("Please select a worker first.");
                return;
            }
            String updateQuery = "UPDATE measurements SET rstatus = 1 WHERE worker = ? AND rstatus = 0";
            stmt = con.prepareStatement(updateQuery);
            stmt.setString(1, selectedWorker);
            int rowsUpdated = stmt.executeUpdate();

            System.out.println("Marked " + rowsUpdated + " orders as received for worker: " + selectedWorker);
            doSearch(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void populateComboBox() {
        ObservableList<String> workers = FXCollections.observableArrayList();
        try {
            String query = "SELECT wname FROM workers";
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String workerName = rs.getString("wname");
                workers.add(workerName);
            }

            GRPcomboworkers.setItems(workers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addDoubleClickHandlerToListView(ListView<String> listView) {
        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();

                if (selectedIndex != -1) {
                    String selectedOrderId = GRPlistorders.getItems().get(selectedIndex);

                    try {
                        String updateQuery = "UPDATE measurements SET rstatus = 1 WHERE orderid = ?";
                        stmt = con.prepareStatement(updateQuery);
                        stmt.setString(1, selectedOrderId);
                        int rowsUpdated = stmt.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Order " + selectedOrderId + " marked as delivered.");
                        }
                        String selectedWorker = GRPcomboworkers.getValue();
                        if (selectedWorker != null && !selectedWorker.isEmpty()) {
                            doSearch(null);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Error updating rstatus for order: " + selectedOrderId);
                    }
                }
            }
        });
    }

    @FXML
    void initialize() {
        assert GRPbtnrecieved != null : "fx:id=\"GRPbtnrecieved\" was not injected: check your FXML file 'getreadyproductsformView.fxml'.";
        assert GRPcomboworkers != null : "fx:id=\"GRPcomboworkers\" was not injected: check your FXML file 'getreadyproductsformView.fxml'.";
        assert GRPlistdod != null : "fx:id=\"GRPlistdod\" was not injected: check your FXML file 'getreadyproductsformView.fxml'.";
        assert GRPlistorders != null : "fx:id=\"GRPlistorders\" was not injected: check your FXML file 'getreadyproductsformView.fxml'.";
        assert GRPlistproducts != null : "fx:id=\"GRPlistproducts\" was not injected: check your FXML file 'getreadyproductsformView.fxml'.";

        con = DatabaseConnection.doConnect();
        if (con == null) {
            System.out.println("Connection Did not Established");
        } else {
            System.out.println("Connection Done");
            populateComboBox();
            addDoubleClickHandlerToListView(GRPlistorders);
            addDoubleClickHandlerToListView(GRPlistproducts);
            addDoubleClickHandlerToListView(GRPlistdod);
        }
    }
}
