package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.DBConnection.useConnection;

public class DML {
    private static String query;
    private static PreparedStatement prepStmt;
    private static ResultSet rsData;
    private static int rowsAffected;

    public static void setDMLPassedValues(int numValues, String[] values){

    }

    /*

    public static String dmlInsert(String customer_name, String address, String post_code, String phone, int division_id, String objClass) throws SQLException {
        String sqlStmt = "INSERT INTO " + objClass + " (Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                "VAlUES(?, ?, ?, ?, ?)";
        try{

            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, customer_name);
            prepStmt.setString(2, address);
            prepStmt.setString(3, post_code);
            prepStmt.setString(4, phone);
            prepStmt.setInt(5, division_id);
            System.out.println(prepStmt.toString());
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        return prepStmt.toString();
    }

    public static String dmlUpdate(String customer_name, String address, String post_code, String phone, int division_id, String objClass) throws SQLException {
        String sqlStmt = "UPDATE " + objClass + " SET " + objUpdCol + " = ? WHERE " + wc;
                "VAlUES(?, ?, ?, ?, ?)";
        try{

            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, customer_name);
            prepStmt.setString(2, address);
            prepStmt.setString(3, post_code);
            prepStmt.setString(4, phone);
            prepStmt.setInt(5, division_id);
            System.out.println(prepStmt.toString());
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        return prepStmt.toString();
    }
    */

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

    public static void doDMLv2(PreparedStatement prepStmt, String sqlStmt) {
        boolean isSelect = sqlStmt.toLowerCase().startsWith("select");
        System.out.println("Processing: " + prepStmt.toString());

        try{
            // determine query execution
            // System.out.println("#1- Is SELECT: " + isSelect);
            if(isSelect){
                rsData=prepStmt.executeQuery();
            }
            else {
                rowsAffected = prepStmt.executeUpdate();
                System.out.println("Rows Affected: " + rowsAffected);
            }
        }
        catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public static ResultSet getResult() {
        return rsData;
    }
}