package dao;


import javafx.event.ActionEvent;
import javafx.scene.Parent;
import model.Appointment;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class SchedulerUtilities {

    private static final String returnGenKeys = ".RETURN_GENERATED_KEYS";
    private static PreparedStatement prepStmt;

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

    public static void recastScene(ActionEvent event, String fxmlFile, String title, String controllerName) {
        Parent root = null;
/*
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/main-menu.fxml"));
            scene = loader.load();
        }
        catch(IOException e) {

        }

 */
    }

}