package javafx;

import database.DbStatements;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GuestController {

    @FXML
    private void logInAction(){
        App.setRoot("login_window");
    }
    @FXML
    private void guestPharmacyAction(){
        App.setRoot("pharmacy_guest");
    }

}
