package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static DAO.DBConnection.useConnection;

public class DML {
    private static String query;
    private static PreparedStatement prepStmt;
    private static ResultSet rsData;

    public static void makeDML(String sqlStmt) {
        query = sqlStmt;

        try{
            // determine query execution
            if(query.toLowerCase().startsWith("select")){
                prepStmt= useConnection().prepareStatement(query);
                rsData=prepStmt.executeQuery();
            }
            else {
                prepStmt= useConnection().prepareStatement(sqlStmt);
                prepStmt.executeUpdate();
            }
        }
        catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public static ResultSet getResult(){
        return rsData;
    }
}