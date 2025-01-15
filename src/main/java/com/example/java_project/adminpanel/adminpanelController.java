package com.example.java_project.adminpanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class adminpanelController {

    @FXML
    private AnchorPane rootLayout; // Updated to AnchorPane to match the FXML root element

    @FXML
    private Button costumerEnrollmentButton;

    @FXML
    private Button measurementsButton;

    @FXML
    private Button measurementExplorerButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button workerConsoleButton;

    @FXML
    private Button workerListButton;

    @FXML
    private Button readyProductsButton;

    @FXML
    private Button btnLogout;

    @FXML
    private Button orderDeliveryButton;

    @FXML
    void btnLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Click 'Yes' to logout or 'No' to stay.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/java_project/loginpanell/loginView.fxml"));
                Parent loginView = loader.load();
                Scene loginScene = new Scene(loginView);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
                Stage loginStage = new Stage();
                loginStage.setTitle("Login");
                loginStage.setScene(loginScene);
                loginStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert.close();
        }
    }

    @FXML
    void initialize() {
        costumerEnrollmentButton.setOnAction(event -> loadScene("/com/example/java_project/enrollmentt/enrollmentformView.fxml"));
        measurementsButton.setOnAction(event -> loadScene("/com/example/java_project/measurementss/measurementsformView.fxml"));
        measurementExplorerButton.setOnAction(event -> loadScene("/com/example/java_project/measurementsexplorerr/measurementsexplorerView.fxml"));
        workerConsoleButton.setOnAction(event -> loadScene("/com/example/java_project/workersconsolee/workersconsoleformView.fxml"));
        workerListButton.setOnAction(event -> loadScene("/com/example/java_project/allworkerss/allworkersformView.fxml"));
        readyProductsButton.setOnAction(event -> loadScene("/com/example/java_project/getreadyproductss/getreadyproductsformView.fxml"));
        orderDeliveryButton.setOnAction(event -> loadScene("/com/example/java_project/orderdeliverypanell/orderdeliverypanelformView.fxml"));
        settingsButton.setOnAction(event -> loadScene("/com/example/java_project/settingsPanel/settingsView.fxml"));
    }

    private void loadScene(String fxmlFilePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Pane newContent = loader.load();
            rootLayout.getChildren().clear();
            rootLayout.getChildren().add(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
