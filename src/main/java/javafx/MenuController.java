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
        //to pewnie wyleci i zamiast tego będzie choice box
        mainController.set_center("language.fxml");
        check_toggle();
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

//    @FXML
//    private void switchToPrimary() throws IOException {
//        App.setRoot("login_pane");
//    }
}