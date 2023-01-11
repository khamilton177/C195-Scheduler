package com.thecodebarista.controller;

import com.thecodebarista.dao.CountryDaoImpl;
import com.thecodebarista.dao.CustomerDAO;
import com.thecodebarista.dao.CustomerDaoImpl;
import com.thecodebarista.dao.FirstLevelDivisionDAOImpl;
import com.thecodebarista.model.Appointment;
import com.thecodebarista.model.Country;
import com.thecodebarista.model.Customer;
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

    CustomerDAO cstDao = new CustomerDaoImpl();
    CountryDaoImpl coCBItems = new CountryDaoImpl();
    FirstLevelDivisionDAOImpl divCBItems = new FirstLevelDivisionDAOImpl();

    Country selectedCoCBItem;
    FirstLevelDivision selectedDivCBItem;

    @javafx.fxml.FXML
    private Label AddUpdateCstLabel;

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

    protected void sendCstModifyData(Customer selectedCst) throws SQLException {
        selectedCst = cstDao.extract(selectedCst.getCustomer_ID());
        System.out.println("1 Setting fields");

        customer_ID_TxtFld.setText(String.valueOf(selectedCst.getCustomer_ID()));
        phone_TxtFld.setText(String.valueOf(selectedCst.getPhone()));
        customer_Name_TxtFld.setText(String.valueOf(selectedCst.getCustomer_Name()));
        address_TxtFld.setText(String.valueOf(selectedCst.getAddress()));
        postal_Code_TxtFld.setText(String.valueOf(selectedCst.getPostal_Code()));

        int setCurrDivCo = divCBItems.extract(selectedCst.getDivision_ID()).getCountry_ID();
        Country setCurrCo = coCBItems.extract(setCurrDivCo);
        country_ID_CBox.setValue(setCurrCo);

        // FirstLevelDivisionDAOImpl divCBItems = new FirstLevelDivisionDAOImpl();
        FirstLevelDivision setCurrDiv = divCBItems.extract(selectedCst.getDivision_ID());
        division_ID_CBox.setValue(setCurrDiv);
        System.out.println("2 Setting fields");
    }


    protected void saveCstData() throws SQLException {
        int result = 0;

        String phone = phone_TxtFld.getText();
        String customer_Name = customer_Name_TxtFld.getText();
        String address = address_TxtFld.getText();
        String postal_Code = postal_Code_TxtFld.getText();

        int getCurrDiv = division_ID_CBox.getSelectionModel().getSelectedItem().getDivision_ID();
        int country_ID = division_ID_CBox.getSelectionModel().getSelectedItem().getCountry_ID();

        System.out.println("Curr. Div ID: " + getCurrDiv);

        CustomerDAO cstDAOSave = new CustomerDaoImpl();
        switch (static_AddUpdateLabel.getText()) {
            case "Add Customer":
                Customer cstIns = new Customer(0, customer_Name, address, postal_Code, phone, getCurrDiv, country_ID);
                result = cstDAOSave.insert(cstIns);
                break;
            case "Update Customer":
                int customer_ID = Integer.parseInt(customer_ID_TxtFld.getText());
                Customer cstUpd = new Customer(customer_ID, customer_Name, address, postal_Code, phone, getCurrDiv, country_ID);
                result = cstDAOSave.update(cstUpd);
                break;
        }
        System.out.println(result);
    }

    @javafx.fxml.FXML
    public void onActionSetDivisionList(ActionEvent actionEvent) throws SQLException {
 //       FirstLevelDivisionDAOImpl divCBItems = new FirstLevelDivisionDAOImpl();
        Country selectedCoCBItem = country_ID_CBox.getValue();
        division_ID_CBox.setItems(divCBItems.getDivByCountry(selectedCoCBItem.getCountry_ID()));
    }

    @javafx.fxml.FXML
    public void onActionSaveCst(ActionEvent actionEvent) {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            //Boolean validForm = displayProductRowData(btnTxt);
            boolean validForm = true;

            if (validForm) {

                saveCstData();

                // Cast window to stage
                stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
                stage.setTitle("C195-Scheduler");
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    @javafx.fxml.FXML
    public void OnActionCancel(ActionEvent actionEvent) {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());

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

        static_AddUpdateLabel = AddUpdateCstLabel;
        //CountryDaoImpl coCBItems = new CountryDaoImpl();

        try {
            country_ID_CBox.setItems(coCBItems.extractAll());
            //division_ID_CBox.setItems(divCBItems.extractAll().stream().filter(coItem -> coItem.equals(selectedCoCBItem)).sorted());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
