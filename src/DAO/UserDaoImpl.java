package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.DBConnection.useConnection;

public class UserDaoImpl {

    private static User getCst_rsData(ResultSet rs) throws SQLException {
        User rsDataUser;
        int user_id=rs.getInt("User_ID");
        String user_name=rs.getString("User_Name");
        String password=rs.getString("Password");
        rsDataUser = new User(user_id, user_name, password);
        return rsDataUser;
    }

    public static User userLogin(String userAuth) throws SQLException, Exception {
        String sqlStmt = userAuth;
        DML.doDML(sqlStmt);
        ResultSet rs = DML.getResult();
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
        DML.doDML(sqlStatement);
        User userResult;
        ResultSet rs=DML.getResult();
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
        DML.doDML(sqlStatement);
        ResultSet rs=DML.getResult();
        while(rs.next()){
            int userid=rs.getInt("User_ID");
            String userNameG=rs.getString("User_Name");
            String password=rs.getString("Password");
            User userResult= new User(userid, userNameG, password);
            allUsers.add(userResult);
        }
        return allUsers;
    }
}
