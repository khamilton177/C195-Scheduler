package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class MainMenuCtrl extends LoginFormCtrl implements Initializable {

    // Class members
//    public Label CurrentUserIdLbl;

    // Scene Builder Form Items
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
            loader.setLocation(getClass().getResource("view/login-form.fxml"));
            scene = loader.load();

            // Cast window to stage
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
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

    }

}
