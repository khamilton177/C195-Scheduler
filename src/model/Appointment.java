package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Appointment {
    /**
     * The Appointment ID
     */
    private final int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private Timestamp Start;
    private Timestamp End;
    private int Customer_ID;
    private int User_ID;
    private int Contact_ID;

    public Appointment(int appointment_id, String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_id, int user_id, int contact_id) {
        Appointment_ID = appointment_id;
        Title = title;
        Description = description;
        Location = location;
        Type = type;
        Start = start;
        End = end;
        Customer_ID = customer_id;
        User_ID = user_id;
        Contact_ID = contact_id;
    }

    public int getAppointmentID() {
        return Appointment_ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Timestamp getStart() {
        return Start;
    }

    public void setStart(Timestamp start) {
        Start = start;
    }

    public Timestamp getEnd() {
        return End;
    }

    public void setEnd(Timestamp end) {
        End = end;
    }

    public int getCustomerID() {
        return Customer_ID;
    }

    public void setCustomerID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public int getUserID() {
        return User_ID;
    }

    public void setUserID(int user_ID) {
        User_ID = user_ID;
    }

    public int getContactID() {
        return Contact_ID;
    }

    public void setContactID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "Appointment_ID=" + Appointment_ID +
                ", Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", Location='" + Location + '\'' +
                ", Type='" + Type + '\'' +
                ", Start=" + Start +
                ", End=" + End +
                ", Customer_ID=" + Customer_ID +
                ", User_ID=" + User_ID +
                ", Contact_ID=" + Contact_ID +
                '}';
    }
}
