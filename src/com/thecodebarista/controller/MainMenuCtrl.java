package com.thecodebarista.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import com.thecodebarista.dao.*;
import com.thecodebarista.model.Appointment;
import com.thecodebarista.model.Customer;
import com.thecodebarista.model.FirstLevelDivision;
import com.thecodebarista.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainMenuCtrl extends LoginFormCtrl implements Initializable {
    //private final int sessionUserId = currentUser.getUser_ID();
    public static Label static_AddUpdateLabel;

    public Appointment selectedAppt;
    Customer selectedCst;
    Alert alert;
    String btnTxt;
    Optional<ButtonType> confirm;
    Tab currentTab;

    FirstLevelDivisionDAOImpl divCBItems = new FirstLevelDivisionDAOImpl();

    @javafx.fxml.FXML
    protected Parent includeApptMo;
    @javafx.fxml.FXML
    protected Parent includeApptWk;
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
    protected MenuItem CntSchedule_MenuItem;
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
    protected MenuItem THIRD_MenuItem;
    @javafx.fxml.FXML
    protected TableColumn<Customer, String> CstNameCol;
    @javafx.fxml.FXML
    protected Button ApptAddBtn;
    @javafx.fxml.FXML
    protected TableColumn<Customer, Integer> CstDivisionIdCol;
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
    private TableColumn<FirstLevelDivision, Integer> CstCountryIdCol;
    @javafx.fxml.FXML
    private Label CurrentUserNameLbl;
    @javafx.fxml.FXML
    private TabPane ApptsTab;
    @javafx.fxml.FXML
    private Tab AppTab;
    @javafx.fxml.FXML
    private Tab AppWkTab;
    @javafx.fxml.FXML
    private Tab AppMoTab;

    protected void loginAppointAlert(int currentUserId, String currentUserName) throws SQLException, NumberFormatException {
        System.out.println("Doing loginAppt Alert #1");
        AppointmentDAO apptdao = new AppointmentDaoImpl();
        UnmanagedDAO userDAOGet = new UserDaoImpl();

        try{
            User currentUser = (User) userDAOGet.extract(currentUserId);
            String userName = currentUser.getUser_Name();
            int userId = currentUser.getUser_ID();

            // User currentUser = (User) userDAOGet.extract(currentUserId);
            // String userName = this.currentUserName;
            // int userId = this.currentUserId;
            System.out.println("Doing loginAppt Alert #2 - userid is: " + userId);

            int userAppts = apptdao.getCustomerApptsByFK("User_ID", userId).size();
            System.out.println("Doing loginAppt Alert #3 - count: " + userAppts);

            if(userAppts > 0) {
                System.out.println("Doing loginAppt Alert #4");

                String msgCtx = "Hi " +
                        userName +
                        ". Relax! You don't have any appoints within the next 15 minutes.";
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
        currentUserId = setCurrentUserId;
        //return currentUserId;
    }

    protected void setCurrentUserNameInfo(String setCurrentUserName) {
        CurrentUserNameLbl.setText(setCurrentUserName);
        currentUserName = setCurrentUserName;
       // return currentUserName;
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
        //Timestamp rawStart = new PropertyValueFactory<>("Start").getProperty().t;
        System.out.println("WHAT'S IT LOOK LIKE RAW: " + new PropertyValueFactory<>("Start").getProperty().toString());
        start_Col.setCellValueFactory(new PropertyValueFactory<>("Start"));
        end_Col.setCellValueFactory(new PropertyValueFactory<>("End"));
        customer_ID_Col.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        user_ID_Col.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        contact_ID_Col.setCellValueFactory(new PropertyValueFactory<>("contact_ID"));

        allApptAppointments.addAll(apptdao.extractAll());
        ApptTblView.setItems(allApptAppointments);
    }

    protected Boolean delCstRow(Customer selectedCst) throws SQLException {
        boolean deleted = false;
        int result = -1;

        AppointmentDAO cstApptDaoDel = new AppointmentDaoImpl();
        result = cstApptDaoDel.deleteAllCstAppts(selectedCst.getCustomer_ID());

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
     * @param selectedSchedItem Selected object from either table view.
     * @param btnTxt Modified Event button text passed as Alert title.
     * @param msgCtx Event errorMsg passed as Alert context.
     */
    public void confirmDelete(Object selectedSchedItem, String btnTxt, String msgCtx){
        alert = buildAlert(Alert.AlertType.CONFIRMATION, btnTxt, msgCtx);
        confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK){
            if (selectedSchedItem instanceof Customer){
                try {
                    if (delCstRow(selectedCst)){
                        displayApptTblViewData();
                        displayCstWithCoInfo();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }




            }
        }
    }

    /**
     * Loads the ApptAddUpdateFormCtrl to create appointments in the appt-add-update-form view.
     * @param actionEvent New form button, ApptAddBtn, clicked.
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
            currentTab = ApptsTab.getSelectionModel().getSelectedItem();
            System.out.println("Current Tab: " + currentTab.getId());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/thecodebarista/view/appt-add-update-form.fxml"));
            scene = loader.load();
            ApptAddUpdateFormCtrl modelCtrl = loader.getController();
            switch (currentTab.getId()) {
                case "AppMoTab":
                    TableView.TableViewSelectionModel<Appointment> selectorMo = includeApptMoController.ApptTblViewMonthly.getSelectionModel();
                    selectedAppt = selectorMo.getSelectedItem();
                    break;
                case "AppWkTab":
                    TableView.TableViewSelectionModel<Appointment> selectorWk = includeApptWkController.ApptTblViewWeekly.getSelectionModel();
                    selectedAppt = selectorWk.getSelectedItem();
                    break;
                default:
                    selectedAppt = ApptTblView.getSelectionModel().getSelectedItem();
                    break;
            }
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

    @javafx.fxml.FXML
    public void onTabSelectLoad(Event event) {
        try {
            currentTab = ApptsTab.getSelectionModel().getSelectedItem();
            System.out.println("Current Tab Inquiry for tabSelect: " + currentTab.getId());
/*

            FXMLLoader loader = new FXMLLoader();
            switch (currentTab.getId()){
                case "AppMoTab":
                    getTableView().
                    TableSelectionModel<Appointment> selector = ApptTblViewMonthly.getTableView().getSelectionModel();
                    selectedAppt = selector.getSelectedItem();

                    loader.setLocation(getClass().getResource("/com/thecodebarista//view/ApptTblMonthlyView.fxml"));
                    scene = loader.load();
                    ApptTableMonthlyCtrl modelCtrl = loader.getController();
                    modelCtrl.getTableView();
                    break;
                case "AppWkTab":
//                    loader.setLocation(getClass().getResource("/com/thecodebarista//view/ApptTblWeeklyView.fxml"));
//                    scene = loader.load();
//                    ApptTableWeeklyCtrl modelCtrl = loader.getController();
//                    modelCtrl.displayApptTblViewWeekly();
                    break;
                default:
//                    loader.setLocation(getClass().getResource("/com/thecodebarista//view/main-menu.fxml"));


            }
 //           loader.setLocation(getClass().getResource("/com/thecodebarista//view/ApptTblMonthlyView.fxml"));
  //          scene = loader.load();
  //          ApptTableMonthlyCtrl modelCtrl = loader.getController();
//              modelCtrl.displayApptTblViewMonthly();
*/

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    private void convertBusinessHrs() {

    }

    private void appointmentSeed() throws SQLException {
        int result;

        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("Raw Now- : " + ldt);
        Timestamp tsStart = Timestamp.valueOf(ldt.withSecond(0).withNano(0).plusMinutes((65-ldt.getMinute())%5));
        Timestamp tsEnd = Timestamp.valueOf(ldt.withSecond(0).withNano(0).plusMinutes((65-ldt.getMinute())%5).plusMinutes(45));

        //LocalDateTime ldt = ts.toLocalDateTime();

        try{
            AppointmentDAO apptdaoI = new AppointmentDaoImpl();
            // ObservableList<Appointment> minCstId = apptdaoI.adhocQuery("SELECT MIN(Customer_ID) FROM Appointment");
            // minCstId.stream().findFirst().get().getCustomer_ID();
            Appointment appointment2 = new Appointment(0,"Java help", "Seed data", "remote", "Mentor", tsStart, tsEnd, 2, 2, 3);
            result = apptdaoI.insert(appointment2);
            System.out.println(result);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            //test seed data
            //appointmentSeed();

            System.out.println("On init newLogin: " + isNewLogin);
            displayApptTblViewData();

            //displayCstTblViewData();
            displayCstWithCoInfo();

            setCurrentUserid(currentUserId);

            if (isNewLogin) {
                System.out.println("Made it to newLogin Test");
                //currentUserId = Integer.parseInt(CurrentUserIdLbl.getText());
                System.out.println("Made it to newLogin Test - id: " + sessionUserId);
                //currentUserName = CurrentUserNameLbl.getText();
                System.out.println("Made it to newLogin Test - name: " + sessionUserId);

                //loginAppointAlert(currentUserId, currentUserName);
                //loginAppointAlert(currentUserId, currentUserName);

            }
            isNewLogin = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
