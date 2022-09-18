package com.thecodebarista.controller;

import com.thecodebarista.dao.CountryDaoImpl;
import com.thecodebarista.dao.CustomerDAO;
import com.thecodebarista.dao.CustomerDaoImpl;
import com.thecodebarista.dao.FirstLevelDivisionDAOImpl;
import com.thecodebarista.model.Country;
import com.thecodebarista.model.Customer;
import com.thecodebarista.model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CstAddModifyFormCtrl extends MainMenuCtrl implements Initializable {



    @javafx.fxml.FXML
    private Label cstAlertBoxLbl;

    @javafx.fxml.FXML
    private TextField customer_ID_TxtFld;

    @javafx.fxml.FXML
    private TextField phone_TxtFld;

    @javafx.fxml.FXML
    private TextField customer_Name_TxtFld;

    @javafx.fxml.FXML
    private TextField address_TxtFld;

    @javafx.fxml.FXML
    private TextField postal_Code_TxtFld;

    @javafx.fxml.FXML
    private ComboBox<Country> country_ID_CBox;

    @javafx.fxml.FXML
    private ComboBox<FirstLevelDivision> division_ID_CBox;

    @javafx.fxml.FXML
    private Button CstSaveBtn;

    @javafx.fxml.FXML
    private Button CstCancelBtn;

    @javafx.fxml.FXML
    public void onActionSaveCst(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void OnActionCancel(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CountryDaoImpl coCB = new CountryDaoImpl();
        FirstLevelDivisionDAOImpl divCB = new FirstLevelDivisionDAOImpl();

//        country_ID_CBox.
//                division_ID_CBox.
//                static_ZoneId

//        colName.setCellValueFactory(cellData -> {
 //           return cellData.getValue().getName();
    //    try {
            // displayCstTblViewData();
      //  } catch (SQLException e) {
           //  e.printStackTrace();
    //    }
    }

}
