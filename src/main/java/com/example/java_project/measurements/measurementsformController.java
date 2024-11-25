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
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    private String uploadedImageName = ""; // To store the uploaded image name
    private String uploadedImagePath = ""; // To store the uploaded image's full path

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

    private final String IMAGES_FOLDER = "images/"; // Folder to save images


    @FXML
    void MSRbtnnclose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void MSRbtnnnew(ActionEvent event) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the FXML file (ensure the path is correct)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/java_project/measurementss/measurementsformView.fxml"));

            // Create a new scene with the loaded FXML
            Scene newScene = new Scene(loader.load());

            // Set the new scene to the current stage and show it
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void MSRbtnnupdate(ActionEvent event) {
        // Ensure a mobile number is provided
        String mobile = MSRmobile.getText();
        if (mobile.isEmpty()) {
            System.out.println("Mobile number is required to update data.");
            showAlert("Missing Mobile Number", "Please fetch or provide a mobile number to update the record.");
            return;
        }

        // Ensure required fields are not empty
        if (MSRdress.getSelectionModel().isEmpty() || MSRworkers.getSelectionModel().isEmpty() || uploadedImageName.isEmpty()) {
            System.out.println("Required fields are missing.");
            showAlert("Missing Fields", "Please ensure all required fields (dress, worker, and image) are filled before updating.");
            return;
        }

        try {
            // Update the database record with new values
            String updateQuery = "UPDATE measurements SET dress = ?, pic = ?, dodel = ?, price = ?, qty = ?, bill = ?, measurements = ?, worker = ? WHERE mobile = ?";
            stmt = con.prepareStatement(updateQuery);

            // Set the parameters
            stmt.setString(1, MSRdress.getSelectionModel().getSelectedItem()); // Dress type
            stmt.setString(2, uploadedImageName); // Image name
            LocalDate local = MSRdob.getValue();
            if (local != null) {
                stmt.setDate(3, java.sql.Date.valueOf(local)); // Delivery date
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }
            stmt.setInt(4, Integer.parseInt(MSRprice.getText())); // Price per item
            stmt.setInt(5, Integer.parseInt(MSRquantity.getText())); // Quantity
            int bill = Integer.parseInt(MSRprice.getText()) * Integer.parseInt(MSRquantity.getText());
            stmt.setInt(6, bill); // Total bill
            stmt.setString(7, MSRmeasurements.getText()); // Measurements details
            stmt.setString(8, MSRworkers.getSelectionModel().getSelectedItem()); // Worker
            stmt.setString(9, mobile); // Mobile number (identifier for update)

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



    PreparedStatement stmt;
    @FXML
    void MSRbtnsave(ActionEvent event) {

//        try
//        {
//            stmt= con.prepareStatement("insert into measurements(mobile,dress,pic,dodel,qty,bill,measurements,worker,doorder) values(?,?,?,?,?,?,?,?,current_date)");
//            stmt.setString(1,(MSRmobile.getText()));
//            stmt.setString(2,MSRdress.getSelectionModel().getSelectedItem().toString());
//            stmt.setString(3,MSRdesign.imageProperty().getName().toString());
//            LocalDate local=MSRdob.getValue();
//            java.sql.Date date=java.sql.Date.valueOf(local);
//            stmt.setDate(4, date);
//            //int-5,6
//            stmt.setInt(5,Integer.parseInt(MSRquantity.getText()));
//            int billll=Integer.parseInt(MSRprice.getText())*Integer.parseInt(MSRquantity.getText());
//            stmt.setInt(6,billll);
//            stmt.setString(7,MSRmeasurements.getText());
//            stmt.setString(8,MSRworkers.getSelectionModel().getSelectedItem().toString());
//            stmt.executeUpdate();
//            System.out.println("Record Saved");
//
//        }
//        catch(Exception exp)
//        {
//            exp.printStackTrace();
//        }

        if (uploadedImageName.isEmpty()) {
            System.out.println("No image uploaded. Please upload an image before saving.");
            return;
        }

        try {
            // Save image to the "images" folder
            File sourceFile = new File(uploadedImagePath);
            File destFile = new File(IMAGES_FOLDER + uploadedImageName);

            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs(); // Create "images" folder if it doesn't exist
            }

            Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Prepare SQL insert statement
            stmt = con.prepareStatement("INSERT INTO measurements (mobile, dress, pic, dodel, price, qty, bill, measurements, worker, doorder) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE)");

            // Set parameters
            stmt.setString(1, MSRmobile.getText());
            stmt.setString(2, MSRdress.getSelectionModel().getSelectedItem());
            stmt.setString(3, uploadedImageName); // Save image name in DB
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
            stmt = con.prepareStatement("SELECT * FROM measurements WHERE mobile = ?");
            stmt.setString(1, mobile);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                MSRdress.getSelectionModel().select(resultSet.getString("dress"));
                uploadedImageName = resultSet.getString("pic");

                // Load the image from the images folder
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

        // Populate dress combo box with predefined values
        MSRdress.getItems().addAll(dressss);

        // Populate workers combo box from database
        loadWorkersFromDatabase();

        // Add listeners to calculate the bill automatically
        MSRquantity.textProperty().addListener((observable, oldValue, newValue) -> calculateBill());
        MSRprice.textProperty().addListener((observable, oldValue, newValue) -> calculateBill());
    }

    private void loadWorkersFromDatabase() {
        try {
            String query = "SELECT wname FROM workers";  // Modify this query according to your database schema
            PreparedStatement stmt = con.prepareStatement(query);
            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String workerName = resultSet.getString("wname");
                MSRworkers.getItems().add(workerName);  // Add each worker's name to the ComboBox
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            System.out.println("Error fetching worker names from database: " + exp.getMessage());
        }
    }


    private void calculateBill() {
        try {
            // Parse the quantity and price fields
            int quantity = Integer.parseInt(MSRquantity.getText().trim());
            int price = Integer.parseInt(MSRprice.getText().trim());

            // Calculate the bill
            int bill = quantity * price;

            // Set the calculated bill in the bill field
            MSRbill.setText(String.valueOf(bill));
        } catch (NumberFormatException e) {
            // Clear the bill field if inputs are invalid
            MSRbill.clear();
        }
    }

}
