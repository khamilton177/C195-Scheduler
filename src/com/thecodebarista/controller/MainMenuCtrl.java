package com.thecodebarista.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.thecodebarista.TimeMachine;
import com.thecodebarista.dao.*;
import com.thecodebarista.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main menu of the Global Consulting Scheduler Application.
 */
public class MainMenuCtrl extends LoginFormCtrl implements Initializable, TimeMachine {

    /**
     * Label used to retain button used to produce the form, New or Update associated table.
     */
    public static Label static_AddUpdateLabel;

    /**
     * Appointment row selected in tableview.
     */
    public Appointment selectedAppt;

    /**
     * Customer row selected in tableview or appointment forms.
     */
    Customer selectedCst;

    /**
     * Contact row selected in appointment or report form dropdowns.
     */
    Contact selectedCnt;

    /**
     * User row selected in appointment form dropdown.
     */
    User selectedUser;

    /**
     * The alert built by the onAction event triggered.
     */
    Alert alert;

    /**
     * Tab selected in current screen.
     */
    Tab currentTab;

    /**
     * Tab selected in current screen.
     */
    protected String prevTab;

    /**
     * Tab selected under Appointments tab screen.
     */
    Tab currentSubTab;

    /**
     * The return of calculateStartLdt method.
     */
    LocalDateTime StartTime;

    /**
     * The return of calculateEndLdt method.
     */
    LocalDateTime EndTime;

    /**
     * The confirm button presented in the alert confirmation dialog.
     */
    Optional<ButtonType> confirm;

    /**
     * Formats timestamps for use in the start_TxtFld and end_TxtFld.
     */
    SimpleDateFormat tsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * ObservableList of User's Names Status.
     */
    ObservableList<String> userNames = FXCollections.observableArrayList();

    /**
     * ObservableList of Contact's Names Status.
     */
    ObservableList<String> cntNames = FXCollections.observableArrayList();

    /**
     * ObservableList of Admin Status.
     */
    ObservableList<String> adminStatus = FXCollections.observableArrayList();

    /**
     * ObservableList of Active Statuses.
     */
    ObservableList<String> activeStatus = FXCollections.observableArrayList();

    /**
     * ObservableList of Months by full name.
     */
    ObservableList<String> monthsItems = FXCollections.observableArrayList();

    /**
     * ObservableList of types found in the DB appointments table.
     */
    ObservableList<String> typeItems = FXCollections.observableArrayList();

    /**
     * Boolean value representing MonthCB selected value.
     */
    private Boolean moFilter = false;

    /**
     * Boolean value representing TypeCB selected value.
     */
    private Boolean typeFilter = false;
    private Boolean cntFilter = false;
    private Boolean cntMoFilter = false;
    private Boolean cntWkFilter = false;

    /**
     * Label holds the User_Name of the session user on Main Menu screen.
     */
    @javafx.fxml.FXML
    public Label CurrentUserNameLbl;

    /**
     * Label holds the User_ID of the session user on Main Menu screen.
     */
    @javafx.fxml.FXML
    public Label CurrentUserIdLbl;

    /**
     * Logout link on Main Menu screen.
     */
    @javafx.fxml.FXML
    protected Hyperlink LogOutBtn;

    /**
     * Tab pane that holds the all tabs.
     */
    @javafx.fxml.FXML
    protected TabPane MainTabPane;

    /**
     * SelectModel for the MainTabPane.
     */
    SingleSelectionModel<Tab> tabSelect;

    /**
     * The Appointments tab.
     */
    @javafx.fxml.FXML
    protected Tab ApptTab;

    /**
     * The Customers tab.
     */
    @javafx.fxml.FXML
    protected Tab CstTab;

    /**
     * The Reports tab.
     */
    @javafx.fxml.FXML
    protected Tab RptTab;

    /**
     * The Users tab.
     */
    @javafx.fxml.FXML
    protected Tab UserAdminTab;

    /**
     * The Contacts tab.
     */
    @javafx.fxml.FXML
    protected Tab CntAdminTab;

    /*
    --- Start of Appointment's Appointments Tabs FXML controls ---
     */

    /**
     * The Appointment Period Radio group.
     */
    @javafx.fxml.FXML
    protected ToggleGroup ApptPeriodGrp;

    /**
     * The 'All' Appointment's Radio button.
     */
    @javafx.fxml.FXML
    protected RadioButton AppAllRadio;

    /**
     * The Monthly Appointment's Radio button.
     */
    @javafx.fxml.FXML
    protected RadioButton AppWkRadio;

    /**
     * The Weekly Appointment's Radio Button.
     */
    @javafx.fxml.FXML
    protected RadioButton AppMoRadio;

    /**
     * New button on the Appointment Tabs.
     */
    @javafx.fxml.FXML
    protected Button ApptAddBtn;

    /**
     * Update button on the Appointment Tabs.
     */
    @javafx.fxml.FXML
    protected Button ApptUpdateBtn;

    /**
     * Delete button on the Appointment Tabs.
     */
    @javafx.fxml.FXML
    protected Button ApptDeleteBtn;

    /**
     * The Monthly Appointment Tab Controller.
     */
    @javafx.fxml.FXML
    protected ApptTableMonthlyCtrl includeApptMoController;

    /**
     * The Weekly Appointment Tab Controller.
     */
    @javafx.fxml.FXML
    protected ApptTableWeeklyCtrl includeApptWkController;

    /**
     * Tab pane that holds the Appointment sub tabs.
     */
    @javafx.fxml.FXML
    private TabPane ApptTabs;

    /**
     * Appointments sub Tab.
     */
    @javafx.fxml.FXML
    private Tab AppTab;

    /**
     * Appointments by Month sub Tab.
     */
    @javafx.fxml.FXML
    private Tab AppMoTab;

    /**
     * Appointment by Week sub Tab.
     */
    @javafx.fxml.FXML
    private Tab AppWkTab;

    /**
     * The Appointments Table View on each Tab
     */
    @javafx.fxml.FXML
    protected TableView<Appointment> ApptTblView;

    /**
     * Appointment ID column on the ApptTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> appointment_ID_Col;

    /**
     * Title column on the ApptTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> title_Col;

    /**
     * Description column on the ApptTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String>  description_Col;

    /**
     * Location column on the ApptTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String>  location_Col;

    /**
     * Contact column on the ApptTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> contact_ID_Col;

    /**
     * Type column on the ApptTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String>  type_Col;

    /**
     * Start Date and Time column on the ApptTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Timestamp>  start_Col;

    /**
     * End Date and Time column on the ApptTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Timestamp>  end_Col;

    /**
     * Customer ID column on the ApptTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> customer_ID_Col;

    /**
     * User ID column on the ApptTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> user_ID_Col;

    /*
    --- End of Appointment's Appointments Tabs FXML controls ---
     */


    /*
    --- Start of Customers Tab FXML controls ---
     */

    /**
     * New button on the Customers Tab.
     */
    @javafx.fxml.FXML
    protected Button CstAddBtn;

    /**
     * Update button on the Customers Tab.
     */
    @javafx.fxml.FXML
    protected Button CstModifyBtn;

    /**
     * Delete button on the Customers Tab.
     */
    @javafx.fxml.FXML
    protected Button CstDeleteBtn;

    /**
     * The Customers Table View on the Customers Tab.
     */
    @javafx.fxml.FXML
    protected TableView<Customer> CstTblView;

    /**
     * Customer ID column on the CstTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Customer, Integer> CstIdCol;

    /**
     * Customer Name column on the CstTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Customer, String> CstNameCol;

    /**
     * Address column on the CstTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Customer, String> CstAddressCol;

    /**
     * The Postal Code column on the CstTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Customer, String> CstPostalCodeCol;

    /**
     * Phone column on the CstTblView.
     */
    @javafx.fxml.FXML
    private TableColumn<Customer, String> CstPhoneCol;

    /**
     * Country ID column on the CstTblView.
     */
    @javafx.fxml.FXML
    private TableColumn<FirstLevelDivision, Integer> CstCountryIdCol;

    /**
     * Division ID column on the CstTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Customer, Integer> CstDivisionIdCol;
    /*
    --- End of Customers Tab FXML controls ---
     */

    /*
    --- Start of Reports Tab FXML controls ---
     */

    /**
     * Report Menu dropdown on Reports Tab.
     */
    @javafx.fxml.FXML
    protected MenuButton ReportMenuBtn;

    /**
     * Stack Pane holding the Report Panes on the Reports Menu.
     */
    @javafx.fxml.FXML
    private StackPane reportFilterStackPane;

    /**
     * Pane on Reports Tab used to display the return of the Report selected.
     */
    @javafx.fxml.FXML
    private TitledPane ReportTitlePane;

    /**
     *
     */
    @javafx.fxml.FXML
    private Pane CstMonthTypePane;
    @javafx.fxml.FXML
    private ComboBox<String> MonthCB;
    @javafx.fxml.FXML
    private ComboBox<String> TypeCB;
    @javafx.fxml.FXML
    private MenuItem CstMonthTypePaneMenuItem;
    @javafx.fxml.FXML
    private MenuItem CntSchedulePaneMenuItem;
    @javafx.fxml.FXML
    private Pane CntSchedulePane;

