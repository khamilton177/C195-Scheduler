package com.thecodebarista.controller;

import com.thecodebarista.dao.CustomerDAO;
import com.thecodebarista.dao.CustomerDaoImpl;
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
    protected TableColumn<?, ?> customer_ID_Col;
    @javafx.fxml.FXML
    protected TableColumn<?, ?> customer_Name_Col;
    @javafx.fxml.FXML
    protected TableColumn<?, ?> phone_Col;
    @javafx.fxml.FXML
    protected TableColumn<?, ?> division_ID_Col;
    @javafx.fxml.FXML
    protected TableColumn<?, ?> postal_Code_Col;
    @javafx.fxml.FXML
    protected TableColumn<?, ?> address_Col;

    /**
     * Displays the Customer ObservableList data in the TableView.
     */

//
    public void displayCstTblViewData() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        CustomerDAO cstdao = new CustomerDaoImpl();

        // Set the cell to the property value for the specified column name in string
        customer_ID_Col.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        customer_Name_Col.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        phone_Col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        division_ID_Col.setCellValueFactory(new PropertyValueFactory<>("division_ID"));
        postal_Code_Col.setCellValueFactory(new PropertyValueFactory<>("postal_Code"));
        address_Col.setCellValueFactory(new PropertyValueFactory<>("address"));

        allCustomers.addAll(cstdao.extractAll());
        CstTblView.setItems(allCustomers);
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
