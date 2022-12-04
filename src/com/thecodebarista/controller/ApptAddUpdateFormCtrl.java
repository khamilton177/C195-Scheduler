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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.*;
import java.util.ResourceBundle;

public class ApptAddUpdateFormCtrl extends MainMenuCtrl implements Initializable {

    /**
     * Values for the Appointment duration. Select value will NOT be persisted.
     */
    ObservableList<Long> durations = FXCollections.observableArrayList();
    ObservableList<Integer> hours;
    ObservableList<Integer> minutes = FXCollections.observableArrayList();

    AppointmentDAO apptDao = new AppointmentDaoImpl();
//    AppointmentDAO aptDaoSelected = new AppointmentDaoImpl();

    CustomerDAO cstLVItems = new CustomerDaoImpl();
    ContactDaoImpl cntLVItems = new  ContactDaoImpl();
    UserDaoImpl userLVItems = new  UserDaoImpl();

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
    private ComboBox EndTimeHrs;
    @javafx.fxml.FXML
    private ComboBox EndTimeMins;

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
        StartTimeHrs.setValue(selectedAppt.getStart().toLocalDateTime().toLocalTime().getHour());
        StartTimeMins.setValue(selectedAppt.getStart().toLocalDateTime().toLocalTime().getMinute());

//        EndTime.setText(persistStartLDT.plusMinutes(DurationCB.getValue()).toLocalTime().toString());
        customer_ID_TxtFld.setText(String.valueOf(selectedAppt.getCustomer_ID()));
        contact_ID_TxtFld.setText(String.valueOf(selectedAppt.getContact_ID()));
        user_ID_TxtFld.setText(String.valueOf(selectedAppt.getUser_ID()));
        System.out.println("3 Setting fields");

        selectedCstId = Integer.parseInt(user_ID_TxtFld.getText());
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
        LocalTime lt = LocalTime.of(hrs, mins);
        return lt;
    }

    public LocalDateTime getLDT(LocalDate ldt, LocalTime lt) {
        LocalDateTime lDteT = LocalDateTime.of(ldt, lt);
        return lDteT;
    }

    private ObservableList<Integer> buildHours() {
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        //hours.add(Long.valueOf(String.valueOf(hrs)));

        for (int hrs = 0; hrs < 24; hrs++) {
            hours.add(hrs);
        }
        StartTimeHrs.setItems(hours);
        return hours;
    }

    private void buildMinutes() {
        // DecimalFormat decF = new DecimalFormat("00");
        minutes.addAll(0, 15, 30, 45);
        StartTimeMins.setItems(minutes);
    }

    private void buildDurations() {
        // DecimalFormat decF = new DecimalFormat("00");
        durations.addAll(null, 15L, 30L, 45L);
        DurationCB.setItems(durations);
    }

    private void calcDurationAlert() {
        StringBuilder checkLDTFields = new StringBuilder();

        if(!dpSet) {
            checkLDTFields.append("Start Date");
            checkLDTFields.append(System.getProperty("line.separator"));
        }

        if(!hrSet) {
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
        }
    }

    private Boolean canCalcDuration() {
        Boolean canCalc = false;
        StringBuilder checkLDTFields = new StringBuilder();

        if(!dpSet) {
            checkLDTFields.append("Start Date");
            checkLDTFields.append(System.getProperty("line.separator"));
        }
        else{

        }

        if(!hrSet){
            checkLDTFields.append("Start Time hours");
            checkLDTFields.append(System.getProperty("line.separator"));
        }else{

        }
        if(!minSet){
            checkLDTFields.append("Start Time minutes");
            checkLDTFields.append(System.getProperty("line.separator"));
        }else{

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

/*
    SimpleIntegerProperty StartTime = new SimpleIntegerProperty();
    SimpleIntegerProperty EndTime= new SimpleIntegerProperty();
    {
        // EndTimeMins.bind(StartTimeMins.add(DurationCB.getValue()));

        EndTimeMins= StartTimeMins.plusMinutes(DurationCB.getValue()));
        System.out.println(second.get()); //'105'
        second.bind(StartTime.subtract(50));
        System.out.println(second.get()); //'-45'
    }
*/

    private void setDuration() {
        if (static_AddUpdateLabel.getText().equals("Update Appointment")) {
            // LocaleDateTime from DB timestamp conversion
            LocalDateTime persistStartLDT = selectedAppt.getStart().toLocalDateTime();
            LocalDateTime persistEndLDT = selectedAppt.getEnd().toLocalDateTime();
            Long minEnd = Long.valueOf(persistEndLDT.toLocalTime().getMinute());
            Long minStart = Long.valueOf(persistStartLDT.toLocalTime().getMinute());
            DurationCB.setValue(minEnd-minStart);
            System.out.println("Duration set to: " + String.valueOf(minEnd-minStart));
        }
    }

    @javafx.fxml.FXML
    public Boolean onStartDate(ActionEvent actionEvent) {
        return dpSet = true;
    }

    @javafx.fxml.FXML
    public Boolean onStartHrSet(ActionEvent actionEvent) {
        return hrSet = true;
    }

    @javafx.fxml.FXML
    public Boolean onStartMinSet(ActionEvent actionEvent) {
        return minSet = true;
    }

    @javafx.fxml.FXML
    public void onDurationUpdate(ActionEvent actionEvent) throws IOException {
        System.out.println("Duration changed: " + ((ComboBox)actionEvent.getSource()).getId());
        btnTxt = "Duration changed";

        if((!DurationCB.getSelectionModel().isEmpty())){
            System.out.println("Resetting Duration #3");
       //     return;
      //  }else{
            try {
                Boolean doCalc = canCalcDuration();
                if (doCalc) {
                    LocalTime lt = ltInput(StartTimeHrs.getValue(), StartTimeMins.getValue());
                    // StartTime = getLDT(lt.getHour(), lt.getMinute());
                    StartTime = lt.atDate(ApptStart_DatePick.getValue());
                    ZonedDateTime locZdt = ZonedDateTime.of(StartTime, ZoneId.of(ZoneIdLbl.getText()));
                    locZdt.toLocalDateTime();
                    EndTime = StartTime.plusMinutes(DurationCB.getValue());
                }
                else{
                    if(confirm.isPresent() && confirm.get() == ButtonType.OK) {
                      //  DurationCB.setValue(null);
                    }
                }
            }
            catch(NullPointerException e) {
                System.out.println("Resetting Duration #2");
                System.out.println(e.getMessage());
                System.out.println(e.getCause());
            }
        }
    }



    @javafx.fxml.FXML
    public void onActionSaveAppt(ActionEvent actionEvent) {


        for (Customer cst : customer_ID_ListView.getItems()){

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

        if (static_AddUpdateLabel.getText().equals("Update Appointment")) {
            setDuration();
            System.out.println("Set Duration Done!");
        }

        try {
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
