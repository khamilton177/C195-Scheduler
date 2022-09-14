package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static dao.DBConnection.useConnection;

import model.Appointment;
import model.Customer;

public class DMLUtils {
    public static String query;
    public static PreparedStatement prepStmt;
    public static ResultSet rsData;
    public static int rowsAffected;
    public static String returnGenKeys = ".RETURN_GENERATED_KEYS";

    public static void doDML(String sqlStmt) {
        query = sqlStmt;

        try{
            // determine query execution
            if(query.toLowerCase().startsWith("select")){
                //prepStmt= useConnection().prepareStatement(sqlStmt);
                rsData=prepStmt.executeQuery();
            }
            else {
                //prepStmt= useConnection().prepareStatement(sqlStmt);
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

    /**
     * Build a class object from the returned ResultSet
     *
     * @param rs The return ResultSet from a doDML call.
     * @return Customer with populated data.
     * @throws SQLException Log will have SQL statement error.
     */
    public static Customer getCstData(ResultSet rs) throws SQLException {
        int customer_id = (rs.getInt("Customer_ID"));
        String customer_name = rs.getString("Customer_Name");
        String address = rs.getString("Address");
        String postal_code = rs.getString("Postal_Code");
        String phone = rs.getString("Phone");
        int division_id = rs.getInt("Division_ID");
        return new Customer(customer_id, customer_name, address, postal_code, phone, division_id);
    }

    /**
     * Build a class object from the returned ResultSet
     *
     * @param rs The return ResultSet from a doDMLv2 call.
     * @return Appointment with populated data.
     * @throws SQLException Log will have SQL statement error.
     */
    public static Appointment getApptData(ResultSet rs) throws SQLException {
        int appointment_id = (rs.getInt("Appointment_ID"));
        String title = rs.getString("Title");
        String description = rs.getString("Description");
        String location = rs.getString("Location");
        String type = rs.getString("Type");
        Timestamp start = rs.getTimestamp("Start");
        Timestamp end = rs.getTimestamp("End");
        int customer_id = rs.getInt("Customer_ID");
        int user_id = rs.getInt("User_ID");
        int contact_id = rs.getInt("Contact_ID");
        return new Appointment(appointment_id, title, description, location, type, start, end, customer_id, user_id, contact_id);
    }

}