package com.example.java_project.allworker;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.java_project.DatabaseConnection;
import javafx.stage.Stage;

public class allworkersformControlller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AWbtnallworkers;

    @FXML
    private Button AWbtnSearchWorkers;

    @FXML
    private ComboBox<String> AWsplzcombo;

    @FXML
    private TableView<ProfileBean> AWtblallworkers;

    PreparedStatement stmt;
    Connection con;

    @FXML
    void AWshowWorkers(ActionEvent event) {
        loadWorkersTable(null);
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
    void AWshowSpecificWorkers(ActionEvent event) {
        String selectedSpecialization = AWsplzcombo.getValue();
        if (selectedSpecialization == null || selectedSpecialization.isEmpty()) {
            System.out.println("No specialization selected.");
            return;
        }
        loadWorkersTable(selectedSpecialization);
    }

    void loadWorkersTable(String specialization) {
        AWtblallworkers.getColumns().clear();

        // Create table columns
        TableColumn<ProfileBean, String> uidC = new TableColumn<>("Name");
        uidC.setCellValueFactory(new PropertyValueFactory<>("wname"));
        uidC.setMinWidth(100);

        TableColumn<ProfileBean, String> Cage = new TableColumn<>("Address");
        Cage.setCellValueFactory(new PropertyValueFactory<>("address"));
        Cage.setMinWidth(100);

        TableColumn<ProfileBean, String> Cdt = new TableColumn<>("Mobile");
        Cdt.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        Cdt.setMinWidth(100);

        TableColumn<ProfileBean, String> Cspl = new TableColumn<>("Specialization");
        Cspl.setCellValueFactory(new PropertyValueFactory<>("splz"));
        Cspl.setMinWidth(100);

        AWtblallworkers.getColumns().addAll(uidC, Cage, Cdt, Cspl);
        AWtblallworkers.setItems(getRecords(specialization));
    }

    ObservableList<ProfileBean> getRecords(String specialization) {
        ObservableList<ProfileBean> ary = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM workers";
            if (specialization != null && !specialization.isEmpty()) {
                query += " WHERE splz LIKE ?";
                stmt = con.prepareStatement(query);
                stmt.setString(1, "%" + specialization + "%");
            } else {
                stmt = con.prepareStatement(query);
            }

            ResultSet records = stmt.executeQuery();
            while (records.next()) {
                String wname1 = records.getString("wname");
                String address1 = records.getString("address");
                String mobile1 = records.getString("mobile");
                String splz1 = records.getString("splz");
                ary.add(new ProfileBean(wname1, address1, mobile1, splz1));
                System.out.println(wname1 + "  " + address1 + "  " + mobile1 + "  " + splz1);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return ary;
    }

    void populateComboBox() {
        ObservableList<String> specializations = FXCollections.observableArrayList();
        try {
            stmt = con.prepareStatement("SELECT DISTINCT splz FROM workers");
            ResultSet records = stmt.executeQuery();
            while (records.next()) {
                specializations.add(records.getString("splz"));
            }
            AWsplzcombo.setItems(specializations);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert AWbtnallworkers != null : "fx:id=\"AWbtnallworkers\" was not injected: check your FXML file 'allworkersformView.fxml'.";
        assert AWbtnSearchWorkers != null : "fx:id=\"AWbtnSearchWorkers\" was not injected: check your FXML file 'allworkersformView.fxml'.";
        assert AWsplzcombo != null : "fx:id=\"AWsplzcombo\" was not injected: check your FXML file 'allworkersformView.fxml'.";
        assert AWtblallworkers != null : "fx:id=\"AWtblallworkers\" was not injected: check your FXML file 'allworkersformView.fxml'.";

        con = DatabaseConnection.doConnect();
        if (con == null) {
            System.out.println("Connection Did not Established");
        } else {
            System.out.println("Connection Done");
            populateComboBox();
        }
    }
}
