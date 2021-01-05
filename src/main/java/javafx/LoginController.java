package javafx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import database.DbConnector;
import database.DbStatements;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    private TextField userLoginField;

    @FXML
    private PasswordField userPassField;

    @FXML
    private Text notification;


    @FXML
    private void log_in() throws IOException {
        //Connection c = DbConnector.connect();
        //System.out.println("Login: " + userLoginField.getText() + " | Password: " + userPassField.getText());
        //try {
            //if((userLoginField.getText() != null && !userLoginField.getText().isEmpty() &&
            //        userPassField.getText() != null && !userPassField.getText().isEmpty()) &&
            //        (DbStatements.checkUser(c, userLoginField.getText(), userPassField.getText()) != -1)) {
        DbStatements.type=1;    //usun pozniej
        DbStatements.id=1;  //usun pozniej
                choose_pane();
            //} else {
            //    notification.setFill(Color.FIREBRICK);
            //    notification.setText(App.getString("badLoginOrPass"));
            //}
        //} catch (SQLException ex){
        //    ex.printStackTrace();
        //}
    }

    private void choose_pane() {
        switch(DbStatements.type){
            case 0: //pacjent
                App.setRoot("patient_pane");
                break;
            case 1: //lekarz
                App.setRoot("patient_pane");
                break;
            case 2: //aptekarz
                App.setRoot("apothecary_pane");
                break;
            case 3: //ksiegowy
                App.setRoot("patient_pane");
                break;
            case 4: //admin
                App.setRoot("admin_pane");
                break;
            case 5: //kierownik
                App.setRoot("patient_pane");
                break;
            case 6: //recepcja
                App.setRoot("patient_pane");
                break;
        }
    }

    @FXML
    private void reg_on() {
        notification.setFill(Color.BLUE);
        notification.setText(App.getString("signIn"));
    }

    @FXML
    private void returnAction(){
        App.setRoot("guest_pane");
    }
}
