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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

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

    @FXML
    void doSearch(ActionEvent event) {
        String selectedWorker = GRPcomboworkers.getValue(); // Get selected worker name from the ComboBox

        if (selectedWorker == null || selectedWorker.isEmpty()) {
            System.out.println("Please select a worker first.");
            return;
        }

        // Clear existing data in the ListViews
        GRPlistorders.getItems().clear();
        GRPlistproducts.getItems().clear();
        GRPlistdod.getItems().clear();

        try {
            // Query to fetch order ID, dress, and delivery date for the selected worker where rstatus = 0
            String query = "SELECT orderid, dress, dodel FROM measurements WHERE worker = ? AND rstatus = 0";
            stmt = con.prepareStatement(query);
            stmt.setString(1, selectedWorker); // Set the selected worker name in the query

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Get the order ID, dress name, and delivery date from the result set
                String orderId = rs.getString("orderid");
                String dress = rs.getString("dress");
                String dod = rs.getString("dodel");

                // Add the data to the corresponding ListViews
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

            // Update rstatus to 1 for all records of the selected worker
            String updateQuery = "UPDATE measurements SET rstatus = 1 WHERE worker = ? AND rstatus = 0";
            stmt = con.prepareStatement(updateQuery);
            stmt.setString(1, selectedWorker);
            int rowsUpdated = stmt.executeUpdate();

            System.out.println("Marked " + rowsUpdated + " orders as received for worker: " + selectedWorker);

            // Refresh the ListViews
            doSearch(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void populateComboBox() {
        ObservableList<String> workers = FXCollections.observableArrayList();
        try {
            String query = "SELECT wname FROM workers"; // Fetch worker names from the workers table
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String workerName = rs.getString("wname");
                workers.add(workerName);
            }

            GRPcomboworkers.setItems(workers); // Populate the ComboBox
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle double-click to update the rstatus
    private void addDoubleClickHandlerToListView(ListView<String> listView) {
        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { // Double-click detected
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();

                if (selectedIndex != -1) { // Ensure a valid row is selected
                    String selectedOrderId = GRPlistorders.getItems().get(selectedIndex); // Get the corresponding order ID

                    try {
                        // Update the rstatus of the selected order ID to 1
                        String updateQuery = "UPDATE measurements SET rstatus = 1 WHERE orderid = ?";
                        stmt = con.prepareStatement(updateQuery);
                        stmt.setString(1, selectedOrderId);
                        int rowsUpdated = stmt.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Order " + selectedOrderId + " marked as delivered.");
                        }

                        // Refresh the ListView
                        String selectedWorker = GRPcomboworkers.getValue();
                        if (selectedWorker != null && !selectedWorker.isEmpty()) {
                            doSearch(null); // Refresh data
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
            populateComboBox(); // Populate the ComboBox with workers

            // Add double-click handlers to all ListViews
            addDoubleClickHandlerToListView(GRPlistorders);
            addDoubleClickHandlerToListView(GRPlistproducts);
            addDoubleClickHandlerToListView(GRPlistdod);
        }
    }
}
