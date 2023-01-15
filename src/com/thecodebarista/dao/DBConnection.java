package com.thecodebarista.dao;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class DBConnection {

    //These variables are separated for future use to plugin new connection parameters
    private static final String dbDriver = "jdbc";
    private static final String dbVendorName = "mysql";
    private static final String dbHost = "localhost";
    private static final String dbServerPort = "3306";

    //Build the jdbcURL and properties
    private static final String protocol = dbDriver + ":" + dbVendorName + ":";
    private static final String dbHostURL = "//" + dbHost + ":" + dbServerPort + "/";
    private static final String dbName = "client_schedule";

    //Save the timestamp data in UTC while presenting time in GUI to user's zoned session.
    private static final String connProperties = "?connectionTimeZone=UTC&forceConnectionTimeZoneToSession=true";
    private static final String MySQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    private static final String jdbcURL = protocol + dbHostURL + dbName + connProperties;

    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    private static Connection conn = null;
    private static PreparedStatement prepStmt;
    private static ResultSet rs;

    public static Connection establishConnection() {
        try {
            Class.forName(MySQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, userName, password);
        }
        catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection useConnection() {
        return conn;
    }

    // Releasing the resource accordance with MySQL docs.
    // Connection is not closed until application closes
    public static void releaseRS(ResultSet lastRS) {
        rs = lastRS;
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // nothing to do
            }
            rs = null;
        }
    }

    // Releasing the resource accordance with MySQL docs.
    // Connection is not closed until application closes
    public static void releasePrepStmt(PreparedStatement LastPrepStmt){
        prepStmt = LastPrepStmt;
        if (prepStmt != null) {
            try {
                prepStmt.close();
            } catch(SQLException e) {
                // nothing to do
            }
            prepStmt = null;
        }
    }

    public static void closeConnection() {
        try{
            conn.close();
        }
        catch(Exception e) {
            // nothing to do
        }
    }
}
