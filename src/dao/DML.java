package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dao.DBConnection.useConnection;

public class DML {
    private static String query;
    private static PreparedStatement prepStmt;
    private static ResultSet rsData;
    private static int rowsAffected;

    public static void setDMLPassedValues(int numValues, String[] values){

    }

    public static void doDML(String sqlStmt) {
        query = sqlStmt;

        try{
            // determine query execution
            if(query.toLowerCase().startsWith("select")){
                prepStmt= useConnection().prepareStatement(sqlStmt);
                rsData=prepStmt.executeQuery();
            }
            else {
                prepStmt= useConnection().prepareStatement(sqlStmt);
                prepStmt.executeUpdate();
            }
        }
        catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

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
}