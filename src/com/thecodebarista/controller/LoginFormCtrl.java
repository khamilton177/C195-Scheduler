package com.thecodebarista.controller;
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
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import com.thecodebarista.model.User;
import com.thecodebarista.dao.UserDaoImpl;

/**
 * Login Screen controller.
 */
public class LoginFormCtrl implements Initializable {
    /**
     * Top-level container of the JavaFX application.
     */
    Stage stage;

    /**
     * The loaded fxml file used to present a JavaFX application screen.
     */
    Parent scene;

    /**
     * Obtains user information from users table for Login Attempt User.
     */
    User notLoginUser;

    /**
     * Logger to track application login attempts.
     */
    public static Logger actLog;

    /**
     * Boolean to instruct application to use the foreign language error messages
     */
    public static boolean useLocale;

    /**
     * Static string used to retain the current user's ZoneID information in the form Label- ZoneIdLbl.
     */
    public static String static_ZoneId;

    /**
     * Static Session User Object after Successful Login.
     */
    public static User sessionUser;

    /**
     * Static int used to retain the Login user's ID information.
     */
    public static int sessionUserId;

    /**
     * Static String used to retain the Login user's name information.
     */
    public static String sessionUserName;

    /**
     * Static int used to retain the Login user's Admin status information.
     */
    public static int sessionUserAccess;

    @javafx.fxml.FXML
    private Label LoginMsgTxt;
    @javafx.fxml.FXML
    private Label BrandNameLbl;
    @javafx.fxml.FXML
    public Label ZoneIdLbl;
    @javafx.fxml.FXML
    private Button LoginBtn;
    @javafx.fxml.FXML
    private ImageView LoginIconImageView;
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
     * Method gets current users ZoneID at login then sets the labels in Login Form.
     * Timezone info is used for Scheduling appointments.
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
    private void setLoginLanguage() {
        ResourceBundle rb = ResourceBundle.getBundle("Lang");
        String userSystemLocaleLang = String.valueOf(Locale.getDefault().getLanguage());
        System.out.println("Language is " + userSystemLocaleLang);

        if(userSystemLocaleLang.equals("fr")) {
            BrandNameLbl.setText(rb.getString("BrandName"));
            UserNameLbl.setText(rb.getString("Username"));
            PasswordLbl.setText(rb.getString("Password"));
            LoginBtn.setText(rb.getString("Login"));
            ExitBtn.setText(rb.getString("Exit"));
        }
    }

    /**
     * Method logs login attempt activity to file.
     */
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
     * <b>LAMBDA USAGE - </b>Method uses user's locale for alert while writing the message to the logger in English.
     * This is not custom lambda; it was generated by IDE.<BR>I didn't find it useful in this case and did not use it for any other switch statements.
     * @param  msg - Message in user's locale language.
     * @return String message for alert and logger.
     */
    private String setLoginErrMsg(String msg) {
        ResourceBundle rb = ResourceBundle.getBundle("Lang");
        ResourceBundle enRb = ResourceBundle.getBundle("Lang", Locale.forLanguageTag("en"));
        StringBuilder logMsg = new StringBuilder("Valid Login: ");
        logMsg.append(msg.contains("Success"));
        logMsg.append(System.getProperty("line.separator"));
        logMsg.append("Reason: ");

        // Used IDE suggested lambda to enhance code. No parameter
        switch (msg) {
            case "Success" -> logMsg.append(enRb.getString("Success"));

            case "Inactive" -> {
                LoginMsgTxt.setText(rb.getString("Inactive"));
                logMsg.append(enRb.getString("Inactive"));
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
                logMsg.append(enRb.getString("BothEmptyLog"));
            }
        }
        logMsg.append(System.getProperty("line.separator"));
        return logMsg.toString();
    }

    /**
     * Method builds the query string with users inputted credentials.
     * @return String loginCredentials
     */
    private String login() {
        String userName = UserNameTxtFld.getText();
        String password = PwdFld.getText();
        String passCredentials = "";

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
        return passCredentials;
    }

    /**
     * Set the successful login userid to the session user id.
     * @param objId - The User_ID of login user.
     */
    public void setCurrentUserid(int objId) {
        LoginFormCtrl.sessionUserId = objId;
    }

    /**
     * Set the successful login user access level to the session user access.
     * @param objIsAdmin - The User_ID of login user.
     */
    public void setCurrentUsesAccess(int objIsAdmin) {
        LoginFormCtrl.sessionUserAccess = objIsAdmin;
    }

    /**
     * Exits application.
     * @param event Cancel Button clicked.
     */
    @javafx.fxml.FXML
    public void onActionExit(ActionEvent event) {
        System.out.println("Cancel Button Source: " + event.getSource().toString());
        System.out.println("Cancel Button ID: " + ((Button)event.getSource()).getId());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Returns screen to MainForm on successful login.
     * login attempts will be logged in login_activity.txt.
     * @param event Login Button clicked.
     * @throws IOException Catch unknown exceptions.
     */
    @javafx.fxml.FXML
    private void onActionLogin(ActionEvent event) throws IOException {
        String btnTxt = ((Button)event.getSource()).getId().replace("Btn", "");
        System.out.println("Login Button clicked.: " + ((Button)event.getSource()).getId());
        sessionUserId = -1;

        try{
            String credentials = login();

            if(!credentials.isEmpty()) {
                notLoginUser = UserDaoImpl.userLogin(credentials);
                int getCurrentUserid;

                try{
                    getCurrentUserid = Objects.requireNonNull(notLoginUser).getUser_ID();
                    if (getCurrentUserid > 0){
                        if (notLoginUser.getActive() < 1){
                            setLoginErrMsg("Inactive");
                            actLog.info(setLoginErrMsg("Inactive"));
                            return;
                        }
                        System.out.println("Valid Login: " + getCurrentUserid);
                        setLoginErrMsg("Success");
                        actLog.info(setLoginErrMsg("Success"));
                        sessionUser = notLoginUser;
                        sessionUserId = getCurrentUserid;
                        setCurrentUserid(getCurrentUserid);

                        sessionUserName = notLoginUser.getUser_Name();

                        sessionUserAccess = notLoginUser.getIs_Admin();
                        setCurrentUsesAccess(sessionUserAccess);

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
                        scene = loader.load();
                        MainMenuCtrl formController = loader.getController();
                        formController.setCurrentUserIdInfo(sessionUserId);
                        formController.setCurrentUserNameInfo(sessionUserName);
                        formController.setCurrentUserViewAccess(sessionUserAccess);

                        formController.loginAppointAlert();

                        // Cast window to stage
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(scene));
                        stage.show();

                        System.out.println("This is session user: " + LoginFormCtrl.sessionUserId);
                    }
                }
                catch(NullPointerException e) {
                    actLog.warning(setLoginErrMsg("ErrorMsg"));
                }
            }
        }
        catch(Exception e) {
            System.out.println(String.format("%s msg: %s", btnTxt, e.getMessage()));
            System.out.println(String.format("%s msg: %s", btnTxt, e.getCause()));
        }
    }

    /**
     * Initializes the LoginFormCtrl class.
     * Sets the label text for the TimeZone ID at Login and retrieves the language used for login.
     * @param url default application URL
     * @param rb default application ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the Login Form's label with the ZoneID information.
        setZoneId();
        static_ZoneId = ZoneIdLbl.getText();
        System.out.println("Region: " + ZoneIdLbl.getText().split("/")[0]);

        // Get the locale information and direct application to use the foreign messages for the Login Form.
        setLoginLanguage();
        //System.out.println("Are we using foreign error messages? " + useLocale);
    }
}