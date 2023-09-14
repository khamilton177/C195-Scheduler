package com.thecodebarista.controller;

import com.thecodebarista.dao.UserDAO;
import com.thecodebarista.dao.UserDaoImpl;
import com.thecodebarista.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * User controller for Add/Update form.
 */
public class UserAddUpdateFormCtrl extends MainMenuCtrl implements Initializable {

    /**
     * Label holds the text for New/Update button selected on the Appointments New/Update form.
     */
    @javafx.fxml.FXML
    Label AddUpdateUserLabel;

    @javafx.fxml.FXML
    private Label UserAlertBoxLbl;

    @javafx.fxml.FXML
    private TextField user_ID_TxtFld;

    @javafx.fxml.FXML
    private Button UserSaveBtn;

    @javafx.fxml.FXML
    private Button UserCancelBtn;

    @javafx.fxml.FXML
    private ButtonBar AddUpdBtnBar;

    @javafx.fxml.FXML
    private ButtonBar PwdBtnBar;

    @javafx.fxml.FXML
    private Button ChangePwdBtn;

    /**
     * Method populates the Update Form with the User data from database.
     * @param selectedUser
     * @throws SQLException
     */
    protected void sendUserModifyData(User selectedUser) throws SQLException {
        user_ID_TxtFld.setText(String.valueOf(selectedUser.getUser_ID()));
        user_Name_TxtFld.setText(String.valueOf(selectedUser.getUser_Name()));
        pwd_PwdFld.setText(String.valueOf(selectedUser.getPassword()));

        if (!static_AddUpdateLabel.getText().equals("Add User")) {
            Boolean isAdmin = false;
            Boolean isActive = true;

            if (selectedUser.getIs_Admin() == 1) {
                isAdmin = true;
            }
            admin_ChkBox.setSelected(isAdmin);

            if (selectedUser.getActive() == 0) {
                isActive = false;
            }
            user_Active_ChkBox.setSelected(isActive);
        }
    }

    /**
     * Method enable Admin Only updatable fields on Update User form.
     */
    protected void showAdminOnlyFlds() {
        if (sessionUserAccess > 0) {
            admin_ChkBox.setDisable(false);
            user_Active_ChkBox.setDisable(false);
        }
        else {
            user_Name_TxtFld.setDisable(true);
        }
    }

    /**
     * Method saves form data for New and Updated Contacts.
     * @throws SQLException
     */
    protected void saveUserData() throws SQLException {
        int result = 0;
        int user_ID;

        String user_Name = user_Name_TxtFld.getText();
        String password = pwd_PwdFld.getText();
        int is_Admin = admin_ChkBox.isSelected()? 1:0;
        int active = user_Active_ChkBox.isSelected()? 1:0;

        UserDAO userDAOSave = new UserDaoImpl();
        switch (static_AddUpdateLabel.getText()) {
            case "Add User":
                User userIns = new User(0, user_Name, password);
                result = userDAOSave.insert(userIns);
                break;
            case "User Info":
                user_ID = Integer.parseInt(user_ID_TxtFld.getText());
                User userUpd = new User(user_ID, user_Name, password, is_Admin, active);
                result = userDAOSave.update(userUpd);
                break;
            case "Update User":
                user_ID = Integer.parseInt(user_ID_TxtFld.getText());
                if (sessionUserAccess > 0) {
                    userUpd = new User(user_ID, user_Name, password, is_Admin, active);
                    result = userDAOSave.update(userUpd);
                }

        }
        System.out.println(result);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        static_AddUpdateLabel = AddUpdateUserLabel;
    }


    @javafx.fxml.FXML
    public void OnActionUserEdit(ActionEvent actionEvent) {

    }

    @javafx.fxml.FXML
    public void onActionSaveUser(ActionEvent actionEvent) {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + btnTxt);

        try{
            boolean validForm = validateFormFields(btnTxt);

            if (validForm) {
                saveUserData();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
                scene = loader.load();
                MainMenuCtrl formController = loader.getController();
                if (sessionUserAccess > 0) {
                    formController.setMainTabPane("UserAdminTab");
                }
                else{
                    formController.setCurrentUserViewAccess(sessionUserAccess);
                }
                stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("C195-Global Consulting Scheduler");
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(Exception e) {
            System.out.println("UserSave exception: " +e.getMessage());
            System.out.println(e.getCause());
        }
    }

    @javafx.fxml.FXML
    public void OnActionCancel(ActionEvent actionEvent) {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + ((Button)actionEvent.getSource()).getId());

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
            scene = loader.load();
            MainMenuCtrl formController = loader.getController();
            if (sessionUserAccess > 0) {
                formController.setMainTabPane("UserAdminTab");
            }
            else{
                formController.setCurrentUserViewAccess(sessionUserAccess);
            }

            // Cast window to stage
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("C195-Global Consulting Scheduler");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }
}
