package com.thecodebarista.dao;

import com.thecodebarista.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static com.thecodebarista.dao.DBConnection.useConnection;
import static com.thecodebarista.dao.DBUtils.getCntData;

/**
 * Contact Data Access Object Interface.
 * Generate queries for the DB contacts table
 */
public class ContactDaoImpl implements ContactDAO {
    private static DatabaseMetaData metaData;
    private static PreparedStatement prepStmt;
    private int rowsAffected = 0; // Setting to 0. SELECT statements don't return a value so this is a nominal value.

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
            DBUtils.doDMLv2(prepStmt, sqlStmt);
            System.out.println("made it here 2");

            // Get the ResultSet of the executed query.
            System.out.println("made it here 3");
            ResultSet rs = DBUtils.getResult();

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
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

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
    public int insert(Contact contact) throws SQLException {
        String sqlStmt = "INSERT INTO contacts (Contact_Name, Email)" +
                " VAlUES(?, ?)";

        // Build the preparedStatement.
        try {
            //prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt = useConnection().prepareStatement(sqlStmt, Statement.RETURN_GENERATED_KEYS);
            prepStmt.setString(1, contact.getContact_Name());
            prepStmt.setString(2, contact.getEmail());

            // Pass the preparedStatement to be executed with plain string for validation and log.
            rowsAffected = DBUtils.doDMLv2(prepStmt, sqlStmt);

            ResultSet rs = prepStmt.getGeneratedKeys();
            rs.next();
            int Contact_ID = rs.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

        }
        return rowsAffected;
    }

    @Override
    public int update(Contact contact) throws SQLException {
        String sqlStmt = "UPDATE contacts SET" +
                " Contact_Name = ?," +
                " Email = ?," +
                " Active = ?" +
                " WHERE (Contact_ID = ?)"; // AND (Contact_ID > 3)";

        // Build the preparedStatement.
        try {
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setString(1, contact.getContact_Name());
            prepStmt.setString(2, contact.getEmail());
            prepStmt.setInt(3, contact.getActive());
            prepStmt.setInt(4, contact.getContact_ID());
            rowsAffected = DBUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int delete(Contact contact) {
        String sqlStmt = "DELETE FROM contacts" +
                " WHERE Contact_ID = ?";
        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, contact.getContact_ID());
            rowsAffected = DBUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ObservableList<Contact> sfQuery(String sfQuery) throws SQLException {
        ObservableList<Contact> sfContacts = FXCollections.observableArrayList();

        try{
            String sqlStmt = sfQuery;
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

            // Extract the ResultSet to a class object.
            while (rs.next()) {
                Contact contact = getCntData(rs);
                sfContacts.add(contact);
            }
            return sfContacts;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return sfContacts;
    }

    @Override
    public ObservableList<String> genericData(String query) throws SQLException {
        return null;
    }

    @Override
    public int existColumns(String column) throws SQLException {
        try{
            metaData = useConnection().getMetaData();

            // Pass the DatabaseMetaData to be executed.
            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.checkDDL(metaData, "contacts", column);

            // If Column is not found, create column.
            if (!rs.next()) {
                rowsAffected = alterTable(column);
            }
            else {
                System.out.println("Column Found: " + !rs.next());
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int alterTable(String column) throws SQLException {
        String sqlStmt = "ALTER TABLE contacts" +
                " ADD COLUMN " +
                column;
        if (column.equals("Role")) {
            sqlStmt += " varchar(100) NOT NULL DEFAULT 0";
        }
        if (column.equals("Active")) {
            sqlStmt += " INT NOT NULL DEFAULT 1";
        }

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            rowsAffected = DBUtils.doDDL(prepStmt, sqlStmt);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int showIndexes(String column) throws SQLException {
        String sqlStmt = "SHOW INDEXES FROM contacts WHERE Column_name = '" +
                column +
                "'";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DBUtils.doDMLv2(prepStmt, sqlStmt);
            ResultSet rs = DBUtils.getResult();

            // Make column unique if it's not already
            if (!rs.next()) {
                makeColumnUnique(column);
            }
            else {
                System.out.println("Column " + column + " already UNIQUE");
            }
        }
        catch(SQLException e)  {
            e.printStackTrace();
        }
        return rowsAffected;    }

    @Override
    public int makeColumnUnique(String column) throws SQLException {
        String sqlStmt = "ALTER TABLE contacts" +
                " ADD UNIQUE (" +
                column +
                ")";

        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            rowsAffected = DBUtils.doDDL(prepStmt, sqlStmt);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int setActivationStatus(int active, int id) {
        String sqlStmt = "UPDATE contacts SET" +
                " Active = ?" +
                " WHERE (Contact_ID = ?)";
                // + " AND (Contact_ID > 3)";
        try{
            prepStmt = useConnection().prepareStatement(sqlStmt);
            prepStmt.setInt(1, active);
            prepStmt.setInt(2, id);
            rowsAffected = DBUtils.doDMLv2(prepStmt, sqlStmt);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ObservableList<Contact> ActiveContacts() throws SQLException {
        ObservableList<Contact> allActiveContacts = FXCollections.observableArrayList();

        try{
            String sqlStmt = "SELECT * FROM contacts" +
                    " WHERE active = 1" +
                    " ORDER BY Contact_ID ASC";
            prepStmt = useConnection().prepareStatement(sqlStmt);
            DBUtils.doDMLv2(prepStmt, sqlStmt);

            // Get the ResultSet of the executed query.
            ResultSet rs = DBUtils.getResult();

            // Extract the ResultSet to a class object.
            while (rs.next()) {
                Contact contact = getCntData(rs);
                allActiveContacts.add(contact);
            }
            return allActiveContacts;
        }
        catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return allActiveContacts;
    }

/* COMMENT OUT METHODS USED WHEN CONTACTS WAS UNMANAGED
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
            DBUtils.doDMLv2(prepStmt, sqlStmt);
            System.out.println("made it here 2");

            // Get the ResultSet of the executed query.
            System.out.println("made it here 3");
            ResultSet rs = DBUtils.getResult();

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
*/

}
