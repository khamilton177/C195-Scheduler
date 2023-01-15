package com.thecodebarista.dao;

import com.thecodebarista.model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.thecodebarista.dao.DBConnection.useConnection;
import static com.thecodebarista.dao.DMLUtils.getCntData;
import static com.thecodebarista.dao.DMLUtils.getDivData;

public class FirstLevelDivisionDAOImpl implements UnmanagedDAO{
    private static PreparedStatement prepStmt;

    @Override
    public FirstLevelDivision extract(int id) throws SQLException {
        FirstLevelDivision division = null;
        String sqlStmt="SELECT * FROM first_Level_divisions" +
                " WHERE Division_ID = ?";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, id);
            System.out.println("made it here 1");
            // Pass the preparedStatement to be executed.

            // Pass the preparedStatement to be executed.
            DMLUtils.doDMLv2(prepStmt, sqlStmt);
            System.out.println("made it here 2");

            // Get the ResultSet of the executed query.
            System.out.println("made it here 3");
            ResultSet rs = DMLUtils.getResult();

            // If FirstLevelDivision data found, extract the ResultSet to a FirstLevelDivision object and return.
            if (rs.next()) {
                System.out.println("made it here 4");
                division = getDivData(rs);
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        // No FirstLevelDivision data found return null object
        System.out.println("made it here 5");
        return division;
    }

    @Override
    public ObservableList<FirstLevelDivision> extractAll() throws SQLException{
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

        try{
            String sqlStmt = "SELECT * FROM first_Level_divisions";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

            // Extract the ResultSet to a class object.
            while (rs.next()) {
                FirstLevelDivision division = getDivData(rs);
                allDivisions.add(division);
            }
            return allDivisions;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return allDivisions;
    }

    @Override
    public Object getByName(String name) throws SQLException {
        FirstLevelDivision division = null;
        String sqlStmt="SELECT * FROM first_Level_divisions" +
                " WHERE Division = '?'";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, name);
            System.out.println("made it here 1");
            // Pass the preparedStatement to be executed.

            // Pass the preparedStatement to be executed.
            DMLUtils.doDMLv2(prepStmt, sqlStmt);
            System.out.println("made it here 2");

            // Get the ResultSet of the executed query.
            System.out.println("made it here 3");
            ResultSet rs = DMLUtils.getResult();

            // If FirstLevelDivision data found, extract the ResultSet to a FirstLevelDivision object and return.
            if (rs.next()) {
                System.out.println("made it here 4");
                division = getDivData(rs);
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        // No FirstLevelDivision data found return null object
        System.out.println("made it here 5");
        return division;
    }

    @Override
    public int getObjByIndex(int id) {
        return 0;
    }

    public ObservableList<FirstLevelDivision> getDivByCountry(int id) throws SQLException {
        ObservableList<FirstLevelDivision> selectedCoDivs = FXCollections.observableArrayList();

        String sqlStmt="SELECT * FROM first_Level_divisions" +
                " WHERE Country_ID = ?";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, id);
            System.out.println("Passing selected Country ID.");

            // Pass the preparedStatement to be executed.
            DMLUtils.doDMLv2(prepStmt, sqlStmt);
            System.out.println("Process query");

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

            // Extract the ResultSet to a class object.
            System.out.println("Building Division List based on Country.");
            while (rs.next()) {
                FirstLevelDivision division = getDivData(rs);
                selectedCoDivs.add(division);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return selectedCoDivs;
    }
    
}
