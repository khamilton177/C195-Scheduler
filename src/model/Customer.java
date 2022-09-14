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

    public int getCustomerId() {
        return Customer_ID;
    }

    public String getCustomerName() {
        return Customer_Name;
    }

    public void setCustomerName(String customer_name) {
        Customer_Name = customer_name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostalCode() {
        return Postal_Code;
    }

    public void setPostalCode(String postal_code) {
        Postal_Code = postal_code;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getDivisionID() {
        return Division_ID;
    }

    public void setDivisionId(int division_id) {
        Division_ID = division_id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "Customer_ID=" + Customer_ID +
                ", Customer_Name='" + Customer_Name + '\'' +
                ", Address='" + Address + '\'' +
                ", Postal_Code='" + Postal_Code + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Division_ID=" + Division_ID +
                '}';
    }
}

