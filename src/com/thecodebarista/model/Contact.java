package com.thecodebarista.model;

public class Contact {
    private int Contact_ID;
    private String Contact_Name;
    private String Email;

    public Contact(int contact_ID, String contact_Name, String email) {
        Contact_ID = contact_ID;
        Contact_Name = contact_Name;
        Email = email;
    }

    public int getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    public String getContact_Name() {
        return Contact_Name;
    }

    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    // Customized toString for Listview Selections
    @Override
    public String toString() {
        return "[" + Contact_ID +
                "] " + Contact_Name;
    }

}
