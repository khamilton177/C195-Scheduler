package com.thecodebarista.dao;

import com.thecodebarista.TimeMachine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.thecodebarista.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UserDaoImpl {

    public static User userLogin(String userAuth) throws SQLException, Exception {
        String sqlStmt = userAuth;
        DMLUtils.doDML(sqlStmt);
        ResultSet rs = DMLUtils.getResult();
        if (rs.next()) {
            User userResult;
            int user_id=rs.getInt("User_ID");
            String user_name=rs.getString("User_Name");
            String password=rs.getString("Password");
            userResult= new User(user_id, user_name, password);
            return userResult;
        }
        return null;
    }

    public static User getUserId(String username) throws SQLException, Exception{
        String sqlStatement="SELECT * FROM users WHERE User_Name  = '" + username+ "'";
        DMLUtils.doDML(sqlStatement);
        User userResult;
        ResultSet rs= DMLUtils.getResult();
        while(rs.next()){
            int user_id=rs.getInt("User_ID");
            String user_name=rs.getString("User_Name");
            String password=rs.getString("Password");
            userResult= new User(user_id, user_name, password);
            return userResult;
        }
        return null;
    }

    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> allUsers= FXCollections.observableArrayList();
        String sqlStatement="SELECT * FROM users";
        DMLUtils.doDML(sqlStatement);
        ResultSet rs= DMLUtils.getResult();
        while(rs.next()){
            int userid=rs.getInt("User_ID");
            String userNameG=rs.getString("User_Name");
            String password=rs.getString("Password");
            User userResult= new User(userid, userNameG, password);
            allUsers.add(userResult);
        }
        return allUsers;
    }

    /*
    @Override
    public LocalDateTime getLDT(LocalDate ldt, LocalTime lt) {
        LocalDateTime lDteT = LocalDateTime.of(ldt, lt);
        return lDteT;
    };
*/
}
