package com.thecodebarista.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class DivListFormCtrl extends MainMenuCtrl implements Initializable {
    @javafx.fxml.FXML
    private TableView CstTblView;
    @javafx.fxml.FXML
    private TableColumn phone_Col;
    @javafx.fxml.FXML
    private TableColumn address_Col;
    @javafx.fxml.FXML
    private TableColumn division_ID_Col;
    @javafx.fxml.FXML
    private TableColumn customer_Name_Col;
    @javafx.fxml.FXML
    private TableColumn postal_Code_Col;
    @javafx.fxml.FXML
    private TableColumn customer_ID_Col;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
}
