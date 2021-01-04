package javafx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import database.DbConnector;
import database.DbStatements;
import javafx.fxml.FXML;
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
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void choose_pane() {
        switch(DbStatements.type){
            case 0: //pacjent
                MenuController.mainController.set_center("patient_pane.fxml");
                break;
            case 1: //lekarz
                MenuController.mainController.set_center("admin_pane.fxml");
                break;
            case 2: //aptekarz
                MenuController.mainController.set_center("admin_pane.fxml");
                break;
            //case 3: //gosc
            //    MenuController.mainController.set_center("admin_pane.fxml");
            //    break;
            case 4: //ksiegowy
                MenuController.mainController.set_center("admin_pane.fxml");
                break;
            case 5: //admin
                MenuController.mainController.set_center("admin_pane.fxml");
                break;
            case 6: //kierownik
                MenuController.mainController.set_center("admin_pane.fxml");
                break;
            case 7: //recepcja
                MenuController.mainController.set_center("admin_pane.fxml");
                break;
        }
    }

    @FXML
    private void reg_on() {
        notification.setFill(Color.BLUE);
        notification.setText(App.getString("signIn"));
    }
}
