package com.example.java_project.adminpanel;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class adminpanelController {

    @FXML
    private VBox rootLayout;

    @FXML
    private Button costumerEnrollmentButton;

    @FXML
    private Button measurementsButton;

    @FXML
    private Button measurementExplorerButton;

    @FXML
    private Button workerConsoleButton;

    @FXML
    private Button workerListButton;

    @FXML
    private Button readyProductsButton;

    @FXML
    private Button orderDeliveryButton;

    @FXML
    void initialize() {
        // Root layout styles
        rootLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #4facfe, #00f2fe);"
                + "-fx-font-family: 'Arial', sans-serif;");

        // Common button style
        String buttonStyle = "-fx-background-color: #0066cc; -fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-padding: 10 20; -fx-border-radius: 5; -fx-background-radius: 5;";

        // Apply styles to buttons
        costumerEnrollmentButton.setStyle(buttonStyle);
        measurementsButton.setStyle(buttonStyle);
        measurementExplorerButton.setStyle(buttonStyle);
        workerConsoleButton.setStyle(buttonStyle);
        workerListButton.setStyle(buttonStyle);
        readyProductsButton.setStyle(buttonStyle);
        orderDeliveryButton.setStyle(buttonStyle);

        // Button actions with specific page titles
        costumerEnrollmentButton.setOnAction(event -> openNewScene(
                "/com/example/java_project/enrollmentt/enrollmentformView.fxml", "Customer Enrollment"));
        measurementsButton.setOnAction(event -> openNewScene(
                "/com/example/java_project/measurementss/measurementsformView.fxml", "Measurements"));
        measurementExplorerButton.setOnAction(event -> openNewScene(
                "/com/example/java_project/measurementsexplorerr/measurementsexplorerView.fxml", "Measurement Explorer"));
        workerConsoleButton.setOnAction(event -> openNewScene(
                "/com/example/java_project/workersconsolee/workersconsoleformView.fxml", "Worker Console"));
        workerListButton.setOnAction(event -> openNewScene(
                "/com/example/java_project/allworkerss/allworkersformView.fxml", "Worker List"));
        readyProductsButton.setOnAction(event -> openNewScene(
                "/com/example/java_project/getreadyproductss/getreadyproductsformView.fxml", "Ready Products"));
        orderDeliveryButton.setOnAction(event -> openNewScene(
                "/com/example/java_project/orderdeliverypanell/orderdeliverypanelformView.fxml", "Order Delivery"));
    }

    private void openNewScene(String fxmlFilePath, String title) {
        try {
            // Load the new FXML file
            Parent newScene = FXMLLoader.load(getClass().getResource(fxmlFilePath));

            // Create and configure the new stage
            Stage newStage = new Stage();
            newStage.setScene(new Scene(newScene));
            newStage.setTitle(title); // Set the provided title
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
