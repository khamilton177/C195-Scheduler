package model;

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
     * Base User constructor.
     * @param User_ID   DB generated instance User_ID.
     * @param User_Name  User instance User_Name.
     * @param Password  User instance Password.
     */
    public User(int User_ID, String User_Name, String Password) {
        this.User_ID = User_ID;
        this.User_Name = User_Name;
        this.Password = Password;
    }

    /**
     * @return the User_ID
     */
    public int getUserId() {
        return User_ID;
    }

    /**
     * @return the User_Name
     */
    public String getUserName() {
        return User_Name;
    }

    /**
     * @param User_Name the User_Name to set
     */
    public void setUserName(String User_Name) {
        this.User_Name = User_Name;
    }

    /**
     * @return the Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @param Password the password to set
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    @Override
    public String toString() {
        return "User{" +
                "User_ID=" + User_ID +
                ", User_Name='" + User_Name + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
