package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

import static dao.DBConnection.useConnection;
import static dao.SchedulerUtilities.getCstData;

import java.sql.PreparedStatement;
import java.util.List;

public class CustomerDaoImpl implements CustomerDAO {

    private static PreparedStatement prepStmt;
    private int rowsAffected = 0; // Setting to 0. SELECT statements don't return a value so this is a nominal value.

    @Override
    public Customer extract(int customer_id) throws SQLException {
        Customer customer = null;
        String sqlStmt="SELECT * FROM customers" +
                " WHERE Customer_ID = ?";
        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, customer_id);
            System.out.println("made it here 1");

            // Pass the preparedStatement to be executed.
            DML.doDMLv2(prepStmt, sqlStmt);
            System.out.println("made it here 2");

            // Get the ResultSet of the executed query.
            System.out.println("made it here 3");
            ResultSet rs = DML.getResult();

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
    public ObservableList<Customer> extractAll() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        //ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStmt = "SELECT * FROM customers";
        prepStmt = useConnection().prepareStatement(sqlStmt);
        DML.doDMLv2(prepStmt, sqlStmt);

        // Get the ResultSet of the executed query.
        ResultSet rs = DML.getResult();

        // Extract the ResultSet to a class object.
        System.out.println("Building List");
        while (rs.next()) {
            Customer customer = getCstData(rs);
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public int save(Customer customer) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Customer customer) throws SQLException {
        String sqlStmt = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                " VAlUES(?, ?, ?, ?, ?, ?)";

        // Build the preparedStatement.
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, customer.getCustomerId());
            prepStmt.setString(2, customer.getCustomerName());
            prepStmt.setString(3, customer.getCustomerAddress());
            prepStmt.setString(4, customer.getPostal_Code());
            prepStmt.setString(5, customer.getPhone());
            prepStmt.setInt(6, customer.getDivision_ID());

            // Pass the preparedStatement to be executed with plain string for validation and log.
            rowsAffected = DML.doDMLv2(prepStmt, sqlStmt);
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
            prepStmt.setString(1, customer.getCustomerName());
            prepStmt.setString(2, customer.getCustomerAddress());
            prepStmt.setString(3, customer.getPostal_Code());
            prepStmt.setString(4, customer.getPhone());
            prepStmt.setInt(5, customer.getDivision_ID());
            prepStmt.setInt(6, customer.getCustomerId());

            rowsAffected = DML.doDMLv2(prepStmt, sqlStmt);
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
            prepStmt.setInt(1, customer.getCustomerId());
            rowsAffected = DML.doDMLv2(prepStmt, sqlStmt);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
