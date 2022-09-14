package dao;

import javafx.collections.ObservableList;
import model.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends SchedulerDAO<Customer> {

    /**
     * Get a list of all Customers.
     * @return allCustomers  - List of all Customers.
     */
    @Override
    ObservableList<Customer> extractAll() throws SQLException;
}
