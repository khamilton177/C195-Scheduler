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
    protected TableView ApptTblViewMonthly;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> appointment_ID_ColMo;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> title_ColMo;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> description_ColMo;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> location_ColMo;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> contact_ID_ColMo;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> type_ColMo;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Timestamp> start_ColMo;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Timestamp> end_ColMo;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> customer_ID_ColMo;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> user_ID_ColMo;

    /**
     * Display the Appt ObservableList data in the TableView.
     */
    public void displayApptTblViewMonthly() throws SQLException {
        ObservableList<Appointment> allApptMonthly = FXCollections.observableArrayList();
        AppointmentDAO apptdao = new AppointmentDaoImpl();

        appointment_ID_ColMo.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        title_ColMo.setCellValueFactory(new PropertyValueFactory<>("title"));
        description_ColMo.setCellValueFactory(new PropertyValueFactory<>("description"));
        location_ColMo.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact_ID_ColMo.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));
        type_ColMo.setCellValueFactory(new PropertyValueFactory<>("type"));
        start_ColMo.setCellValueFactory(new PropertyValueFactory<>("Start"));
        end_ColMo.setCellValueFactory(new PropertyValueFactory<>("End"));
        customer_ID_ColMo.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        user_ID_ColMo.setCellValueFactory(new PropertyValueFactory<>("user_ID"));

        allApptMonthly.addAll(apptdao.extractAll());
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
