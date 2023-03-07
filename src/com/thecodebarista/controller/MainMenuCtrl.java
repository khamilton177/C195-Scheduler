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

    /*
    --- Start of Appointment's Appointments Tabs FXML controls ---
     */

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

    /*
    --- End of Reports Tab FXML controls ---
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
        String startTxtFld = "";
        String endTxtFld = "";

        if(btnTxt.equals("CstSave")) {
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

        if (btnTxt.equals("CstSave")) {
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

            if(btnTxt.equals("CstSave")) {
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
        switch (currentTab.getId()) {
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
        try {
            displayApptTblViewData();
            includeApptMoController.displayApptTblViewMonthly();
            includeApptWkController.displayApptTblViewWeekly();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
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

    /**
     * Initializes the controller class.
     * Run the Tableview methods to display all Tab data info and set the session user info.
     * @param url default application URL
     * @param rb default application ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            // For seed data testing. TODO: Don't forget to comment.
            // appointmentSeed();

            displayApptTblViewData();
            //displayCstTblViewData(); //not currently used.
            displayCstWithCoInfo();
            setCurrentUserIdInfo(sessionUserId);
            setCurrentUserNameInfo(sessionUserName);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
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
            tableItemSelector();
            System.out.println("Made it here 1");
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
            tableItemSelector();
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
     * Present alert error dialog when no selection made.
     * @param actionEvent Update form button, CstAddBtn, clicked.
     * @throws IOException java.io.IOException - captures name exception: NullPointerException.
     */
    @javafx.fxml.FXML
    private void onActionCstNew(ActionEvent actionEvent) throws IOException {
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
     * Sets currentTab variable on Appointment tab change.
     * @param event - The Tab clicked.
     */
    @javafx.fxml.FXML
    public void onTabSelectLoad(Event event) {
        currentTab = ApptTabs.getSelectionModel().getSelectedItem();
        System.out.println("Current Tab Inquiry for tabSelect: " + currentTab.getId());
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
            UnmanagedDAO cntCb = new ContactDaoImpl();
            if (CntApptPeriodRadioGrp.getSelectedToggle() != null)
                CntApptPeriodRadioGrp.getSelectedToggle().setSelected(false);
            CntScheduleSearchCB.getSelectionModel().clearSelection();
            CntScheduleSearchCB.setItems(cntCb.extractAll());

        }

        if (menuItemId.equals("ApptDurationPane")) {
            ApptDurationTblView.setText(reportDurationAvg());
        }

        if (menuItemId.equals("CstMostActivePane")) { // NOT IN USE
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
     * Override of Function Interface Abstract Method. Calculates appointment duration.
     * @param appointment - Selected Appointment row.
     * @return - Minutes (Long).
     */
    @Override
    public Long getDurationMins(Appointment appointment) {
        return ChronoUnit.MINUTES.between(appointment.getStart().toLocalDateTime(), appointment.getEnd().toLocalDateTime());
    }
}
