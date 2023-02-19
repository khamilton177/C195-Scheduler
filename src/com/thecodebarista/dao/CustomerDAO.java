package com.thecodebarista.dao;

import com.thecodebarista.model.Appointment;
import javafx.collections.ObservableList;
import com.thecodebarista.model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends SchedulerDAO<Customer> {

    ObservableList<Customer> customerWithCoInfo() throws SQLException;

    // ObservableList<Customer> adhocQuery(String wc) throws SQLException;

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