    @javafx.fxml.FXML
    protected TableView<Appointment> CstMonthTypeTblView;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> rptMonth;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> rptType;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> rptCount;
    @javafx.fxml.FXML
    private TableView<Appointment> CntScheduleTblView;
    @javafx.fxml.FXML
    private StackPane TblViewStackPane;
    @javafx.fxml.FXML
    private TableColumn<Appointment, Integer> rptAppt;
    @javafx.fxml.FXML
    private TableColumn<Appointment, String> rptTitle;
    @javafx.fxml.FXML
    private TableColumn<Appointment, String> rptDesc;
    @javafx.fxml.FXML
    private TableColumn<Appointment, Timestamp> rptStart;
    @javafx.fxml.FXML
    private TableColumn<Appointment, Timestamp> rptEnd;
    @javafx.fxml.FXML
    private TableColumn<Appointment, Integer> rptCnt;
    @javafx.fxml.FXML
    private ComboBox<Contact> CntScheduleSearchCB;
    @javafx.fxml.FXML
    private ToggleGroup CntApptPeriodRadioGrp;
    @javafx.fxml.FXML
    private RadioButton CntScheduleWkRadio;
    @javafx.fxml.FXML
    private RadioButton CntScheduleMoRadio;
    @javafx.fxml.FXML
    private ButtonBar CstMonthTypeBtnBar;
    @javafx.fxml.FXML
    private Button CstMonthTypeSearchBtn;
    @javafx.fxml.FXML
    private Button CstMonthTypeClearBtn;
    @javafx.fxml.FXML
    private TableColumn rptType2;
    @javafx.fxml.FXML
    private MenuItem ApptDurationPaneMenuItem;
    @javafx.fxml.FXML
    private TextArea ApptDurationTblView;
    @javafx.fxml.FXML
    private Pane AgedLoginPane;
    @javafx.fxml.FXML
    private MenuItem AgedLoginPaneMenuItem;
    @javafx.fxml.FXML
    protected TableView<User> AgedLoginTblView;
    @javafx.fxml.FXML
    protected TableColumn<User, String> rptUserName;
    @javafx.fxml.FXML
    protected TableColumn<User, Timestamp> rptLastLogin;
    @javafx.fxml.FXML
    protected TableColumn<User, Integer> rptDays;
    /*
    --- End of Reports Tab FXML controls ---
     */


    /*
    --- Start of User Tab FXML controls ---
     */

    /**
     * New button on the User Tabs.
     */
    @javafx.fxml.FXML
    protected Button UserAddBtn;

    /**
     * Update button on the User Tabs.
     */
    @javafx.fxml.FXML
    protected Button UserModifyBtn;

    /**
     * Activate/Deactivate button on the User Tabs.
     */
    @javafx.fxml.FXML
    protected Button UserActiveStatusBtn;

    @javafx.fxml.FXML
    private Button userSearchFilterBtn;

    @javafx.fxml.FXML
    private Button UserClrSearchBtn;

    @javafx.fxml.FXML
    private Pane userSearchPane;

    /**
     * The Searchable User Filter.
     */
    @javafx.fxml.FXML
    protected ComboBox<String> sfUserNameCB;

    /**
     * The Active Status Search Filter.
     */
    @javafx.fxml.FXML
    protected ComboBox<String> sfAdminCB;

    /**
     * The Active Status Search Filter.
     */
    @javafx.fxml.FXML
    protected ComboBox<String> sfUserActiveCB;

    /**
     * The User Table View.
     * Only shown when user has Admin Access.
     */
    @javafx.fxml.FXML
    protected TableView<User> UserTblView;

    /**
     * User ID column on the UserTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<User, Integer> UserIdCol;

    /**
     * UserName column on the UserTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<User, String> UserNameCol;

    /**
     * User Password column on the UserTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<User, String> UserPwdCol;

    /**
     * User ID column on the UserTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<User, Integer> UserAdminCol;

    /**
     * User ID column on the UserTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<User, Integer> UserActiveCol;

    /*
    --- End of User Tab FXML controls ---
     */


    /*
    --- Start of Contact Tab FXML controls ---
     */

    /**
     * New button on the Contact Tabs.
     */
    @javafx.fxml.FXML
    protected Button CntAddBtn;

    /**
     * Update button on the Contact Tabs.
     */
    @javafx.fxml.FXML
    protected Button CntModifyBtn;

    /**
     * Activate/Deactivate button on the Contact Tabs.
     */
    @javafx.fxml.FXML
    protected Button CntActiveStatusBtn;

    @javafx.fxml.FXML
    private Button cntSearchFilterBtn;

    @javafx.fxml.FXML
    private Button CntClrSearchBtn;

    @javafx.fxml.FXML
    private Pane cntSearchPane;

    /**
     * The Searchable Contact Name Filter.
     */
    @javafx.fxml.FXML
    protected ComboBox<String> sfCntNameCB;

    /**
     * The Active Status Search Filter.
     */
    @javafx.fxml.FXML
    protected TextField sfCntEmail;

    /**
     * The Active Status Search Filter.
     */
    @javafx.fxml.FXML
    protected ComboBox<String> sfCntActiveCB;

    @javafx.fxml.FXML
    private Button CntSearchBtn;

    /**
     * The Contact Table View.
     */
    @javafx.fxml.FXML
    protected TableView<Contact> CntTblView;

    /**
     * Contact ID column on the CntTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Contact, Integer> CntIdCol;

    /**
     * Contact Name column on the CntTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Contact, String> CntNameCol;

    /**
     * Contact Email column on the CntTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Contact, String> CntEmailCol;

    /**
     * Contact Active? column on the CntTblView.
     */
    @javafx.fxml.FXML
    protected TableColumn<Contact, Integer> CntActiveCol;
    /*
    --- End of Contact Tab FXML controls ---
     */


    /*
    --- Appointment New/Update Form Protected FXML Fields Start ---
    */
    /**
     * Title text field on Appointment New/Update form.
     */
    @javafx.fxml.FXML
    protected TextField title_TxtFld;

    /**
     * Description text field on Appointment New/Update form.
     */
    @javafx.fxml.FXML
    protected TextField description_TxtFld;

    /**
     * Location text field on Appointment New/Update form.
     */
    @javafx.fxml.FXML
    protected TextField location_TxtFld;

    /**
     * Type text field on Appointment New/Update form.
     */
    @javafx.fxml.FXML
    protected TextField type_TxtFld;

    /**
     * Start Date field on Appointment New/Update form.
     */
    @javafx.fxml.FXML
    protected DatePicker ApptStart_DatePick;

    /**
     * Start Time 'Hrs' dropdown on Appointment New/Update form.
     */
    @javafx.fxml.FXML
    protected ComboBox<Integer> StartTimeHrs;

    /**
     * Start Time 'Mins' dropdown on Appointment New/Update form.
     */
    @javafx.fxml.FXML
    protected ComboBox<Integer> StartTimeMins;

    /**
     * Duration dropdown on Appointment New/Update form.
     */
    @javafx.fxml.FXML
    protected ComboBox<Long> DurationCB;

    /**
     * Hidden end_TxtFld on Appointment New/Update form.
     * Holds the formatted timestamp text.
     */
    @javafx.fxml.FXML
    protected TextField start_TxtFld;

    /**
     * Hidden end_TxtFld on Appointment New/Update form.
     * Hold the formatted timestamp text.
     */
    @javafx.fxml.FXML
    protected TextField end_TxtFld;

    /**
     * Customer ID# dropdown on Appointment New/Update form.
     */
    @javafx.fxml.FXML
    protected ComboBox<Customer> customer_ID_CBox;

    /**
     * Contact ID# dropdown on Appointment New/Update form.
     */
    @javafx.fxml.FXML
    protected ComboBox<Contact> contact_ID_CBox;

    /**
     * User ID# dropdown on Appointment New/Update form.
     */
    @javafx.fxml.FXML
    protected ComboBox<User> user_ID_CBox;
    /*
    --- Appointment New/Update Form Protected FXML Fields End ---
    */


    /*
    --- Customer Add/Update Form Protected FXML Fields Start ---
    */
    /**
     * Phone text field on Customer Add/Update form.
     */
    @javafx.fxml.FXML
    protected TextField phone_TxtFld;

    /**
     * Customer Name text field on Customer Add/Update form.
     */
    @javafx.fxml.FXML
    protected TextField customer_Name_TxtFld;

    /**
     * Address text field on Customer Add/Update form.
     */
    @javafx.fxml.FXML
    protected TextField address_TxtFld;

    /**
     * Postal Code text field on Customer Add/Update form.
     */
    @javafx.fxml.FXML
    protected TextField postal_Code_TxtFld;

    /**
     *  Country ID# dropdown on Customer Add/Update form.
     */
    @javafx.fxml.FXML
    protected ComboBox<Country> country_ID_CBox;

    /**
     * Division #ID dropdown on Customer Add/Update form.
     */
    @javafx.fxml.FXML
    protected ComboBox<FirstLevelDivision> division_ID_CBox;
    /*
    --- Customer Add/Updated Form Protected FXML Fields End ---
    */



    /*
    --- User Add/Update Form Protected FXML Fields Start ---
    */
    /**
     * UserName text field on User Add/Update form.
     */
    @javafx.fxml.FXML
    protected TextField user_Name_TxtFld;

