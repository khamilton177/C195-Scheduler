package com.thecodebarista.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class MainMenuCtrl extends LoginFormCtrl implements Initializable {
    public Label ZoneIdLbl;

    @javafx.fxml.FXML
    private TabPane ApptTabPane;
    @javafx.fxml.FXML
    private Tab RptTab;
    @javafx.fxml.FXML
    private Tab ApptTab;
    @javafx.fxml.FXML
    private Hyperlink LogOutBtn;
    @javafx.fxml.FXML
    private Tab CalTab;
    @javafx.fxml.FXML
    private Tab RptTab1;
    @javafx.fxml.FXML
    private Tab CstTab;
    @javafx.fxml.FXML
    private Label CurrentUserIdLbl;
    @javafx.fxml.FXML
    private TableColumn CstPostalCodeCol;
    @javafx.fxml.FXML
    private TableColumn CstAddressCol;
    @javafx.fxml.FXML
    private TableColumn ApptTitleCol;
    @javafx.fxml.FXML
    private TableColumn ApptIdCol;
    @javafx.fxml.FXML
    private TableColumn CstPhoneCol;
    @javafx.fxml.FXML
    private TableColumn ApptStartCol;
    @javafx.fxml.FXML
    private TableColumn ApptUserIDCol;
    @javafx.fxml.FXML
    private TableColumn ApptTypeCol;
    @javafx.fxml.FXML
    private TableView CstTblView;
    @javafx.fxml.FXML
    private TableColumn CstIdCol;
    @javafx.fxml.FXML
    private TableColumn CstNameCol;
    @javafx.fxml.FXML
    private TableColumn ApptCstIDCol;
    @javafx.fxml.FXML
    private TableColumn ApptCntIDCol;
    @javafx.fxml.FXML
    private TableColumn CstDivisionIdCol;
    @javafx.fxml.FXML
    private TableColumn ApptDescCol;
    @javafx.fxml.FXML
    private TableColumn ApptLocCol;
    @javafx.fxml.FXML
    private TableView ApptTblView;
    @javafx.fxml.FXML
    private TableColumn ApptEndCol;

    protected void setCurrentUserId(int currentUserId) {
        CurrentUserIdLbl.setText(String.valueOf(currentUserId));
    }

    /**
     * Logout of application.<>BR</>Returns to Login Screen
     *
     */
    @javafx.fxml.FXML
    private void onActionLogout(ActionEvent actionEvent) {

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/login-form.fxml"));
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

 //       try {
 //           displayTblViewData();
//            displayCstTblViewData();
 //       } catch (SQLException e) {
 ///           e.printStackTrace();
 //       }
    }
 //   }

}
