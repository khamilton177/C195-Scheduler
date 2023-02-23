package com.thecodebarista.controller;

import com.thecodebarista.dao.AppointmentDAO;
import com.thecodebarista.dao.AppointmentDaoImpl;
import com.thecodebarista.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ApptTableWeeklyCtrl extends MainMenuCtrl implements Initializable {

    @javafx.fxml.FXML
    protected TableView<Appointment> ApptTblViewWeekly;
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
     * Display the Weekly Appt ObservableList data in the TableView.
     */
    public void displayApptTblViewWeekly() throws SQLException {
        ObservableList<Appointment> allApptWeekly = FXCollections.observableArrayList();
        AppointmentDAO apptDao = new AppointmentDaoImpl();
        apptSetAllRows();
        allApptWeekly.addAll(apptDao.getByWeekly());
        ApptTblViewWeekly.setItems(allApptWeekly);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            displayApptTblViewWeekly();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
