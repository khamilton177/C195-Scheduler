package model;

public class Customer {
    private final int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private int Division_ID;

    public Customer(int customer_id, String customer_name, String address, String postal_code, String phone, int division_id) {
        Customer_ID = customer_id;
        Customer_Name = customer_name;
        Address = address;
        Postal_Code = postal_code;
        Phone = phone;
        Division_ID = division_id;
    }
}
