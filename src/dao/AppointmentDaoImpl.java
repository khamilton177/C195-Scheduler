package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static dao.DBConnection.useConnection;
import static dao.SchedulerUtilities.getApptData;

public class AppointmentDaoImpl implements AppointmentDAO {
    private static final String returnGenKeys = ".RETURN_GENERATED_KEYS";
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
            DML.doDMLv2(prepStmt, sqlStmt);
            System.out.println("made it here 2");

            // Get the ResultSet of the executed query.
            System.out.println("made it here 3");
            ResultSet rs = DML.getResult();

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

        String sqlStmt = "SELECT * FROM appointments";
        prepStmt = useConnection().prepareStatement(sqlStmt);
        DML.doDMLv2(prepStmt, sqlStmt);

        // Get the ResultSet of the executed query.
        ResultSet rs = DML.getResult();

        // Extract the ResultSet to a class object.
        System.out.println("Building Appointments List");
        while (rs.next()) {
            Appointment appointment = getApptData(rs);
            allAppointments.add(appointment);
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
            prepStmt.setInt(7, appointment.getCustomerID());
            prepStmt.setInt(8, appointment.getUserID());
            prepStmt.setInt(9, appointment.getContactID());

            // Pass the preparedStatement to be executed with plain string for validation and log.
            rowsAffected = DML.doDMLv2(prepStmt, sqlStmt);
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
            prepStmt.setInt(7, appointment.getCustomerID());
            prepStmt.setInt(8, appointment.getUserID());
            prepStmt.setInt(9, appointment.getContactID());
            prepStmt.setInt(10, appointment.getAppointmentID());

            // Pass the preparedStatement to be executed with plain string for validation and log.
            rowsAffected = DML.doDMLv2(prepStmt, sqlStmt);
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
            prepStmt.setInt(1, appointment.getAppointmentID());
            rowsAffected = DML.doDMLv2(prepStmt, sqlStmt);
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
}
