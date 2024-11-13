package com.example.java_project.enrollment;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.java_project.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class enrollmentformController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnEnroll;

    @FXML
    private Button btnFetch;

    @FXML
    private Button btnenrClear;

    @FXML
    private Button btnenrEdit;

    @FXML
    private DatePicker enrDob;

    @FXML
    private ComboBox<String> enrGender;

    @FXML
    private TextField txtenrAddress;

    @FXML
    private TextField txtenrCity;

    @FXML
    private TextField txtenrMob;

    @FXML
    private TextField txtenrName;

    String[] Gender={"Male","Female","Other"};

    @FXML
    void CEdoClear(ActionEvent event) {
        System.out.println("Clear");
    }

    @FXML
    void CEdoEdit(ActionEvent event) {
        System.out.println("Edit");
    }

    PreparedStatement stmt;

    @FXML
    void CEdoEnroll(ActionEvent event) {

        try
        {
            stmt= con.prepareStatement("insert into enrollmenttbl values(?,?,?,?,?,?,current_date)");
            stmt.setString(1,(txtenrMob.getText()));
//            stmt.setInt(2,Integer.parseInt(txtage.getSelectionModal().getSelectedItem()))
            stmt.setString(2,txtenrName.getText());
            stmt.setString(3,txtenrAddress.getText());
            stmt.setString(4,txtenrCity.getText());
            stmt.setString(5,enrGender.getSelectionModel().getSelectedItem().toString());

            LocalDate local=enrDob.getValue();
            java.sql.Date date=java.sql.Date.valueOf(local);
            stmt.setDate(6, date);

            stmt.executeUpdate();
            System.out.println("Record Saved");

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void doFetch(ActionEvent event) {
        System.out.println("Fetch");
    }

    Connection con;

    @FXML
    void initialize() {
        assert btnEnroll != null : "fx:id=\"btnEnroll\" was not injected: check your FXML file 'enrollmentformView.fxml'.";
        assert btnFetch != null : "fx:id=\"btnFetch\" was not injected: check your FXML file 'enrollmentformView.fxml'.";
        assert btnenrClear != null : "fx:id=\"btnenrClear\" was not injected: check your FXML file 'enrollmentformView.fxml'.";
        assert btnenrEdit != null : "fx:id=\"btnenrEdit\" was not injected: check your FXML file 'enrollmentformView.fxml'.";
        assert enrDob != null : "fx:id=\"enrDob\" was not injected: check your FXML file 'enrollmentformView.fxml'.";
        assert enrGender != null : "fx:id=\"enrGender\" was not injected: check your FXML file 'enrollmentformView.fxml'.";
        assert txtenrAddress != null : "fx:id=\"txtenrAddress\" was not injected: check your FXML file 'enrollmentformView.fxml'.";
        assert txtenrCity != null : "fx:id=\"txtenrCity\" was not injected: check your FXML file 'enrollmentformView.fxml'.";
        assert txtenrMob != null : "fx:id=\"txtenrMob\" was not injected: check your FXML file 'enrollmentformView.fxml'.";
        assert txtenrName != null : "fx:id=\"txtenrName\" was not injected: check your FXML file 'enrollmentformView.fxml'.";

        con = DatabaseConnection.doConnect();
        if(con==null)
            System.out.println("Connection Did not Established");
        else
            System.out.println("Connection Done");

        enrGender.getItems().addAll(Gender);
    }

}
