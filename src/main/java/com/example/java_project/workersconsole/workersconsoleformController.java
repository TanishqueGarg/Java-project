package com.example.java_project.workersconsole;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.java_project.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class workersconsoleformController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField WCaddress;

    @FXML
    private Button WCbtnNew;

    @FXML
    private Button WCbtnSave;

    @FXML
    private TextField WCmobile;

    String str="";

    @FXML
    void doAddSelected(MouseEvent event) {
        if(event.getClickCount()==2)
        {
            str+=WCsplzList.getSelectionModel().getSelectedItem()+" ";
            WCsplz.setText(str);
        }
    }

    String[] SPLZ={"pent","shirt","coat"};

    @FXML
    private TextField WCname;

    @FXML
    private TextField WCsplz;

    @FXML
    private ListView<String> WCsplzList;

    @FXML
    void doWCnew(ActionEvent event) {

    }

    PreparedStatement stmt;

    @FXML
    void doWCsave(ActionEvent event) {
        try
        {
            stmt= con.prepareStatement("insert into workers values(?,?,?,?)");
            stmt.setString(1,(WCname.getText()));
            stmt.setString(2,WCaddress.getText());
            stmt.setString(3,WCmobile.getText());
            stmt.setString(4,WCsplz.getText());
            stmt.executeUpdate();
            System.out.println("Record Saved");

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    Connection con;

    @FXML
    void initialize() {
        assert WCaddress != null : "fx:id=\"WCaddress\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";
        assert WCbtnNew != null : "fx:id=\"WCbtnNew\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";
        assert WCbtnSave != null : "fx:id=\"WCbtnSave\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";
        assert WCmobile != null : "fx:id=\"WCmobile\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";
        assert WCname != null : "fx:id=\"WCname\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";
        assert WCsplz != null : "fx:id=\"WCsplz\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";
        assert WCsplzList != null : "fx:id=\"WCsplzList\" was not injected: check your FXML file 'workersconsoleformView.fxml'.";

        con = DatabaseConnection.doConnect();
        if(con==null)
            System.out.println("Connection Did not Established");
        else
            System.out.println("Connection Done");

        WCsplzList.getItems().addAll(SPLZ);
    }

}
