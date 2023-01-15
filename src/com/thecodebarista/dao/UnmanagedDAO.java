package com.thecodebarista.dao;

import com.thecodebarista.model.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;


/**
 * Interface used for unmanaged classes; tables whose data in not maintained by the application.
 * @param <T>
 */
public interface UnmanagedDAO<T> {

    T extract(int id) throws SQLException;

    ObservableList<T> extractAll() throws SQLException;

    T getByName(String name) throws SQLException;

    int getObjByIndex(int id);

}