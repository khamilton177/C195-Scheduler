package com.thecodebarista.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.sql.Timestamp;

/**
 * Appointment Model representing client_schedule.appointments DB table.
 */
public class Appointment {
    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private Timestamp Start;
    private Timestamp End;
    private int Customer_ID;
    private int User_ID;
    private int Contact_ID;

    /**
     * Exist in Object Only field used to capture Report Query Tableview field property.
     */
    private StringProperty month = new SimpleStringProperty();

    /**
     * Exist in Object Only field used to capture Report Query Tableview field property.
     */
    private IntegerProperty count = new SimpleIntegerProperty();

    /**
     * Default Appointment Constructor.
     */
    public Appointment() {
    }

    /**
     * All Field Appointment Constructor.
     * @param appointment_ID- Database generated Appointment_ID field
     * @param title - Appointment Title field (String)
     * @param description - Appointment Description field (String)
     * @param location - Appointment Location field (String)
     * @param type - Appointment Type field (String)
     * @param start - Appointment Start field (Timestamp in Model saved as DateTime in Database)
     * @param end - Appointment End field (Timestamp in Model saved as DateTime in Database)
     * @param customer_ID - Appointment Customer_ID field (foreign key - int)
     * @param user_ID - Appointment User_ID field (foreign key - int)
     * @param contact_ID - Appointment Contact _ID field (foreign key - int)
     */
    public Appointment(int appointment_ID, String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_ID, int user_ID, int contact_ID) {
        Appointment_ID = appointment_ID;
        Title = title;
        Description = description;
        Location = location;
        Type = type;
        Start = start;
        End = end;
        Customer_ID = customer_ID;
        User_ID = user_ID;
        Contact_ID = contact_ID;
    }

    public int getAppointment_ID() {
        return Appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
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

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public int getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    public StringProperty monthProperty() {
        return month;
    }

    public String getMonth() {
        return month.get();    }

    public void setMonth(String Month) {
        this.month.set(Month);
    }

    public IntegerProperty countProperty() {
        return count;
    }

    public int getCount() {
        return count.get();
    }

    public void setCount(int Count) {
        this.count.set(Count);
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