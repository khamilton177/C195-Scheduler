package com.thecodebarista.dao;

import com.thecodebarista.model.Contact;
import com.thecodebarista.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.thecodebarista.dao.DBConnection.useConnection;
import static com.thecodebarista.dao.DMLUtils.getCntData;

public class ContactDaoImpl implements UnmanagedDAO {
    private static PreparedStatement prepStmt;

    @Override
    public Contact extract(int id) throws SQLException{
        Contact contact = null;
        String sqlStmt="SELECT * FROM contacts" +
                " WHERE Contact_ID = ?";

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

            // If Contact data found, extract the ResultSet to a Contact object and return.
            if (rs.next()) {
                System.out.println("made it here 4");
                contact = getCntData(rs);
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        // No Contact data found return null object
        System.out.println("made it here 5");
        return contact;
    }

    @Override
    public ObservableList<Contact> extractAll() throws SQLException{
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        try{
            String sqlStmt = "SELECT * FROM contacts" +
                    " ORDER BY Contact_ID ASC";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DMLUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DMLUtils.getResult();

            // Extract the ResultSet to a class object.
            System.out.println("Building Contact List");
            while (rs.next()) {
                Contact contact = getCntData(rs);
                allContacts.add(contact);
            }
            return allContacts;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return allContacts;
    }

    @Override
    public Contact getByName(String name) throws SQLException{
        Contact contact = null;
        String sqlStmt="SELECT * FROM contacts" +
                " WHERE Contact_Name LIKE '?'";

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

            // If Contact data found, extract the ResultSet to a Contact object and return.
            if (rs.next()) {
                System.out.println("made it here 4");
                contact = getCntData(rs);
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        // No Contact data found return null object
        System.out.println("made it here 5");
        return contact;
    }

    @Override
    public int getObjByIndex(int id) {
        return 0;
    }

}
