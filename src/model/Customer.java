package model;

public class Customer {
    private int Customer_ID;
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

    /**
     * @return the Customer_ID
     */
    public int getCustomerId() {
        return Customer_ID;
    }

    /**
     * @return the Customer_Name
     */
    public String getCustomerName() {
        return Customer_Name;
    }

    /**
     * @param Customer_Name the Customer_Name to set
     */
    public void setCustomerName(String Customer_Name) {
        this.Customer_Name = Customer_Name;
    }

    /**
     * @return Address
     */
    public String getCustomerAddress() {
        return Address;
    }

    /**
     * @param Address the Customer_Name to set
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }

    /**
     * @return the Postal_Code
     */
    public String getPostal_Code() {
        return Postal_Code;
    }

    /**
     * @param Postal_Code the Postal_Code to set
     */
    public void setPostal_Code(String Postal_Code) {
        this.Postal_Code = Postal_Code;
    }

    /**
     * @return the Phone
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * @param Phone the Phone to set
     */
    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    /**
     * @return the Division_ID
     */
    public int getDivision_ID() {
        return Division_ID;
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

