package com.thecodebarista.model;

/**
 * Contact Model representing client_schedule.contacts DB table.
 */
public class Contact {
    private int Contact_ID;
    private String Contact_Name;
    private String Email;
    private int Active;

    public Contact(int contact_ID, String contact_Name, String email) {
        Contact_ID = contact_ID;
        Contact_Name = contact_Name;
        Email = email;
    }

    public Contact(int contact_ID, String contact_Name, String email, int active) {
        Contact_ID = contact_ID;
        Contact_Name = contact_Name;
        Email = email;
        Active = active;
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

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    // Customized toString for Listview Selections
    @Override
    public String toString() {
        return "[" + Contact_ID +
                "] " + Contact_Name;
    }

}
