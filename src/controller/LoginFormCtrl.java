package controller;

import DAO.CustomerDaoImpl;
import DAO.UserDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static main.AppointmentScheduler.actLog;
//import static main.AppointmentScheduler.loginLogger;

public class LoginFormCtrl implements Initializable {

    /**
     * Obtains current user's information from users table.
     */
    User currentUser;

    /**
     * Top-level container of the JavaFX application.
     */
    Stage stage;

    /**
     * The loaded fxml file used to present a JavaFX application screen.
     */
    Parent scene;

    /**
     * Boolean to instruct application to use the foreign language error messages
     */
    public static boolean uselocaleErrMsg;

    public static Label static_ZoneIdLabel;

    @javafx.fxml.FXML
    private Label LoginMsgTxt;
    @javafx.fxml.FXML
    private Label BrandNameLbl;
    @javafx.fxml.FXML
    private Label ZoneIdLbl;
    @javafx.fxml.FXML
    private Button LoginBtn;
    @javafx.fxml.FXML
    private ImageView LoginIconImageView;
    @javafx.fxml.FXML
    private Label RegionIdLbl;
    @javafx.fxml.FXML
    private TextField UserNameTxtFld;
    @javafx.fxml.FXML
    private Label UserNameLbl;
    @javafx.fxml.FXML
    private Label PasswordLbl;
    @javafx.fxml.FXML
    private PasswordField PwdFld;
    @javafx.fxml.FXML
    private Button ExitBtn;
    @javafx.fxml.FXML
    private ImageView BrandImageView;


    /**
     * Method gets current users ZoneID at login then sets the labels in Login Form<>BR</>Timezone info is used for Scheduling appointments.
     */
    private void setZoneId() {
        ZoneId zoneId = ZoneId.systemDefault();
        TimeZone defaultTZ = TimeZone.getDefault();
        TimeZone zoneIdTZ = TimeZone.getTimeZone(zoneId); //Will use this since VM may be different from user.

        System.out.println("Default TZ is" + defaultTZ +
                "\n zone id TZ: " + zoneIdTZ);

        ZoneIdLbl.setText(zoneId.toString());
    }

    /**
     * Method sets the Login Form elements text and error messages to language specified by Locale.
     * @return boolean useForeign
     */
    private Boolean setLoginLanguage() {
        ResourceBundle rb = ResourceBundle.getBundle("Lang");
        String userSystemLocale = String.valueOf(Locale.getDefault());
        System.out.println("Locale is: " + userSystemLocale);
        Boolean useForeign = false;

        if(userSystemLocale.equals("fr")) {
            BrandNameLbl.setText(rb.getString("BrandName"));
            UserNameLbl.setText(rb.getString("Username"));
            PasswordLbl.setText(rb.getString("Password"));
            LoginBtn.setText(rb.getString("Login"));
            ExitBtn.setText(rb.getString("Exit"));
            // stage.setTitle(rb.getString("Title"));
            useForeign = true;
        }
        return useForeign;
    }

