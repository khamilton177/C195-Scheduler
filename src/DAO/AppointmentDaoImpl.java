package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static DAO.DBConnection.useConnection;

public abstract class AppointmentDaoImpl {
    private static final String returnGenKeys = ".RETURN_GENERATED_KEYS";
    private static PreparedStatement prepStmt;

    /**
     * Build a class object from the returned ResultSet
     *
     * @param rs The return ResultSet from a doDMLv2 call.
     * @return Appointment with populated data.
     * @throws SQLException Log will have SQL statement error.
     */
    private static Appointment getAppt_rsData(ResultSet rs) throws SQLException {
        Appointment rsDataAppt;
        int appointment_id = (rs.getInt("Appointment_ID"));
        String title = rs.getString("Title");
        String description = rs.getString("Description");
        String location = rs.getString("Location");
        String type = rs.getString("Type");
        Timestamp start = rs.getTimestamp("Start");
        Timestamp end = rs.getTimestamp("End");
        int customer_id = rs.getInt("Customer_ID");
        int user_id = rs.getInt("User_ID");
        int contact_id = rs.getInt("Contact_ID");
        rsDataAppt = new Appointment(appointment_id, title, description, location, type, start, end, customer_id, user_id, contact_id);
        return rsDataAppt;
    }

    public static void insertAppt(String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_id, int user_id, int contact_id) throws SQLException {
        String sqlStmt = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID)" +
                " VAlUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Build the preparedStatement.
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, title);
            prepStmt.setString(2, description);
            prepStmt.setString(3, location);
            prepStmt.setString(4, type);
            prepStmt.setTimestamp(5, start);
            prepStmt.setTimestamp(6, end);
            prepStmt.setInt(7, customer_id);
            prepStmt.setInt(8, user_id);
            prepStmt.setInt(9, contact_id);

            // Pass the preparedStatement to be executed with plain string for validation and log.
            DML.doDMLv2(prepStmt, sqlStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        // return null;
    }

    public static void updateAppt(String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_id, int user_id, int contact_id, int appointment_id) throws SQLException {
        String sqlStmt = "UPDATE appointments SET" +
                " Title = ?," +
                " Description = ?," +
                " Location = ?," +
                " Type = ?," +
                " Start = ?'" +
                " End = ?'" +
                " Customer_ID = ?," +
                " User_ID = ?'" +
                " Contact_ID = ?)" +
                " WHERE Appointment_ID = ?)";

        // Build the preparedStatement.
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, title);
            prepStmt.setString(2, description);
            prepStmt.setString(3, location);
            prepStmt.setString(4, type);
            prepStmt.setTimestamp(5, start);
            prepStmt.setTimestamp(6, end);
            prepStmt.setInt(7, customer_id);
            prepStmt.setInt(8, user_id);
            prepStmt.setInt(9, contact_id);
            prepStmt.setInt(10, appointment_id);

            // Pass the preparedStatement to be executed with plain string for validation and log.
            DML.doDMLv2(prepStmt, sqlStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DML.doDMLv2(prepStmt, sqlStmt);

        // return prepStmt.toString();
    }

    public static void deleteAppt(int appointment_id) throws SQLException {
        String sqlStmt = "DELETE FROM appointments" +
                " WHERE Appointment_ID = ?";
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, appointment_id);
            DML.doDMLv2(prepStmt, sqlStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Appointment getAppointment(int appointment_id) throws SQLException, Exception{
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
                return getAppt_rsData(rs);
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        // No Appointment data found return null object
        System.out.println("made it here 5");
        return null;
    }

    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sqlStmt = "SELECT * FROM appointments";
        DML.doDMLv2(prepStmt, sqlStmt);

        // Get the ResultSet of the executed query.
        ResultSet rs = DML.getResult();

        // Extract the ResultSet to a class object.
        while (rs.next()) {
            System.out.println("Building List");
            Appointment rsData = getAppt_rsData(rs);
            allAppointments.add(rsData);
        }
        return allAppointments;
    }
}
