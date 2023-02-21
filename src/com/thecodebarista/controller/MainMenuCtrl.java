package com.thecodebarista.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.thecodebarista.TimeMachine;
import com.thecodebarista.dao.*;
import com.thecodebarista.model.Appointment;
import com.thecodebarista.model.Contact;
import com.thecodebarista.model.Customer;
import com.thecodebarista.model.FirstLevelDivision;
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
import static java.lang.Math.addExact;
import static java.lang.Math.subtractExact;

public class MainMenuCtrl extends LoginFormCtrl implements Initializable, TimeMachine {

    public static Label static_AddUpdateLabel;
    public Appointment selectedAppt;
    Customer selectedCst;
    Alert alert;
    String btnTxt;
    Optional<ButtonType> confirm;
    Tab currentTab;

    ObservableList<String> monthsItems = FXCollections.observableArrayList();
    ObservableList<String> typeItems = FXCollections.observableArrayList();

    private Boolean moFilter = false;
    private Boolean typeFilter = false;
    private Boolean cntFilter = false;
    private Boolean cntMoFilter = false;
    private Boolean cntWkFilter = false;


    @javafx.fxml.FXML
    protected ApptTableMonthlyCtrl includeApptMoController;
    @javafx.fxml.FXML
    protected ApptTableWeeklyCtrl includeApptWkController;

    @javafx.fxml.FXML
    protected TableView<Appointment> ApptTblView;
    @javafx.fxml.FXML
    protected Button CstAddBtn;
    @javafx.fxml.FXML
    protected TableColumn<Customer, String> CstPostalCodeCol;
    @javafx.fxml.FXML
    protected TableColumn<Customer, String> CstAddressCol;
    @javafx.fxml.FXML
    private TableColumn<Customer, String> CstPhoneCol;
    @javafx.fxml.FXML
    protected Label CurrentUserIdLbl;
    @javafx.fxml.FXML
    protected Button ApptUpdateBtn;
    @javafx.fxml.FXML
    protected Button CstModifyBtn;
    @javafx.fxml.FXML
    protected MenuButton ReportMenuBtn;
    @javafx.fxml.FXML
    protected TableView<Customer> CstTblView;
    @javafx.fxml.FXML
    protected TableColumn<Customer, Integer> CstIdCol;
    @javafx.fxml.FXML
    protected TableColumn<Customer, String> CstNameCol;
    @javafx.fxml.FXML
    protected Button ApptAddBtn;
    @javafx.fxml.FXML
    protected TableColumn<Customer, Integer> CstDivisionIdCol;
    @javafx.fxml.FXML
    protected Hyperlink LogOutBtn;
    @javafx.fxml.FXML
    protected Button ApptDeleteBtn;
    @javafx.fxml.FXML
    protected Button CstDeleteBtn;

    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> appointment_ID_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> title_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String>  description_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String>  location_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> contact_ID_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String>  type_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Timestamp>  start_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Timestamp>  end_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> customer_ID_Col;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> user_ID_Col;
    @javafx.fxml.FXML
    private TableColumn<FirstLevelDivision, Integer> CstCountryIdCol;
    @javafx.fxml.FXML
    private Label CurrentUserNameLbl;
    @javafx.fxml.FXML
    private TabPane ApptTabs;
    @javafx.fxml.FXML
    private Tab AppTab;
    @javafx.fxml.FXML
    private Tab AppWkTab;
    @javafx.fxml.FXML
    private Tab AppMoTab;
    @javafx.fxml.FXML
    private TitledPane ReportTitlePane;
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
    private StackPane reportFilterStackPane;
    @javafx.fxml.FXML
    protected TableView<Appointment> CstMonthTypeTblView;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> rptMonth;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, String> rptType;
    @javafx.fxml.FXML
    protected TableColumn<Appointment, Integer> rptCount;
    @javafx.fxml.FXML
    private TableColumn rptCol11;
    @javafx.fxml.FXML
    private TableColumn rptCol21;
    @javafx.fxml.FXML
    private TableColumn rptCol31;
    @javafx.fxml.FXML
    private TableColumn rptCol41;
    @javafx.fxml.FXML
    private TableColumn rptCol51;
    @javafx.fxml.FXML
    private TableColumn rptCol61;
    @javafx.fxml.FXML
    private TableColumn rptCol71;
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
    private ComboBox CstSearchCB1;
    @javafx.fxml.FXML
    private RadioButton cstTopWkRadio;
    @javafx.fxml.FXML
    private ToggleGroup topCstRadioGrp;
    @javafx.fxml.FXML
    private RadioButton cntMoRadio1;
    @javafx.fxml.FXML
    private ButtonBar CntScheduleBtnBar1;
    @javafx.fxml.FXML
    private Button CntScheduleSearchBtn1;
    @javafx.fxml.FXML
    private Button CntScheduleClearBtn1;
    @javafx.fxml.FXML
    private TableColumn rptType2;
    @javafx.fxml.FXML
    private TextArea rptTextArea;
    @javafx.fxml.FXML
    private Pane CstMostActivePane;
    @javafx.fxml.FXML
    private TableView CstMostActiveTblView;
    @javafx.fxml.FXML
    private MenuItem CstMostActivePaneMenuItem;


