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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ApptAddUpdateFormCtrl extends MainMenuCtrl implements Initializable {

    /**
     * Values for the Appointment duration. Select value will NOT be persisted.
     */
    ObservableList<Integer> durations = FXCollections.observableArrayList();
    Customer selectedCstLVItem;
    Contact selectedCntLVItem;
    User selectedUserLVItem;
    int selectedCstId;
    int selectedCstIndex;

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
    private ComboBox<LocalTime> StartTime;
    @javafx.fxml.FXML
    private ComboBox<Integer> DurationCB;
    @javafx.fxml.FXML
    private TextField EndTime;
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
        AppointmentDAO aptdaoSelected = new AppointmentDaoImpl();
        selectedAppt = aptdaoSelected.extract(selectedAppt.getAppointment_ID());

        appointment_ID_TxtFld.setText(String.valueOf(selectedAppt.getAppointment_ID()));
        title_TxtFld.setText(String.valueOf(selectedAppt.getTitle()));
        description_TxtFld.setText(String.valueOf(selectedAppt.getDescription()));
        location_TxtFld.setText(String.valueOf(selectedAppt.getLocation()));
        type_TxtFld.setText(String.valueOf(selectedAppt.getType()));

        // LocaleDateTime from DB timestamp conversion
        LocalDateTime persistStartLDT = selectedAppt.getStart().toLocalDateTime();
        LocalDateTime persistEndLDT = selectedAppt.getEnd().toLocalDateTime();

        Integer minEnd = (Integer)persistEndLDT.toLocalTime().getMinute();
        Integer minStart = (Integer)persistStartLDT.toLocalTime().getMinute();

        ApptStart_DatePick.setValue(selectedAppt.getStart().toLocalDateTime().toLocalDate());
        StartTime.setValue(selectedAppt.getStart().toLocalDateTime().toLocalTime());
        DurationCB.setValue(minEnd-minStart);
        EndTime.setText(persistStartLDT.plusMinutes(DurationCB.getValue()).toLocalTime().toString());
        customer_ID_TxtFld.setText(String.valueOf(selectedAppt.getCustomer_ID()));
        contact_ID_TxtFld.setText(String.valueOf(selectedAppt.getContact_ID()));
        user_ID_TxtFld.setText(String.valueOf(selectedAppt.getUser_ID()));

        selectedCstId = Integer.parseInt(user_ID_TxtFld.getText());
        selectedCstIndex = getCstByIndex(selectedCstId);
        int selectedCntId = Integer.parseInt(contact_ID_TxtFld.getText());
        int selectedCntIndex = getCntByIndex(selectedCntId);
        int selectedUserId = Integer.parseInt(user_ID_TxtFld.getText());
        int selectedUserIndex = getUserByIndex(selectedUserId);

        customer_ID_ListView.getSelectionModel().select(selectedCstIndex);
        contact_ID_ListView.getSelectionModel().select(selectedCntIndex);
        user_ID_ListView.getSelectionModel().select(selectedUserIndex);
    }

    private void buildDurations() {
        durations.addAll(15, 30, 45);
        DurationCB.setItems(durations);
    }

    public void onDurationUpdate(ActionEvent event) {
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
    public void initialize(URL url, ResourceBundle resourceBundle) {

        buildDurations();
        CustomerDAO cstLVItems = new CustomerDaoImpl();
        ContactDaoImpl cntLVItems = new  ContactDaoImpl();
        UserDaoImpl userLVItems = new  UserDaoImpl();

        try {
            // Code will populate the listviews then assign change listeners to detect and track the selected item.
            customer_ID_ListView.setItems(cstLVItems.extractAll());
            customer_ID_ListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
                @Override
                public void changed(ObservableValue<? extends Customer> observableValue, Customer customer, Customer t1) {
                    selectedCstLVItem = customer_ID_ListView.getSelectionModel().getSelectedItem();
                    selectedCstId = selectedCstLVItem.getCustomer_ID();
                    System.out.println("Selected Customer ID " + selectedCstId);
                    //user_ID_TxtFld.setText(String.valueOf(selectedCstId));
                }
            });

            contact_ID_ListView.setItems(cntLVItems.extractAll());
            contact_ID_ListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
                @Override
                   public void changed(ObservableValue<? extends Contact> observableValue, Contact contact, Contact t1) {
                    selectedCntLVItem = contact_ID_ListView.getSelectionModel().getSelectedItem();
                }
           });

            user_ID_ListView.setItems(userLVItems.extractAll());
            user_ID_ListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
                @Override
                public void changed(ObservableValue<? extends User> observableValue, User user, User t1) {
                    selectedUserLVItem = user_ID_ListView.getSelectionModel().getSelectedItem();
                }
            });
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
