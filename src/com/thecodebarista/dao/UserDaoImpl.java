package com.thecodebarista.dao;

import com.thecodebarista.TimeMachine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.thecodebarista.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.thecodebarista.dao.DBConnection.useConnection;
import static com.thecodebarista.dao.DMLUtils.getUserData;

public class UserDaoImpl implements UnmanagedDAO{
    private static PreparedStatement prepStmt;

    public static User userLogin(String userAuth) throws SQLException{
        String sqlStmt = userAuth;
        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);

            // Pass the preparedStatement to be executed.
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

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
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

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
    public ObservableList<User> extractAll() throws SQLException{
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        try{
            String sqlStmt = "SELECT * FROM users";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

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
    public User getByName(String name) throws SQLException{
        User user = null;
        String sqlStmt="SELECT * FROM users" +
                " WHERE User_Name = '?'";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, name);

            // Pass the preparedStatement to be executed.
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

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
}
