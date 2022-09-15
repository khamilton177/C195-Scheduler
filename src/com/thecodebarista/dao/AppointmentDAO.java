package com.thecodebarista.dao;

import com.thecodebarista.TimeMachine;
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
    @Override
    ObservableList<Appointment> extractAll() throws SQLException;
    // ListView<T> extractAll() throws SQLException;


}



