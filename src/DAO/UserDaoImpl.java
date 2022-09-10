package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDaoImpl {
    private static final String returnGenKeys = ".RETURN_GENERATED_KEYS";

    public static User userLogin(String userAuth) throws SQLException, Exception {
        //   DBConnection.useConnection();
        String sqlStmt = userAuth;
        DML.makeDML(sqlStmt);
        ResultSet rs = DML.getResult();
        if (rs.next()) {
            User userResult;
            int userid=rs.getInt("User_ID");
            String userName=rs.getString("User_Name");
            String password=rs.getString("Password");
            userResult= new User(userid, userName, password);
            return userResult;
        }
        //DBConnection.closeConnection();
        return null;
    }

    public static User getUser(String userName) throws SQLException, Exception{
        //    DBConnection.useConnection();
        String sqlStatement="select * FROM users WHERE User_Name  = '" + userName+ "'";
        //  String sqlStatement="select FROM address";
        DML.makeDML(sqlStatement);
        User userResult;
        ResultSet rs=DML.getResult();
        while(rs.next()){
            int userid=rs.getInt("User_ID");
            String userNameG=rs.getString("User_Name");
            String password=rs.getString("Password");
            userResult= new User(userid, userName, password);
            return userResult;
        }
        //   DBConnection.closeConnection();
        return null;
    }

    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> allUsers= FXCollections.observableArrayList();
        //  DBConnection.useConnection();
        String sqlStatement="select * from users";
        DML.makeDML(sqlStatement);
        ResultSet rs=DML.getResult();
        while(rs.next()){
            int userid=rs.getInt("User_ID");
            String userNameG=rs.getString("User_Name");
            String password=rs.getString("Password");
            User userResult= new User(userid, userNameG, password);
            allUsers.add(userResult);
        }
        //   DBConnection.closeConnection();
        return allUsers;
    }
}
