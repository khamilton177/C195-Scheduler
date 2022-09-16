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
import java.util.ResourceBundle;

public class ApptAddModifyFormCtrl extends MainMenuCtrl implements Initializable {

    @javafx.fxml.FXML
    private TableView<Appointment> ApptTblView;
    @javafx.fxml.FXML
    private TableColumn<Appointment, ?> appointment_ID_Col;
    @javafx.fxml.FXML
    private TableColumn<?, ?> title_Col;
    @javafx.fxml.FXML
    private TableColumn<?, ?> description_Col;
    @javafx.fxml.FXML
    private TableColumn<?, ?> location_Col;
    @javafx.fxml.FXML
    private TableColumn<?, ?> type_Col;
    @javafx.fxml.FXML
    private TableColumn<?, ?> start_Col;
    @javafx.fxml.FXML
    private TableColumn<?, ?> end_Col;
    @javafx.fxml.FXML
    private TableColumn<?, ?> customer_ID_Col;
    @javafx.fxml.FXML
    private TableColumn<?, ?> user_ID_Col;
    @javafx.fxml.FXML
    private TableColumn<?, ?> contact_ID_Col;
    @javafx.fxml.FXML
    private TextField location_TxtFld;
    @javafx.fxml.FXML
    private ComboBox EndTime;
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
    private ComboBox StartTime;
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
    public void displayApptTblViewData() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        AppointmentDAO apptdao = new AppointmentDaoImpl();

        appointment_ID_Col.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        title_Col.setCellValueFactory(new PropertyValueFactory<>("title"));
        description_Col.setCellValueFactory(new PropertyValueFactory<>("description"));
        location_Col.setCellValueFactory(new PropertyValueFactory<>("location"));
        type_Col.setCellValueFactory(new PropertyValueFactory<>("type"));
        start_Col.setCellValueFactory(new PropertyValueFactory<>("Start"));
        end_Col.setCellValueFactory(new PropertyValueFactory<>("End"));
        customer_ID_Col.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        user_ID_Col.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        contact_ID_Col.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));

        allAppointments.addAll(apptdao.extractAll());

        ApptTblView.setItems(allAppointments);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            displayApptTblViewData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
