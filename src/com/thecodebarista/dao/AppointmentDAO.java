package com.thecodebarista.dao;

import javafx.collections.ObservableList;
import com.thecodebarista.model.Appointment;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Appointment Data Access Object Interface.
 * Generate queries for the DB appointments table
 */
public interface AppointmentDAO extends SchedulerDAO<Appointment> {

    ObservableList<String> genericData(String query) throws SQLException;
    ObservableList<Appointment> getApptByCst(int cstId, LocalDate startDt) throws SQLException;
    ObservableList<Appointment> getApptNowByUser(int id) throws SQLException;
    ObservableList<Appointment> getByMonth() throws SQLException;
    ObservableList<Appointment> getByWeekly() throws SQLException;
    ObservableList<Appointment> getByMonthType(String[] reportParams) throws SQLException;
    ObservableList<Appointment> getApptCntByPeriod(String[] reportParams) throws SQLException;
    int deleteAllCstAppt(int id);

    ObservableList<Appointment> getCstAppt(int id) throws SQLException; //not currently being used
    ObservableList<Appointment> getApptByFK(String fk, int id) throws SQLException; // not currently being used

}



