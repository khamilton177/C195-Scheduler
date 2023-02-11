package com.thecodebarista.dao;

import javafx.collections.ObservableList;
import com.thecodebarista.model.Appointment;

import java.sql.SQLException;
import java.time.LocalDate;

public interface AppointmentDAO extends SchedulerDAO<Appointment> {

    /**
     * Get a list of all Appointments.
     * @return allAppointments  - List of all Appointments.
     */

    ObservableList<Appointment> adhocQuery(String wc) throws SQLException;
    ObservableList<Appointment> getCstAppt(int id) throws SQLException;
    ObservableList<Appointment> getCstApptByFK(String fk, int id) throws SQLException;
    ObservableList<Appointment> getApptByCstnUser(int cstId, LocalDate startDt) throws SQLException;
    ObservableList<Appointment> getApptNowByUser(int id) throws SQLException;


    Appointment getByType(String name) throws SQLException;
    ObservableList<Appointment> getByMonth() throws SQLException;
    ObservableList<Appointment> getByWeekly() throws SQLException;
    int deleteAllCstAppts(int id);

}



