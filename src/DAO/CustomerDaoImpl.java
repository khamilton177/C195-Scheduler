package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.DBConnection.useConnection;

public class CustomerDaoImpl {

    private static final String returnGenKeys = ".RETURN_GENERATED_KEYS";
    private static PreparedStatement prepStmt;

    /**
     * Build a class object from the returned ResultSet
     * @param rs The return ResultSet from a doDML call.
     * @return Customer with populated data.
     * @throws SQLException Log will have SQL statement error.
     */
    private static Customer getCst_rsData(ResultSet rs) throws SQLException {
        Customer rsDataCustomer;
        int customer_id = (rs.getInt("Customer_ID"));
        String customer_name = rs.getString("Customer_Name");
        String address = rs.getString("Address");
        String postal_code = rs.getString("Postal_Code");
        String phone = rs.getString("Phone");
        int division_id = rs.getInt("Division_ID");
        rsDataCustomer = new Customer(customer_id, customer_name, address, postal_code, phone, division_id);
        return rsDataCustomer;
    }

    public static void insertCst(String customer_name, String address, String post_code, String phone, int division_id) throws SQLException {
        String sqlStmt = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                " VAlUES(?, ?, ?, ?, ?)";

        // Build the preparedStatement.
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, customer_name);
            prepStmt.setString(2, address);
            prepStmt.setString(3, post_code);
            prepStmt.setString(4, phone);
            prepStmt.setInt(5, division_id);

            // Pass the preparedStatement to be executed with plain string for validation and log.
            DML.doDMLv2(prepStmt, sqlStmt);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

        }
       // return null;
    }

    public static void updateCst(String customer_name, String address, String post_code, String phone, int division_id, int customer_id) throws SQLException {
        String sqlStmt = "UPDATE customers SET" +
                " Customer_Name = ?," +
                " Address = ?," +
                " Postal_Code = ?," +
                " Phone = ?," +
                " Division_ID = ?" +
                " WHERE Customer_ID = ?";
        try{
            prepStmt= useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, customer_name);
            prepStmt.setString(2,address);
            prepStmt.setString(3, post_code);
            prepStmt.setString(4, phone);
            prepStmt.setInt(5, division_id);
            prepStmt.setInt(6, customer_id);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        DML.doDMLv2(prepStmt, sqlStmt);

       // return prepStmt.toString();
    }

    public static void deleteCst(int customer_id) throws SQLException {
        String sqlStmt = "DELETE FROM customers" +
                " WHERE Customer_ID = ?";
        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, customer_id);
            DML.doDMLv2(prepStmt, sqlStmt);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static Customer getCustomer(int customer_id) throws SQLException, Exception{
        String sqlStmt="SELECT * FROM customers" +
            " WHERE Customer_ID = ?";
        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, customer_id);
            System.out.println("made it here 1");
            // Pass the preparedStatement to be executed.

            // Pass the preparedStatement to be executed.
            DML.doDMLv2(prepStmt, sqlStmt);
            System.out.println("made it here 2");

            // Get the ResultSet of the executed query.
            System.out.println("made it here 3");
            ResultSet rs = DML.getResult();

            // If Customer data found, extract the ResultSet to a Customer object and return.
            if (rs.next()) {
                System.out.println("made it here 4");
                return getCst_rsData(rs);
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        // No Customer data found return null object
        System.out.println("made it here 5");
        return null;
    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStmt = "SELECT * FROM customers";
        DML.doDMLv2(prepStmt, sqlStmt);

        // Get the ResultSet of the executed query.
        ResultSet rs = DML.getResult();

        // Extract the ResultSet to a class object.
        while (rs.next()) {
            System.out.println("Building List");
            Customer rsData = getCst_rsData(rs);
            allCustomers.add(rsData);
        }
        return allCustomers;
    }
}
