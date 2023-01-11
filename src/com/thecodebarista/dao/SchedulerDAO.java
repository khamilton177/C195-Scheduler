package com.thecodebarista.dao;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface used for managed classes; tables whose data implements CRUD via the application.
 * @param <T>
 */
public interface SchedulerDAO<T extends ModelsDTO> {

    T extract(int id) throws SQLException;

    ObservableList<T> extractAll() throws SQLException;

    int insert(T t) throws SQLException;

    int update(T t) throws SQLException;

    int delete(T t);

    int save(T t) throws SQLException;

}
