package com.thecodebarista.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.thecodebarista.model.*;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.thecodebarista.dao.DBConnection.useConnection;
import static com.thecodebarista.dao.DBUtils.getUserData;

/**
 * User Data Access Object Interface.
 * Generate queries for the DB users table
 */
public class UserDaoImpl implements UserDAO{
    private static DatabaseMetaData metaData;
    private static PreparedStatement prepStmt;
    private int rowsAffected = 0; // Setting to 0. SELECT statements don't return a value so this is a nominal value.

    public static User userLogin(String userAuth) throws SQLException{
        String sqlStmt = userAuth;
        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);

            // Pass the preparedStatement to be executed.
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

            // If User/Password combo data found, extract the object and return.
            if (rs.next()) {
                User validAuthUser;
                validAuthUser= getUserData(rs);
                return validAuthUser;
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User extract(int id) throws SQLException{
        User user = null;
        String sqlStmt="SELECT * FROM users" +
                " WHERE User_ID = ?";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, id);

            // Pass the preparedStatement to be executed.
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

            // If User data found, extract the ResultSet to a User object and return.
            if (rs.next()) {
                user = getUserData(rs);
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        // No User data found return null object
        return user;
    }

    @Override
    public ObservableList<User> extractAll() throws SQLException{
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        try{
            String sqlStmt = "SELECT * FROM users";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

            // Extract the ResultSet to a class object.
            System.out.println("Building User List");
            while (rs.next()) {
                User user = getUserData(rs);
                allUsers.add(user);
            }
            return allUsers;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return allUsers;
    }

    @Override
    public int insert(User user) throws SQLException {
        String sqlStmt = "INSERT INTO users (User_Name, Password)" +
                " VAlUES(?, ?)";

        // Build the preparedStatement.
        try {
            //prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt = useConnection().prepareStatement(sqlStmt, Statement.RETURN_GENERATED_KEYS);
            prepStmt.setString(1, user.getUser_Name());
            prepStmt.setString(2, user.getPassword());

            // Pass the preparedStatement to be executed with plain string for validation and log.
            rowsAffected = DBUtils.doDMLv2(prepStmt, sqlStmt);

            ResultSet rs = prepStmt.getGeneratedKeys();
            rs.next();
            int User_ID = rs.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

        }
        return rowsAffected;
    }

    @Override
    public int update(User user) throws SQLException {
        String sqlStmt = "UPDATE users SET" +
                " User_Name = ?," +
                " Password = ?," +
                " Is_Admin = ?," +
                " Active = ?" +
                " WHERE (User_ID = ?) AND (User_ID > 2)";

//        prepStmt.getParameterMetaData().getParameterCount();

        // Build the preparedStatement.
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, user.getUser_Name());
            prepStmt.setString(2, user.getPassword());
            prepStmt.setInt(3, user.getIs_Admin());
            prepStmt.setInt(4, user.getActive());
            prepStmt.setInt(5, user.getUser_ID());
            rowsAffected = DBUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int delete(User user) {
        String sqlStmt = "DELETE FROM users" +
                " WHERE (User_ID = ?) AND (User_ID > 2)";
        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, user.getUser_ID());
            rowsAffected = DBUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int existColumns(String column) throws SQLException {

        try{
            metaData = useConnection().getMetaData();

            // Pass the DatabaseMetaData to be executed.
            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.checkDDL(metaData, "users", column);

            // If Column is not found, create column.
            if (!rs.next()) {
                rowsAffected = alterTable(column);
                if (column.equals("Is_Admin")) {
                    assignAdmin (2);
                }
            }
            else {
                System.out.println("Column Found: " + !rs.next());
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int alterTable(String column) throws SQLException {
        String sqlStmt = "ALTER TABLE users" +
                " ADD COLUMN " +
                column;
        if (column.equals("Is_Admin")) {
            sqlStmt += " INT NOT NULL DEFAULT 0";
        }
        if (column.equals("Active")) {
            sqlStmt += " INT NOT NULL DEFAULT 1";
        }

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);

            rowsAffected = DBUtils.doDDL(prepStmt, sqlStmt);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int makeColumnUnique(String column) throws SQLException {
        String sqlStmt = "ALTER TABLE users" +
                " ADD CONSTRAINT UNIQUE (" +
                column +
                ")";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            rowsAffected = DBUtils.doDDL(prepStmt, sqlStmt);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int assignAdmin(int id) throws SQLException {
        String sqlStmt = "UPDATE users SET" +
                " Is_Admin = 1" +
                " WHERE User_ID = ?";
        // Build the preparedStatement.
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

    @Override
    public int setActivationStatus(int active, int id) {
        String sqlStmt = "UPDATE users SET" +
                " Active = " + active +
                " WHERE (User_ID = " + id +
                ") AND (User_ID > 2)";
        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            rowsAffected = DBUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ObservableList<User> ActiveUsers() throws SQLException {
        ObservableList<User> activeUsers = FXCollections.observableArrayList();

        try{
            String sqlStmt = "SELECT * FROM users" +
                    " WHERE active = 1" +
                    " ORDER BY User_ID ASC";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

            // Extract the ResultSet to a class object.
            System.out.println("Building User List");
            while (rs.next()) {
                User user = getUserData(rs);
                activeUsers.add(user);
            }
            return activeUsers;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return activeUsers;
    }

    @Override
    public ObservableList<User> sfQuery(String sfQuery) throws SQLException {
        ObservableList<User> sfUsers = FXCollections.observableArrayList();

        try{
            String sqlStmt = sfQuery;
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

            // Extract the ResultSet to a class object.
            while (rs.next()) {
                User user = getUserData(rs);
                sfUsers.add(user);
            }
            return sfUsers;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return sfUsers;
    }

    @Override
    public ObservableList<String> genericData(String query) throws SQLException {
        return null;
    }

/* COMMENT OUT METHODS USED WHEN USERS WAS UNMANAGED
    @Override
    public User getByName(String name) throws SQLException{
        User user = null;
        String sqlStmt="SELECT * FROM users" +
                " WHERE User_Name = '?'";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, name);

            // Pass the preparedStatement to be executed.
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

            // If User data found, extract the ResultSet to a User object and return.
            if (rs.next()) {
                System.out.println("made it here 4");
                user = getUserData(rs);
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        // No User data found return null object
        return user;
    }

    @Override
    public int getObjByIndex(int id) {
        return 0;
    }
 */

}
