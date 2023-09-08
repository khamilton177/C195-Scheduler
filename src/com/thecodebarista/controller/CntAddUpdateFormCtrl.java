package com.thecodebarista.controller;

import com.thecodebarista.dao.ContactDAO;
import com.thecodebarista.dao.ContactDaoImpl;
import com.thecodebarista.model.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CntAddUpdateFormCtrl extends MainMenuCtrl implements Initializable {

    ContactDAO cntDao = new ContactDaoImpl();

    @javafx.fxml.FXML
    private Label AddUpdateCntLabel;

    @javafx.fxml.FXML
    private Label CntAlertBoxLbl;

    @javafx.fxml.FXML
    private TextField cnt_ID_TxtFld;

    @javafx.fxml.FXML
    private Button CntSaveBtn;

    @javafx.fxml.FXML
    private Button CntCancelBtn;

    /**
     * Method populates the Update Form with the User data from database.
     * @param selectedCnt
     * @throws SQLException
     */
    protected void sendCntModifyData(Contact selectedCnt) throws SQLException {
        selectedCnt = cntDao.extract(selectedCnt.getContact_ID());
        System.out.println("1 Setting fields");

        cnt_ID_TxtFld.setText(String.valueOf(selectedCnt.getContact_ID()));
        cnt_Name_TxtFld.setText(String.valueOf(selectedCnt.getContact_Name()));
        email_TxtFld.setText(String.valueOf(selectedCnt.getEmail()));

        if ((sessionUserAccess > 0) && (static_AddUpdateLabel.getText().equals("Update Contact"))) {
            Boolean isActive = true;

            if (selectedCnt.getActive() == 0) {
                isActive = false;
            }
            cnt_Active_ChkBox.setSelected(isActive);
        }
    }

    /**
     * Method enable Admin Only updatable fields on Update Contacts form.
     */
    protected void showAdminOnlyFlds() {
        if (sessionUserAccess > 0) {
            cnt_Active_ChkBox.setDisable(false);
        }
    }

    /**
     * Method saves form data for New and Updated Contacts.
     * @throws SQLException
     */
    protected void saveCntData() throws SQLException {
        int result = 0;

        String contact_Name = cnt_Name_TxtFld.getText();
        String email = email_TxtFld.getText();

        ContactDAO cntDAOSave = new ContactDaoImpl();
        switch (static_AddUpdateLabel.getText()) {
            case "Add Contact":
                Contact cntIns = new Contact(0, contact_Name, email);
                result = cntDAOSave.insert(cntIns);
                break;
            case "Update Contact":
                int contact_ID = Integer.parseInt(cnt_ID_TxtFld.getText());
                Contact cntUpd = new Contact(contact_ID, contact_Name, email);
                if (sessionUserAccess > 0) {
                    int active = cnt_Active_ChkBox.isSelected() ? 1 : 0;
                    cntUpd = new Contact(contact_ID, contact_Name, email, active);
                }

                result = cntDAOSave.update(cntUpd);
                break;
        }
        System.out.println(result);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        static_AddUpdateLabel = AddUpdateCntLabel;
    }

    @javafx.fxml.FXML
    public void onActionSaveCnt(ActionEvent actionEvent) {
        String btnTxt = ((Button)actionEvent.getSource()).getId().replace("Btn", "");
        System.out.println("Button Clicked: " + btnTxt);

        try{
            boolean validForm = validateFormFields(btnTxt);

            if (validForm) {
                saveCntData();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
                scene = loader.load();
                MainMenuCtrl formController = loader.getController();
                if (sessionUserAccess > 0) {
                    formController.setMainTabPane("CntAdminTab");
                }
                else{
                    formController.setCurrentUserViewAccess(sessionUserAccess);
                }
                stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                // scene = FXMLLoader.load(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
                stage.setTitle("C195-Global Consulting Scheduler");
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(Exception e) {
            System.out.println("CntSave exception: " +e.getMessage());
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
                formController.setMainTabPane("CntAdminTab");
            }
            else{
                formController.setCurrentUserViewAccess(sessionUserAccess);
            }
            // Cast window to stage
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            //scene = FXMLLoader.load(getClass().getResource("/com/thecodebarista/view/main-menu.fxml"));
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
