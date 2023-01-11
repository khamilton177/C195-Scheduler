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
     * Display the Appt ObservableList data in the TableView.
     */
    public void displayApptTblViewWeekly() throws SQLException {
        ObservableList<Appointment> allApptWeekly = FXCollections.observableArrayList();
        AppointmentDAO apptdao = new AppointmentDaoImpl();

        appointment_ID_Col.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        title_Col.setCellValueFactory(new PropertyValueFactory<>("title"));
        description_Col.setCellValueFactory(new PropertyValueFactory<>("description"));
        location_Col.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact_ID_Col.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));
        type_Col.setCellValueFactory(new PropertyValueFactory<>("type"));
        start_Col.setCellValueFactory(new PropertyValueFactory<>("start"));
        end_Col.setCellValueFactory(new PropertyValueFactory<>("end"));
        customer_ID_Col.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        user_ID_Col.setCellValueFactory(new PropertyValueFactory<>("user_ID"));

        allApptWeekly.addAll(apptdao.getByWeekly());
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
