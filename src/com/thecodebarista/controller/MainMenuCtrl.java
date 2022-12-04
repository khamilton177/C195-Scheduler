package com.thecodebarista.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import com.thecodebarista.dao.*;
import com.thecodebarista.model.Appointment;
import com.thecodebarista.model.Customer;
import com.thecodebarista.model.FirstLevelDivision;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainMenuCtrl extends LoginFormCtrl implements Initializable {
    public static Label static_AddUpdateLabel;
    Appointment selectedAppt;
    Customer selectedCst;
    Alert alert;
    String btnTxt;
    Optional<ButtonType> confirm;

    FirstLevelDivisionDAOImpl divCBItems = new FirstLevelDivisionDAOImpl();

    @javafx.fxml.FXML
    protected TableView<Appointment> ApptTblView;
    @javafx.fxml.FXML
    protected DatePicker ApptEnd_SF;
    @javafx.fxml.FXML
    protected Button CstAddBtn;
    @javafx.fxml.FXML
    protected TableColumn<Customer, String> CstPostalCodeCol;
    @javafx.fxml.FXML
    protected TableColumn<Customer, String> CstAddressCol;
    @javafx.fxml.FXML
    protected ListView contact_ID_SF;
    @javafx.fxml.FXML
    protected MenuItem CntSchedule_MenuItem;
    @javafx.fxml.FXML
    protected TextField title_SF;
    @javafx.fxml.FXML
    protected TextField address_SF;
    @javafx.fxml.FXML
    private TableColumn<Customer, String> CstPhoneCol;
    @javafx.fxml.FXML
    protected Label CurrentUserIdLbl;
    @javafx.fxml.FXML
    protected Button ApptUpdateBtn;
    @javafx.fxml.FXML
    protected ComboBox StartTime_SF;
    @javafx.fxml.FXML
    protected Button CstModifyBtn;
    @javafx.fxml.FXML
    protected ComboBox division_ID_SF;
    @javafx.fxml.FXML
    protected ComboBox EndTime_SF;
    @javafx.fxml.FXML
    protected MenuButton ReportMenuBtn;
    @javafx.fxml.FXML
    protected TextField description_SF;
    @javafx.fxml.FXML
    protected TextField type_SF;
    @javafx.fxml.FXML
    protected TableView<Customer> CstTblView;
    @javafx.fxml.FXML
    protected TableColumn<Customer, Integer> CstIdCol;
    @javafx.fxml.FXML
    protected MenuItem THIRD_MenuItem;
    @javafx.fxml.FXML
    protected DatePicker ApptStart_SF;
    @javafx.fxml.FXML
    protected TableColumn<Customer, String> CstNameCol;
    @javafx.fxml.FXML
    protected Button ApptAddBtn;
    @javafx.fxml.FXML
    protected ComboBox country_ID_SF;
    @javafx.fxml.FXML
    protected ListView user_ID_SF;
    @javafx.fxml.FXML
    protected TextField appointment_ID_SF;
    @javafx.fxml.FXML
    protected TableColumn<Customer, Integer> CstDivisionIdCol;
    @javafx.fxml.FXML
    protected TextField customer_Name_SF;
    @javafx.fxml.FXML
    protected TextField postal_Code_SF;
    @javafx.fxml.FXML
    protected TextField location_SF;
    @javafx.fxml.FXML
    protected TextField phone_SF;
    @javafx.fxml.FXML
    protected Hyperlink LogOutBtn;
    @javafx.fxml.FXML
    protected MenuItem TotalCstByMonth_MenuItem;
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
    private TextField customer_ID_cstSF;
    @javafx.fxml.FXML
    private TableColumn<FirstLevelDivision, Integer> CstCountryIdCol;
    @javafx.fxml.FXML
    private ListView customer_ID_apptSF;

    protected void setCurrentUserId(int currentUserId) {
        CurrentUserIdLbl.setText(String.valueOf(currentUserId));
    }

    /**
     * Creates an Alert instance.<BR>RUNTIME ERROR:<BR>java.lang.NullPointerException<BR>Caused by: java.lang.NullPointerException<BR>at controller.MainFormController.buildAlert(MainFormController.java:254)<BR>I orginally planned to use the switch case statements to set the AlertType using "alert.setAlertType(alertType)."<BR>I realized I had not initialized the alert with the "alert = new (alertType)"<BR>Adding the Alert constructor fixed the issue.<BR>I removed the setAlert function anyway since the Alert constructor was being passed the AlertType directly at that point.
     * @param alertType Alert Type to create.
     * @param titleTxt Text for Alert title.
     * @param msgCtx Text for Alert context.
     * @return The Alert instance.
     */
    protected Alert buildAlert(Alert.AlertType alertType, String titleTxt, String msgCtx){
        String header = "";

        try{
            alert = new Alert(alertType);
            //alert.setAlertType(alertType);
            String alertEnum = alertType.toString();

            switch(alertType){
                //switch(alertEnum){
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
     * Displays the Customer ObservableList data in the TableView.
     */
    public void displayCstWithCoInfo() throws SQLException {
        ObservableList<Customer> cstWithCoInfo = FXCollections.observableArrayList();
        CustomerDAO cstdao = new CustomerDaoImpl();

        // Set the cell to the property value for the specified column name in string
        CstIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        CstNameCol.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        CstAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        CstPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postal_Code"));
        CstPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        CstCountryIdCol.setCellValueFactory(new PropertyValueFactory<>("country_ID"));
        CstDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("division_ID"));


/*
        try {
            int DivCo = divCBItems.extract(Integer.parseInt(String.valueOf(new PropertyValueFactory<Customer, String>("division_ID")))).getCountry_ID();
            System.out.println("see divco " + DivCo);
            //CstCountryIdCol.setCellValueFactory(new PropertyValueFactory<FirstLevelDivision, Integer>("country_ID".DivCo);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
*/
        cstWithCoInfo.addAll(cstdao.customerWithCoInfo());
        CstTblView.setItems(cstWithCoInfo);
    }

    /**
     * Displays the Customer ObservableList data in the TableView.
     */
    public void displayCstTblViewData() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        CustomerDAO cstdao = new CustomerDaoImpl();

        // Set the cell to the property value for the specified column name in string
        CstIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        CstNameCol.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        CstAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        CstPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postal_Code"));
        CstPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        CstCountryIdCol.setCellValueFactory(new PropertyValueFactory<>("country_ID"));
        CstDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("division_ID"));

        allCustomers.addAll(cstdao.extractAll());
        CstTblView.setItems(allCustomers);
    }

    /**
     * Display the Appt ObservableList data in the TableView.
     */
    public void displayApptTblViewData() throws SQLException {
        ObservableList<Appointment> allApptAppointments = FXCollections.observableArrayList();
        AppointmentDAO apptdao = new AppointmentDaoImpl();

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

        allApptAppointments.addAll(apptdao.extractAll());

        ApptTblView.setItems(allApptAppointments);
    }

    /**
     * Loads the ApptAddUpdateFormCtrl and calls its method to send the selected row data in the Appointment table view to the appt-add-update-form view.
     * @param actionEvent Update form button, ApptUpdateBtn, clicked.
     * @throws IOException java.io.IOException - captures name exception: NullPointerException.
     * <BR>Present alert error dialog when no selection made.
     */
    @javafx.fxml.FXML
    private void onActionApptNew(ActionEvent actionEvent) throws IOException {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("New Appointment Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("/com/thecodebarista/view/appt-add-update-form.fxml")));
            scene = loader.load();

            // Cast window to stage
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            static_AddUpdateLabel.setText("New Appointment");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
     //   catch (SQLException e) {
    //        e.printStackTrace();
    //    }
    }

    /**
     * Loads the ApptAddUpdateFormCtrl and calls its method to send the selected row data in the Appointment table view to the appt-add-update-form view.
     * @param actionEvent Modify form button, ApptUpdateBtn, clicked.
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
            selectedAppt = ApptTblView.getSelectionModel().getSelectedItem();
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
     * Loads the CstAddUpdateFormCtrl and calls its method to send the selected row data in the Customer table view to the cst-add-update-form view.
     * @param actionEvent Update form button, CstUpdateBtn, clicked.
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
        //   catch (SQLException e) {
        //        e.printStackTrace();
        //    }
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
     * Logout of application.<>BR</>Returns to Login Screen
     *
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            displayApptTblViewData();
            //displayCstTblViewData();
            displayCstWithCoInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
