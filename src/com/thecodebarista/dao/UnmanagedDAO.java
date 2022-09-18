package com.thecodebarista.dao;

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
    // List<T> extractAll() throws SQLException;

}
