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
        System.out.println(userLoginField.getText() + " " + userPassField.getText());
        try {
            if((userLoginField.getText() != null && !userLoginField.getText().isEmpty() &&
                    userPassField.getText() != null && !userPassField.getText().isEmpty()) &&
                    (DbStatements.checkUser(c, userLoginField.getText(), userPassField.getText()) == true)) {
                App.setRoot("secondary");
            } else {
                notification.setFill(Color.FIREBRICK);
                notification.setText("Złe hasło lub login!");
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void reg_on() {
        notification.setFill(Color.BLUE);
        notification.setText("Aby się zarejestrować skontaktuj się z administratorem!");
    }
}
