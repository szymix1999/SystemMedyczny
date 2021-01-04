package javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GuestController {

    @FXML
    private void logInAction(){
        App.setRoot("login_window");
    }

}