    public static void loginLogger() {
        try{
            actLog = Logger.getLogger("/login_activity.txt");
            FileHandler fh = new FileHandler("login_activity.txt", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            actLog.addHandler(fh);
            actLog.setLevel(Level.ALL);
        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    /**
     * Sets the Login Form error messages in current users locale while writing the message to the logger in English.<>BR</>REQ. B: Improved switch statement with lamba expression.
     * @param msg
     * @return
     */
    private String setLoginErrMsg(String msg) {
        ResourceBundle rb = ResourceBundle.getBundle("Lang");
        ResourceBundle enRb = ResourceBundle.getBundle("Lang", Locale.forLanguageTag("en"));
        StringBuilder logMsg = new StringBuilder("Valid Login: ");
        logMsg.append(msg.contains("Success"));
        logMsg.append(System.getProperty("line.separator"));
        logMsg.append("Reason: ");

        // Using lamda to enhance code.
        switch (msg) {
            case "Success" -> {
                // LoginMsgTxt.setText(rb.getString("Success"));
                logMsg.append(enRb.getString("Success"));
            }
            case "EmptyName" -> {
                LoginMsgTxt.setText(rb.getString("EmptyName"));
                logMsg.append(enRb.getString("EmptyName"));
            }
            case "EmptyPassword" -> {
                LoginMsgTxt.setText(rb.getString("EmptyPassword"));
                logMsg.append(enRb.getString("EmptyPassword"));
            }
            case "ErrorMsg" -> {
                LoginMsgTxt.setText(rb.getString("ErrorMsg"));
                logMsg.append(enRb.getString("ErrorMsg"));
            }
            default -> {
                LoginMsgTxt.setText(rb.getString("BothEmptyForm"));
                logMsg.append(String.valueOf(enRb.getString("BothEmptyLog")));
            }
        }
        logMsg.append(System.getProperty("line.separator"));
        return logMsg.toString();
    }

    /**
     * Method builds the query string with users inputted credentials
     * @return String loginCredentials
     */
    private String login() {
        String userName = UserNameTxtFld.getText();
        String password = PwdFld.getText();
        String emptyCredentials = "";

        if (userName.isEmpty() && password.isEmpty()){
            actLog.warning(setLoginErrMsg("EmptyBoth"));
        }
        else if (password.isEmpty()){
            actLog.warning(setLoginErrMsg("EmptyPassword"));
        }
        else if (userName.isEmpty()){
            actLog.warning(setLoginErrMsg("EmptyName"));
        }
        else{
            String loginCredentials = "SELECT * FROM users WHERE User_Name  = '" +
                    userName +
                    "' AND Password  = '" + password + "'";
            return loginCredentials;
        }
        return emptyCredentials;
    }

    /**
     * Exits application.
     * @param event Cancel Button clicked.
     */
    @javafx.fxml.FXML
    public void onActionExit(ActionEvent event) {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Returns screen to MainForm on successful login.
     * @param event Login Button clicked.
     * @throws IOException Catch unknown exceptions.<>BR</>login attempts will be logged in login_activity.txt.
     */
    @javafx.fxml.FXML
    private void onActionLogin(ActionEvent event) throws IOException {
        String btnTxt = ((Button)event.getSource()).getId().replace("Btn", "");
        System.out.println("Login Button clicked.: " + ((Button)event.getSource()).getId());


        try{
            String credentials = login();

            if(!credentials.isEmpty()) {
                currentUser = UserDaoImpl.userLogin(credentials);
                int getCurrentUserid;

                try{
                    getCurrentUserid = Objects.requireNonNull(currentUser).getUserId();
                    if (getCurrentUserid > 0){
                        System.out.println("Valid Login: " + String.valueOf(getCurrentUserid));
                        setLoginErrMsg("Success");
                        actLog.info(setLoginErrMsg("Success"));

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../view/main-menu.fxml"));
                        scene = loader.load();
                        MainMenuCtrl formController = loader.getController();
                        formController.setCurrentUserId(getCurrentUserid);

                        // Cast window to stage
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                }
                catch(NullPointerException e) {
                    actLog.warning(setLoginErrMsg("ErrorMsg"));
                }
            }
        }
        catch(Exception e) {
            System.out.println("from here 5 " + e.getMessage());
            System.out.println("from here 6 " +e.getCause());
        }
    }



    /**
     * Initializes the LoginFormController class.
     * <BR>Sets the label text for the TimeZone ID at Login.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the Login Form label with the ZoneID information.
        setZoneId();
        //loginLogger();

        try {
            Customer seeCust1;
            seeCust1 = CustomerDaoImpl.getCustomer(3);
            System.out.println("Customer Name :" + seeCust1.getCustomerName());

            Customer seeCust2;
            seeCust2 = CustomerDaoImpl.getCustomer(6);
            System.out.println("Customer Name :" + seeCust2.getCustomerName());

        //    CustomerDaoImpl.deleteCst(5);

         //  CustomerDaoImpl.insertCst("John Smith", "Third Street", "22222", "555-666-5555", 61);
            CustomerDaoImpl.updateCst("Mary Allen", "5th Street", "88888", "718-666-5555",103, 6);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("From getCustomer :" + e.getMessage());
        }

        // Get the locale information and direct application to use the foreign messages for the Login Form.
        uselocaleErrMsg = setLoginLanguage();
        System.out.println("Are we using foreign error messages? " + uselocaleErrMsg);
    }

}
