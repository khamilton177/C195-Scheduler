package com.thecodebarista.dao;

import javafx.collections.ObservableList;
import java.sql.SQLException;

/**
 * Interface used for managed classes; tables whose data implements CRUD via the application.
 * @param <T> Generic Type for interface used by multiple classes.
 */
public interface SchedulerDAO<T> {

    T extract(int id) throws SQLException;

    ObservableList<T> extractAll() throws SQLException;

    int insert(T t) throws SQLException;

    int update(T t) throws SQLException;

    int delete(T t);

}
