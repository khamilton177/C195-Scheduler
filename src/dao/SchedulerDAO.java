package dao;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface SchedulerDAO<T>{

    T extract(int id) throws SQLException;

    ObservableList<T> extractAll() throws SQLException;

    int save(T t) throws SQLException;

    int insert(T t) throws SQLException;

    int update(T t) throws SQLException;

    int delete(T t);

}
