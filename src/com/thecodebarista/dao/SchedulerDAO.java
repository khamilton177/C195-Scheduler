package com.thecodebarista.dao;

import com.thecodebarista.model.User;
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

    ObservableList<T> sfQuery(String sfQuery) throws SQLException;
    ObservableList<String> genericData(String query) throws SQLException;

    int existColumns(String column) throws SQLException;
    int alterTable(String column) throws SQLException;
    int makeColumnUnique(String column) throws SQLException;
}
