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
import static com.thecodebarista.AppointmentScheduler.actLog;

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
    public static String static_ZoneId;
    String ZoneIdRegion;


    public Boolean isNewLogin = true;
    public static int sessionUserId;

    int currentUserId;
    String currentUserName;


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
            case "Success" -> logMsg.append(enRb.getString("Success"));

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

    public void setCurrentUserid(int objId) {
        LoginFormCtrl.sessionUserId = objId;
    }

/*

    public int getCurrentUserId(int objId) {
        currentUserId = objId;
        return currentUserId;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }
*/

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
     * @param event Login Button clicked.
     * @throws IOException Catch unknown exceptions.<>BR</>login attempts will be logged in login_activity.txt.
     */
    @javafx.fxml.FXML
    private void onActionLogin(ActionEvent event) throws IOException {
        String btnTxt = ((Button)event.getSource()).getId().replace("Btn", "");
        System.out.println("Login Button clicked.: " + ((Button)event.getSource()).getId());
        currentUserId = -1;

        try{
            String credentials = login();

            if(!credentials.isEmpty()) {
                currentUser = UserDaoImpl.userLogin(credentials);
                int getCurrentUserid;

                try{
                    getCurrentUserid = Objects.requireNonNull(currentUser).getUser_ID();
                    if (getCurrentUserid > 0){
                        System.out.println("Valid Login: " + getCurrentUserid);
                        setLoginErrMsg("Success");
                        actLog.info(setLoginErrMsg("Success"));
                        currentUserId = getCurrentUserid;

                        setCurrentUserid(getCurrentUserid);


                        currentUserName = currentUser.getUser_Name();

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
                        scene = loader.load();
                        MainMenuCtrl formController = loader.getController();
                        formController.setCurrentUserIdInfo(currentUserId);
                        formController.setCurrentUserNameInfo(currentUserName);


                        // formController.loginAppointAlert(currentUserId, currentUserName);

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
        // Set the Login Form's label with the ZoneID information.
        setZoneId();
        static_ZoneId = ZoneIdLbl.getText();
        ZoneIdRegion = ZoneIdLbl.getText().split("/")[0];
        System.out.println("Region: " + ZoneIdRegion);

        // Get the locale information and direct application to use the foreign messages for the Login Form.
        uselocaleErrMsg = setLoginLanguage();
        System.out.println("Are we using foreign error messages? " + uselocaleErrMsg);
    }

}
