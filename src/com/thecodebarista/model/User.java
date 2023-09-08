package com.thecodebarista.model;

import java.sql.Timestamp;

/**
 * User Model representing client_schedule.users DB table.
 */
public class User {
    /**
     * The User ID.
     */
    private final int User_ID;
    /**
     * The User name.
     */
    private String User_Name;
    /**
     * The User Password.
     */
    private String Password;

    /**
     * The User Admin status.
     */
    private int Is_Admin;

    /**
     * The User Active status.
     */
    private int Active;

    /**
     * The User last successful login timestamp.
     * This will not be a part of the new/update forms.
     */
    private Timestamp Last_Login;

    /**
     * User constructor for end-user.
     * @param user_ID
     * @param user_Name
     * @param password
     */
    public User(int user_ID, String user_Name, String password) {
        User_ID = user_ID;
        User_Name = user_Name;
        Password = password;
    }

    /**
     * User constructor for Admins.
     * @param user_ID
     * @param user_Name
     * @param password
     * @param is_Admin
     * @param active
     */
    public User(int user_ID, String user_Name, String password, int is_Admin, int active) {
        User_ID = user_ID;
        User_Name = user_Name;
        Password = password;
        Is_Admin = is_Admin;
        Active = active;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getIs_Admin() {
        return Is_Admin;
    }

    public void setIs_Admin(int is_Admin) {
        Is_Admin = is_Admin;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public Timestamp getLast_Login() {
        return Last_Login;
    }

    public void setLast_Login(Timestamp last_Login) {
        Last_Login = last_Login;
    }

    @Override
    public String toString() {
        return "[" + User_ID +
                "] " + User_Name;
    }
}
