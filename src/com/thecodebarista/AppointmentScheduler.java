package com.thecodebarista;

import com.thecodebarista.dao.*;
import com.thecodebarista.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.SQLException;
import java.util.Locale; //DO NOT DELETE

public class AppointmentScheduler extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // TODO: 2/19/2023 Comment out. Used for test Locale ONLY! 
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
