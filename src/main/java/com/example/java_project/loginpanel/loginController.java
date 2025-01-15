package com.example.java_project.loginpanel;

import com.example.java_project.common.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class loginController {

    @FXML
    private PasswordField pwdVal;

    @FXML
    void btnCheck(ActionEvent event) {
        String enteredPassword = pwdVal.getText().trim();

        if (enteredPassword.equals(PasswordManager.getPassword())) {
            ((javafx.stage.Stage) pwdVal.getScene().getWindow()).close();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/java_project/adminpanell/adminpanelView.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Darjee");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to open the admin panel.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Incorrect password.", Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
