package com.example.java_project.measurements;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.java_project.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class measurementsformController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField MSRbill;

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

    @FXML
    void MSRbtnnclose(ActionEvent event) {

    }

    @FXML
    void MSRbtnnnew(ActionEvent event) {

    }

    @FXML
    void MSRbtnnupdate(ActionEvent event) {

    }
    PreparedStatement stmt;
    @FXML
    void MSRbtnsave(ActionEvent event) {

        try
        {
            stmt= con.prepareStatement("insert into measurements(mobile,dress,pic,dodel,qty,bill,measurements,worker,doorder) values(?,?,?,?,?,?,?,?,current_date)");
            stmt.setString(1,(MSRmobile.getText()));
            stmt.setString(2,MSRdress.getSelectionModel().getSelectedItem().toString());
            stmt.setString(3,MSRdesign.toString());
            LocalDate local=MSRdob.getValue();
            java.sql.Date date=java.sql.Date.valueOf(local);
            stmt.setDate(4, date);
            //int-5,6
            stmt.setInt(5,Integer.parseInt(MSRquantity.getText()));
            int billll=Integer.parseInt(MSRprice.getText())*Integer.parseInt(MSRquantity.getText());
            stmt.setInt(6,billll);
            stmt.setString(7,MSRmeasurements.getText());
            stmt.setString(8,MSRworkers.getSelectionModel().getSelectedItem().toString());
            stmt.executeUpdate();
            System.out.println("Record Saved");

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }

    }

    @FXML
    void MSRbtnupload(ActionEvent event) {

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
        if(con==null)
            System.out.println("Connection Did not Established");
        else
            System.out.println("Connection Done");


        MSRdress.getItems().addAll(dressss);

        MSRworkers.getItems().addAll(workers);
    }

}
