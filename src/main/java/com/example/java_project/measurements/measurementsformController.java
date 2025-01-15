package com.example.java_project.measurements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.java_project.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class measurementsformController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField MSRbill;

    private String uploadedImageName = "";
    private String uploadedImagePath = "";

    @FXML
    private ImageView MSRdesign;

    @FXML
    private DatePicker MSRdob;

    String[] dressss={"pent","shirt","coat"};

    String[] workers={"mohanlal","pappu","chotu","kaala"};


    @FXML
    private ComboBox<String> MSRdress;

    @FXML
    private ImageView MSRlogo;

    @FXML
    private TextArea MSRmeasurements;

    @FXML
    private TextField MSRmobile;

    @FXML
    private TextField MSRprice;

    @FXML
    private TextField MSRquantity;

    @FXML
    private ComboBox<String> MSRworkers;

    private final String IMAGES_FOLDER = "Java_Project/images/";


    @FXML
    void MSRbtnnclose(ActionEvent event) {
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
    void MSRbtnnnew(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/java_project/measurementss/measurementsformView.fxml"));
            Scene newScene = new Scene(loader.load());
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void MSRbtnnupdate(ActionEvent event) {
        String mobile = MSRmobile.getText();
        if (mobile.isEmpty()) {
            System.out.println("Mobile number is required to update data.");
            showAlert("Missing Mobile Number", "Please fetch or provide a mobile number to update the record.");
            return;
        }
        if (MSRdress.getSelectionModel().isEmpty() || MSRworkers.getSelectionModel().isEmpty() || uploadedImageName.isEmpty()) {
            System.out.println("Required fields are missing.");
            showAlert("Missing Fields", "Please ensure all required fields (dress, worker, and image) are filled before updating.");
            return;
        }

        try {
            String updateQuery = "UPDATE measurements SET dress = ?, pic = ?, dodel = ?, price = ?, qty = ?, bill = ?, measurements = ?, worker = ? WHERE mobile = ?";
            stmt = con.prepareStatement(updateQuery);
            stmt.setString(1, MSRdress.getSelectionModel().getSelectedItem());
            stmt.setString(2, uploadedImageName);
            LocalDate local = MSRdob.getValue();
            if (local != null) {
                stmt.setDate(3, java.sql.Date.valueOf(local));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }
            stmt.setInt(4, Integer.parseInt(MSRprice.getText()));
            stmt.setInt(5, Integer.parseInt(MSRquantity.getText()));
            int bill = Integer.parseInt(MSRprice.getText()) * Integer.parseInt(MSRquantity.getText());
            stmt.setInt(6, bill);
            stmt.setString(7, MSRmeasurements.getText());
            stmt.setString(8, MSRworkers.getSelectionModel().getSelectedItem());
            stmt.setString(9, mobile);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Record updated successfully!");
                showAlert("Update Successful", "The record has been successfully updated.");
            } else {
                System.out.println("No record found to update for the given mobile number.");
                showAlert("Update Failed", "No record found for the provided mobile number.");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            System.out.println("Error updating record: " + exp.getMessage());
            showAlert("Update Error", "An error occurred while updating the record. Please try again.");
        }
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    PreparedStatement stmt;
    @FXML
    void MSRbtnsave(ActionEvent event) {
        if (uploadedImageName.isEmpty()) {
            System.out.println("No image uploaded. Please upload an image before saving.");
            return;
        }

        try {
            File sourceFile = new File(uploadedImagePath);
            File destFile = new File(IMAGES_FOLDER + uploadedImageName);

            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }

            Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            stmt = con.prepareStatement("INSERT INTO measurements (mobile, dress, pic, dodel, price, qty, bill, measurements, worker, doorder) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE)");
            stmt.setString(1, MSRmobile.getText());
            stmt.setString(2, MSRdress.getSelectionModel().getSelectedItem());
            stmt.setString(3, uploadedImageName);
            LocalDate local = MSRdob.getValue();
            java.sql.Date date = java.sql.Date.valueOf(local);
            stmt.setDate(4, date);
            stmt.setInt(5, Integer.parseInt(MSRprice.getText()));
            stmt.setInt(6, Integer.parseInt(MSRquantity.getText()));
            int bill = Integer.parseInt(MSRprice.getText()) * Integer.parseInt(MSRquantity.getText());
            stmt.setInt(7, bill);
            stmt.setString(8, MSRmeasurements.getText());
            stmt.setString(9, MSRworkers.getSelectionModel().getSelectedItem());

            stmt.executeUpdate();
            System.out.println("Record saved successfully!");

        } catch (Exception exp) {
            exp.printStackTrace();
            System.out.println("Error saving record: " + exp.getMessage());
        }
    }

    @FXML
    void MSRbtnfetch(ActionEvent event) {
        String mobile = MSRmobile.getText();

        if (mobile.isEmpty()) {
            System.out.println("Mobile number is required to fetch data.");
            return;
        }

        try {
            stmt = con.prepareStatement("SELECT * FROM measurements WHERE mobile = ? ORDER BY orderid DESC LIMIT 1");
            stmt.setString(1, mobile);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                MSRdress.getSelectionModel().select(resultSet.getString("dress"));
                uploadedImageName = resultSet.getString("pic");
                File imageFile = new File(IMAGES_FOLDER + uploadedImageName);
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    MSRdesign.setImage(image);
                } else {
                    System.out.println("Image not found at specified path: " + uploadedImageName);
                }

                MSRdob.setValue(resultSet.getDate("dodel").toLocalDate());
                MSRquantity.setText(String.valueOf(resultSet.getInt("qty")));
                MSRprice.setText(String.valueOf(resultSet.getInt("price")));
                MSRbill.setText(String.valueOf(resultSet.getInt("bill")));
                MSRmeasurements.setText(resultSet.getString("measurements"));
                MSRworkers.getSelectionModel().select(resultSet.getString("worker"));
                System.out.println("Record fetched successfully!");
            } else {
                System.out.println("No record found for the entered mobile number.");
                showAlert("No Record Found", "No user exists with the provided mobile number.");
            }

        } catch (Exception exp) {
            exp.printStackTrace();
            System.out.println("Error fetching record: " + exp.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    void MSRbtnupload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Design Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                MSRdesign.setImage(image);

                uploadedImageName = selectedFile.getName();
                uploadedImagePath = selectedFile.getAbsolutePath();

                System.out.println("Image uploaded successfully: " + uploadedImageName);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error loading image: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected.");
        }

    }

    Connection con;
    @FXML
    void initialize() {
        assert MSRbill != null : "fx:id=\"MSRbill\" was not injected: check your FXML file 'measurementsformView.fxml'.";
        assert MSRdesign != null : "fx:id=\"MSRdesign\" was not injected: check your FXML file 'measurementsformView.fxml'.";
        assert MSRdob != null : "fx:id=\"MSRdob\" was not injected: check your FXML file 'measurementsformView.fxml'.";
        assert MSRdress != null : "fx:id=\"MSRdress\" was not injected: check your FXML file 'measurementsformView.fxml'.";
        assert MSRlogo != null : "fx:id=\"MSRlogo\" was not injected: check your FXML file 'measurementsformView.fxml'.";
        assert MSRmeasurements != null : "fx:id=\"MSRmeasurements\" was not injected: check your FXML file 'measurementsformView.fxml'.";
        assert MSRmobile != null : "fx:id=\"MSRmobile\" was not injected: check your FXML file 'measurementsformView.fxml'.";
        assert MSRprice != null : "fx:id=\"MSRprice\" was not injected: check your FXML file 'measurementsformView.fxml'.";
        assert MSRquantity != null : "fx:id=\"MSRquantity\" was not injected: check your FXML file 'measurementsformView.fxml'.";
        assert MSRworkers != null : "fx:id=\"MSRworkers\" was not injected: check your FXML file 'measurementsformView.fxml'.";

        con = DatabaseConnection.doConnect();
        if (con == null)
            System.out.println("Connection Did not Established");
        else
            System.out.println("Connection Done");
        MSRdress.getItems().addAll(dressss);
        loadWorkersFromDatabase();
        MSRquantity.textProperty().addListener((observable, oldValue, newValue) -> calculateBill());
        MSRprice.textProperty().addListener((observable, oldValue, newValue) -> calculateBill());
    }

    private void loadWorkersFromDatabase() {
        try {
            String query = "SELECT wname FROM workers";
            PreparedStatement stmt = con.prepareStatement(query);
            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String workerName = resultSet.getString("wname");
                MSRworkers.getItems().add(workerName);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            System.out.println("Error fetching worker names from database: " + exp.getMessage());
        }
    }


    private void calculateBill() {
        try {
            int quantity = Integer.parseInt(MSRquantity.getText().trim());
            int price = Integer.parseInt(MSRprice.getText().trim());
            int bill = quantity * price;
            MSRbill.setText(String.valueOf(bill));
        } catch (NumberFormatException e) {
            MSRbill.clear();
        }
    }

}
