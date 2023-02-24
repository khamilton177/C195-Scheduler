package com.thecodebarista.dao;

import javafx.collections.ObservableList;
import com.thecodebarista.model.Customer;

import java.sql.SQLException;

/**
 * Customer Data Access Object Interface.
 * Generate queries for the DB customers table
 */
public interface CustomerDAO extends SchedulerDAO<Customer> {

    ObservableList<Customer> customerWithCoInfo() throws SQLException;

    String CSTVIEW = "SELECT customers.Customer_ID" +
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
            " ORDER BY customers.Customer_ID ASC";

}