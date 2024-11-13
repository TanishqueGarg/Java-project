package com.example.java_project.allworker;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.java_project.DatabaseConnection;
import javafx.scene.control.Button;

public class allworkersformControlller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AWbtnallworkers;

    @FXML
    private ComboBox<?> AWsplzcombo;

    @FXML
    private TableView<ProfileBean> AWtblallworkers;

    @FXML
    void AWshowWorkers(ActionEvent event) {

        AWtblallworkers.getColumns().clear();

        TableColumn<ProfileBean, String> uidC=new TableColumn<ProfileBean, String>("wname");//kuch bhi
        uidC.setCellValueFactory(new PropertyValueFactory<>("wname"));
        uidC.setMinWidth(100);

        TableColumn<ProfileBean, String> Cage=new TableColumn<ProfileBean, String>("address");//kuch bhi
        Cage.setCellValueFactory(new PropertyValueFactory<>("address"));
        Cage.setMinWidth(100);

        TableColumn<ProfileBean, String> Cdt=new TableColumn<ProfileBean, String>("mobile");//kuch bhi
        Cdt.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        Cdt.setMinWidth(100);

        TableColumn<ProfileBean, String> Cspl=new TableColumn<ProfileBean, String>("splz");//kuch bhi
        Cspl.setCellValueFactory(new PropertyValueFactory<>("splz"));
        Cspl.setMinWidth(100);

        AWtblallworkers.getColumns().addAll(uidC,Cage,Cdt,Cspl);
        AWtblallworkers.setItems(getRecords());
    }

    PreparedStatement stmt;
    Connection con;

    ObservableList<ProfileBean> getRecords()
    {
        ObservableList<ProfileBean> ary= FXCollections.observableArrayList();
        ary.removeAll();
        try {
            stmt = con.prepareStatement("select * from workers");
            ResultSet records= stmt.executeQuery();
            while(records.next())
            {
                String wname1=records.getString("wname");//col name
                String address1=records.getString("address");//col name
                String mobile1=records.getString("mobile");//col name
                String splz1=records.getString("splz");//col name
                ary.add(new ProfileBean(wname1,address1,mobile1,splz1));
                System.out.println(wname1+"  "+address1+"  "+mobile1+"  "+splz1);

            }

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        System.out.println(ary.size());
        return ary;
    }


    @FXML
    void initialize() {
        assert AWbtnallworkers != null : "fx:id=\"AWbtnallworkers\" was not injected: check your FXML file 'allworkersformView.fxml'.";
        assert AWsplzcombo != null : "fx:id=\"AWsplzcombo\" was not injected: check your FXML file 'allworkersformView.fxml'.";
        assert AWtblallworkers != null : "fx:id=\"AWtblallworkers\" was not injected: check your FXML file 'allworkersformView.fxml'.";

        con = DatabaseConnection.doConnect();
        if(con==null)
            System.out.println("Connection Did not Established");
        else
            System.out.println("Connection Done");

        getRecords();
    }

}
