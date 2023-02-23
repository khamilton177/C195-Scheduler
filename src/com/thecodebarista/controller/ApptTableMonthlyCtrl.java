package com.thecodebarista.controller;

import com.thecodebarista.dao.AppointmentDAO;
import com.thecodebarista.dao.AppointmentDaoImpl;
import com.thecodebarista.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

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
     */
    public void displayApptTblViewMonthly() throws SQLException {
        ObservableList<Appointment> allApptMonthly = FXCollections.observableArrayList();
        AppointmentDAO apptDao = new AppointmentDaoImpl();
        apptSetAllRows();
        allApptMonthly.addAll(apptDao.getByMonth());
        ApptTblViewMonthly.setItems(allApptMonthly);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            displayApptTblViewMonthly();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
