package com.thecodebarista.dao;

import com.thecodebarista.TimeMachine;
import com.thecodebarista.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.thecodebarista.model.Appointment;

import javax.swing.text.html.ListView;
import java.sql.SQLException;
import java.util.List;

public interface AppointmentDAO extends SchedulerDAO<Appointment> {

    /**
     * Get a list of all Appointments.
     * @return allAppointments  - List of all Appointments.
     */

    ObservableList<Appointment> adhocQuery(String wc) throws SQLException;
    ObservableList<Appointment> getCustomerAppts(int id) throws SQLException;
    ObservableList<Appointment> getCustomerApptsByFK(String fk, int id) throws SQLException;
    ObservableList<Appointment> getCustomerApptsByUser(int id) throws SQLException;


    Appointment getByType(String name) throws SQLException;
    ObservableList<Appointment> getByMonth() throws SQLException;
    ObservableList<Appointment> getByWeekly() throws SQLException;
    int deleteAllCstAppts(int id);

}



