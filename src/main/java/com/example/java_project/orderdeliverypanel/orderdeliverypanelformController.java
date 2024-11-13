package com.example.java_project.orderdeliverypanel;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class orderdeliverypanelformController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<?> ODPbilllist;

    @FXML
    private Button ODPbtndeliveredall;

    @FXML
    private Button ODPbtndofindorders;

    @FXML
    private ListView<?> ODPitemlist;

    @FXML
    private ListView<?> ODPorderidlist;

    @FXML
    private ListView<?> ODPstatuslist;

    @FXML
    private TextField ODPtotalbill;

    @FXML
    private TextField ODPtxtmobile;

    @FXML
    void doDeliveredAll(ActionEvent event) {

    }

    @FXML
    void doFindOrders(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert ODPbilllist != null : "fx:id=\"ODPbilllist\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPbtndeliveredall != null : "fx:id=\"ODPbtndeliveredall\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPbtndofindorders != null : "fx:id=\"ODPbtndofindorders\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPitemlist != null : "fx:id=\"ODPitemlist\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPorderidlist != null : "fx:id=\"ODPorderidlist\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPstatuslist != null : "fx:id=\"ODPstatuslist\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPtotalbill != null : "fx:id=\"ODPtotalbill\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";
        assert ODPtxtmobile != null : "fx:id=\"ODPtxtmobile\" was not injected: check your FXML file 'orderdeliverypanelformView.fxml'.";

    }

}