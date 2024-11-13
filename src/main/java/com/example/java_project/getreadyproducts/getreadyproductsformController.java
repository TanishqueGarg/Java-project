package com.example.java_project.getreadyproducts;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import com.example.java_project.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class getreadyproductsformController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button GRPbtnrecieved;

    @FXML
    private Button GRPbtnsearch;

    @FXML
    private ComboBox<String> GRPcomboworkers;

    @FXML
    private ListView<String> GRPlistdod;

    @FXML
    private ListView<String> GRPlistorders;

    @FXML
    private ListView<String> GRPlistproducts;

    @FXML
    void doSearch(ActionEvent event) {

    }

    @FXML
    void doRecieveAll(ActionEvent event) {

    }

    Connection con;
    @FXML
    void initialize() {
        assert GRPbtnrecieved != null : "fx:id=\"GRPbtnrecieved\" was not injected: check your FXML file 'getreadyproductsformView.fxml'.";
        assert GRPcomboworkers != null : "fx:id=\"GRPcomboworkers\" was not injected: check your FXML file 'getreadyproductsformView.fxml'.";
        assert GRPlistdod != null : "fx:id=\"GRPlistdod\" was not injected: check your FXML file 'getreadyproductsformView.fxml'.";
        assert GRPlistorders != null : "fx:id=\"GRPlistorders\" was not injected: check your FXML file 'getreadyproductsformView.fxml'.";
        assert GRPlistproducts != null : "fx:id=\"GRPlistproducts\" was not injected: check your FXML file 'getreadyproductsformView.fxml'.";

        con = DatabaseConnection.doConnect();
        if(con==null)
            System.out.println("Connection Did not Established");
        else
            System.out.println("Connection Done");

    }

}