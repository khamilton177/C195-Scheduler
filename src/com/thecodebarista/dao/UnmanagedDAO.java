package com.thecodebarista.dao;

import javafx.collections.ObservableList;
import java.sql.SQLException;

/**
 * Interface used for unmanaged classes; tables whose data in not maintained by the application.
 * @param <T> Generic Type for interface used by multiple classes.
 */
public interface UnmanagedDAO<T> {

    T extract(int id) throws SQLException;

    ObservableList<T> extractAll() throws SQLException;

    T getByName(String name) throws SQLException;

    int getObjByIndex(int id);

}