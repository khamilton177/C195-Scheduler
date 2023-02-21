package com.thecodebarista.controller;

import com.thecodebarista.dao.*;
import com.thecodebarista.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class ApptAddUpdateFormCtrl extends MainMenuCtrl implements Initializable {

    /**
     * List values for the hours ComboBox
     */
    ObservableList<Integer> hours = FXCollections.observableArrayList();

    /**
     * List values for the minutes ComboBox
     */
    ObservableList<Integer> minutes = FXCollections.observableArrayList();

    /**
     * List values for the Appointment duration. Select value will NOT be persisted in database.
     */
    ObservableList<Long> durations = FXCollections.observableArrayList();

    AppointmentDAO apptDao = new AppointmentDaoImpl();
    CustomerDAO cstLVItems = new CustomerDaoImpl();
    ContactDaoImpl cntLVItems = new ContactDaoImpl();
    UserDaoImpl userLVItems = new UserDaoImpl();

    int selectedCstId;
    int selectedCntId;
    int selectedUserId;

    LocalDateTime StartTime;
    LocalDateTime EndTime;
    SimpleDateFormat tsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    LocalDate dpPrevDate;

    private Boolean dpSet = false;
    private Boolean hrSet = false;
    private Boolean minSet = false;

    @javafx.fxml.FXML
    private TextField appointment_ID_TxtFld;
    @javafx.fxml.FXML
    private TextField title_TxtFld;
    @javafx.fxml.FXML
    private TextField description_TxtFld;
    @javafx.fxml.FXML
    private TextField location_TxtFld;
    @javafx.fxml.FXML
    private TextField type_TxtFld;
    @javafx.fxml.FXML
    private ComboBox<Long> DurationCB;
    @javafx.fxml.FXML
    private DatePicker ApptStart_DatePick;
    @javafx.fxml.FXML
    private Button ApptSaveBtn;
    @javafx.fxml.FXML
    private Button ApptCancelBtn;
    @javafx.fxml.FXML
    private ComboBox<Integer> StartTimeHrs;
    @javafx.fxml.FXML
    private ComboBox<Integer> StartTimeMins;
    @javafx.fxml.FXML
    private Label AddUpdateApptLabel;
    @javafx.fxml.FXML
    private TextField end_TxtFld;
    @javafx.fxml.FXML
    private TextField start_TxtFld;
    @javafx.fxml.FXML
    private ComboBox<Contact> contact_ID_CBox;
    @javafx.fxml.FXML
    private ComboBox<Customer> customer_ID_CBox;
    @javafx.fxml.FXML
    private ComboBox<User> user_ID_CBox;


    /**
     * Fills the StartTimeHrs ComboBox with the local business hours for Appointments.
     */
    private void buildHours() {
        LocalDateTime ldt = LocalDateTime.now();

        int locHrsOpen = getLocOpenHr(ldt);
        for (int hrs = -1; hrs < totalBusHrs-1; hrs++) {
            hours.add(locHrsOpen);
            locHrsOpen++;
        }
        StartTimeHrs.setItems(hours);
    }

    /**
     * Fills the StartTimeMins ComboBox with the minutes intervals for Appointments.
     */
    private void buildMinutes() {
        int d = 0;
        DecimalFormat decF = new DecimalFormat("00");
        minutes.addAll(0, 15, 30, 45);
        StartTimeMins.setItems(minutes);
    }

    /**
     * Fills the DurationCB ComboBox with time periods for Appointments.
     */
    private void buildDurations() {
        durations.addAll(15L, 30L, 45L, 60l);
        DurationCB.setItems(durations);
    }

    /**
     * Calculate and sets the DurationCB ComboBox in the Update Appointment form. The duration is not persisted in DB.
     * @param selectedAppt
     */
    private void setDuration(Appointment selectedAppt) {
        // LocaleDateTime from DB timestamp conversion
        LocalDateTime dbUtcStartLDT = selectedAppt.getStart().toLocalDateTime();
        LocalDateTime dbUtcEndLDT = selectedAppt.getEnd().toLocalDateTime();
        //calculate difference between start and end
        Long durationMins = ChronoUnit.MINUTES.between(dbUtcStartLDT, dbUtcEndLDT);
        DurationCB.setValue(durationMins);
        System.out.println("Duration set to: " + durationMins);
    }

    /**
     * Set the User ComboBox to the Session User on New Appointments
     * @throws SQLException
     */
    protected void setDefaultUserOnNew() {
        System.out.println("DOING USER DEFAULT");
        if (static_AddUpdateLabel.getText().equals("New Appointment")) {
            try{
                User selectedUser = userLVItems.extract(sessionUserId);
                user_ID_CBox.setValue(selectedUser);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method populates the Update Form with the Appointment data from database
     * @param selectedAppt
     * @throws SQLException
     */
    protected void sendApptModifyData(Appointment selectedAppt) throws SQLException {
        selectedAppt = apptDao.extract(selectedAppt.getAppointment_ID());
        System.out.println("1 Setting fields");

        appointment_ID_TxtFld.setText(String.valueOf(selectedAppt.getAppointment_ID()));
        title_TxtFld.setText(String.valueOf(selectedAppt.getTitle()));
        description_TxtFld.setText(String.valueOf(selectedAppt.getDescription()));
        location_TxtFld.setText(String.valueOf(selectedAppt.getLocation()));
        type_TxtFld.setText(String.valueOf(selectedAppt.getType()));
        System.out.println("2 Setting fields");

        ApptStart_DatePick.setValue(selectedAppt.getStart().toLocalDateTime().toLocalDate());
        dpSet = true;
        StartTimeHrs.setValue(selectedAppt.getStart().toLocalDateTime().toLocalTime().getHour());
        hrSet = true;
        StartTimeMins.setValue(selectedAppt.getStart().toLocalDateTime().toLocalTime().getMinute());
        minSet = true;
        setDuration(selectedAppt);

        start_TxtFld.setText(tsFormat.format(selectedAppt.getStart()));
        end_TxtFld.setText(tsFormat.format(selectedAppt.getEnd()));

        System.out.println("3a Setting Customer fields");
        Customer selectedCst = cstLVItems.extract(selectedAppt.getCustomer_ID());
        customer_ID_CBox.setValue(selectedCst);


        System.out.println("3b Setting Contact fields");
        Contact selectedCnt = cntLVItems.extract(selectedAppt.getContact_ID());
        contact_ID_CBox.setValue(selectedCnt);

        System.out.println("3c Setting User fields");
        User selectedUser = userLVItems.extract(selectedAppt.getUser_ID());
        user_ID_CBox.setValue(selectedUser);

        System.out.println("Finished Setting fields");
    }

    private LocalDateTime calculateStartLdt() {
        LocalTime lt = ltInput(StartTimeHrs.getValue(), StartTimeMins.getValue());
        StartTime = lt.atDate(ApptStart_DatePick.getValue());
        Timestamp tsStart = Timestamp.valueOf(StartTime);
        start_TxtFld.setText(tsFormat.format(tsStart));
        System.out.println("Invisible Start Text Field: " + start_TxtFld.getText());
        return StartTime;
    }

    private LocalDateTime calculateEndLdt() {
        StartTime = calculateStartLdt();
        EndTime = StartTime.plusMinutes(DurationCB.getValue());
        System.out.println("This is end time: " + EndTime);
        Timestamp tsEnd = Timestamp.valueOf(EndTime);
        end_TxtFld.setText(tsFormat.format(tsEnd));
        System.out.println("End Text Field: " + end_TxtFld.getText());
        return EndTime;
    }

    /**
     * Check Appointment conflicts for Customer.
     * @throws SQLException
     */
    private Boolean apptOverlapCheck() throws SQLException {
        if(static_AddUpdateLabel.getText().equals("Update Appointment")){
            calculateEndLdt();
        }
        System.out.println("CHECKING APPOINT OVERLAPS");
        Boolean noConflict = true;
        AppointmentDAO apptDao = new AppointmentDaoImpl();
        StringBuilder validateErrMsg = new StringBuilder();
        String validateMsg = "";
        int i = 0;
        int thisApptId = (appointment_ID_TxtFld.getText().isEmpty() ? 0 : Integer.parseInt(appointment_ID_TxtFld.getText()));
        System.out.println("This appt id- " + thisApptId);
        LocalTime busCloseTime = getLocCloseTime(LocalDateTime.now());
        LocalTime thisAppt = EndTime.toLocalTime();

        //Prevent appointment being scheduled after Business Hours Close
        if(thisAppt.isAfter(busCloseTime)) {
            validateMsg = String.format("Please choose a shorter duration.%n Appointment ends after Business Close- %tR",
                   busCloseTime);
            validateErrMsg.append(validateMsg);
        }
        //Test for and prevent conflicts with existing appointments.
        else {
            try {
                ObservableList<Appointment> apptOverLap = apptDao.getApptByCst(selectedCstId, ApptStart_DatePick.getValue());
                for (Appointment appt : apptOverLap) {
                    int apptId = appt.getAppointment_ID();
                    LocalDateTime apptStart = appt.getStart().toLocalDateTime();
                    LocalDateTime apptEnd = appt.getEnd().toLocalDateTime();
                    System.out.println(String.format("Appt ID# %d%nAppt. Time:  %s", appt.getAppointment_ID(), appt.getStart().toLocalDateTime().toString()));
                    if (apptStart.equals(StartTime) && apptId != thisApptId) {
                        validateMsg = String.format("Please select a new Start Time.%n Customer already scheduled for appointment #%d at that time.",
                                appt.getAppointment_ID());
                    }
                    if (apptStart.isBefore(StartTime) && apptEnd.isAfter(StartTime) && apptId != thisApptId) {
                        validateMsg = String.format("Appointment overlaps customer's existing appointment #%d.",
                                appt.getAppointment_ID());
                    }
                    if (apptStart.isAfter(StartTime) && apptStart.isBefore(EndTime) && apptId != thisApptId) {
                        validateMsg = String.format("Please choose a shorter duration.%n Next appointment starts at %tR",
                                apptStart.toLocalTime());
                    }

                    validateErrMsg.append(validateMsg);
                    if (validateErrMsg.length() > 0) {
                        break;
                    }
                    System.out.println(String.format("Has Conflict at BREAK- Loop %d ", i++));
                    System.out.println("error length- " + validateErrMsg.length());
                }
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }

        if (validateErrMsg.length() > 0) {
            noConflict = false;
            alert = buildAlert(Alert.AlertType.ERROR, "Customer Appointment Conflict", validateErrMsg.toString());
            confirm = alert.showAndWait();
        }

        System.out.println("No Conflicts? " + noConflict);
        return noConflict;
    }

    private Boolean validateFormFields() {
        Boolean isValid = false;
        StringBuilder validateErrMsg = new StringBuilder();
        String validateMsg = "";

        String startTxtFld = start_TxtFld.getText();
        //System.out.println("Invisible Start Text Field: " + start);
        String endTxtFld = end_TxtFld.getText();
        //System.out.println("On Save End Text Field: " + end);

        if (ApptStart_DatePick.getValue() == null) {
            validateMsg = "Please select a valid Start Date";
            validateErrMsg.append(validateMsg);
            validateErrMsg.append(System.getProperty("line.separator"));
        }

        TextField[] formFields = {title_TxtFld, description_TxtFld, location_TxtFld, type_TxtFld, start_TxtFld, end_TxtFld};
        for (TextField field : formFields) {
            if (field.getText() == null || field.getText().isEmpty()) {
                System.out.println(field.getId() + ": " + field.getText());
                if (field.getId().equals("start_TxtFld")) {
                    if (ApptStart_DatePick.getValue() != null && StartTimeHrs.getValue() != null && StartTimeMins.getValue() != null) {
                        calculateEndLdt();
                    }
                    validateMsg = "";

                } else if (field.getId().equals("end_TxtFld")) {
                    validateMsg = "Please select a valid Start Time and Duration";
                } else {
                    validateMsg = "Please enter value for field: " + field.getId().replace("_TxtFld", "").replace("_ID", " ID#").toUpperCase();
                }

                validateErrMsg.append(validateMsg);
                if (!validateMsg.isEmpty()) {
                    validateErrMsg.append(System.getProperty("line.separator"));
                }
            }
        }

        ComboBox[] formCbFields = {customer_ID_CBox, contact_ID_CBox, user_ID_CBox};
        for (ComboBox box : formCbFields) {
            if (box.getValue() == null || box.getValue().toString().isEmpty()) {
                validateMsg = "Please select a " + box.getId().toString().replace("_ID_CBox", "");
                validateErrMsg.append(validateMsg);
                validateErrMsg.append(System.getProperty("line.separator"));
            }
        }

        if (validateErrMsg.length() > 0) {
            alert = buildAlert(Alert.AlertType.ERROR, "Form Incomplete", validateErrMsg.toString());
            confirm = alert.showAndWait();
        }
        else {
            Timestamp start = Timestamp.valueOf(startTxtFld);
            System.out.println("Invisible Start Text Field: " + start);
            Timestamp end = Timestamp.valueOf(endTxtFld);
            System.out.println("On Save End Text Field: " + end);
            isValid = true;
        }
        return isValid;
    }

    /**
     * Method Validates all fields used in Date, Start time, and End time\n
     * Alerts user of invalid fields
     * @return True if no validation errors
     */
    private Boolean canCalcDuration() {
        Boolean canCalc = false;
        StringBuilder validateErrMsg = new StringBuilder();

        if(!dpSet) {
            validateErrMsg.append("Start Date");
            validateErrMsg.append(System.getProperty("line.separator"));
        }

        if(!hrSet) {
            validateErrMsg.append("Start Time hours");
            validateErrMsg.append(System.getProperty("line.separator"));
        }

        if(!minSet) {
            validateErrMsg.append("Start Time minutes");
            validateErrMsg.append(System.getProperty("line.separator"));
        }

        if(validateErrMsg.length() > 0) {
            validateErrMsg.insert(0, System.getProperty("line.separator"));
            validateErrMsg.insert(0, "Please enter value for field: ");
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, validateErrMsg.toString());
            confirm = alert.showAndWait();
        }
        else {
            canCalc = true;
        }
        return canCalc;
    }

    /**
     * Method saves form data for New and Updated Appointments
     * @throws SQLException
     */
    protected void saveApptData() throws SQLException {
        int result = 0;
        System.out.println("LABEL- " + static_AddUpdateLabel.getText());

        String title = title_TxtFld.getText();
        String description = description_TxtFld.getText();
        String location = location_TxtFld.getText();
        String type = type_TxtFld.getText();
        Timestamp start = Timestamp.valueOf(start_TxtFld.getText());
        System.out.println("Invisible Start Text Field: " + start);
        Timestamp end = Timestamp.valueOf(end_TxtFld.getText());
        System.out.println("On Save End Text Field: " + end);
        int customer_ID = customer_ID_CBox.getValue().getCustomer_ID();
        int user_ID = user_ID_CBox.getValue().getUser_ID();
        int contact_ID = contact_ID_CBox.getValue().getContact_ID();

        AppointmentDAO apptDAOSave = new AppointmentDaoImpl();
        switch (static_AddUpdateLabel.getText()) {
            case "New Appointment":
                Appointment apptIns = new Appointment(0, title, description, location, type, start, end, customer_ID, user_ID, contact_ID);
                result = apptDAOSave.insert(apptIns);
                break;
            case "Update Appointment":
                int appointment_ID = Integer.parseInt(appointment_ID_TxtFld.getText());
                Appointment apptUpd = new Appointment(appointment_ID, title, description, location, type, start, end, customer_ID, user_ID, contact_ID);
                result = apptDAOSave.update(apptUpd);
                break;
        }
        System.out.println(result);
    }

    /**
     * Utilized lambda to replace the anonymous function used to create the Callback<DatePicker, DateCell> in the
     * setDayCellFactory function to set DataPicker dayCellFactoryProperty date values before current date or empty to disable.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        static_AddUpdateLabel = AddUpdateApptLabel;
        buildHours();
        buildMinutes();
        buildDurations();

        // Utilized lambda
        ApptStart_DatePick.setDayCellFactory(dp -> new DateCell() {
            // Disable all cell dates before current date and empty cells.
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(LocalDateTime.now().toLocalDate()) || empty); // Prevent past and empty date cells from being selected.
            }
        });

        try {
            ApptStart_DatePick.valueProperty().addListener(new ChangeListener<LocalDate>() {
                @Override
                public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate ldtPrev, LocalDate ldtNew) {
                    System.out.println("Start Previous Date: " + ldtPrev);
                    dpPrevDate = ldtPrev;
                }
            });

            customer_ID_CBox.setItems(cstLVItems.extractAll());
            contact_ID_CBox.setItems(cntLVItems.extractAll());
            user_ID_CBox.setItems(userLVItems.extractAll());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mouse click action on ApptStart_DatePick. Returns the current Start Date to use as reset value during validations.
     * @param event
     * @return
     */
    @javafx.fxml.FXML
    public LocalDate getPrev(Event event) {
        btnTxt = ((DatePicker)event.getSource()).getId().replace("_", " ").concat("er");
        System.out.println("Mouse Clicked: "+ btnTxt);
        System.out.println(ApptStart_DatePick.getValue());
        return ApptStart_DatePick.getValue();
    }

    /**
     * OnAction method for ApptStart_DatePick selection is made. Validates if date choose current or future date.\n
     * Set the boolean dpSet to true if valid or alerts user if not valid.
     * @param actionEvent
     * @return
     */
    @javafx.fxml.FXML
    public Boolean onStartDate(ActionEvent actionEvent) {
        btnTxt = ((DatePicker)actionEvent.getSource()).getId().replace("_", " ").concat("er");
        if ((static_AddUpdateLabel.getText() == "New Appointment")
                && ((ApptStart_DatePick.getValue().isBefore(LocalDateTime.now().toLocalDate())))) {
            StringBuilder validateErrMsg = new StringBuilder();
            String validateMsg = "Selected date has past.";
            validateErrMsg.append(validateMsg);
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, validateErrMsg.toString());
            confirm = alert.showAndWait();
            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                ApptStart_DatePick.setValue(dpPrevDate);
            }
            return dpSet;
        }
        if (ApptStart_DatePick.getValue() != null) {
            dpSet = true;

            if (!start_TxtFld.getText().isEmpty()
                    && !end_TxtFld.getText().isEmpty()) { // If DatePick value changed after end date calculate,
                calculateEndLdt(); // recalculate start and end dates

            }
            if ((!start_TxtFld.getText().isEmpty())
                    && (end_TxtFld.getText().isEmpty())
                    && (DurationCB.getValue() > 0l)) { // If DatePick value changed after canCalcDuration() validation
                calculateEndLdt(); // recalculate start and end dates
            }
        }
        return dpSet;
    }

    /**
     * OnAction method for Duration and Start Time hours and minutes ComboBoxes selections.\n
     * Set the boolean validation variables- hrSet and minSet to true if respective ComboBoxes selected.\n
     * If all three have valid selections End Date and Time field is updated.
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void onDurationUpdate(ActionEvent actionEvent) {
        btnTxt = ((ComboBox)actionEvent.getSource()).getId().replace("Btn", "");

        if (btnTxt.equalsIgnoreCase("StartTimeHrs")) {
            hrSet = true;

            if(DurationCB.getValue() == null) {
                return;
            }
        }

        if (btnTxt.equalsIgnoreCase("StartTimeMins")) {
            minSet = true;

            if(DurationCB.getValue() == null) {
                return;
            }
        }

        if(( DurationCB.getValue() == null || DurationCB.getValue().equals(0L) || DurationCB.getSelectionModel().isEmpty())) {
            System.out.println("No Duration #3");
        }
        else {
            System.out.println("Duration Updated #3");
            try {
                Boolean doCalc = canCalcDuration();
                System.out.println("Check doCalc: " + doCalc);

                if (doCalc) {
                    System.out.println("In doCalc #3.1");
                    calculateEndLdt();
                    System.out.println("In doCalc #3.2" );
                    // System.out.println("This is end time: " + EndTime);
                } else {
                    if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    }
                }
            }
            catch (RuntimeException e) {
                System.out.println("Resetting Duration #2");
                System.out.println(e.getMessage());
                System.out.println(e.getCause());
            }
        }
    }

    /**
     * Save new or updated customer appointment.
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void onActionSaveAppt(ActionEvent actionEvent) {
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try {
            boolean validForm = validateFormFields();

            if (validForm) {
                boolean noConflict = apptOverlapCheck();
                if (noConflict) {
                    saveApptData();
                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
                    stage.setTitle("C195-Scheduler");
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    /**
     * OnAction method cancels New/Updated Appointment activity. Returns user to Main Menu.
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void onActionCancel(ActionEvent actionEvent) {
        System.out.println("Cancel Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try {
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
            stage.setTitle("C195-Scheduler");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

}
