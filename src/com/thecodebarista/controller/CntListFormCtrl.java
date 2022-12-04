package com.thecodebarista.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CntListFormCtrl extends MainMenuCtrl implements Initializable {
    @javafx.fxml.FXML
    private TableView CntTblView;
    @javafx.fxml.FXML
    private TableColumn contact_ID_Col;
    @javafx.fxml.FXML
    private TableColumn contact_Name_Col;
    @javafx.fxml.FXML
    private TableColumn email_Col;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
}