    /**
     * Alert user of appointment happening within the next 15 minutes.
     * @throws SQLException
     * @throws NumberFormatException
     */
    protected void loginAppointAlert() throws NumberFormatException {
        System.out.println("Doing loginAppt Alert #1");
        AppointmentDAO apptDao = new AppointmentDaoImpl();
        String msgCtx = "";

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
                    if(apptStart.toLocalDate().equals(ldt.toLocalDate())){
                        if(apptStart.toLocalTime().isAfter(ldt.toLocalTime())) {
                            Long minsTill = ChronoUnit.MINUTES.between(ldt, apptStart);
                            System.out.println("Minutes until appointment: " + minsTill);
                            if (minsTill <= 16L){
                                msgCtx = String.format("Hi %s,%nYou have Appointment ID #%d soon!%n%tD at %tR",
                                        CurrentUserNameLbl.getText(), appt.getAppointment_ID(), apptStart.toLocalDate(), apptStart.toLocalTime());
                                alert = buildAlert(Alert.AlertType.INFORMATION, "", msgCtx);
                                confirm = alert.showAndWait();
                                return;
                            }
                        }
                    }
                }
                System.out.println("Doing loginAppt Alert #4");

                msgCtx = String.format("Hi %s. Relax! You don't have any appoints within the next 15 minutes.",
                        CurrentUserNameLbl.getText());
                alert = buildAlert(Alert.AlertType.INFORMATION, "", msgCtx);
                confirm = alert.showAndWait();
            }
        }
        catch(SQLException | NumberFormatException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    protected void setCurrentUserIdInfo(int setCurrentUserId) {
        CurrentUserIdLbl.setText("ID #" + setCurrentUserId);
        sessionUserId = setCurrentUserId;
    }

    protected void setCurrentUserNameInfo(String setCurrentUserName) {
        CurrentUserNameLbl.setText(setCurrentUserName);
        sessionUserName = setCurrentUserName;
    }

    /**
     * Creates an Alert instance.<BR>RUNTIME ERROR:<BR>java.lang.NullPointerException<BR>Caused by: java.lang.NullPointerException<BR>at controller.MainFormController.buildAlert(MainFormController.java:254)<BR>I originally planned to use the switch case statements to set the AlertType using "alert.setAlertType(alertType)."<BR>I realized I had not initialized the alert with the "alert = new (alertType)"<BR>Adding the Alert constructor fixed the issue.<BR>I removed the setAlert function anyway since the Alert constructor was being passed the AlertType directly at that point.
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
     * Displays the Customer ObservableList data in the TableView with the Country Info.
     */
    public void displayCstWithCoInfo() throws SQLException {
        ObservableList<Customer> cstWithCoInfo = FXCollections.observableArrayList();
        CustomerDAO cstDao = new CustomerDaoImpl();

        CstIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        CstNameCol.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        CstAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        CstPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postal_Code"));
        CstPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        CstCountryIdCol.setCellValueFactory(new PropertyValueFactory<>("country_ID"));
        CstDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("division_ID"));

        cstWithCoInfo.addAll(cstDao.customerWithCoInfo());
        CstTblView.setItems(cstWithCoInfo);
    }

    /**
     * Displays the Customer ObservableList data in the TableView.
     */
    public void displayCstTblViewData() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        CustomerDAO cstDao = new CustomerDaoImpl();

        CstIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        CstNameCol.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        CstAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        CstPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postal_Code"));
        CstPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        CstCountryIdCol.setCellValueFactory(new PropertyValueFactory<>("country_ID"));
        CstDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("division_ID"));

        allCustomers.addAll(cstDao.extractAll());
        CstTblView.setItems(allCustomers);
    }

    /**
     * Display the Appt ObservableList data in the TableView.
     */
    public void displayApptTblViewData() throws SQLException {
        ObservableList<Appointment> allApptAppointments = FXCollections.observableArrayList();
        AppointmentDAO apptDao = new AppointmentDaoImpl();

        appointment_ID_Col.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        title_Col.setCellValueFactory(new PropertyValueFactory<>("title"));
        description_Col.setCellValueFactory(new PropertyValueFactory<>("description"));
        location_Col.setCellValueFactory(new PropertyValueFactory<>("location"));
        type_Col.setCellValueFactory(new PropertyValueFactory<>("type"));
        start_Col.setCellValueFactory(new PropertyValueFactory<>("Start"));
        end_Col.setCellValueFactory(new PropertyValueFactory<>("End"));
        customer_ID_Col.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        user_ID_Col.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        contact_ID_Col.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));

        allApptAppointments.addAll(apptDao.extractAll());
        ApptTblView.setItems(allApptAppointments);
    }

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

    private void tableItemSelector() {
        TableView.TableViewSelectionModel<Appointment> itemSelector;
        switch (currentTab.getId()) {
            case "AppMoTab":
                itemSelector = includeApptMoController.ApptTblViewMonthly.getSelectionModel();
                selectedAppt = itemSelector.getSelectedItem();
                break;
            case "AppWkTab":
                itemSelector = includeApptWkController.ApptTblViewWeekly.getSelectionModel();
                selectedAppt = itemSelector.getSelectedItem();
                break;
            default:
                itemSelector = ApptTblView.getSelectionModel();
                selectedAppt = itemSelector.getSelectedItem();
                break;
        }
    }

    /**
     * Repopulate all Appointment Tableviews
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
     * Fills the MonthCB ComboBox with the months of the year. Used on Reports tab.
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            // For seed data testing. TODO: Don't forget to comment.
            //appointmentSeed();

            displayApptTblViewData();
            //displayCstTblViewData();
            displayCstWithCoInfo();
            setCurrentUserid(sessionUserId);
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
     * Loads the ApptAddUpdateFormCtrl and calls its method to send the selected row data in the Appointment table view to the appt-add-update-form view.
     * @param actionEvent Update form button, ApptUpdateBtn, clicked.
     * @throws IOException java.io.IOException - captures name exception: NullPointerException.
     * <BR>Present alert error dialog when no selection made.
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

            // Cast window to stage
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
     * Deletes selected row in Appointment table view.  Presents alert confirmation dialog box.
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
     * @param actionEvent Update form button, CstAddBtn, clicked.
     * @throws IOException java.io.IOException - captures name exception: NullPointerException.
     * <BR>Present alert error dialog when no selection made.
     */
    @javafx.fxml.FXML
    private void onActionCstNew(ActionEvent actionEvent) throws IOException {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("New Customer Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("/com/thecodebarista/view/cst-add-update-form.fxml")));
            scene = loader.load();

            // Cast window to stage
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
     * Loads the CstAddUpdateFormCtrl and calls its method to send the selected row data in the Customer table view to the cst-add-update-form view.
     * @param actionEvent Modify form button, CstModifyBtn, clicked.
     * @throws IOException java.io.IOException - captures name exception: NullPointerException.
     * <BR>Present alert error dialog when no selection made.
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

            // Cast window to stage
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
     * Deletes selected row in Customer table view.  Presents alert confirmation dialog box.
     * @param actionEvent Delete form button, CstDeleteBtn, clicked.
     * @throws IOException java.io.IOException - captures name exception: NullPointerException.
     * <BR>Present alert error dialog when no selection made.
     */
    @javafx.fxml.FXML
    private void onActionDeleteCst(ActionEvent actionEvent) throws IOException {
        String btnTxt = ((Button) actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try {
            selectedCst = CstTblView.getSelectionModel().getSelectedItem();
            String msgCtx = "Please confirm deletion of " + "Customer ID: " + selectedCst.getCustomer_ID() +
                    System.getProperty("line.separator") + System.getProperty("line.separator") + "ALL ASSOCIATED CUSTOMER APPOINTMENTS WILL BE DELETED.";
            confirmDelete(selectedCst, btnTxt, msgCtx);
        } catch (NullPointerException e) {
            String errorMsg = "Error: No Customer Selected!";
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, errorMsg);
            confirm = alert.showAndWait();
        }
    }

    /**
     * Sets currentTab variable on Appointment tab change.
     * @param event
     */
    @javafx.fxml.FXML
    public void onTabSelectLoad(Event event) {
        currentTab = ApptTabs.getSelectionModel().getSelectedItem();
        System.out.println("Current Tab Inquiry for tabSelect: " + currentTab.getId());
    }

    /**
     * Show pane with filter options for selected Report Menu's.
     * LAMBDA USAGE - Lambdas used to filter for all visible Panes and set to invisible.\n
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
            CntScheduleSearchCB.setItems(cntCb.extractAll());
        }

        if (menuItemId.equals("CstMostActivePane")) {

        }

        ReportTitlePane.setText(menuItem);
        reportFilterStackPane.getChildren().stream().filter((v) -> v.isVisible())
                .forEach((v) -> v.setVisible(false));
        Node paneNode = reportFilterStackPane.getChildren().stream().filter((p) -> p.getId().equals(menuItemId)).findFirst().get();
        paneNode.setVisible(true);
        paneNode.toFront();
        System.out.println("CHILDREN: " + TblViewStackPane.getChildren().stream().toList());
        Node tblViewNode = TblViewStackPane.getChildren().stream().filter((p) -> p.getId().equals(tblView)).findFirst().get();
        tblViewNode.toFront();
    }

    @javafx.fxml.FXML
    public void onReportFilterUpd(ActionEvent actionEvent) throws SQLException {
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
/*
        if (btnTxt.equals("CntSearchCB")) {
            cntFilter = true;
        }

        if (btnTxt.equals("CntWkRadio")) {
            cntWkFilter = true;
        }

        if (btnTxt.equals("CntMoRadio")) {
            cntMoFilter = true;
        }*/
    }

    @javafx.fxml.FXML
    public void onActionDoQuery(ActionEvent actionEvent) {
        System.out.println("Button Clicked: " + actionEvent.getSource().toString());
        String btnTxt = "";
        int wc = 0;
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
                String wcContact = String.valueOf(CntScheduleSearchCB.getValue().getContact_ID());
                String wcPeriod = CntApptPeriodRadioGrp.getSelectedToggle().toString();
                if (CntScheduleMoRadio.isSelected()) {
                    wc = 1;
                }
                reportParams[0] = wcContact;
                reportParams[1] = wcPeriod;
                reportParams[2] = String.valueOf(wc);
            }

            displayReportQuery(btnTxt, reportParams);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void onActionClearFilter(ActionEvent actionEvent) {
        btnTxt = ((Button) actionEvent.getSource()).getId().replace("SearchBtn", "");
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
     * Logout of application.<>BR</>Returns to Log In Screen
     */
    @javafx.fxml.FXML
    private void onActionLogout(ActionEvent actionEvent) throws Exception{

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

    @Override
    public <T> void timeConverter() {

    }

/*

    @Override
    public LocalDateTime makeLDT() {
        return null;
    }
*/

}
