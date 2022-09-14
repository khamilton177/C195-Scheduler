package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CstAddModifyFormCtrl extends SchedulerCtrl implements Initializable {
    @javafx.fxml.FXML
    protected TableView CstTblView;
    @javafx.fxml.FXML
    private TableColumn CstIdCol;
    @javafx.fxml.FXML
    private TableColumn CstNameCol;
    @javafx.fxml.FXML
    private TableColumn CstPhoneCol;
    @javafx.fxml.FXML
    private TableColumn CstDivisionIdCol;
    @javafx.fxml.FXML
    private TableColumn CstPostalCodeCol;
    @javafx.fxml.FXML
    private TableColumn CstAddressCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
