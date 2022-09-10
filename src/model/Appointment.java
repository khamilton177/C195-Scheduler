package model;

public class Appointment {
    /**
     * The Appointment ID
     */
    private final int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private int Start;
    private int End;
    private int Customer_ID;
    private int User_ID;
    private int Contact_ID;

    public Appointment(int appointment_id, String title, String description, String location, String type, int start, int end, int customer_id, int user_id, int contact_id) {
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
}
