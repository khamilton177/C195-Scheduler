package com.thecodebarista.controller;

import com.thecodebarista.dao.CountryDaoImpl;
import com.thecodebarista.dao.FirstLevelDivisionDAOImpl;
import com.thecodebarista.model.Country;
import com.thecodebarista.model.FirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CstAddUpdateFormCtrl extends MainMenuCtrl implements Initializable {

    Country selectedCoCBItem;
    FirstLevelDivision selectedDivCBItem;

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
        System.out.println("Cancel Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            // Cast window to stage
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
            stage.setTitle("C195-Scheduler");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CountryDaoImpl coCBItems = new CountryDaoImpl();
        FirstLevelDivisionDAOImpl divCBItems = new FirstLevelDivisionDAOImpl();

        try {
            country_ID_CBox.setItems(coCBItems.extractAll());
            division_ID_CBox.setItems(divCBItems.extractAll());

        //    division_ID_CBox.setItems(divCBItems.extractAll().stream().filter(coItem -> coItem.equals(selectedCoCBItem)).sorted());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
