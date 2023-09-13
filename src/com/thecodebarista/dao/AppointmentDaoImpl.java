package com.thecodebarista.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.thecodebarista.model.Appointment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.thecodebarista.dao.DBConnection.useConnection;
import static com.thecodebarista.dao.DBUtils.getApptData;

/**
 * Implements the Abstract methods in the AppointmentDAO class.
 */
public class AppointmentDaoImpl implements AppointmentDAO {
    private static final String returnGenKeys = ".RETURN_GENERATED_KEYS";
    private static PreparedStatement prepStmt;
    private int rowsAffected = 0; // Setting to 0. SELECT statements don't return a value so this is a nominal value.

    /**
     * Select all row data for Appointment of selected Appointment_ID.
     * @param appointment_id - The Appointment_ID for the query WHERE clause.
     * @return the Appointment row if found or Null.
     * @throws SQLException
     */
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
            DBUtils.doDMLv2(prepStmt, sqlStmt);
            System.out.println("made it here 2");

            // Get the ResultSet of the executed query.
            System.out.println("made it here 3");
            ResultSet rs = DBUtils.getResult();

            // If Appointment data found, extract the ResultSet to an Appointment object and return.
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

    /**
     * Select all row data for all Appointments.
     * @return ObservableList of Appointment rows.
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointment> extractAll() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try{
            String sqlStmt = "SELECT * FROM appointments";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

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

    /**
     * Insert a new Appointment in DB appointments table.
     * @param appointment - Appointment constructor data from New Appointment form.
     * @return - rowsAffected (int)
     * @throws SQLException
     */
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
            rowsAffected = DBUtils.doDMLv2(prepStmt, sqlStmt);

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

