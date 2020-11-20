package javafx;

import database.DbStatements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleGroup;


public class MenuController {

    static MainController mainController;
    ObservableList<String> languList = FXCollections.observableArrayList(App.getString("eng"), App.getString("pl"));

    @FXML
    private ChoiceBox languChange;

    @FXML
    private ToggleGroup toggleMenu;

    @FXML
    private void initialize() {
        languChange.setValue(App.getString("eng"));
        languChange.setItems(languList);
    }

    @FXML
    private void open_profil() {
        mainController.set_center("profile.fxml");
        check_toggle();
    }

    @FXML
    private void change_language() {
        if(languChange.getValue() == App.getString("pl")) {
            System.out.println("pl");
        } else {
            System.out.println("eng");
        }
    }

    public void setMainController(MainController mController){
        mainController = mController;
    }

    public void check_toggle() {
        if(toggleMenu.getSelectedToggle()==null) {
            mainController.set_center("login_pane.fxml");
            //tutaj trzeba dać metode która sprawdzi do którego panelu cofnąć
            //bo nie zawsze będziesz cofać do login_pane
        }
    }

    @FXML
    private void log_out() {
        DbStatements.type = -1;
        mainController.set_center("login_pane.fxml");
    }

//    @FXML
//    private void switchToPrimary() throws IOException {
//        App.setRoot("login_pane");
//    }
}