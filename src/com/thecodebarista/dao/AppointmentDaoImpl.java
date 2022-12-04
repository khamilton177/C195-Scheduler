package com.thecodebarista.dao;

import com.thecodebarista.TimeMachine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.thecodebarista.model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.thecodebarista.dao.DBConnection.useConnection;
import static com.thecodebarista.dao.DMLUtils.getApptData;

public class AppointmentDaoImpl implements AppointmentDAO, TimeMachine {
    private static String returnGenKeys = ".RETURN_GENERATED_KEYS";
    private static PreparedStatement prepStmt;
    private int rowsAffected = 0; // Setting to 0. SELECT statements don't return a value so this is a nominal value.


    @Override
    public Appointment extract(int appointment_id) throws SQLException {
        Appointment appointment = null;
        String sqlStmt="SELECT * FROM appointments" +
                " WHERE Appointment_ID = ?";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, appointment_id);
            System.out.println("made it here 1");
            // Pass the preparedStatement to be executed.

            // Pass the preparedStatement to be executed.
            DMLUtils.doDMLv2(prepStmt, sqlStmt);
            System.out.println("made it here 2");

            // Get the ResultSet of the executed query.
            System.out.println("made it here 3");
            ResultSet rs = DMLUtils.getResult();

            // If Appointment data found, extract the ResultSet to a Appointment object and return.
            if (rs.next()) {
                System.out.println("made it here 4");
                appointment = getApptData(rs);
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        // No Appointment data found return null object
        System.out.println("made it here 5");
        return appointment;
    }

    @Override
    public ObservableList<Appointment> extractAll() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    //    List<Appointment> allAppointments = new ArrayList<>();

        try{
            String sqlStmt = "SELECT * FROM appointments";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

            // Extract the ResultSet to a class object.
            System.out.println("Building Appointments List");
            while (rs.next()) {
                Appointment appointment = getApptData(rs);
                allAppointments.add(appointment);
            }
            return allAppointments;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return allAppointments;
    }

    @Override
    public int insert(Appointment appointment) throws SQLException {
        String sqlStmt = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID)" +
                " VAlUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Build the preparedStatement.
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, appointment.getTitle());
            prepStmt.setString(2, appointment.getDescription());
            prepStmt.setString(3, appointment.getLocation());
            prepStmt.setString(4, appointment.getType());
            prepStmt.setTimestamp(5, appointment.getStart());
            prepStmt.setTimestamp(6, appointment.getEnd());
            prepStmt.setInt(7, appointment.getCustomer_ID());
            prepStmt.setInt(8, appointment.getUser_ID());
            prepStmt.setInt(9, appointment.getContact_ID());

            // Pass the preparedStatement to be executed with plain string for validation and log.
            rowsAffected = DMLUtils.doDMLv2(prepStmt, sqlStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return rowsAffected;
    }

    @Override
    public int update(Appointment appointment) throws SQLException {
        String sqlStmt = "UPDATE appointments SET" +
                " Title = ?," +
                " Description = ?," +
                " Location = ?," +
                " Type = ?," +
                " Start = ?," +
                " End = ?," +
                " Customer_ID = ?," +
                " User_ID = ?," +
                " Contact_ID = ?" +
                " WHERE Appointment_ID = ?";

        // Build the preparedStatement.
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, appointment.getTitle());
            prepStmt.setString(2, appointment.getDescription());
            prepStmt.setString(3, appointment.getLocation());
            prepStmt.setString(4, appointment.getType());
            prepStmt.setTimestamp(5, appointment.getStart());
            prepStmt.setTimestamp(6, appointment.getEnd());
            prepStmt.setInt(7, appointment.getCustomer_ID());
            prepStmt.setInt(8, appointment.getUser_ID());
            prepStmt.setInt(9, appointment.getContact_ID());
            prepStmt.setInt(10, appointment.getAppointment_ID());

            // Pass the preparedStatement to be executed with plain string for validation and log.
            rowsAffected = DMLUtils.doDMLv2(prepStmt, sqlStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int delete(Appointment appointment) {
        String sqlStmt = "DELETE FROM appointments" +
                " WHERE Appointment_ID = ?";
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, appointment.getAppointment_ID());
            rowsAffected = DMLUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int save(Appointment appointment) throws SQLException {
        return 0;
    }

    @Override
    public ObservableList<Appointment> adhocQuery(String wc) throws SQLException{
        ObservableList<Appointment> wcAppointments = FXCollections.observableArrayList();

        try{
            String sqlStmt = wc;
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

            // Extract the ResultSet to a class object.
            System.out.println("Return Adhoc query results");
            while (rs.next()) {
                Appointment appointment = getApptData(rs);
                wcAppointments.add(appointment);
            }
            return wcAppointments;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return wcAppointments;
    }

    public ObservableList<Appointment> getCustomerAppts(int id) throws SQLException{
        ObservableList<Appointment> cstAppointments = FXCollections.observableArrayList();
        String sqlStmt = "SELECT appointment_id FROM appointments" +
                " WHERE Customer_ID = ?";
        prepStmt = useConnection().prepareStatement(sqlStmt);
        DMLUtils.doDMLv2(prepStmt, sqlStmt);

        // Get the ResultSet of the executed query.
        ResultSet rs = DMLUtils.getResult();

        // Extract the ResultSet to a class object.
        System.out.println("Building Associated Appointments List");
        while (rs.next()) {
            Appointment appointment = getApptData(rs);
            cstAppointments.add(appointment);
        }
        return cstAppointments;
    }

    @Override
    public Appointment getByType(String name) throws SQLException {
        return null;
    }

    @Override
    public Appointment getByMonth(String name) throws SQLException {
        return null;
    }

    @Override
    public Appointment getByWeek(String name) throws SQLException {
        return null;
    }

    public int deleteCstAppointment(Appointment appointment) {
        String sqlStmt = "DELETE FROM appointments" +
                " WHERE Appointment_ID = ?";
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, appointment.getAppointment_ID());
            rowsAffected = DMLUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public LocalDateTime makeLDT() {
        return null;
    }
}
