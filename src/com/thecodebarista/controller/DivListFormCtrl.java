package com.thecodebarista.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class DivListFormCtrl extends MainMenuCtrl implements Initializable {
    @javafx.fxml.FXML
    private TableView DivTblView;
    @javafx.fxml.FXML
    private TableColumn division_ID_Col;
    @javafx.fxml.FXML
    private TableColumn division_Col;
    @javafx.fxml.FXML
    private TableColumn country_ID_Col;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
}
