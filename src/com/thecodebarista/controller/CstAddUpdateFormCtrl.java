package com.thecodebarista.controller;

import com.thecodebarista.dao.CountryDaoImpl;
import com.thecodebarista.dao.CustomerDAO;
import com.thecodebarista.dao.CustomerDaoImpl;
import com.thecodebarista.dao.FirstLevelDivisionDAOImpl;
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

/**
 * Customer controller for Add/Update form.
 */
public class CstAddUpdateFormCtrl extends MainMenuCtrl implements Initializable {

    CustomerDAO cstDao = new CustomerDaoImpl();
    CountryDaoImpl coCBItems = new CountryDaoImpl();
    FirstLevelDivisionDAOImpl divCBItems = new FirstLevelDivisionDAOImpl();

    @javafx.fxml.FXML
    private Label AddUpdateCstLabel;

    @javafx.fxml.FXML
    private TextField customer_ID_TxtFld;

    @javafx.fxml.FXML
    private Button CstSaveBtn;

    @javafx.fxml.FXML
    private Button CstCancelBtn;

    /**
     * Method populates the Update Form with the Customer data from database.
     * @param selectedCst
     * @throws SQLException
     */
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

        FirstLevelDivision setCurrDiv = divCBItems.extract(selectedCst.getDivision_ID());
        division_ID_CBox.setValue(setCurrDiv);
        System.out.println("2 Setting fields");
    }

    /**
     * Method saves form data for New and Updated Customers.
     * @throws SQLException
     */
    protected void saveCstData() throws SQLException {
        int result = 0;

        String phone = phone_TxtFld.getText();
        String customer_Name = customer_Name_TxtFld.getText();
        String address = address_TxtFld.getText();
        String postal_Code = postal_Code_TxtFld.getText();
        int getCurrDiv = division_ID_CBox.getValue().getDivision_ID();
        int country_ID = division_ID_CBox.getValue().getCountry_ID();

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
        Country selectedCoCBItem = country_ID_CBox.getValue();
        division_ID_CBox.setItems(divCBItems.getDivByCountry(selectedCoCBItem.getCountry_ID()));
    }

    /**
     * Save new or updated customer.
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void onActionSaveCst(ActionEvent actionEvent) {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + btnTxt);

        try{
            boolean validForm = validateFormFields(btnTxt);

            if (validForm) {
                saveCstData();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
                scene = loader.load();
                MainMenuCtrl formController = loader.getController();
                formController.setMainTabPane("CstTab");

                if (sessionUserAccess < 1) {
                    formController.setCurrentUserViewAccess(sessionUserAccess);
                }
                stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                //scene = FXMLLoader.load(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
                stage.setTitle("C195-Global Consulting Scheduler");
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    /**
     * OnAction method cancels New/Updated Customer activity. Returns user to Main Menu.
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void OnActionCancel(ActionEvent actionEvent) {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
            scene = loader.load();
            MainMenuCtrl formController = loader.getController();
            formController.setMainTabPane("CstTab");

            if (sessionUserAccess < 1) {
                formController.setCurrentUserViewAccess(sessionUserAccess);
            }
            // Cast window to stage
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            //scene = FXMLLoader.load(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
            stage.setTitle("C195-Global Consulting Scheduler");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    /**
     * Initializes the CstAddUpdateFormCtrl class.
     * Sets the label text to the action performed by the view.
     * <BR>Loads the items in the Country ComboBox.
     * @param url default application URL
     * @param resourceBundle default application ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        static_AddUpdateLabel = AddUpdateCstLabel;

        try {
            country_ID_CBox.setItems(coCBItems.extractAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
