package com.thecodebarista;


import com.thecodebarista.dao.CustomerDAO;
import com.thecodebarista.dao.CustomerDaoImpl;
import com.thecodebarista.model.Appointment;
import com.thecodebarista.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalTime;


public final class SchedulerUtilities {
    /**
     * The alert built by the onAction event triggered.
     */
    Alert alert;
    LocalTime ESTOfficeHrsStart = LocalTime.of(8, 0);
    LocalTime ESTOfficeHrsEnd = LocalTime.of(22, 0);




    public static void recastScene(ActionEvent event, String targetFXML, String title, String controllerName) {
 //       Parent root = null;
/*
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(targetFXML));
            scene = loader.load();
        }
        catch(IOException e) {

        }
*/

    }

    /**
     * Creates an Alert instance.
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

}





