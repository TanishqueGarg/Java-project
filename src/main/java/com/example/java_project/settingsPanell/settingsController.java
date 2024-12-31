package com.example.java_project.settingsPanell;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.java_project.common.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class settingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtcnfmpwd;

    @FXML
    private TextField txtnewpwd;

    @FXML
    private TextField txtoldpwd;

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
    void btnchngPwd(ActionEvent event) {
        String oldPassword = txtoldpwd.getText().trim();
        String newPassword = txtnewpwd.getText().trim();
        String confirmPassword = txtcnfmpwd.getText().trim();

        // Check if the old password matches the current password
        if (!oldPassword.equals(PasswordManager.getPassword())) {
            showAlert("Error", "The old password is incorrect.", Alert.AlertType.ERROR);
            return;
        }

        // Check if the new password matches the confirm password
        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "The new password and confirm password do not match.", Alert.AlertType.ERROR);
            return;
        }

        // Check if the new password is empty
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Password fields cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        // Update the current password
        PasswordManager.setPassword(newPassword);
        showAlert("Success", "Password updated successfully!", Alert.AlertType.INFORMATION);

        // Clear the text fields
        txtoldpwd.clear();
        txtnewpwd.clear();
        txtcnfmpwd.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        assert txtcnfmpwd != null : "fx:id=\"txtcnfmpwd\" was not injected: check your FXML file 'settingsView.fxml'.";
        assert txtnewpwd != null : "fx:id=\"txtnewpwd\" was not injected: check your FXML file 'settingsView.fxml'.";
        assert txtoldpwd != null : "fx:id=\"txtoldpwd\" was not injected: check your FXML file 'settingsView.fxml'.";
    }
}
