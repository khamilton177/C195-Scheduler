package dao;

import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.SQLException;

public interface AppointmentDAO extends SchedulerDAO<Appointment>{

    /**
     * Get a list of all Appointments.
     * @return allAppointments  - List of all Appointments.
     */
    @Override
    ObservableList<Appointment> extractAll() throws SQLException;
}

