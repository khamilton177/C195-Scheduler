package com.thecodebarista;

import com.thecodebarista.dao.*;
import com.thecodebarista.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Optional;

public class AppointmentScheduler extends Application {


    public final class SchedulerUtils {
        /**
         * The alert built by the onAction event triggered.
         */
        //Alert alert;

        /**
         *
         */
        LocalTime ESTOfficeHrsStart = LocalTime.of(8, 0);

        /**
         *
         */
        LocalTime ESTOfficeHrsEnd = LocalTime.of(22, 0);

        public void launchConfirmAlert(String btnTxt, StringBuilder validateErrMsg) {
            Alert alert;
            Optional<ButtonType> confirm;
  //          alert = buildAlert(Alert.AlertType.ERROR, btnTxt, validateErrMsg.toString());
  //          confirm = alert.showAndWait();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Locale.setDefault(new Locale("fr")); // Use this for testng language conversion only

        try{
            Parent root = FXMLLoader.load(getClass().getResource("view/login-form.fxml"));
            stage.setTitle("C195-Scheduler");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e) {
            System.out.println("from here 3" + e.getMessage());
            System.out.println("from here 4" + e.getCause());
        }
    }

    public static void main(String[] args) throws SQLException {
        DBConnection.establishConnection();
        LoginFormCtrl.loginLogger();

        launch(args);
        DBConnection.closeConnection();
    }

}
