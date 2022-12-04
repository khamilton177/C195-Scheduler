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

    Appointment getByType(String name) throws SQLException;
    Appointment getByMonth(String name) throws SQLException;
    Appointment getByWeek(String name) throws SQLException;


    //   void displayApptTblViewData() throws SQLException;


}



