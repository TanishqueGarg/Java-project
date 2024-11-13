package com.example.java_project.measurementsexplorer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class measurementsexplorerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button MEbtnFetch;

    @FXML
    private TextField MEcustomermob;

    @FXML
    private ComboBox<?> MEorderstatus;

    @FXML
    private TableView<?> MEtblOrders;

    @FXML
    private ComboBox<?> MEworkers;

    @FXML
    void MEdoFetch(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert MEbtnFetch != null : "fx:id=\"MEbtnFetch\" was not injected: check your FXML file 'measurementsexplorerView.fxml'.";
        assert MEcustomermob != null : "fx:id=\"MEcustomermob\" was not injected: check your FXML file 'measurementsexplorerView.fxml'.";
        assert MEorderstatus != null : "fx:id=\"MEorderstatus\" was not injected: check your FXML file 'measurementsexplorerView.fxml'.";
        assert MEtblOrders != null : "fx:id=\"MEtblOrders\" was not injected: check your FXML file 'measurementsexplorerView.fxml'.";
        assert MEworkers != null : "fx:id=\"MEworkers\" was not injected: check your FXML file 'measurementsexplorerView.fxml'.";

    }

}
