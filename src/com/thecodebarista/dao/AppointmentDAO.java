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

    ObservableList<String> genericData(String query) throws SQLException;
    ObservableList<String> getDataDistinct(String unique, String columns, String wc) throws SQLException;
    ObservableList<Appointment> adhocQuery(String query) throws SQLException;
    ObservableList<Appointment> getCstAppt(int id) throws SQLException;
    ObservableList<Appointment> getApptByFK(String fk, int id) throws SQLException;
    ObservableList<Appointment> getApptByCst(int cstId, LocalDate startDt) throws SQLException;
    ObservableList<Appointment> getApptNowByUser(int id) throws SQLException;


    ObservableList<Appointment> getByMonth() throws SQLException;
    ObservableList<Appointment> getByWeekly() throws SQLException;
    ObservableList<Appointment> getByMonthType(String[] reportParams) throws SQLException;
    ObservableList<Appointment> getApptCntByPeriod(String[] reportParams) throws SQLException;

    int deleteAllCstAppt(int id);

}