    /**
     * User Password text field on User Add/Update form.
     */
    @javafx.fxml.FXML
    protected PasswordField pwd_PwdFld;

    @javafx.fxml.FXML
    protected CheckBox user_Active_ChkBox;

    @javafx.fxml.FXML
    protected CheckBox admin_ChkBox;
    /*
    --- User Add/Update Form Protected FXML Fields End ---
    */



    /*
    --- Contact Add/Update Form Protected FXML Fields Start ---
    */
    /**
     * Contact Name text field on User Add/Update form.
     */
    @javafx.fxml.FXML
    protected TextField cnt_Name_TxtFld;

    /**
     * Contact Email text field on User Add/Update form.
     */
    @javafx.fxml.FXML
    protected TextField email_TxtFld;

    @javafx.fxml.FXML
    protected CheckBox cnt_Active_ChkBox;
    /*
    --- Contact Add/Updated Form Protected FXML Fields End ---
    */

    /**
     * Alert user of appointment happening within the next 15 minutes.
     * @throws NumberFormatException
     */
    protected void loginAppointAlert() throws NumberFormatException {
        System.out.println("Doing loginAppt Alert #1");
        AppointmentDAO apptDao = new AppointmentDaoImpl();
        String msgCtx = "";
        String alertTitle = "Appointment Notification";

        try{
            ObservableList<Appointment> userAppt = apptDao.getApptNowByUser(sessionUserId);
            int userApptSize = userAppt.size();
            System.out.println("Doing loginAppt Alert #3 - count: " + userApptSize);

            if(userApptSize > 0) {
                //For testing. TODO: Don't forget to comment out and use now()
                //LocalDateTime ldt = LocalDateTime.of(2023, 02, 04, 19, 15);
                LocalDateTime ldt = LocalDateTime.now();

                for (Appointment appt : userAppt) {
                    LocalDateTime apptStart = appt.getStart().toLocalDateTime();
                    System.out.println(String.format("Appt ID# %d%nAppt. Time:  %s", appt.getAppointment_ID(), apptStart.toString()));
                    if (apptStart.toLocalDate().equals(ldt.toLocalDate())) {
                        if (apptStart.toLocalTime().withSecond(0).withNano(0).isAfter(ldt.toLocalTime().withSecond(0).withNano(0))
                                || apptStart.toLocalTime().withSecond(0).withNano(0).equals(ldt.toLocalTime().withSecond(0).withNano(0))) {
                            Long minsTill = ChronoUnit.MINUTES.between(ldt, apptStart);
                            System.out.println("Minutes until appointment: " + minsTill);
                            if (minsTill >= 0l && minsTill < 16L) {
                                msgCtx = String.format("Hi %s,%nYou have Appointment ID #%d soon!%n%tD at %tR",
                                        CurrentUserNameLbl.getText(), appt.getAppointment_ID(), apptStart.toLocalDate(), apptStart.toLocalTime());
                                alert = buildAlert(Alert.AlertType.INFORMATION, alertTitle, msgCtx);
                                confirm = alert.showAndWait();
                                return;
                            }
                        }
                    }
                }
                System.out.println("Doing loginAppt Alert #4");
                userApptSize = 0; // None of the User's appointments were within 15 minutes set to 0.
            }
            if(userApptSize == 0) {
                msgCtx = String.format("Hi %s. Relax! You don't have any appoints within the next 15 minutes.",
                        CurrentUserNameLbl.getText());
                alert = buildAlert(Alert.AlertType.INFORMATION, alertTitle, msgCtx);
                confirm = alert.showAndWait();
            }
        }
        catch(SQLException | NumberFormatException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Sets Userid session label and user.
     * @param sessionUserId
     */
    protected void setCurrentUserIdInfo(int sessionUserId) {
        CurrentUserIdLbl.setText("ID #" + sessionUserId);
    }

    /**
     * Sets User name session label and user.
     * @param sessionUserName
     */
    protected void setCurrentUserNameInfo(String sessionUserName) {
        CurrentUserNameLbl.setText(sessionUserName);
    }

    /**
     * Removes the tabs that are only for Admin level access.
     * @param sessionUserAccess
     */

    protected void setCurrentUserViewAccess(int sessionUserAccess) {
        System.out.println("Access Level: " + sessionUserAccess);
        if (sessionUserAccess < 1) {

            int count = (int) MainTabPane.getTabs().stream().filter((p) -> p.getId().contains("Admin")).count();
            for (int i = 0; i < count; i++) {
                ObservableList<Tab> adminTabs = MainTabPane.getTabs();
                System.out.println("Tab List Count: " + adminTabs.size());
                for (Tab tab : adminTabs) {
                    if (tab.getId().contains("Admin")) {
                        adminTabs.remove(tab);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Creates an Alert instance.
     * @param alertType Alert Type to create.
     * @param titleTxt Text for Alert title.
     * @param msgCtx Text for Alert context.
     * @return The Alert instance.
     */
    protected Alert buildAlert(Alert.AlertType alertType, String titleTxt, String msgCtx){
        String header = "";

        try{
            alert = new Alert(alertType);
            String alertEnum = alertType.toString();

            switch(alertType){
                case CONFIRMATION:
                    header = alertEnum + " REQUIRED:";
                    break;

                case WARNING:
                    header = "";
                    break;

                case ERROR:
                    header = null;
                    break;

                default:
            }

            alert.setTitle(alertEnum + " " + titleTxt);
            alert.setHeaderText(header);
            alert.setContentText(msgCtx);
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
        }

        return alert;
    }

    /**
     * Method builds the message used in alerts.
     * @param msg Message for alert
     * @param useSeparator Boolean determines if new line is appended
     */
    protected String buildMessage(String msg, Boolean useSeparator) {
        StringBuilder validateErrMsg = new StringBuilder();
        String validateMsg = msg;
        validateErrMsg.append(validateMsg);
        if (useSeparator) {
            validateErrMsg.append(System.getProperty("line.separator"));
        }
        return validateErrMsg.toString();
    }

    /**
     * Method calculates the Start time and set the formatted timestamp in hidden text field.
     * @return StartTime (LocalDateTime) of the Appointment.
     */
    protected LocalDateTime calculateStartLdt() {
        LocalTime lt = LocalTime.of(StartTimeHrs.getValue(), StartTimeMins.getValue());
        StartTime = lt.atDate(ApptStart_DatePick.getValue());
        Timestamp tsStart = Timestamp.valueOf(StartTime);
        start_TxtFld.setText(tsFormat.format(tsStart));
        System.out.println("Invisible Start Text Field: " + start_TxtFld.getText());
        return StartTime;
    }

    /**
     * Method calculates the End time and set the formatted timestamp in hidden text field.
     * @return EndTime (LocalDateTime) of the Appointment.
     */
    protected LocalDateTime calculateEndLdt() {
        StartTime = calculateStartLdt();
        EndTime = StartTime.plusMinutes(DurationCB.getValue());
        System.out.println("This is end time: " + EndTime);
        Timestamp tsEnd = Timestamp.valueOf(EndTime);
        end_TxtFld.setText(tsFormat.format(tsEnd));
        System.out.println("End Text Field: " + end_TxtFld.getText());
        return EndTime;
    }

    /**
     * Method sets all User Constructor fields in a Tableview.
     */
    protected void userSetAllRows() {
        UserIdCol.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        UserNameCol.setCellValueFactory(new PropertyValueFactory<>("user_Name"));
        UserPwdCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        UserAdminCol.setCellValueFactory(new PropertyValueFactory<>("is_Admin"));
        UserActiveCol.setCellValueFactory(new PropertyValueFactory<>("active"));
    }

    /**
     * Method sets all Contact Constructor fields in a Tableview.
     */
    protected void cntSetAllRows() {
        CntIdCol.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));
        CntNameCol.setCellValueFactory(new PropertyValueFactory<>("contact_Name"));
        CntEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        CntActiveCol.setCellValueFactory(new PropertyValueFactory<>("active"));

    }

    /**
     * Method sets all Customer Constructor fields in a Tableview.
     */
    protected void cstSetAllRows() {
        CstIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        CstNameCol.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        CstAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        CstPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postal_Code"));
        CstPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        CstCountryIdCol.setCellValueFactory(new PropertyValueFactory<>("country_ID"));
        CstDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("division_ID"));
    }

    /**
     * Method sets all Appointment Constructor fields in a Tableview.
     */
    protected void apptSetAllRows() {
        appointment_ID_Col.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        title_Col.setCellValueFactory(new PropertyValueFactory<>("title"));
        description_Col.setCellValueFactory(new PropertyValueFactory<>("description"));
        location_Col.setCellValueFactory(new PropertyValueFactory<>("location"));
        type_Col.setCellValueFactory(new PropertyValueFactory<>("type"));
        start_Col.setCellValueFactory(new PropertyValueFactory<>("start"));
        end_Col.setCellValueFactory(new PropertyValueFactory<>("end"));
        customer_ID_Col.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        user_ID_Col.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        contact_ID_Col.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));
    }

    /**
     * Displays the User ObservableList data in the TableView.
     */
    public void displayUserTblViewData() throws SQLException {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        UserDAO userDao = new UserDaoImpl();
        userSetAllRows();
        allUsers.addAll(userDao.extractAll());
        UserTblView.setItems(allUsers);
    }

    /**
     * Displays the User ObservableList data in the TableView.
     */
    public void displayUserTblViewData(String option, String param) throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();
        UserDAO userDao = new UserDaoImpl();
        userSetAllRows();

        switch (option) {
            case "All":
                users.addAll(userDao.extractAll());
                break;
            case "sf":
                users.addAll(userDao.sfQuery(param));
                break;
            case "sfClear":
            default:
                users.addAll(userDao.ActiveUsers());
        }
        UserTblView.setItems(users);
    }

    /**
     * Displays the Contact ObservableList data in the TableView.
     */
    public void displayCntTblViewData() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        ContactDAO cntDao = new ContactDaoImpl();
        cntSetAllRows();
        allContacts.addAll(cntDao.extractAll());
        CntTblView.setItems(allContacts);
    }

