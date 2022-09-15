package com.thecodebarista.controller;

import com.thecodebarista.dao.CustomerDAO;
import com.thecodebarista.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CstAddModifyFormCtrl extends MainMenuCtrl implements Initializable {
    @javafx.fxml.FXML
    protected TableView<Customer> CstTblView;
    @javafx.fxml.FXML
    protected TableColumn<?, ?> CstIdCol;
    @javafx.fxml.FXML
    protected TableColumn<?, ?> CstNameCol;
    @javafx.fxml.FXML
    protected TableColumn<?, ?> CstPhoneCol;
    @javafx.fxml.FXML
    protected TableColumn<?, ?> CstDivisionIdCol;
    @javafx.fxml.FXML
    protected TableColumn<?, ?> CstPostalCodeCol;
    @javafx.fxml.FXML
    protected TableColumn<?, ?> CstAddressCol;

    /**
     * Displays the Customer ObservableList data in the TableView.
     */

//
    public void displayCstTblViewData() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        //   ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        CustomerDAO cstdao = new com.thecodebarista.dao.CustomerDaoImpl();
        allCustomers.addAll(cstdao.extractAll());
        CstTblView.setItems(allCustomers);

        // Set the cell to the property value for the specified column name in string
        CstIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        CstNameCol.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        CstPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        CstDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("division_ID"));
        CstPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postal_Code"));
        CstAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            displayCstTblViewData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
