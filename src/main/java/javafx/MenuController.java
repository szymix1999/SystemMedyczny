package javafx;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class MenuController {

    static MainController mainController;

    @FXML
    private ToggleGroup toggleMenu;

    @FXML
    public void open_profil() {
        mainController.set_center("profile.fxml");
        check_toggle();
    }

    @FXML
    public void change_language() {
        mainController.set_center("language.fxml");
        check_toggle();
    }

    public void setMainController(MainController mController){
        mainController = mController;
    }

    public void check_toggle() {
        if(toggleMenu.getSelectedToggle()==null) {
            mainController.set_center("login_pane.fxml");
        }
    }

//    @FXML
//    private void switchToPrimary() throws IOException {
//        App.setRoot("login_pane");
//    }
}