    /**
     * Displays the Contact ObservableList data in the TableView.
     */
    public void displayCntTblViewData(String option, String param) throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        ContactDAO cntDao = new ContactDaoImpl();
        cntSetAllRows();

        switch (option) {
            case "All":
                contacts.addAll(cntDao.extractAll());
                break;
            case "sf":
                contacts.addAll(cntDao.sfQuery(param));
                break;
            case "sfClear":
            default:
                contacts.addAll(cntDao.ActiveContacts());
        }
        CntTblView.setItems(contacts);
    }

    /**
     * Displays the Customer ObservableList data in the TableView with the Country Info.
     */
    public void displayCstWithCoInfo() throws SQLException {
        ObservableList<Customer> cstWithCoInfo = FXCollections.observableArrayList();
        CustomerDAO cstDao = new CustomerDaoImpl();
        cstSetAllRows();
        cstWithCoInfo.addAll(cstDao.customerWithCoInfo());
        CstTblView.setItems(cstWithCoInfo);
    }

    /**
     * Displays the Customer ObservableList data in the TableView.
     */
    public void displayCstTblViewData() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        CustomerDAO cstDao = new CustomerDaoImpl();
        cstSetAllRows();
        allCustomers.addAll(cstDao.extractAll());
        CstTblView.setItems(allCustomers);
    }

    /**
     * Display the Appt ObservableList data in the TableView.
     */
    public void displayApptTblViewData() throws SQLException {
        ObservableList<Appointment> allApptAppointments = FXCollections.observableArrayList();
        AppointmentDAO apptDao = new AppointmentDaoImpl();
        apptSetAllRows();
        allApptAppointments.addAll(apptDao.extractAll());
        ApptTblView.setItems(allApptAppointments);
    }

    /**
     * Display the Appt ObservableList data in the TableView based on ApptPeriodGrp selection.
     */
    public void displayApptTblViewData(String btnTxt) throws SQLException {
        ObservableList<Appointment> ApptAppointments = FXCollections.observableArrayList();
        AppointmentDAO apptDao = new AppointmentDaoImpl();
        apptSetAllRows();
        if(btnTxt.equals("AppMoRadio")) {
            ApptAppointments.addAll(apptDao.getByMonth());
        } else if (btnTxt.equals("AppWkRadio") ){
            ApptAppointments.addAll(apptDao.getByWeekly());
        } else{
            ApptAppointments.addAll(apptDao.extractAll());
        }
        ApptTblView.setItems(ApptAppointments);
    }

    void displayAgedLogins() throws SQLException {
        ObservableList<User> agedLogins = FXCollections.observableArrayList();
        UserDAO agedDao = new UserDaoImpl();

        rptUserName.setCellValueFactory(new PropertyValueFactory<>("User_Name"));
        rptLastLogin.setCellValueFactory(new PropertyValueFactory<>("Last_Login"));
        rptDays.setCellValueFactory(new PropertyValueFactory<>("Days"));
        agedLogins.addAll(agedDao.agedLogins());
        AgedLoginTblView.setItems(agedLogins);
    }

    /**
     * Validates Both Appointment and Customer forms for missing data before save.
     * Alerts user.
     * @return True if all fields are populated
     */
    protected Boolean validateFormFields(String btnTxt) {
        Boolean isValid = false;
        StringBuilder validateErrMsg = new StringBuilder();
        String validateMsg = "";
        TextField[] formFields;
        ComboBox[] formCbFields;
        CheckBox[] formCxbFields;
        String startTxtFld = "";
        String endTxtFld = "";

        if(btnTxt.equals("UserSave")) {
            formFields = new TextField[]{user_Name_TxtFld, pwd_PwdFld};
        }
        else if(btnTxt.equals("CntSave")) {
            formFields = new TextField[]{cnt_Name_TxtFld, email_TxtFld};
        }
        else if(btnTxt.equals("CstSave")) {
            formFields = new TextField[]{phone_TxtFld, customer_Name_TxtFld, address_TxtFld, postal_Code_TxtFld};
        }
        else {
            formFields = new TextField[]{title_TxtFld, description_TxtFld, location_TxtFld, type_TxtFld, start_TxtFld, end_TxtFld};
            startTxtFld = start_TxtFld.getText();
            //System.out.println("Invisible Start Text Field: " + start);
            endTxtFld = end_TxtFld.getText();
            //System.out.println("On Save End Text Field: " + end);

            if (ApptStart_DatePick.getValue() == null) {
                validateMsg = "Please select a valid Start Date";
                validateErrMsg.append(validateMsg);
                validateErrMsg.append(System.getProperty("line.separator"));
            }

        }

        for (TextField field : formFields) {
            if (field.getText() == null || field.getText().isEmpty()) {
                if (field.getId().equals("start_TxtFld")) {
                    if (ApptStart_DatePick.getValue() != null && StartTimeHrs.getValue() != null && StartTimeMins.getValue() != null) {
                        calculateStartLdt();
                    }
                    validateMsg = "";

                } else if (field.getId().equals("end_TxtFld")) {
                    validateMsg = "Please select a valid Start Time and Duration";
                } else {
                    validateMsg = "Please enter value for field: " + field.getId().replace("_TxtFld", "")
                            .replace("_ID", " ID#").replace("_", " ").toUpperCase();
                }

                validateErrMsg.append(validateMsg);
                if (!validateMsg.isEmpty()) {
                    validateErrMsg.append(System.getProperty("line.separator"));
                }
            }
        }

        if (btnTxt.equals("UserSave")) {
            formCxbFields = new CheckBox[]{admin_ChkBox, user_Active_ChkBox};
        }
        else if (btnTxt.equals("CntSave")) {
            formCxbFields = new CheckBox[]{cnt_Active_ChkBox};
        }
        else if (btnTxt.equals("CstSave")) {
            formCxbFields = new CheckBox[]{};
        }
        else {
            formCxbFields = new CheckBox[]{};
        }
        for (CheckBox check : formCxbFields) {
            if (!check.isSelected()) {
                validateMsg = "";
                validateErrMsg.append(validateMsg);
            }
        }

        if (btnTxt.equals("UserSave")) {
            formCbFields = new ComboBox[]{};
        }
        else if (btnTxt.equals("CntSave")) {
            formCbFields = new ComboBox[]{};
        }
        else if (btnTxt.equals("CstSave")) {
            formCbFields = new ComboBox[]{country_ID_CBox, division_ID_CBox};
        }
        else {
            formCbFields = new ComboBox[]{customer_ID_CBox, contact_ID_CBox, user_ID_CBox};
        }
        for (ComboBox box : formCbFields) {
            if (box.getValue() == null || box.getValue().toString().isEmpty()) {
                validateMsg = "Please select a " + box.getId().toString().replace("_ID_CBox", "").toUpperCase();
                validateErrMsg.append(validateMsg);
                validateErrMsg.append(System.getProperty("line.separator"));
            }
        }

        if (validateErrMsg.length() > 0) {
            alert = buildAlert(Alert.AlertType.ERROR, "Form Incomplete", validateErrMsg.toString());
            confirm = alert.showAndWait();
        }
        else {
            isValid = true;

            if(btnTxt.equals("UserSave") || btnTxt.equals("CntSave") || btnTxt.equals("CstSave")) {
                return isValid;
            }
            else {
                Timestamp start = Timestamp.valueOf(startTxtFld);
                System.out.println("Invisible Start Text Field: " + start);
                Timestamp end = Timestamp.valueOf(endTxtFld);
                System.out.println("On Save End Text Field: " + end);
            }
        }
        return isValid;
    }

    /**
     * Displays the data for the various Reports on the Reports tab.
     * @param btnTxt
     * @param reportParams
     * @throws SQLException
     */
    public void displayReportQuery(String btnTxt, String[] reportParams) throws SQLException {
        ObservableList<Appointment> reportQuery = FXCollections.observableArrayList();
        AppointmentDAO apptDao = new AppointmentDaoImpl();

        try {
            switch(btnTxt) {
                case "CstMonthType":
                    rptMonth.setCellValueFactory(new PropertyValueFactory<>("Month"));
                    rptType.setCellValueFactory(new PropertyValueFactory<>("Type"));
                    rptCount.setCellValueFactory(new PropertyValueFactory<>("Count"));
                    reportQuery.addAll(apptDao.getByMonthType(reportParams));
                    CstMonthTypeTblView.setItems(reportQuery);
                    break;
                case "CntSchedule":
                    rptAppt.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
                    rptTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
                    rptType2.setCellValueFactory(new PropertyValueFactory<>("Type"));
                    rptDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
                    rptStart.setCellValueFactory(new PropertyValueFactory<>("Start"));
                    rptEnd.setCellValueFactory(new PropertyValueFactory<>("End"));
                    rptCnt.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
                    reportQuery.addAll(apptDao.getApptCntByPeriod(reportParams));
                    CntScheduleTblView.setItems(reportQuery);
                    break;
                case "":
                    break;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method deletes associated appointments then Selected customer.
     * @param selectedCst - The Customer selected for deletion.
     * @return true on successful deletion.
     * @throws SQLException
     */
    protected Boolean delCstRow(Customer selectedCst) throws SQLException {
        boolean deleted = false;
        int result = -1;

        AppointmentDAO cstApptDaoDel = new AppointmentDaoImpl();
        result = cstApptDaoDel.deleteAllCstAppt(selectedCst.getCustomer_ID());

        if(result > -1) {
            result = -1;
            CustomerDAO cstDaoDel = new CustomerDaoImpl();
            Customer cstDel = cstDaoDel.extract(selectedCst.getCustomer_ID());
            result = cstDaoDel.delete(cstDel);

            if (result > -1) {
                deleted = true;
            }
        }
        System.out.println(result);
        return deleted;
    }

    /**
     * Delete Scheduler Item with confirmation.
     * @param selectedSchedulerItem Selected object from either table view.
     * @param btnTxt Modified Event button text passed as Alert title.
     * @param msgCtx Event errorMsg passed as Alert context.
     */
    public void confirmDelete(Object selectedSchedulerItem, String btnTxt, String msgCtx){
        alert = buildAlert(Alert.AlertType.CONFIRMATION, btnTxt, msgCtx);
        confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK){
            if (selectedSchedulerItem instanceof Customer) {
                try {
                    if (delCstRow(selectedCst)){
                        refreshApptTables();
                        displayCstWithCoInfo();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (selectedSchedulerItem instanceof Appointment) {
                AppointmentDAO apptDao = new AppointmentDaoImpl();
                try {
                    if (apptDao.delete(selectedAppt) > -1) {
                        refreshApptTables();
                    }
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method detects the current tab and utilizes the selector in the fxml Include file Controllers.
     */
    private void tableItemSelector() {
        TableView.TableViewSelectionModel<Appointment> itemSelector;
        switch (currentSubTab.getId()) {
            case "AppMoTab":
                itemSelector = includeApptMoController.ApptTblViewMonthly.getSelectionModel(); // Tab is Include FXML file
                selectedAppt = itemSelector.getSelectedItem();
                break;
            case "AppWkTab":
                itemSelector = includeApptWkController.ApptTblViewWeekly.getSelectionModel(); // Tab is Include FXML file
                selectedAppt = itemSelector.getSelectedItem();
                break;
            default:
                itemSelector = ApptTblView.getSelectionModel();
                selectedAppt = itemSelector.getSelectedItem();
                break;
        }
    }

    /**
     * Repopulate all Appointment Tableviews.
     * @throws SQLException
     */
    private void refreshApptTables() throws SQLException {
        String apptPeriod = CntApptPeriodRadioGrp.getSelectedToggle().toString();
        System.out.println("Current Appt Period Selected: " + apptPeriod);

        try {
            displayApptTblViewData(apptPeriod);
            // displayApptTblViewData();
            // includeApptMoController.displayApptTblViewMonthly();
            // includeApptWkController.displayApptTblViewWeekly();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Creates data for Search Filter ComboBox options.
     */
    private void buildSfFilters() throws SQLException {
        activeStatus.addAll("Active", "Inactive");
        adminStatus.addAll("Yes", "No");

        // Create User sfFilters
        sfAdminCB.setItems(adminStatus);
        sfUserActiveCB.setItems(activeStatus);
        UserDAO userNameDao = new UserDaoImpl();

        for (User user : userNameDao.extractAll()) {
            userNames.add(user.getUser_Name());
        }
        sfUserNameCB.setItems(userNames);

        // Create Contact sfFilters
        sfCntActiveCB.setItems(activeStatus);
        ContactDAO cntNameDao = new ContactDaoImpl();

        for (Contact cnt : cntNameDao.extractAll()) {
            cntNames.add(cnt.getContact_Name());
        }
        sfCntNameCB.setItems(cntNames);
    }

    /**
     * <b>LAMBDA USAGE - </b>Fills the MonthCB ComboBox with the months of the year.
     * Lambda filters out the empty value in the months enum then streams it to a list. Used on Reports tab.
     */
    private void buildMonths() {
        String[] months = new DateFormatSymbols().getInstance().getMonths();
        monthsItems.addAll(Arrays.stream(months).filter((mo) -> (!mo.isEmpty())).toList());
        MonthCB.setItems(monthsItems);
    }

    /**
     * Builds the data for Type ComboBox based on DB entries in Type field. Used on Reports tab.
     * @throws SQLException
     */
    protected void buildTypes() throws SQLException {
        AppointmentDAO apptDao = new AppointmentDaoImpl();
        String[] types = apptDao.genericData("SELECT DISTINCT type FROM Appointments").toArray(new String[0]);
        typeItems.addAll(Arrays.stream(types).sorted().toList());
        TypeCB.setItems(typeItems);
    }

    /**
     * Seed Data for appointment Alert and Update testing; it is not validated for Business Hour Constraints.
     * @throws SQLException
     */
    private void appointmentSeed() throws SQLException {
        int result;

        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("Raw Now- : " + ldt);
        Timestamp tsStart = Timestamp.valueOf(ldt.withSecond(0).withNano(0).plusMinutes((65-ldt.getMinute())%5));
        Timestamp tsEnd = Timestamp.valueOf(ldt.withSecond(0).withNano(0).plusMinutes((65-ldt.getMinute())%5).plusMinutes(45));

        try{
            AppointmentDAO apptDao = new AppointmentDaoImpl();
            ObservableList<String> minCstId = apptDao.genericData("SELECT MIN(Customer_ID) FROM appointments");
            minCstId.stream().findFirst().get();
            Appointment appointment = new Appointment(0,"Java help", "Seed data", "remote", "Mentor", tsStart, tsEnd, Integer.parseInt(minCstId.stream().findFirst().get()), sessionUserId, 3);
            result = apptDao.insert(appointment);
            System.out.println(result);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMainTabPane(String tabName) {
        tabSelect = MainTabPane.getSelectionModel();
        System.out.println(String.format("Tab index is %s", tabSelect.getSelectedIndex()));
        System.out.println(String.format("Prev Tab name is %s", tabName));
        ObservableList<Tab> mainTabs = MainTabPane.getTabs();
        for (Tab tab : mainTabs) {
            if (tab.getId().equals(tabName)) {
                tabSelect.select(tab);
                break;
            }
        }
    }

    /**
     * Sets currentTab variable on Main Menu changes.
     */
    public String onTabChange() {
        currentTab = MainTabPane.getSelectionModel().getSelectedItem();
        System.out.println("Current Tab Inquiry for tabSelect: " + currentTab.getId());
        return currentTab.getId();
    }

    /**
     * <b>LAMBDA USAGE - </b>Method streams appointments then does a Map/Filter/Reduce to produce the average duration of all Appointments.
     * <BR>Additionally the function interface method is used to calculate the appointments durations in the mapToLong lambda.
     * @return - String sentence with calculated average information.
     */
    protected String reportDurationAvg() {
        String medium = "";
        try {
            AppointmentDAO apptDao = new AppointmentDaoImpl();
            ObservableList<Appointment> apptByDuration = apptDao.extractAll();
            OptionalDouble avg = apptByDuration.stream().mapToLong((a) -> getDurationMins(a))
                    .filter(m -> m >= 15l && m <= 60l).average();
            medium = avg.isPresent() ? String.format("AVERAGE TIME OF APPOINTMENTS: %s",String.valueOf((long)avg.getAsDouble())) : "NO APPOINTMENT TIMES TO AVERAGE";
            System.out.println("AVERAGE TIME OF APPOINTMENTS: " + medium);
        }
        catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return medium;
    }

    /**
     * Loads the ApptAddUpdateFormCtrl to create appointments in the appt-add-update-form view.
     * @param actionEvent New form button, ApptAddBtn, clicked.
     */
    @javafx.fxml.FXML
    private void onActionApptNew(ActionEvent actionEvent) {
        System.out.println("New Appointment Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("/com/thecodebarista/view/appt-add-update-form.fxml")));
            scene = loader.load();

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            static_AddUpdateLabel.setText("New Appointment");
            stage.setScene(new Scene(scene));
            ApptAddUpdateFormCtrl modelCtrl = loader.getController();
            modelCtrl.setDefaultUserOnNew();
            stage.show();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    /**
     * Loads the ApptAddUpdateFormCtrl and calls its method to send the selected row data in the Appointment table view to the appt-add-update-form.
     * Present alert error dialog when no selection made.
     * @param actionEvent Update form button, ApptUpdateBtn, clicked.
     * @throws IOException java.io.IOException - captures name exception: NullPointerException.
     */
    @javafx.fxml.FXML
    private void onActionApptUpdate(ActionEvent actionEvent) throws IOException {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Update Appointment Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/thecodebarista/view/appt-add-update-form.fxml"));
            scene = loader.load();
            ApptAddUpdateFormCtrl modelCtrl = loader.getController();
            // tableItemSelector();
            System.out.println("Made it here 1");
            selectedAppt = ApptTblView.getSelectionModel().getSelectedItem();
            modelCtrl.sendApptModifyData(selectedAppt);

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            static_AddUpdateLabel.setText("Update Appointment");
            System.out.println("Made it here 2");
            stage.show();
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            String errorMsg = "Error: No Product Selected!";
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, errorMsg);
            confirm = alert.showAndWait();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes selected row in Appointment table view.
     * Presents alert confirmation dialog box.
     * @param actionEvent Delete form button, ApptDeleteBtn, clicked.
     */
    @javafx.fxml.FXML
    public void onActionDeleteAppt(ActionEvent actionEvent) {
        String btnTxt = ((Button) actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try {
            // tableItemSelector();
            selectedAppt = ApptTblView.getSelectionModel().getSelectedItem();
            String msgCtx = "Please confirm deletion of " + "Appointment ID: " + selectedAppt.getAppointment_ID() +
                    System.getProperty("line.separator") + "Type: " + selectedAppt.getType();
            confirmDelete(selectedAppt, btnTxt, msgCtx);
        } catch (NullPointerException e) {
            String errorMsg = "Error: No Appointment Selected!";
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, errorMsg);
            confirm = alert.showAndWait();
        }
    }

    /**
     * Loads the CstAddUpdateFormCtrl to create customers in view the cst-add-update-form view.
     * @param actionEvent Update form button, CstAddBtn, clicked.
     */
    @javafx.fxml.FXML
    private void onActionCstNew(ActionEvent actionEvent) {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("New Customer Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("/com/thecodebarista/view/cst-add-update-form.fxml")));
            scene = loader.load();

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("New Customer");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    /**
     * Loads the CstAddUpdateFormCtrl and calls its method to send the selected row data in the Customer table view to the cst-add-update-form.
     * Present alert error dialog when no selection made.
     * @param actionEvent Modify form button, CstModifyBtn, clicked.
     * @throws IOException java.io.IOException - captures name exception: NullPointerException.
     */
    @javafx.fxml.FXML
    private void onActionCstUpdate(ActionEvent actionEvent) throws IOException {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Update Customer Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/thecodebarista/view/cst-add-update-form.fxml"));
            scene = loader.load();
            CstAddUpdateFormCtrl modelCtrl = loader.getController();
            selectedCst = CstTblView.getSelectionModel().getSelectedItem();
            System.out.println("Made it here 1");
            modelCtrl.sendCstModifyData(selectedCst);

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            static_AddUpdateLabel.setText("Update Customer");
            System.out.println("Made it here 2");
            stage.show();
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            String errorMsg = "Error: No Customer Selected!";
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, errorMsg);
            confirm = alert.showAndWait();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes selected row in Customer table view. Presents alert confirmation dialog box.
     * @param actionEvent Delete form button, CstDeleteBtn, clicked.
     */
    @javafx.fxml.FXML
    private void onActionDeleteCst(ActionEvent actionEvent) {
        String btnTxt = ((Button) actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());
        StringBuilder msgCtx = new StringBuilder();
        try {
            selectedCst = CstTblView.getSelectionModel().getSelectedItem();
            msgCtx.append(String.format("Please confirm deletion of Customer ID: %d\n\n", selectedCst.getCustomer_ID()));

            AppointmentDAO apptDao = new AppointmentDaoImpl();
            ObservableList<Appointment> userAppt = apptDao.getApptByFK("Customer_ID", selectedCst.getCustomer_ID());
            System.out.println("Customer has- " + userAppt.size());

            if(userAppt.size() > 0){
                msgCtx.append(String.format("ALL ASSOCIATED CUSTOMER APPOINTMENTS WILL BE DELETED.\n\n"));
                for (Appointment appt : userAppt) {
                    msgCtx.append(String.format("Appointment ID# %d%nType: %s\n", appt.getAppointment_ID(), appt.getType()));
                }
                confirmDelete(selectedCst, btnTxt, msgCtx.toString());
            }
            else{
                confirmDelete(selectedCst, btnTxt, msgCtx.toString());
            }
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            String errorMsg = "Error: No Customer Selected!";
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, errorMsg);
            confirm = alert.showAndWait();
        }
    }

    /**
     * Loads the UserAddUpdateFormCtrl to create users in view the user-add-update-form view.
     * @param actionEvent Update form button, UserAddBtn, clicked.
     */
    @javafx.fxml.FXML
    public void onActionUserNew(ActionEvent actionEvent) {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("New User Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("/com/thecodebarista/view/user-add-update-form.fxml")));
            scene = loader.load();

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            // stage.setTitle("New User");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    /**
     * Loads the UserAddUpdateFormCtrl and calls its method to send the selected row data in the User table view to the user-add-update-form.
     * Present alert error dialog when no selection made.
     * @param actionEvent Modify form button, CstModifyBtn, clicked.
     * @throws IOException java.io.IOException - captures name exception: NullPointerException.
     */
    @javafx.fxml.FXML
    public void onActionUserUpdate(ActionEvent actionEvent) throws IOException{
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Update User Button Clicked: " + ((Button)actionEvent.getSource()).getId());
        String errorMsg = "Error: No User Selected!";
        // onTabChange();

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/thecodebarista/view/user-add-update-form.fxml"));
            scene = loader.load();
            UserAddUpdateFormCtrl modelCtrl = loader.getController();
            selectedUser = UserTblView.getSelectionModel().getSelectedItem();
            if (selectedUser.getUser_ID() < 3) {
                errorMsg = String.format("You do not have privileges to modify Legacy User: '%s'", selectedUser.getUser_Name());

            }
            static_AddUpdateLabel.setText("Update User");
            modelCtrl.showAdminOnlyFlds();
            modelCtrl.sendUserModifyData(selectedUser);
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, errorMsg);
            confirm = alert.showAndWait();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void onActionSetActiveStatusUser(ActionEvent actionEvent) {
        String btnTxt = ((Button) actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());
        String msgCtx = "";
        int activeStatus = 1;

        if (UserActiveStatusBtn.getText().equals("Deactivate")) {
            activeStatus = 0;
        }

        try {
            selectedUser = UserTblView.getSelectionModel().getSelectedItem();
            if (selectedUser.getUser_ID() < 3) {
                msgCtx = String.format("You do not have privileges to deactivate Legacy User: '%s'", selectedUser.getUser_Name());
                alert = buildAlert(Alert.AlertType.ERROR, btnTxt, msgCtx);
                confirm = alert.showAndWait();
            }
            else {
                msgCtx = String.format("Please confirm deactivation for User ID: %d\nUser Name: %s\n", selectedUser.getUser_ID(), selectedUser.getUser_Name());
                alert = buildAlert(Alert.AlertType.CONFIRMATION, btnTxt, msgCtx);
                confirm = alert.showAndWait();
                if (confirm.isPresent() && confirm.get() == ButtonType.OK){
                    UserDAO userDao = new UserDaoImpl();
                    userDao.setActivationStatus(activeStatus, selectedUser.getUser_ID());
                    displayUserTblViewData("Active", null);
                }
            }
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            String errorMsg = "Error: No User Selected!";
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, errorMsg);
            confirm = alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void onActionShowUser(Event event)  throws IOException {
        String btnTxt = ((Button)event.getSource()).getId().replace("Btn", "");
        System.out.println("Update User Button Clicked: " + ((Button)event.getSource()).getId());

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/thecodebarista/view/user-add-update-form.fxml"));
            scene = loader.load();
            UserAddUpdateFormCtrl modelCtrl = loader.getController();
            static_AddUpdateLabel.setText("User Info");
            modelCtrl.showAdminOnlyFlds();
            modelCtrl.sendUserModifyData(sessionUser);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            System.out.println("Made it here 2");
            stage.show();
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @javafx.fxml.FXML
    public void onKeyPressSfUserList(Event event)  throws IOException {
        String searchText = sfUserNameCB.getEditor().getText().toLowerCase();
        ObservableList<String> filterList = null;
        if (searchText.isBlank()) {
            System.out.println("Empty search: " + searchText);
            sfUserNameCB.setItems(userNames);
            sfUserNameCB.getSelectionModel().clearSelection();
            sfUserNameCB.show();
        } else {
            try {
                filterList = sfUserNameCB.getItems().stream().filter(n -> n.toLowerCase()
                                .contains(searchText))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                if (!filterList.isEmpty()) {
                    sfUserNameCB.setItems(filterList);
                    sfUserNameCB.show();
                } else {
                    sfUserNameCB.hide();
                }
            } catch (NullPointerException e) {
                System.out.println("Error: " + e.getMessage());
                sfUserNameCB.setItems(userNames);
                sfUserNameCB.getSelectionModel().clearSelection();
                sfUserNameCB.show();
            }
        }
    }

    @javafx.fxml.FXML
    public void onActionCntNew(ActionEvent actionEvent) {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("New Contact Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("/com/thecodebarista/view/cnt-add-update-form.fxml")));
            scene = loader.load();

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("New Contact");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    @javafx.fxml.FXML
    public void onActionCntUpdate(ActionEvent actionEvent) throws IOException{
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Update Contact Button Clicked: " + ((Button)actionEvent.getSource()).getId());
        String errorMsg = "Error: No Contact Selected!";
        // onTabChange();

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/thecodebarista/view/cnt-add-update-form.fxml"));
            scene = loader.load();
            CntAddUpdateFormCtrl modelCtrl = loader.getController();
            selectedCnt = CntTblView.getSelectionModel().getSelectedItem();
            if (selectedCnt.getContact_ID() < 4) {
                errorMsg = String.format("You do not have privileges to modify Legacy Contact: '%s'", selectedCnt.getContact_Name());

            }
            static_AddUpdateLabel.setText("Update Contact");
            modelCtrl.sendCntModifyData(selectedCnt);
            modelCtrl.showAdminOnlyFlds();
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, errorMsg);
            confirm = alert.showAndWait();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void onActionSetActiveStatusCnt(ActionEvent actionEvent) {
        String btnTxt = ((Button) actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());
        String msgCtx = "";
        int activeStatus = 1;

        if (CntActiveStatusBtn.getText().equals("Deactivate")) {
            activeStatus = 0;
        }

        try {
            selectedCnt = CntTblView.getSelectionModel().getSelectedItem();
            msgCtx = String.format("Please confirm deactivation for Contact ID: %d\nContact Name: %s\n", selectedCnt.getContact_ID(), selectedCnt.getContact_Name());
            alert = buildAlert(Alert.AlertType.CONFIRMATION, btnTxt, msgCtx);
            confirm = alert.showAndWait();
            if (confirm.isPresent() && confirm.get() == ButtonType.OK){
                ContactDAO cntDao = new ContactDaoImpl();
                cntDao.setActivationStatus(activeStatus, selectedCnt.getContact_ID());
                displayCntTblViewData("Active", null);
            }
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            String errorMsg = "Error: No Contact Selected!";
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, errorMsg);
            confirm = alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void onKeyPressSfCntList(Event event)  throws IOException {
        String searchText = sfCntNameCB.getEditor().getText().toLowerCase();
        ObservableList<String> filterList = null;
        if (searchText.isBlank()) {
            System.out.println("Empty search: " + searchText);
            sfCntNameCB.setItems(cntNames);
            sfCntNameCB.getSelectionModel().clearSelection();
            sfCntNameCB.show();
        } else {
            try {
                filterList = sfCntNameCB.getItems().stream().filter(n -> n.toLowerCase()
                                .contains(searchText))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                if (!filterList.isEmpty()) {
                    sfCntNameCB.setItems(filterList);
                    sfCntNameCB.show();
                } else {
                    sfCntNameCB.hide();
                }
            } catch (NullPointerException e) {
                System.out.println("Error: " + e.getMessage());
                sfCntNameCB.setItems(cntNames);
                sfCntNameCB.getSelectionModel().clearSelection();
                sfCntNameCB.show();
            }
        }
    }

    @javafx.fxml.FXML
    public void onActionShowHideSearch(ActionEvent actionEvent) {
        String btnTxt = ((Button) actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        if (btnTxt.contains("user")) {
            if (userSearchFilterBtn.getText().equals("Show Search Filter")) {
                userSearchPane.setVisible(true);
                userSearchFilterBtn.setText("Hide Search Filter");
            }
            else {
                userSearchPane.setVisible(false);
                userSearchFilterBtn.setText("Show Search Filter");
            }
        }
        else {
            if (cntSearchFilterBtn.getText().equals("Show Search Filter")) {
                cntSearchPane.setVisible(true);
                cntSearchFilterBtn.setText("Hide Search Filter");
            }
            else {
                cntSearchPane.setVisible(false);
                cntSearchFilterBtn.setText("Show Search Filter");
            }
        }

    }

    @javafx.fxml.FXML
    public void onActionSfSearch(Event event)  throws SQLException {
        String btnTxt = ((Button) event.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)event.getSource()).getId());
        StringBuilder sfQuery = new StringBuilder();
        StringBuilder sfWhere = new StringBuilder();
        String sfNameSelect;
        String wc_name = "";
        String wc_email;
        int wc_admin;
        int wc_active;
        String sfStatement;
        if (btnTxt.contains("User")) {
            sfQuery.append("SELECT * FROM users");

            // If Name list item was selected set where clause to equal.
            if (sfUserNameCB.getValue() != null) {
                sfNameSelect = sfUserNameCB.getValue();
                System.out.println(String.format("User Selected is %s", sfNameSelect));
                Boolean item = sfUserNameCB.getItems().stream().allMatch(n -> n.equals(sfUserNameCB.getValue()))? true : false;

                if(sfNameSelect.isEmpty()){
                    // Do Nothing
                }
                // If Name list item was selected set where clause to equal.
                else if(item) {
                    wc_name = String.format(" User_Name = '%s'", sfNameSelect);
                }
                else {
                    // If there's a value but not selected set where clause to Like.
                    wc_name = String.format(" User_Name LIKE '%%%s%%'", sfNameSelect);
                }
            }

            if (!wc_name.isEmpty()) {
                sfWhere.append(wc_name);
                System.out.println(String.format("NAME WHERE CLAUSE: %s", sfWhere));
            }

            // Test that Active value was passed. If not Null add it to where clause.
            if (sfAdminCB.getValue() != null) {
                if (!sfWhere.isEmpty()) {
                    sfWhere.append(" AND");
                }
                wc_admin = sfAdminCB.getValue().equals("Yes")? 1:0;
                sfWhere.append(String.format(" Is_Admin = %d",wc_admin));
            }

            // Test that Active value was passed. If not Null or Empty selected added to where clause.
            if (sfUserActiveCB.getValue() != null) {
                if (!sfUserActiveCB.getValue().isEmpty()) {
                    if (!sfWhere.isEmpty()) {
                        sfWhere.append(" AND");
                    }
                    wc_active = sfUserActiveCB.getValue().equals("Active")? 1:0;
                    System.out.println(String.format("Active: %d", wc_active));
                    sfWhere.append(String.format(" Active = %d", wc_active));
                }
            }

            // If there's a where clause. Added it to the Query.
            if (!sfWhere.isEmpty()) {
                sfStatement = sfQuery.append(String.format(" WHERE%s", sfWhere)).toString();
            }
            else {
                sfStatement = sfQuery.toString();
            }

            displayUserTblViewData("sf", sfStatement);
        }

        if (btnTxt.contains("Cnt")) {
            sfQuery.append("SELECT * FROM contacts");

            // If Name list item was selected set where clause to equal.
            if (sfCntNameCB.getValue() != null) {
                sfNameSelect = sfCntNameCB.getValue();
                System.out.println(String.format("User Selected is %s", sfNameSelect));
                Boolean item = sfCntNameCB.getItems().stream().allMatch(n -> n.equals(sfUserNameCB.getValue()))? true : false;

                if(sfNameSelect.isEmpty()){
                    // Do Nothing
                }
                // If Name list item was selected set where clause to equal.
                else if(item) {
                    wc_name = String.format(" Contact_Name = '%s'", sfNameSelect);
                }
                else {
                    // If there's a value but not selected set where clause to Like.
                    wc_name = String.format(" Contact_Name LIKE '%%%s%%'", sfNameSelect);
                }
            }

            if (!wc_name.isEmpty()) {
                sfWhere.append(wc_name);
                System.out.println(String.format("NAME WHERE CLAUSE: %s", sfWhere));
            }

            // Test that Email value was passed. If not Null, set it to LIKE where clause.
            if (sfCntEmail.getText() != null) {
                if (!sfWhere.isEmpty()) {
                    sfWhere.append(" AND");
                }
                wc_email = sfCntEmail.getText();
                sfWhere.append(String.format(" Email LIKE '%%%s%%'",wc_email));
            }

            // Test that Active value was passed. If not Null or Empty selected added to where clause.
            if (sfCntActiveCB.getValue() != null) {
                if (!sfCntActiveCB.getValue().isEmpty()) {
                    if (!sfWhere.isEmpty()) {
                        sfWhere.append(" AND");
                    }
                    wc_active = sfCntActiveCB.getValue().equals("Active")? 1:0;
                    System.out.println(String.format("Active: %d", wc_active));
                    sfWhere.append(String.format(" Active = %d", wc_active));
                }
            }

            // If there's a where clause. Added it to the Query.
            if (!sfWhere.isEmpty()) {
                sfStatement = sfQuery.append(String.format(" WHERE%s", sfWhere)).toString();
            }
            else {
                sfStatement = sfQuery.toString();
            }
            displayCntTblViewData("sf", sfStatement);
        }
    }

    @javafx.fxml.FXML
    public void onActionSfClear(Event event) throws SQLException {
        String btnTxt = ((Button) event.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)event.getSource()).getId());

        if (btnTxt.contains("User")) {
            sfUserNameCB.getSelectionModel().clearSelection();
            sfUserNameCB.getEditor().clear();
            sfAdminCB.getSelectionModel().clearSelection();
            sfUserActiveCB.getSelectionModel().clearSelection();
            displayUserTblViewData("sfClear", null);
        }
        if (btnTxt.contains("Cnt")) {
            sfCntNameCB.getSelectionModel().clearSelection();
            sfCntNameCB.getEditor().clear();
            sfCntEmail.clear();
            sfCntActiveCB.getSelectionModel().clearSelection();
            displayCntTblViewData("sfClear", null);
        }
    }

    @javafx.fxml.FXML
    public void onMouseEnterUserBtn(Event event) throws SQLException {

    }

    @javafx.fxml.FXML
    public void onMouseExitUserBtn(Event event) throws SQLException {

    }

    @javafx.fxml.FXML
    public void onApptRadioSelect(Event event) throws SQLException {
        System.out.println("Button Clicked: " + event.getSource().toString());
        String btnTxt = "";
        btnTxt = ((RadioButton) event.getSource()).getId();
        // CntApptPeriodRadioGrp.getSelectedToggle();
        try {
            displayApptTblViewData(btnTxt);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets currentSubTab variable on Appointment tab changes.
     * @param event - The Tab clicked.
     */
    @javafx.fxml.FXML
    public void onSubTabChange(Event event) {
        currentSubTab = ApptTabs.getSelectionModel().getSelectedItem();
        System.out.println("Current SubTab Inquiry for subTabSelect: " + currentSubTab.getId());
    }

    /**
     * <b>LAMBDA USAGE - </b>Lambdas used to filter for all visible Panes and set to invisible.
     * Show pane with filter options for selected Report Menu's.
     * Filter children by ID, set to visible if needed and bring to front of StackPanes.
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void showFilterPane(ActionEvent actionEvent) throws SQLException {
        String menuItemId = ((MenuItem) actionEvent.getSource()).getId().replace("MenuItem", "");
        String menuItem = ((MenuItem) actionEvent.getSource()).getText();
        String tblView = menuItemId.replace("Pane", "TblView");
        System.out.println("Name of TblView- " + tblView);
        if (menuItemId.equals("CstMonthTypePane")){
            buildMonths();
            buildTypes();
        }
        if (menuItemId.equals("CntSchedulePane")){
            ContactDAO cntCb = new ContactDaoImpl();
            if (CntApptPeriodRadioGrp.getSelectedToggle() != null)
                CntApptPeriodRadioGrp.getSelectedToggle().setSelected(false);
            CntScheduleSearchCB.getSelectionModel().clearSelection();
            CntScheduleSearchCB.setItems(cntCb.extractAll());

        }

        if (menuItemId.equals("ApptDurationPane")) {
            ApptDurationTblView.setText(reportDurationAvg());
        }

        if (menuItemId.equals("AgedLoginPane")) {
            displayAgedLogins();
        }

        ReportTitlePane.setText(menuItem);
        reportFilterStackPane.getChildren().stream().filter((v) -> v.isVisible())
                .forEach((v) -> v.setVisible(false));
        Node paneNode = reportFilterStackPane.getChildren().stream().filter((p) -> p.getId().equals(menuItemId)).findFirst().get();
        paneNode.setVisible(true);
        paneNode.toFront();
        System.out.println("CHILDREN: " + TblViewStackPane.getChildren().stream().toList());
        Node tblViewNode = TblViewStackPane.getChildren().stream().filter((p) -> p.getId().equalsIgnoreCase(tblView)).findFirst().get();
        tblViewNode.toFront();
    }

    /**
     * On action method set boolean variable for control.
     * @param actionEvent - Month or Type ComboBox or Radio button clicked.
     */
    @javafx.fxml.FXML
    public void onReportFilterUpd(ActionEvent actionEvent) {
        System.out.println("Button Clicked: " + actionEvent.getSource().toString());
        String btnTxt = "";
        if (actionEvent.getSource().toString().contains("ComboBox")){
            btnTxt = ((ComboBox) actionEvent.getSource()).getId();
        }
        else if (actionEvent.getSource().toString().contains("RadioButton")){
            btnTxt = ((RadioButton) actionEvent.getSource()).getId();
        }
        else{
            btnTxt = ((Button) actionEvent.getSource()).getId();
        }

        if (btnTxt.equals("MonthCB")) {
            moFilter = true;
            return;
        }

        if (btnTxt.equals("TypeCB")) {
            typeFilter = true;
            return;
        }

        if (btnTxt.equals("CntScheduleSearchCB")) {
            if (CntApptPeriodRadioGrp.getSelectedToggle() != null) {
                CntScheduleTblView.getItems().clear();
                CntApptPeriodRadioGrp.getSelectedToggle().setSelected(false);
            }
        }

    }

    /**
     * On action Method for controls on Report tab. gathers the where clause param data.
     * @param actionEvent - Various Control clicked to filter information.
     */
    @javafx.fxml.FXML
    public void onActionDoQuery(ActionEvent actionEvent) {
        System.out.println("Button Clicked: " + actionEvent.getSource().toString());
        String btnTxt = "";
        int wc = 0;
        String wcPeriod ="";
        String[] reportParams = new String[3];

        if (actionEvent.getSource().toString().contains("ComboBox")){
            btnTxt = ((ComboBox) actionEvent.getSource()).getId().replace("SearchCB", "");
        }
        else if (actionEvent.getSource().toString().contains("RadioButton")){
            btnTxt = ((RadioButton) actionEvent.getSource()).getId();
        }
        else{
            btnTxt = ((Button) actionEvent.getSource()).getId().replace("SearchBtn", "");
        }
        try {
            if(btnTxt.equals("CstMonthType")) {
                String wcMonth = "";
                String wcType = "";

                if (moFilter) {
                    wcMonth = MonthCB.getValue();
                    wc = 2;
                }

                if (typeFilter) {
                    wcType = TypeCB.getValue();
                    wc++;
                }
                reportParams[0] = wcMonth;
                reportParams[1] = wcType;
                reportParams[2] = String.valueOf(wc);
            }

            if (btnTxt.contains("CntSchedule")) {
                btnTxt = "CntSchedule";
                if (CntScheduleSearchCB.getValue() == null){
                    alert = buildAlert(Alert.AlertType.INFORMATION, "Contact Schedules", "Please select a contact");
                    confirm = alert.showAndWait();
                    CntApptPeriodRadioGrp.getSelectedToggle().setSelected(false);
                    return;
                }
                else {
                    String wcContact = String.valueOf(CntScheduleSearchCB.getValue().getContact_ID());
                    if(CntApptPeriodRadioGrp.getSelectedToggle() != null) {
                        CntScheduleTblView.getItems().clear();
                        //wcPeriod = CntApptPeriodRadioGrp.getSelectedToggle().toString();
                        wc = ((CntScheduleMoRadio.isSelected()) ? 1 : 0);
                        System.out.println(String.format("Selected radio- '%s' and assoc wc #%d ",wcPeriod, wc));
                    }
                    reportParams[0] = wcContact;
                    reportParams[1] = String.valueOf(wc);
                    reportParams[2] = wcPeriod;
                }
            }
            System.out.println(String.format("Btn Text- %s\nParams- %s\n%s\n%s ",btnTxt, reportParams[0], reportParams[1], reportParams[2]));
            displayReportQuery(btnTxt, reportParams);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * On action method from clears selections in fields on Report tab.
     * @param actionEvent - Clear button clicked.
     */
    @javafx.fxml.FXML
    public void onActionClearFilter(ActionEvent actionEvent) {
        String btnTxt = ((Button) actionEvent.getSource()).getId().replace("SearchBtn", "");
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        if (moFilter) {
            MonthCB.getSelectionModel().clearSelection();
            moFilter = false;
        }

        if (typeFilter) {
            TypeCB.getSelectionModel().clearSelection();
            typeFilter = false;
        }
    }

    /**
     * Logout of application; returns to Log In Screen.
     * @param actionEvent Logout link clicked.
     */
    @javafx.fxml.FXML
    private void onActionLogout(ActionEvent actionEvent) {

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/thecodebarista//view/login-form.fxml"));
            scene = loader.load();

            // Cast window to stage
            stage = (Stage)((Hyperlink)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    /**
     * Override of Function Interface Abstract Method. Calculates appointment duration.
     * @param appointment - Selected Appointment row.
     * @return - Minutes (Long).
     */
    @Override
    public Long getDurationMins(Appointment appointment) {
        return ChronoUnit.MINUTES.between(appointment.getStart().toLocalDateTime(), appointment.getEnd().toLocalDateTime());
    }

    /**
     * Initializes the controller class.
     * Run the Tableview methods to display all Tab data info and set the session user info.
     * @param url default application URL
     * @param rb default application ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            // For seed data testing. TODO: Don't forget to comment out.
            // appointmentSeed();
            displayApptTblViewData();
            //displayCstTblViewData(); //not currently used.
            displayCstWithCoInfo();
            displayUserTblViewData("Active", null);
            displayCntTblViewData("Active", null);
            setCurrentUserIdInfo(sessionUserId);
            setCurrentUserNameInfo(sessionUserName);
            if (sessionUserAccess > 0) {
                buildSfFilters();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.getMessage();
        }
    }
}
