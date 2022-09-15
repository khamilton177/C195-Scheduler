package com.thecodebarista.controller;

import com.mysql.cj.conf.StringProperty;
import com.thecodebarista.dao.AppointmentDAO;
import com.thecodebarista.dao.AppointmentDaoImpl;
import com.thecodebarista.model.Appointment;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ApptAddModifyFormCtrl extends MainMenuCtrl implements Initializable {

    @javafx.fxml.FXML
    private TableView<Appointment> ApptTblView;
    @javafx.fxml.FXML
    private TableColumn<Appointment, ?> ApptIdCol;
    @javafx.fxml.FXML
    private TableColumn<?, ?> ApptTitleCol;
    @javafx.fxml.FXML
    private TableColumn<?, ?> ApptDescCol;
    @javafx.fxml.FXML
    private TableColumn<?, ?> ApptLocCol;
    @javafx.fxml.FXML
    private TableColumn<?, ?> ApptTypeCol;
    @javafx.fxml.FXML
    private TableColumn<?, ?> ApptStartCol;
    @javafx.fxml.FXML
    private TableColumn<?, ?> ApptEndCol;
    @javafx.fxml.FXML
    private TableColumn<?, ?> ApptCstIDCol;
    @javafx.fxml.FXML
    private TableColumn<?, ?> ApptUserIDCol;
    @javafx.fxml.FXML
    private TableColumn<?, ?> ApptCntIDCol;

    /**
     * Display the Appt ObservableList data in the TableView.
     */
    public void displayTblViewData() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        AppointmentDAO apptdao = new AppointmentDaoImpl();
        allAppointments.addAll(apptdao.extractAll());

        ApptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        ApptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        ApptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        ApptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        ApptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        ApptStartCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        ApptEndCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        ApptCstIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        ApptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        ApptCntIDCol.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));

        allAppointments.addAll(apptdao.extractAll());

        ApptTblView.setItems(allAppointments);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        AppointmentDAO apptdao = new AppointmentDaoImpl();

        ApptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        ApptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        ApptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        ApptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        ApptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        ApptStartCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        ApptEndCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        ApptCstIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        ApptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        ApptCntIDCol.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));

        try {
  //          ObservableList<Appointment> allAppointments = apptdao.extractAll();

            allAppointments.addAll(apptdao.extractAll());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        ApptTblView.setItems(allAppointments);

    }
}
