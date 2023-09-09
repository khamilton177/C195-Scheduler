package com.thecodebarista.dao;

import com.thecodebarista.model.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * User Data Access Object Interface.
 * Generate queries for the DB users table
 */
public interface UserDAO extends SchedulerDAO<User>  {

    int assignAdmin (int id) throws SQLException;
    int updateLastLogin(Timestamp login, int id) throws SQLException;
    int setActivationStatus(int active, int id);
    ObservableList<User> ActiveUsers() throws SQLException;
    ObservableList<User> agedLogins() throws SQLException;
}
