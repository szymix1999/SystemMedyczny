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
import javafx.scene.layout.VBox;
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
    private VBox vboxReg;


    @FXML
    private void log_in() {
        Connection c = DbConnector.connect();
        System.out.println("Login: " + userLoginField.getText() + " | Password: " + userPassField.getText());
        try {
            if((userLoginField.getText() != null && !userLoginField.getText().isEmpty() &&
                    userPassField.getText() != null && !userPassField.getText().isEmpty()) &&
                    (DbStatements.checkUser(c, userLoginField.getText(), userPassField.getText()) != -1)) {
                choose_pane();
            } else {
                notification.setFill(Color.FIREBRICK);
                notification.setText(App.getString("badLoginOrPass"));
            }
            c.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
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
                App.setRoot("administration_pane");
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
    private void reg_on() {     //pokazuje okno rejestracji
        notification.setFill(Color.BLUE);
        notification.setText(App.getString("signIn"));
        vboxReg.setVisible(true);
    }

    @FXML
    private void send_reg() {   //metoda po kliknieciu Send w register
        notification.setText("");   //tutaj można wpisać co wyskoczy po wysłaniu
        vboxReg.setVisible(false);
    }

    @FXML
    private void returnAction(){
        App.setRoot("guest_pane");
    }
}
