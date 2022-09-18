package com.thecodebarista.model;

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

    public User(int user_ID, String user_Name, String password) {
        User_ID = user_ID;
        User_Name = user_Name;
        Password = password;
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

    @Override
    public String toString() {
        return "[" + User_ID +
                "] " + User_Name;
    }
}
