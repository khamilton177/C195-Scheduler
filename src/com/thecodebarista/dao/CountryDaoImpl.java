package com.thecodebarista.dao;

import com.thecodebarista.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.thecodebarista.dao.DBConnection.useConnection;
import static com.thecodebarista.dao.DMLUtils.*;

public class CountryDaoImpl implements UnmanagedDAO {

    @Override
    public Country extract(int id) throws SQLException {
        Country country = null;
        String sqlStmt="SELECT * FROM countries" +
                " WHERE Country_ID = ?";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, id);

            // Pass the preparedStatement to be executed.
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

            // If Country data found, extract the ResultSet to a Country object and return.
            if (rs.next()) {
                System.out.println("made it here 4");
                country = getCoData(rs);
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        // No Country data found return null object
        return country;
    }

    @Override
    public ObservableList<Country> extractAll() throws SQLException{
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        try{
            String sqlStmt = "SELECT * FROM countries";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

            // Extract the ResultSet to a class object.
            while (rs.next()) {
                Country country = getCoData(rs);
                allCountries.add(country);
            }
            return allCountries;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return allCountries;
    }

    @Override
    public Country getByName(String name) throws SQLException {
        Country country = null;
        String sqlStmt="SELECT * FROM countries" +
                " WHERE Country = '?'";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, name);

            // Pass the preparedStatement to be executed.
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

            // If Country data found, extract the ResultSet to a Country object and return.
            if (rs.next()) {
                System.out.println("made it here 4");
                country = getCoData(rs);
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        // No Country data found return null object
        return country;
    }

}
