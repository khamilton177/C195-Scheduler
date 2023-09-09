package com.thecodebarista.dao;

import com.thecodebarista.model.*;
import java.sql.*;

/**
 * Class for DML; processes the prepared statements.
 */
public class DBUtils {
    public static DatabaseMetaData metaData;
    public static PreparedStatement prepStmt;
    public static ResultSet rsData;
    public static int rowsAffected;

    /**
     * Method processes SQL ALTER statements.
     * @param metaData prepared statement
     * @return The number of rows affected for columns altered.
     */
    public static ResultSet checkDDL(DatabaseMetaData metaData, String table, String column) {
        rowsAffected =0;

        try{
            rsData = metaData.getColumns(null, null, table, column);
        }
        catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
        return rsData;
    }

    /**
     * Method processes SQL ALTER statements.
     * @param prepStmt prepared statement
     * @param sqlStmt query passed in from the DaoImpl classes.
     * @return The number of rows affected for columns altered.
     */
    public static int doDDL(PreparedStatement prepStmt, String sqlStmt) {
        System.out.println("Processing: " + prepStmt.toString());
        rowsAffected =0;

        try{
            rowsAffected = prepStmt.executeUpdate();
            System.out.println("Rows Affected from Table Alter Statement: " + rowsAffected);
        }
        catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * Method processes SQL Statements.
     * The isSelect determines if the prepare statement will execute as a query or an update.
     * @param prepStmt prepared statement
     * @param sqlStmt query passed in from the DaoImpl classes.
     * @return The number of rows affected from insert, update, delete or 0 for queries.
     */
    public static int doDMLv2(PreparedStatement prepStmt, String sqlStmt) {
        boolean isSelect = sqlStmt.toLowerCase().startsWith("select");
        System.out.println("Processing: " + prepStmt.toString());
        rowsAffected =0;

        try{
            // determine query execution
            // System.out.println("#1- Is SELECT: " + isSelect);
            if(isSelect){
                rsData=prepStmt.executeQuery();
                System.out.println("Rows Affected from SELECT: " + rowsAffected);
            }
            else {
                rowsAffected = prepStmt.executeUpdate();
                System.out.println("Rows Affected from InsUpdateDel: " + rowsAffected);
            }
        }
        catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static ResultSet getResult() {
        return rsData;
    }

    /**
     * Build a User object from the returned ResultSet.
     * @param rs The return ResultSet from a doDMLv2 call.
     * @return User with populated data.
     * @throws SQLException Log will have SQL statement error.
     */
    public static User getUserData(ResultSet rs) throws SQLException {
        int user_ID = (rs.getInt("User_ID"));
        String user_Name = (rs.getString("User_Name"));
        String password = (rs.getString("Password"));
        int is_Admin = (rs.getInt("Is_Admin"));
        int active = (rs.getInt("Active"));
        return new User(user_ID, user_Name, password, is_Admin, active);
    }

    /**
     * Build an Appointment object from the returned ResultSet.
     * @param rs The return ResultSet from a doDMLv2 call.
     * @return Appointment with populated data.
     * @throws SQLException Log will have SQL statement error.
     */
    public static Appointment getApptData(ResultSet rs) throws SQLException {
        int appointment_ID = (rs.getInt("Appointment_ID"));
        String title = rs.getString("Title");
        String description = rs.getString("Description");
        String location = rs.getString("Location");
        String type = rs.getString("Type");
        Timestamp start = rs.getTimestamp("Start");
        Timestamp end = rs.getTimestamp("End");
        int customer_ID = rs.getInt("Customer_ID");
        int user_ID = rs.getInt("User_ID");
        int contact_ID = rs.getInt("Contact_ID");
        return new Appointment(appointment_ID, title, description, location, type, start, end, customer_ID, user_ID, contact_ID);
    }

    /**
     * Build a Customer object from the returned ResultSet.
     * @param rs The return ResultSet from a doDMLv2 call.
     * @return Customer with populated data.
     * @throws SQLException Log will have SQL statement error.
     */
    public static Customer getCstData(ResultSet rs) throws SQLException {
        int customer_ID = (rs.getInt("Customer_ID"));
        String customer_Name = rs.getString("Customer_Name");
        String address = rs.getString("Address");
        String postal_Code = rs.getString("Postal_Code");
        String phone = rs.getString("Phone");
        int division_ID = rs.getInt("Division_ID");
        int country_ID = rs.getInt("Country_ID");

        return new Customer(customer_ID, customer_Name, address, postal_Code, phone, division_ID, country_ID);
    }

    /**
     * Build a Contact object from the returned ResultSet.
     * @param rs The return ResultSet from a doDMLv2 call.
     * @return Contact with populated data.
     * @throws SQLException Log will have SQL statement error.
     */
    public static Contact getCntData(ResultSet rs) throws SQLException {
        int contact_ID = (rs.getInt("Contact_ID"));
        String contact_Name = (rs.getString("Contact_Name"));
        String email = (rs.getString("Email"));
        int active = (rs.getInt("Active"));
        return new Contact(contact_ID, contact_Name, email, active);
    }

    /**
     * Build a Country object from the returned ResultSet.
     * @param rs The return ResultSet from a doDMLv2 call.
     * @return Country with populated data.
     * @throws SQLException Log will have SQL statement error.
     */
    public static Country getCoData(ResultSet rs) throws SQLException {
        int country_ID = (rs.getInt("Country_ID"));
        String country = (rs.getString("Country"));
        return new Country(country_ID, country);
    }

    /**
     * Build a FirstLevelDivision object from the returned ResultSet.
     * @param rs The return ResultSet from a doDMLv2 call.
     * @return FirstLevelDivision with populated data.
     * @throws SQLException Log will have SQL statement error.
     */
    public static FirstLevelDivision getDivData(ResultSet rs) throws SQLException {
        int division_ID = (rs.getInt("Division_ID"));
        String division = (rs.getString("division"));
        int country_ID = (rs.getInt("Country_ID"));
        return new FirstLevelDivision(division_ID, division, country_ID);
    }

    /**
     * Build class object for the Appointment by Month/Type returned ResultSet.
     * @param rs The return ResultSet from a doDMLv2 call.
     * @return FirstLevelDivision with populated data.
     * @throws SQLException Log will have SQL statement error.
     */
    public static Appointment getReportData(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        String Month = (rs.getString(metaData.getColumnLabel(1)));;
        //System.out.println("Got Month? " + Month);
        String type = (rs.getString(metaData.getColumnLabel(2)));;
        int Count = (rs.getInt(metaData.getColumnLabel(3)));;
        Appointment rptRow = new Appointment();
        rptRow.setMonth(Month);;
        rptRow.setType(type);;
        rptRow.setCount(Count);;
        return rptRow;
    }

    /**
     * Build class object for the Appointment Contact Scheduled returned ResultSet.
     * @param rs The return ResultSet from a doDMLv2 call.
     * @return FirstLevelDivision with populated data.
     * @throws SQLException Log will have SQL statement error.
     */
    public static Appointment getApptCntByPeriodData(ResultSet rs) throws SQLException {
        Appointment rptRow = new Appointment();
        rptRow.setAppointment_ID(rs.getInt("Appointment_ID"));
        rptRow.setTitle(rs.getString("Title"));;
        rptRow.setType(rs.getString("Type"));
        rptRow.setDescription(rs.getString("Description"));
        rptRow.setStart(rs.getTimestamp("Start"));
        rptRow.setEnd(rs.getTimestamp("End"));
        rptRow.setCustomer_ID(rs.getInt("Customer_ID"));
        return rptRow;
    }

    public static User getAgedLogins(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        String user_Name = (rs.getString(metaData.getColumnLabel(1)));
        System.out.println("Got Name? " + user_Name);
        Timestamp last_Login = (rs.getTimestamp(metaData.getColumnLabel(2)));
        System.out.println("Got Login? " + last_Login);
        int days = (rs.getInt(metaData.getColumnLabel(3)));;
        User agedRow = new User();
        agedRow.setUser_Name(user_Name);
        agedRow.setLast_Login(last_Login);
        agedRow.setDays(days);
        return agedRow;
    }
}
