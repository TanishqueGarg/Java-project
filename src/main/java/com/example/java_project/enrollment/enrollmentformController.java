package com.example.java_project.enrollment;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.java_project.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class enrollmentformController {

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnEnroll, btnFetch, btnenrClear, btnenrEdit;

    @FXML
    private DatePicker enrDob;

    @FXML
    private ComboBox<String> enrGender;

    @FXML
    private TextField txtenrAddress, txtenrCity, txtenrMob, txtenrName;

    private final String[] Gender = {"Male", "Female", "Other"};

    private Connection con;
    private PreparedStatement stmt;

    @FXML
    void CEdoClear(ActionEvent event) {
        txtenrName.clear();
        txtenrMob.clear();
        txtenrAddress.clear();
        txtenrCity.clear();
        enrGender.getSelectionModel().clearSelection();
        enrDob.setValue(null);
        System.out.println("Fields Cleared");
    }

//    @FXML
    @FXML
    void CEdoEdit(ActionEvent event) {
        // Get values from the form fields
        String name = txtenrName.getText();
        String mobile = txtenrMob.getText();
        String address = txtenrAddress.getText();
        String city = txtenrCity.getText();
        String gender = enrGender.getSelectionModel().getSelectedItem();
        LocalDate dob = enrDob.getValue();

        // Check if the required fields are filled
        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty() || city.isEmpty() || gender == null || dob == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "All fields must be filled to update the record.");
            return;
        }

        try {
            // Prepare SQL query to update the record based on the fetched name
            stmt = con.prepareStatement("UPDATE enrollmenttbl SET mobile = ?, address = ?, city = ?, gender = ?, dob = ? WHERE cname = ?");
            stmt.setString(1, mobile);
            stmt.setString(2, address);
            stmt.setString(3, city);
            stmt.setString(4, gender);
            stmt.setDate(5, java.sql.Date.valueOf(dob));
            stmt.setString(6, name);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Record updated successfully.");
                System.out.println("Record updated successfully.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Update Failed", "No record found for the provided name.");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the record.");
        }
    }


//    @FXML
    @FXML
    void CEdoEnroll(ActionEvent event) {
        try {
            // Check if user with the same name or mobile already exists
            String checkQuery = "SELECT * FROM enrollmenttbl WHERE cname = ? OR mobile = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setString(1, txtenrName.getText());
            checkStmt.setString(2, txtenrMob.getText());
            var resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                // Determine the type of conflict
                String existingName = resultSet.getString("cname");
                String existingMobile = resultSet.getString("mobile");
                String enteredName = txtenrName.getText();
                String enteredMobile = txtenrMob.getText();

                if (existingName.equalsIgnoreCase(enteredName)) {
                    showAlert(Alert.AlertType.WARNING, "Duplicate Entry", "A user with this name already exists.");
                } else if (existingMobile.equals(enteredMobile)) {
                    showAlert(Alert.AlertType.WARNING, "Duplicate Entry", "A user with this mobile number already exists.");
                }
            } else {
                // Insert the new user record
                stmt = con.prepareStatement("INSERT INTO enrollmenttbl VALUES(?,?,?,?,?,?,CURRENT_DATE)");
                stmt.setString(1, txtenrMob.getText());
                stmt.setString(2, txtenrName.getText());
                stmt.setString(3, txtenrAddress.getText());
                stmt.setString(4, txtenrCity.getText());
                stmt.setString(5, enrGender.getSelectionModel().getSelectedItem());

                LocalDate local = enrDob.getValue();
                java.sql.Date date = java.sql.Date.valueOf(local);
                stmt.setDate(6, date);

                stmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Record saved successfully.");
                System.out.println("Record saved successfully.");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while saving the record.");
        }
    }

    //    @FXML
    @FXML
    void doFetch(ActionEvent event) {
        String name = txtenrName.getText();

        if (name.isEmpty()) {
            // Show alert if name field is empty
            showAlert(Alert.AlertType.ERROR, "Input Error", "Name field is empty. Please enter a name.");
            return;
        }

        try {
            // Prepare SQL query to fetch details based on name
            stmt = con.prepareStatement("SELECT * FROM enrollmenttbl WHERE cname = ?");
            stmt.setString(1, name);

            // Execute query and process the result
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // Populate fields with fetched data
                txtenrMob.setText(resultSet.getString("mobile"));
                txtenrAddress.setText(resultSet.getString("address"));
                txtenrCity.setText(resultSet.getString("city"));
                enrGender.getSelectionModel().select(resultSet.getString("gender"));
                enrDob.setValue(resultSet.getDate("dob").toLocalDate());

                System.out.println("Details fetched successfully.");
            } else {
                // Show alert if no record found
                showAlert(Alert.AlertType.WARNING, "Not Found", "No user found with the entered name.");
            }

        } catch (Exception exp) {
            exp.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while fetching details.");
        }
    }


    @FXML
    void initialize() {
        con = DatabaseConnection.doConnect();
        if (con == null)
            System.out.println("Connection not established.");
        else
            System.out.println("Database connected successfully.");

        enrGender.getItems().addAll(Gender);
    }
}