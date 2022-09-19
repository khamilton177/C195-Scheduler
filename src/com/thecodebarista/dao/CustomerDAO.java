package com.thecodebarista.dao;

import javafx.collections.ObservableList;
import com.thecodebarista.model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends SchedulerDAO<Customer> {

    Customer getCstLVIndex(int id);

}
