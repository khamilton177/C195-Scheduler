package dao;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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