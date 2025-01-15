package com.example.java_project.workersconsole;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.java_project.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class workersconsoleformController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField WCaddress;

    @FXML
    private Button WCbtnNew;

    @FXML
    private Button WCbtnSave;

    @FXML
    private TextField WCmobile;

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

    String str="";

    @FXML
    void doAddSelected(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String selectedSpecialization = WCsplzList.getSelectionModel().getSelectedItem();
            if (!str.contains(selectedSpecialization)) {
                str += selectedSpecialization + " ";
                WCsplz.setText(str.trim());
            } else {
                showAlert(Alert.AlertType.WARNING, "Already Added", "Specialization already added: "+ selectedSpecialization);
            }
        }
    }


    String[] SPLZ={"Pent","Shirt","Coat","T-Shirt","Kurta Pyjaama"};

    @FXML
    private TextField WCname;

    @FXML
    private TextField WCsplz;

    @FXML
    private ListView<String> WCsplzList;

    @FXML
    void doWCnew(ActionEvent event) {
        WCname.clear();
        WCaddress.clear();
        WCmobile.clear();
        WCsplz.clear();

        str = "";
        WCsplzList.getSelectionModel().clearSelection();

        System.out.println("Fields cleared for new worker entry.");
    }


    PreparedStatement stmt;

    @FXML
    void doWCsave(ActionEvent event) {
        if (WCname.getText().trim().isEmpty() ||
                WCaddress.getText().trim().isEmpty() ||
                WCmobile.getText().trim().isEmpty() ||
                WCsplz.getText().trim().isEmpty()) {

            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please fill all fields before saving.");
            return;
        }

        try {
            PreparedStatement checkStmt = con.prepareStatement("SELECT COUNT(*) FROM workers WHERE mobile = ?");
            checkStmt.setString(1, WCmobile.getText());
            var resultSet = checkStmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Mobile Number", "The mobile number already exists. Please use a unique mobile number.");
                return;
            }

            stmt = con.prepareStatement("INSERT INTO workers (wname, address, mobile, splz) VALUES (?, ?, ?, ?)");
            stmt.setString(1, WCname.getText());
            stmt.setString(2, WCaddress.getText());
            stmt.setString(3, WCmobile.getText());
            stmt.setString(4, WCsplz.getText());
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Worker record saved successfully.");
            System.out.println("Record Saved");

        } catch (Exception exp) {
            exp.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while saving the record: " + exp.getMessage());
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    Connection con;

    @FXML
    void initialize() {
        assert WCaddress != null : "fx:id=\"WCaddress\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";
        assert WCbtnNew != null : "fx:id=\"WCbtnNew\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";
        assert WCbtnSave != null : "fx:id=\"WCbtnSave\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";
        assert WCmobile != null : "fx:id=\"WCmobile\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";
        assert WCname != null : "fx:id=\"WCname\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";
        assert WCsplz != null : "fx:id=\"WCsplz\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";
        assert WCsplzList != null : "fx:id=\"WCsplzList\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";

        con = DatabaseConnection.doConnect();
        if(con==null)
            System.out.println("Connection Did not Established");
        else
            System.out.println("Connection Done");

        WCsplzList.getItems().addAll(SPLZ);
    }

}
