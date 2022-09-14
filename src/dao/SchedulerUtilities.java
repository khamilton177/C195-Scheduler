package dao;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public final class SchedulerUtilities {

    public static void recastScene(ActionEvent event, String targetFXML, String title, String controllerName) {
        Parent root = null;
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
     * Displays the Csts ObservableList data in the TableView.
     */

    /*
    public void displayCstTblViewData(){
        CstTblView.setItems(Inventory.extractAll());

        // Set the cell to the property value for the specified column name in string
        CstIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        CstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        CstPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        CstStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /**
     * Display the Apptucts ObservableList data in the TableView.
     */
    /*
    public void displayApptTblViewData(){
        ApptTblView.setItems(Inventory.getAllApptucts());

        ApptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ApptNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ApptPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ApptStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
*/

}