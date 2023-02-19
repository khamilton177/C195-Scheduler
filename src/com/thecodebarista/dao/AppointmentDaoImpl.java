package com.thecodebarista.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.thecodebarista.model.Appointment;
import javafx.scene.control.TableColumn;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.thecodebarista.dao.DBConnection.useConnection;
import static com.thecodebarista.dao.DMLUtils.getApptData;

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
            prepStmt = useConnection().prepareStatement(sqlStmt, Statement.RETURN_GENERATED_KEYS);
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

            ResultSet rs = prepStmt.getGeneratedKeys();
            rs.next();
            int Appointment_ID = rs.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

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

    public int deleteAllCstAppt(int id) {
        String sqlStmt = "DELETE FROM appointments" +
                " WHERE Customer_ID = ?";
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, id);
            rowsAffected = DMLUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ObservableList<Appointment> getCstAppt(int id) throws SQLException{
        ObservableList<Appointment> cstAppointments = FXCollections.observableArrayList();

        try {
            String sqlStmt = "SELECT * FROM appointments" +
                    " WHERE Customer_ID = ?";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, id);
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
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return cstAppointments;
    }

    @Override
    public ObservableList<Appointment> getCstApptByFK(String fk, int id) throws SQLException{
        ObservableList<Appointment> cstAppointments = FXCollections.observableArrayList();

        String fkID = fk;
        int qID = id;
        try {
            String sqlStmt = "SELECT * FROM appointments" +
                    " WHERE " + fkID +
                    " = " + qID;
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
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return cstAppointments;
    }

    @Override
    public ObservableList<Appointment> getApptByCst(int cstId, LocalDate startDt) throws SQLException{
        ObservableList<Appointment> cstAppointments = FXCollections.observableArrayList();

        try {
            String sqlStmt = "SELECT * FROM appointments" +
                    " WHERE (Customer_ID = ?) &&" +
                    " (DATE(Start) = ? || DATE(Start) = ?-1 || DATE(Start) = ?+1)";

            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, cstId);
            prepStmt.setDate(2, Date.valueOf(startDt));
            prepStmt.setDate(3, Date.valueOf(startDt));
            prepStmt.setDate(4, Date.valueOf(startDt));
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
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return cstAppointments;
    }

    @Override
    public ObservableList<Appointment> getApptNowByUser(int id) throws SQLException{
        ObservableList<Appointment> cstAppointments = FXCollections.observableArrayList();
        int qID = id;

        try {
            String sqlStmt = "SELECT * FROM appointments" +
                " WHERE User_ID = " +
                qID +
                " ORDER BY Start";

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
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return cstAppointments;
    }

    @Override
    public ObservableList<Appointment> getByWeekly() throws SQLException {
        ObservableList<Appointment> allApptWeekly = FXCollections.observableArrayList();

        try{
            String sqlStmt = "SELECT Appointment_ID" +
                    ", Title" +
                    ", Description" +
                    ", Location" +
                    ", Contact_ID" +
                    ", Type" +
                    ", Start" +
                    ", End" +
                    ", Customer_ID" +
                    ", User_ID" +
                    " FROM appointments" +
                    " WHERE YEARWEEK(Start) = YEARWEEK(NOW())" +
                    " ORDER BY End";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

            // Extract the ResultSet to a class object.
            System.out.println("Building Appt. Wkly List");
            while (rs.next()) {
                Appointment appointment = getApptData(rs);
                allApptWeekly.add(appointment);
            }
            return allApptWeekly;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return allApptWeekly;
    }

    @Override
    public ObservableList<Appointment> getByMonth() throws SQLException {
        ObservableList<Appointment> allApptMonthly = FXCollections.observableArrayList();

        try{
            String sqlStmt = "SELECT Appointment_ID" +
                    ", Title" +
                    ", Description" +
                    ", Location" +
                    ", Contact_ID" +
                    ", Type" +
                    ", Start" +
                    ", End" +
                    ", Customer_ID" +
                    ", User_ID" +
                    " FROM appointments" +
                    " WHERE MONTH(Start) = MONTH(NOW())" +
                    " ORDER BY End";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

            // Extract the ResultSet to a class object.
            System.out.println("Building Appt. Monthly List");
            while (rs.next()) {
                Appointment appointment = getApptData(rs);
                allApptMonthly.add(appointment);
            }
            return allApptMonthly;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return allApptMonthly;
    }

    @Override
    public ObservableList<String> genericData(String query) throws SQLException{
        ObservableList<String> stringData = FXCollections.observableArrayList();
        int i;

        try{
            String sqlStmt = query;
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                List<String> rowData = new ArrayList<>();
                for (i = 1; i <= columnCount; i++) {
                    rowData.add(rs.getString(metaData.getColumnLabel(i)));
                }
                stringData.addAll(rowData);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return stringData;
    }

    @Override
    public ObservableList<String> getDataDistinct(String unique, String columns, String wc) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Appointment> getByMonthType(String[] reportParams) throws SQLException {
        ObservableList<Appointment> allCstByMoTypeTotal = FXCollections.observableArrayList();
        String wcMonth = reportParams[0];
        String wcType = reportParams[1];
        int wc = Integer.parseInt(reportParams[2]);
        StringBuilder sqlStmt = new StringBuilder("SELECT monthname(Start) as Month" +
                ", Type" +
                ", count(*) as Count" +
                " FROM appointments");

        // Create WHERE clause based on params passed
        switch (wc) {
            case 3:
                sqlStmt.append(" WHERE monthname(Start) = '" + wcMonth +
                        "' AND Type = '" + wcType +
                        "'");
                break;
            case 2:
                sqlStmt.append(" WHERE monthname(Start) = '" + wcMonth +
                        "'");
                break;
            case 1:
                sqlStmt.append(" WHERE Type = '" + wcType +
                        "'");
                break;
            default:
                break;
        }
        sqlStmt.append(" Group By Month, Type");

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt.toString());
            DMLUtils.doDMLv2(prepStmt, sqlStmt.toString());

            // Get the ResultSet and ResultSet metadata of the executed query.
            ResultSet rs = DMLUtils.getResult();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Building Appointment Count By Month and Type");
            // Extract the ResultSet to a class object.
            while (rs.next()) {
                Appointment rptRows = DMLUtils.getReportData(rs);
                allCstByMoTypeTotal.add(rptRows);
            }
            return allCstByMoTypeTotal;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return allCstByMoTypeTotal;
    }

    @Override
    public ObservableList<Appointment> adhocQuery(String query) throws SQLException{
        ObservableList<Appointment> adhocData = FXCollections.observableArrayList();
        int i = 1;

        try{
            String sqlStmt = query;
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

            // Extract the ResultSet to a class object.
            System.out.println("Return Adhoc query results");
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            //TableColumn[] colName = new TableColumn[columnCount];
            for (i = 1; i <= columnCount; i++) {
                TableColumn colName = new TableColumn<>(metaData.getColumnLabel(i));
                //columns.add(colName.getText());
            }
            while (rs.next()) {
                switch (metaData.getColumnTypeName(i)) {
                    case "int":
                        rs.getInt(metaData.getColumnLabel(i));
                        break;
                    case "timestamp":
                    case "datetime":
                        rs.getTimestamp(metaData.getColumnLabel(i));
                        break;
                    default:
                        rs.getString(metaData.getColumnLabel(i));
                        break;
                }

                for (Appointment column : adhocData) {
                    rs.getString(metaData.getColumnLabel(i).toString());
                }
            }

            //Appointment appointment = getApptData(rs);
            // stringData.add(appointment);
            //}
            return adhocData;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return adhocData;
    }

}
