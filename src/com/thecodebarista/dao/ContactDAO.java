package com.thecodebarista.dao;

import com.thecodebarista.model.Contact;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Contact Data Access Object Interface.
 * Generate queries for the DB contacts table
 */
public interface ContactDAO extends SchedulerDAO<Contact> {

    int setActivationStatus(int active, int id);
    ObservableList<Contact> ActiveContacts() throws SQLException;
}
