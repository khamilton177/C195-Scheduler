package com.thecodebarista.controller;

import com.thecodebarista.dao.AppointmentDAO;
import com.thecodebarista.dao.AppointmentDaoImpl;
import com.thecodebarista.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * Appointment Monthly Tab controller.
 * Corresponding FXML (ApptTblMonthlyView) is Included in main-menu.fxml.
 */
public class ApptTableMonthlyCtrl extends MainMenuCtrl implements Initializable {

    @javafx.fxml.FXML
    protected TableView<Appointment> ApptTblViewMonthly;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> appointment_ID_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> title_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String>  description_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String>  location_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> contact_ID_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String>  type_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Timestamp>  start_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Timestamp>  end_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> customer_ID_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> user_ID_Col;

    /**
     * Display the Monthly Appt ObservableList data in the TableView.
     * @throws SQLException catch in printStackTrace
     */
    public void displayApptTblViewMonthly() throws SQLException {
        try {
            ObservableList<Appointment> allApptMonthly = FXCollections.observableArrayList();
            AppointmentDAO apptDao = new AppointmentDaoImpl();
            apptSetAllRows();
            allApptMonthly.addAll(apptDao.getByMonth());
            ApptTblViewMonthly.setItems(allApptMonthly);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the ApptTableMonthlyCtrl class.
     * Call the tableview method for Appointment Monthly Tab.
     * @param url default application URL
     * @param rb default application ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            displayApptTblViewMonthly();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
