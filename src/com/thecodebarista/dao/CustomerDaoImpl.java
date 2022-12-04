package com.thecodebarista.dao;

import com.thecodebarista.model.Appointment;
import com.thecodebarista.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.thecodebarista.dao.DBConnection.useConnection;
import static com.thecodebarista.dao.DMLUtils.getCstData;

public class CustomerDaoImpl implements CustomerDAO {
    private static String returnGenKeys = "Statement.RETURN_GENERATED_KEYS";
    private static PreparedStatement prepStmt;
    private int rowsAffected = 0; // Setting to 0. SELECT statements don't return a value so this is a nominal value.

    @Override
    public Customer extract(int id) throws SQLException {
        Customer customer = null;
      //String sqlStmt="SELECT * FROM customers" +
       //         " WHERE Customer_ID = ?";

        String sqlStmt="SELECT customers.Customer_ID" +
                " ,customers.Customer_Name" +
                " ,customers.Address" +
                " ,customers.Postal_Code" +
                " ,customers.Phone" +
                " ,countries.Country_ID" +
                " ,countries.Country" +
                " ,customers.Division_ID" +
                " ,first_level_divisions.Division" +
                " FROM customers, first_level_divisions, countries" +
                " WHERE first_level_divisions.Division_ID = customers.Division_ID" +
                " AND countries.Country_ID = first_level_divisions.Country_ID" +
                " AND Customer_ID = ?";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, id);
            System.out.println("made it here 1");

            // Pass the preparedStatement to be executed.
            DMLUtils.doDMLv2(prepStmt, sqlStmt);
            System.out.println("made it here 2");

            // Get the ResultSet of the executed query.
            System.out.println("made it here 3");
            ResultSet rs = DMLUtils.getResult();

            // If Customer data found, extract the ResultSet to a Customer object and return.
            if (rs.next()) {
                System.out.println("made it here 4");
                customer = getCstData(rs); //created method to hold and create the fields
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        // No Customer data found return null object
        System.out.println("made it here 5");
        return customer;
    }

    @Override
    public ObservableList<Customer> extractAll() throws SQLException{
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        //       List<Customer> allCustomers = new ArrayList<>();
        //String sqlStmt = "SELECT * FROM customers";

        String sqlStmt="SELECT customers.Customer_ID" +
                " ,customers.Customer_Name" +
                " ,customers.Address" +
                " ,customers.Postal_Code" +
                " ,customers.Phone" +
                " ,countries.Country_ID" +
                " ,countries.Country" +
                " ,customers.Division_ID" +
                " ,first_level_divisions.Division" +
                " FROM customers, first_level_divisions, countries" +
                " WHERE first_level_divisions.Division_ID = customers.Division_ID" +
                " AND countries.Country_ID = first_level_divisions.Country_ID";


        prepStmt = useConnection().prepareStatement(sqlStmt);
        DMLUtils.doDMLv2(prepStmt, sqlStmt);

        // Get the ResultSet of the executed query.
        ResultSet rs = DMLUtils.getResult();

        // Extract the ResultSet to a class object.
        System.out.println("Building Customer List");
        while (rs.next()) {
            Customer customer = getCstData(rs);
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public int insert(Customer customer) throws SQLException {
        String sqlStmt = "INSERT INTO customers ( Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                " VAlUES(NULL, ?, ?, ?, ?, ?)";

        // Build the preparedStatement.
        try {
            //prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt = useConnection().prepareStatement(sqlStmt, Statement.RETURN_GENERATED_KEYS);
            prepStmt.setString(2, customer.getCustomer_Name());
            prepStmt.setString(3, customer.getAddress());
            prepStmt.setString(4, customer.getPostal_Code());
            prepStmt.setString(5, customer.getPhone());
            prepStmt.setInt(6, customer.getDivision_ID());

            // Pass the preparedStatement to be executed with plain string for validation and log.
            rowsAffected = DMLUtils.doDMLv2(prepStmt, sqlStmt);

            ResultSet rs = prepStmt.getGeneratedKeys();
            rs.next();
            int Customer_ID = rs.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

        }
        return rowsAffected;
    }

    @Override
    public int update(Customer customer) throws SQLException {
        String sqlStmt = "UPDATE customers SET" +
                " Customer_Name = ?," +
                " Address = ?," +
                " Postal_Code = ?," +
                " Phone = ?," +
                " Division_ID = ?" +
                " WHERE Customer_ID = ?";
        try{
            prepStmt= useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, customer.getCustomer_Name());
            prepStmt.setString(2, customer.getAddress());
            prepStmt.setString(3, customer.getPostal_Code());
            prepStmt.setString(4, customer.getPhone());
            prepStmt.setInt(5, customer.getDivision_ID());
            prepStmt.setInt(6, customer.getCustomer_ID());

            rowsAffected = DMLUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int delete(Customer customer) {
        String sqlStmt = "DELETE FROM customers" +
                " WHERE Customer_ID = ?";
        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, customer.getCustomer_ID());
            rowsAffected = DMLUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int save(Customer customer) throws SQLException {
        return 0;
    }

    @Override
    public ObservableList<Customer> customerWithCoInfo() throws SQLException {
        ObservableList<Customer> cstWithCoInfo = FXCollections.observableArrayList();
        String sqlStmt = CSTVIEW;
        prepStmt = useConnection().prepareStatement(sqlStmt);
        DMLUtils.doDMLv2(prepStmt, sqlStmt);

        // Get the ResultSet of the executed query.
        ResultSet rs = DMLUtils.getResult();

        // Extract the ResultSet to a class object.
        System.out.println("Building Customer with Country List");
        while (rs.next()) {
            Customer customer = getCstData(rs);
            cstWithCoInfo.add(customer);
        }
        return cstWithCoInfo;
    }
}
