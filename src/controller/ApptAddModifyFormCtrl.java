package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ApptAddModifyFormCtrl extends SchedulerCtrl implements Initializable {

    @javafx.fxml.FXML
    private TableView ApptTblView;
    @javafx.fxml.FXML
    private TableColumn ApptIdCol;
    @javafx.fxml.FXML
    private TableColumn ApptTitleCol;
    @javafx.fxml.FXML
    private TableColumn ApptDescCol;
    @javafx.fxml.FXML
    private TableColumn ApptLocCol;
    @javafx.fxml.FXML
    private TableColumn ApptTypeCol;
    @javafx.fxml.FXML
    private TableColumn ApptStartCol;
    @javafx.fxml.FXML
    private TableColumn ApptEndCol;
    @javafx.fxml.FXML
    private TableColumn ApptCstIDCol;
    @javafx.fxml.FXML
    private TableColumn ApptUserIDCol;
    @javafx.fxml.FXML
    private TableColumn ApptCntIDCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
