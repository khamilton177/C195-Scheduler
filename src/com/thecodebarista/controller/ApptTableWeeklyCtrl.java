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
    protected TableColumn<Appointment, Integer> appointment_ID_ColWK;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> title_ColWK;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> description_ColWK;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> location_ColWK;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> contact_ID_ColWK;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> type_ColWK;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Timestamp> start_ColWK;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Timestamp> end_ColWK;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> customer_ID_ColWK;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> user_ID_ColWK;


    @javafx.fxml.FXML
    private TextField location_TxtFld;
    @javafx.fxml.FXML
    private ComboBox<Timestamp> EndTime;
    @javafx.fxml.FXML
    private ListView customer_ID_ListView;
    @javafx.fxml.FXML
    private ListView contact_ID_ListView;
    @javafx.fxml.FXML
    private Button ApptModifyBtn;
    @javafx.fxml.FXML
    private TextField title_TxtFld;
    @javafx.fxml.FXML
    private DatePicker ApptStart_DatePick;
    @javafx.fxml.FXML
    private ListView user_ID_ListView;

    @javafx.fxml.FXML
    private TextField appointment_ID_TxtFld;
    @javafx.fxml.FXML
    private TextField type_TxtFld;
    @javafx.fxml.FXML
    private ComboBox<Timestamp> StartTime;
    @javafx.fxml.FXML
    private Button ApptAddBtn;

    @javafx.fxml.FXML
    private TextField description_TxtFld;

    @javafx.fxml.FXML
    private DatePicker ApptEnd_DatePick;
    @javafx.fxml.FXML
    private Button ApptDeleteBtn;


    /**
     * Display the Appt ObservableList data in the TableView.
     */
    public void displayApptTblViewDataWeekly() throws SQLException {
        ObservableList<Appointment> allAppsWeekly = FXCollections.observableArrayList();
        AppointmentDAO apptdao = new AppointmentDaoImpl();

        appointment_ID_ColWK.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        title_ColWK.setCellValueFactory(new PropertyValueFactory<>("title"));
        description_ColWK.setCellValueFactory(new PropertyValueFactory<>("description"));
        location_ColWK.setCellValueFactory(new PropertyValueFactory<>("location"));
        type_ColWK.setCellValueFactory(new PropertyValueFactory<>("type"));
        start_ColWK.setCellValueFactory(new PropertyValueFactory<>("Start"));
        end_ColWK.setCellValueFactory(new PropertyValueFactory<>("End"));
        customer_ID_ColWK.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        user_ID_ColWK.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        contact_ID_ColWK.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));

        allAppsWeekly.addAll(apptdao.extractAll());
        ApptTblViewWeekly.setItems(allAppsWeekly);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            displayApptTblViewDataWeekly();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
