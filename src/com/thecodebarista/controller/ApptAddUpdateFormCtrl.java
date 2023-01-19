package com.thecodebarista.controller;

import com.thecodebarista.dao.*;
import com.thecodebarista.model.Appointment;
import com.thecodebarista.model.Contact;
import com.thecodebarista.model.Customer;
import com.thecodebarista.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ApptAddUpdateFormCtrl extends MainMenuCtrl implements Initializable {

    /**
     * Values for the Appointment duration. Select value will NOT be persisted.
     */
    ObservableList<Long> durations = FXCollections.observableArrayList();
    ObservableList<Integer> hours = FXCollections.observableArrayList();
    ObservableList<Integer> minutes = FXCollections.observableArrayList();

    AppointmentDAO apptDao = new AppointmentDaoImpl();
    CustomerDAO cstLVItems = new CustomerDaoImpl();
    ContactDaoImpl cntLVItems = new ContactDaoImpl();
    UserDaoImpl userLVItems = new UserDaoImpl();

    Customer selectedCstLVItem;
    Contact selectedCntLVItem;
    User selectedUserLVItem;
    int selectedCstId;
    int selectedCstIndex;
    int selectedCntId;
    int selectedCntIndex;
    int selectedUserId;
    int selectedUserIndex;

    LocalDateTime StartTime;
    LocalDateTime EndTime;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    SimpleDateFormat tsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected LocalDate propValDtPick;
    protected Integer propValHrs;
    protected Integer propValMins;

    private Boolean dpSet = false;
    private Boolean hrSet = false;
    private Boolean minSet = false;

    @javafx.fxml.FXML
    private Label apptAlertBoxLbl;
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
    private ListView<Customer> customer_ID_ListView;
    @javafx.fxml.FXML
    private ListView<Contact> contact_ID_ListView;
    @javafx.fxml.FXML
    private ListView<User> user_ID_ListView;
    @javafx.fxml.FXML
    private TextField customer_ID_TxtFld;
    @javafx.fxml.FXML
    private TextField contact_ID_TxtFld;
    @javafx.fxml.FXML
    private TextField user_ID_TxtFld;
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
    private DatePicker ApptEnd_DatePick;
    @javafx.fxml.FXML
    private TextField end_TxtFld;
    @javafx.fxml.FXML
    private ComboBox<Integer> EndTimeHrs;
    @javafx.fxml.FXML
    private ComboBox<Integer> EndTimeMins;
    @javafx.fxml.FXML
    private TextField start_TxtFld;

    /**
     * Fills the list with the local business hours for Appointments.
     */
    private void buildHours() {
        LocalDateTime ldt = LocalDateTime.now();

        int locHrsOpen = getLocOpenHr(ldt);
        for (int hrs = -1; hrs < totalBusHrs; hrs++) {
            hours.add(locHrsOpen);
            locHrsOpen++;
        }
        StartTimeHrs.setItems(hours);
    }

    /**
     * Fills the list with the minutes intervals for Appointments.
     */
    private void buildMinutes() {
        int d = 0;
        DecimalFormat decF = new DecimalFormat("00");
        minutes.addAll(0, 15, 30, 45);
        StartTimeMins.setItems(minutes);
    }

    /**
     * Fills the duration list with time periods for Appointments.
     */
    private void buildDurations() {
        // DecimalFormat decF = new DecimalFormat("00");
        durations.addAll(0L, 15L, 30L, 45L, 60l);
        DurationCB.setItems(durations);
    }

    private int getCstByIndex(int id) {
        int index = -1;

        for (Customer customer : customer_ID_ListView.getItems()){
            index++;

            if (customer.getCustomer_ID() == id)
                return index;
        }
        return -1;
    }

    private int getCntByIndex(int id) {
        int index = -1;

        for (Contact contact : contact_ID_ListView.getItems()){
            index++;

            if (contact.getContact_ID() == id)
                return index;
        }
        return -1;
    }

    private int getUserByIndex(int id) {
        int index = -1;

        for (User user : user_ID_ListView.getItems()){
            index++;

            if (user.getUser_ID() == id)
                return index;
        }
        return -1;
    }

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
//        String tsEndFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(selectedAppt.getEnd());
        end_TxtFld.setText(tsFormat.format(selectedAppt.getEnd()));


        customer_ID_TxtFld.setText(String.valueOf(selectedAppt.getCustomer_ID()));
        contact_ID_TxtFld.setText(String.valueOf(selectedAppt.getContact_ID()));
        user_ID_TxtFld.setText(String.valueOf(selectedAppt.getUser_ID()));
        System.out.println("3 Setting fields");

        selectedCstId = Integer.parseInt(customer_ID_TxtFld.getText());
        selectedCstIndex = getCstByIndex(selectedCstId);
        int selectedCntId = Integer.parseInt(contact_ID_TxtFld.getText());
        int selectedCntIndex = getCntByIndex(selectedCntId);
        int selectedUserId = Integer.parseInt(user_ID_TxtFld.getText());
        int selectedUserIndex = getUserByIndex(selectedUserId);
        System.out.println("4 Setting fields");

        customer_ID_ListView.getSelectionModel().select(selectedCstIndex);
        contact_ID_ListView.getSelectionModel().select(selectedCntIndex);
        user_ID_ListView.getSelectionModel().select(selectedUserIndex);
        System.out.println("Finished Setting fields");
    }

    public LocalTime ltInput(int hrs, int mins) {
        return LocalTime.of(hrs, mins);
    }

    public LocalDateTime getLDT(LocalDate ldt, LocalTime lt) {
        return LocalDateTime.of(ldt, lt);
    }

    private String getTextFormFields() {
        String fields;
        String title = title_TxtFld.getText();
        String description = description_TxtFld.getText();
        String location = location_TxtFld.getText();
        String type = type_TxtFld.getText();
        String startTxtFld = start_TxtFld.getText();
        String endTxtFld = end_TxtFld.getText();
        String customerIdTxt = customer_ID_TxtFld.getText();
        String userIdTxt = user_ID_TxtFld.getText();
        String contactIdTxt = contact_ID_TxtFld.getText();
        return fields = "title, description, location, type, startTxtFld, endTxtFld, customerIdTxt, userIdTxt, contactIdTxt";
    }

    private LocalDateTime calculateStartDt() {
        LocalTime lt = ltInput(StartTimeHrs.getValue(), StartTimeMins.getValue());
        StartTime = lt.atDate(ApptStart_DatePick.getValue());
        Timestamp tsStart = Timestamp.valueOf(StartTime);
        start_TxtFld.setText(tsFormat.format(tsStart));
        System.out.println("Invisible Start Text Field: " + start_TxtFld.getText());
        return StartTime;
    }

    private void calculateEndDt() {
        StartTime = calculateStartDt();
        EndTime = StartTime.plusMinutes(DurationCB.getValue());
        Timestamp tsEnd = Timestamp.valueOf(EndTime);
        end_TxtFld.setText(tsFormat.format(tsEnd));
        System.out.println("End Text Field: " + end_TxtFld.getText());
        System.out.println("This is end time: " + EndTime);
    }

    private Boolean validateFormFields() {
        Boolean isValid = false;
        StringBuilder validateErrMsg = new StringBuilder();
        String validateMsg = "";

        String title = title_TxtFld.getText();
        String description = description_TxtFld.getText();
        String location = location_TxtFld.getText();
        String type = type_TxtFld.getText();
        String startTxtFld = start_TxtFld.getText();
        //System.out.println("Invisible Start Text Field: " + start);
        String endTxtFld = end_TxtFld.getText();
        //System.out.println("On Save End Text Field: " + end);
        String customerIdTxt = customer_ID_TxtFld.getText();
        String userIdTxt = user_ID_TxtFld.getText();
        String contactIdTxt = contact_ID_TxtFld.getText();

        if (ApptStart_DatePick.getValue() == null) {
            validateMsg = "Please select a valid Start Date";
            validateErrMsg.append(validateMsg);
            validateErrMsg.append(System.getProperty("line.separator"));
        }
        TextField[] formFields = {title_TxtFld, description_TxtFld, location_TxtFld, type_TxtFld, start_TxtFld, end_TxtFld, customer_ID_TxtFld, user_ID_TxtFld, contact_ID_TxtFld};
        for (TextField field : formFields) {
            if(field.getText() == null || field.getText().isEmpty()){
                if(field.getId().equals("start_TxtFld")){
                    if (ApptStart_DatePick.getValue() != null && StartTimeHrs.getValue() != null  && StartTimeMins.getValue() != null) {
                        calculateStartDt();
                        validateMsg = "";
                    }
                }
                else if(field.getId().equals("end_TxtFld")) {
                    validateMsg = "Please select a valid Start Time and Duration";
                }
                else {
                    validateMsg = "Please enter value for field: " + field.getId().replace("_TxtFld", "").replace("_ID", " ID#").toUpperCase();
                }
            validateErrMsg.append(validateMsg);
            if (!validateMsg.isEmpty())
                validateErrMsg.append(System.getProperty("line.separator"));
            }
        }

        if (validateErrMsg.length() > 0) {
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, validateErrMsg.toString());
            confirm = alert.showAndWait();
        }
        else {
            Timestamp start = Timestamp.valueOf(startTxtFld);
            System.out.println("Invisible Start Text Field: " + start);
            Timestamp end = Timestamp.valueOf(endTxtFld);
            System.out.println("On Save End Text Field: " + end);
            int customer_ID = Integer.parseInt(customerIdTxt);
            int user_ID = Integer.parseInt(userIdTxt);
            int contact_ID = Integer.parseInt(contactIdTxt);
            isValid = true;
        }
        return isValid;
    }

    private Boolean canCalcDuration() {
        Boolean canCalc = false;
        StringBuilder checkLDTFields = new StringBuilder();

        if(!dpSet) {
            checkLDTFields.append("Start Date");
            checkLDTFields.append(System.getProperty("line.separator"));
        }

        if(!hrSet){
            checkLDTFields.append("Start Time hours");
            checkLDTFields.append(System.getProperty("line.separator"));
        }

        if(!minSet){
            checkLDTFields.append("Start Time minutes");
            checkLDTFields.append(System.getProperty("line.separator"));
        }

        if(checkLDTFields.length() > 0) {
            checkLDTFields.insert(0, System.getProperty("line.separator"));
            checkLDTFields.insert(0, "Please Fill-in the following fields:");
            alert = buildAlert(Alert.AlertType.ERROR, btnTxt, checkLDTFields.toString());
            confirm = alert.showAndWait();
        }else{
            canCalc = true;
        }
        return canCalc;
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
        int customer_ID = Integer.parseInt(customer_ID_TxtFld.getText());
        int user_ID = Integer.parseInt(user_ID_TxtFld.getText());
        int contact_ID = Integer.parseInt(contact_ID_TxtFld.getText());

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

    @javafx.fxml.FXML
    public Boolean onStartDate(ActionEvent actionEvent) {
        if (ApptStart_DatePick.getValue() != null){
            dpSet = true;
        }
        if (!start_TxtFld.getText().isEmpty() && !end_TxtFld.getText().isEmpty()) {
            // If DatePick value changed after end date calculate, recalculate start and end dates
            calculateEndDt();
/*            if (ApptStart_DatePick.getValue() != null && StartTimeHrs.getValue() != null  && StartTimeMins.getValue() != null) {
                return dpSet = true;
            }
            if (ApptStart_DatePick.getValue() != null && StartTimeHrs.getValue() != null  && StartTimeMins.getValue() != null) {
                return dpSet = true;
            }*/
        }
        return dpSet;
    }

    @javafx.fxml.FXML
    public void onDurationUpdate(ActionEvent actionEvent) throws Exception {
        btnTxt = ((ComboBox)actionEvent.getSource()).getId().replace("Btn", "");

        if (btnTxt.equalsIgnoreCase("StartTimeHrs")){
            hrSet = true;

            if(DurationCB.getValue() == null){
                return;
            }
        }

        if (btnTxt.equalsIgnoreCase("StartTimeMins")){
            minSet = true;

            if(DurationCB.getValue() == null){
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
                System.out.println("Check doCalc #3");

                if (doCalc) {
                    System.out.println("In doCalc #3");
            /*        LocalTime lt = ltInput(StartTimeHrs.getValue(), StartTimeMins.getValue());
                    System.out.println("In doCalc #3.1 " + lt);
                    // StartTime = getLDT(lt.getHour(), lt.getMinute());
                    StartTime = lt.atDate(ApptStart_DatePick.getValue());
                    Timestamp tsStart = Timestamp.valueOf(StartTime);
                    String tsStartFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tsStart);
                    start_TxtFld.setText(tsStartFormatted);
                    System.out.println("In doCalc #3.2 " + StartTime);
                    ZonedDateTime locZdt = ZonedDateTime.of(StartTime, ZoneId.of(static_ZoneId));
                    ZonedDateTime utcZdt = locZdt.withZoneSameInstant(ZoneOffset.UTC);
                    System.out.println("In doCalc #3.3 -Local time: " + locZdt);
                    System.out.println("In doCalc #3.3 -UTC time: " + utcZdt);
                    System.out.println("In doCalc #3.4 ");*/

/*                    StartTime = calculateStartDt();
                    EndTime = StartTime.plusMinutes(DurationCB.getValue());
                    Timestamp tsEnd = Timestamp.valueOf(EndTime);
                    String tsEndFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tsEnd);
                    end_TxtFld.setText(tsEndFormatted);*/

                    calculateEndDt();
                    System.out.println("In doCalc #3.5" );
                    // System.out.println("This is end time: " + EndTime);
                } else {
                    if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    }
                }
            } catch (RuntimeException e) {
                System.out.println("Resetting Duration #2");
                System.out.println(e.getMessage());
                System.out.println(e.getCause());
            }
        }
    }

    @javafx.fxml.FXML
    public void onActionSaveAppt(ActionEvent actionEvent) {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());
        //if (ApptStart_DatePick.valueProperty().isNull().ApptAddBtn.){

        try{
            boolean validForm = validateFormFields();

            if (validForm) {
                //saveApptData();
                // Cast window to stage
                stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
                stage.setTitle("C195-Scheduler");
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    @javafx.fxml.FXML
    public void onActionCancel(ActionEvent actionEvent) {
        System.out.println("Cancel Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            // Cast window to stage
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

    @Override
        public void initialize(URL url, ResourceBundle rb) {
        static_AddUpdateLabel = AddUpdateApptLabel;
        buildHours();
        buildMinutes();
        buildDurations();

        try {
            //ApptSaveBtn.disableProperty().bind(ApptStart_DatePick.valueProperty().isNull());

/*
            ApptStart_DatePick.valueProperty().addListener(new ChangeListener<LocalDate>() {
                @Override
                public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate ldtPrev, LocalDate ldtNew) {
                    propValDtPick = ldtNew;
                    System.out.println("Start Date Changed to " + ldtNew);

                }
            });

*/

            // Code will populate the listviews then assign change listeners to detect and track the selected item.
            customer_ID_ListView.setItems(cstLVItems.extractAll());
            customer_ID_ListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
                @Override
                public void changed(ObservableValue<? extends Customer> observableValue, Customer cstPrev, Customer cstNew) {
                    selectedCstLVItem = customer_ID_ListView.getSelectionModel().getSelectedItem();
                    System.out.println("Customer Listener set");

                    selectedCstId = selectedCstLVItem.getCustomer_ID();
                    System.out.println("Selected Customer ID " + selectedCstId);
                    customer_ID_TxtFld.setText(String.valueOf(selectedCstId));
                }
            });

            contact_ID_ListView.setItems(cntLVItems.extractAll());
            contact_ID_ListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
                @Override
                   public void changed(ObservableValue<? extends Contact> observableValue, Contact cntPrev, Contact cntNew) {
                    selectedCntLVItem = contact_ID_ListView.getSelectionModel().getSelectedItem();
                    System.out.println("Contact Listener set");

                    selectedCntId = selectedCntLVItem.getContact_ID();
                    System.out.println("Selected Contact ID " + selectedCntId);
                    contact_ID_TxtFld.setText(String.valueOf(selectedCntId));
                }
           });

            user_ID_ListView.setItems(userLVItems.extractAll());
            user_ID_ListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
                @Override
                public void changed(ObservableValue<? extends User> observableValue, User userPrev, User userNew) {
                    selectedUserLVItem = user_ID_ListView.getSelectionModel().getSelectedItem();
                    System.out.println("User Listener set");

                    selectedUserId = selectedUserLVItem.getUser_ID();
                    System.out.println("Selected User ID " + selectedUserId);
                    user_ID_TxtFld.setText(String.valueOf(selectedUserId));
                }
            });
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