    /**
     * Updates the selected row in the DB appointments table.
     * @param appointment - The selected Appointment in the Update Appointment form as the WHERE clause.
     * @return rowsAffected (int)
     * @throws SQLException
     */
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
            rowsAffected = DBUtils.doDMLv2(prepStmt, sqlStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * Deletes the selected appointment from the DB appointments table.
     * @param appointment - The selected Appointment from the Main Menu as the WHERE clause.
     * @return rowsAffected (int)
     */
    @Override
    public int delete(Appointment appointment) {
        String sqlStmt = "DELETE FROM appointments" +
                " WHERE Appointment_ID = ?";
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, appointment.getAppointment_ID());
            rowsAffected = DBUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ObservableList<Appointment> sfQuery(String sfQuery) throws SQLException {
        return null;
    }

    @Override
    public int existColumns(String column) throws SQLException {
        return 0;
    }

    @Override
    public int alterTable(String column) throws SQLException {
        return 0;
    }

    @Override
    public int showIndexes(String column) throws SQLException {
        return 0;
    }

    @Override
    public int makeColumnUnique(String column) throws SQLException {
        return 0;
    }

    /**
     * Query Method used to Delete all Associated Customer Appointments in Bulk.
     * @param id - Customer_ID of appointments to be deleted.
     * @return - rowsAffected ( all rows deleted - int)
     */
    public int deleteAllCstAppt(int id) {
        String sqlStmt = "DELETE FROM appointments" +
                " WHERE Customer_ID = ?";
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, id);
            rowsAffected = DBUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * Query Method for all Appointments for the selected customer.
     * @param id - The Customer_ID for the WHERE clause
     * @return ObservableList of the customers appointments.
     */
    @Override
    public ObservableList<Appointment> getCstAppt(int id) {
        ObservableList<Appointment> cstAppointments = FXCollections.observableArrayList();

        try {
            String sqlStmt = "SELECT * FROM appointments" +
                    " WHERE Customer_ID = ?";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, id);
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

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

    /**
     * Query Method allows any foreign key field in Appointments to be specified.
     * @param fk - The field name of the foreign key (i.e. Customer_ID).
     * @param id - The id of the foreign key (int).
     * @return ObservableList of the Appointment rows found.
     */
    @Override
    public ObservableList<Appointment> getApptByFK(String fk, int id) {
        ObservableList<Appointment> cstAppointments = FXCollections.observableArrayList();

        String fkID = fk;
        int qID = id;
        try {
            String sqlStmt = "SELECT * FROM appointments" +
                    " WHERE " + fkID +
                    " = " + qID;
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

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

    /**
     * Query Method used to identify Appointment overlaps and conflicts.
     * Narrows date search.
     * @param cstId - Customer_ID for WHERE Clause.
     * @param startDt - Date of the appointment being created.
     * @return ObservableList of return Appointment rows.
     */
    @Override
    public ObservableList<Appointment> getApptByCst(int cstId, LocalDate startDt) {
        ObservableList<Appointment> cstAppointments = FXCollections.observableArrayList();

        try {
            String sqlStmt = "SELECT * FROM appointments" +
                    " WHERE (Customer_ID = ?) &&" +
                    " (DATE(Start) = DATE(?) || DATE(Start) = DATE(?) - 1 || DATE(Start) = DATE(?) + 1)";

            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, cstId);
            prepStmt.setDate(2, Date.valueOf(startDt));
            prepStmt.setDate(3, Date.valueOf(startDt));
            prepStmt.setDate(4, Date.valueOf(startDt));
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

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

    /**
     * Query Method used to Identify appointments for 15 minute Login User Alert.
     * @param id - USER_ID of login user.
     * @return ObservableList of Appointment rows found in WHERE clause date range.
     */
    @Override
    public ObservableList<Appointment> getApptNowByUser(int id) {
        ObservableList<Appointment> cstAppointments = FXCollections.observableArrayList();
        int qID = id;

        try {
            String sqlStmt = "SELECT * FROM appointments" +
                " WHERE User_ID = " +
                qID +
                " AND DATE(Start) >= CURDATE()-2 AND DATE(Start) <= CURDATE()+2" +
                " ORDER BY Start";

            prepStmt = useConnection().prepareStatement(sqlStmt);
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

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

    /**
     * Query Method used to identify Appointments by current Week.
     * @return ObservableList of Appointments found.
     */
    @Override
    public ObservableList<Appointment> getByWeekly() {
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
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

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

    /**
     * Query Method used to identify Appointments by current Month.
     * @return ObservableList of Appointments found.
     */
    @Override
    public ObservableList<Appointment> getByMonth() {
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
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

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

    /**
     * Query method for any plugged-in select statement.
     * Method used to build
     * @param query - The Select query statement to run.
     * @return - ObservableList of String type of found rows.
     */
    @Override
    public ObservableList<String> genericData(String query) {
        ObservableList<String> stringData = FXCollections.observableArrayList();

        try{
            String sqlStmt = query;
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                List<String> rowData = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
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

    /**
     * Query Method used to generate the Report Tab- all customer appointments by month and type.
     * @param reportParams - Array of parameters used in the WHERE clause.
     * @return ObservableList of Appointment rows found.
     */
    @Override
    public ObservableList<Appointment> getByMonthType(String[] reportParams) {
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
        sqlStmt.append(" ORDER BY MONTH(Start)");

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt.toString());
            DBUtils.doDMLv2(prepStmt, sqlStmt.toString());

            // Get the ResultSet and ResultSet metadata of the executed query.
            ResultSet rs = DBUtils.getResult();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Building Appointment Count By Month and Type");
            // Extract the ResultSet to a class object.
            while (rs.next()) {
                Appointment rptRows = DBUtils.getReportData(rs);
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

    /**
     * Query Method used to generate the Report Tab- contact appointments by current month or week.
     * @param reportParams - Array of parameters used in the WHERE clause.
     * @return ObservableList of Appointment rows found.
     */
    @Override
    public ObservableList<Appointment> getApptCntByPeriod(String[] reportParams) {
        ObservableList<Appointment> apptCntSched = FXCollections.observableArrayList();
        int wcCnt = Integer.parseInt(reportParams[0]);
        int wc = Integer.parseInt(reportParams[1]);
        String wcPeriod = reportParams[2];
        StringBuilder sqlStmt = new StringBuilder("SELECT Appointment_ID" +
                ", Title" +
                ", Type" +
                ", Description" +
                ", Start" +
                ", End" +
                ", Customer_ID" +
                " FROM appointments" +
                " WHERE Contact_ID = " +
                wcCnt);

        // Create WHERE clause based on params passed
        switch (wc) {
            case 1:
                sqlStmt.append(" AND MONTH(Start) = MONTH(NOW())");
                break;
            default:
                sqlStmt.append(" AND WEEK(Start) = WEEK(NOW())");
                break;
        }
        sqlStmt.append(" ORDER BY Start");

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt.toString());
            DBUtils.doDMLv2(prepStmt, sqlStmt.toString());

            // Get the ResultSet and ResultSet metadata of the executed query.
            ResultSet rs = DBUtils.getResult();

            System.out.println("Building Contact Appointment By Month/week");
            // Extract the ResultSet to a class object.
            while (rs.next()) {
                Appointment rptRows = DBUtils.getApptCntByPeriodData(rs);
                apptCntSched.add(rptRows);
            }
            return apptCntSched;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return apptCntSched;
    }

}
