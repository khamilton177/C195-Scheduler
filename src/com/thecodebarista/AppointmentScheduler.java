package com.thecodebarista;

import com.thecodebarista.dao.*;
import com.thecodebarista.controller.*;
import com.thecodebarista.model.Appointment;
import com.thecodebarista.model.Country;
import com.thecodebarista.model.Customer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AppointmentScheduler extends Application {
    /**
     * Top-level container of the JavaFX application.
     */
    Stage stage;

    /**
     * The loaded fxml file used to present a JavaFX application screen.
     */
    Parent scene;

    /**
     * Label to retain current user's ZoneID information on forms.
     */
    public static Label static_ZoneIdLbl;
    public static Logger actLog;

    @Override
    public void start(Stage stage) throws Exception {
        // Locale.setDefault(new Locale("fr")); // USe this for testng language conversion only
        ResourceBundle rb = ResourceBundle.getBundle("Lang");
        Logger actLog = Logger.getLogger("/login_activity.txt");

        try{
            Parent root = FXMLLoader.load(getClass().getResource("view/login-form.fxml"));
//            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/login-form.fxml")));
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
        Logger actLog = Logger.getLogger("login_activity.txt");
        DBConnection.establishConnection();
        LoginFormCtrl.loginLogger();

        launch(args);
        DBConnection.closeConnection();
    }

}
