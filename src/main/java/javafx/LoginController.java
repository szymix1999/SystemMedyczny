package javafx;

import java.sql.Connection;
import java.sql.SQLException;
import database.DbConnector;
import database.DbStatements;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginController {

    Connection c = DbConnector.connect();

    @FXML
    private TextField userLoginField;
    @FXML
    private PasswordField userPassField;
    @FXML
    private Text notification;
    @FXML
    private VBox vboxReg;
    @FXML
    private TextField FTxtToken;
    @FXML
    private TextField FTxtUsername;
    @FXML
    private TextField FTxtPassword;


    @FXML
    private void log_in() {
        System.out.println("Login: " + userLoginField.getText() + " | Password: " + userPassField.getText());
        try {
            if((userLoginField.getText() != null && !userLoginField.getText().isEmpty() &&
                    userPassField.getText() != null && !userPassField.getText().isEmpty()) &&
                    (DbStatements.checkUser(c, userLoginField.getText(), userPassField.getText()) != -1)) {
                choose_pane();
                c.close();
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
                App.setRoot("patient_pane");
                break;
            case 1: //lekarz
                App.setRoot("doctor_pane");
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
                App.setRoot("administration_pane");
                break;
            case 6: //recepcja
                App.setRoot("administration_pane");
                break;
        }
    }

    @FXML
    private void reg_on() {     //pokazuje okno rejestracji
        if(!vboxReg.isVisible()) {
            notification.setFill(Color.BLUE);
            notification.setText(App.getString("signIn"));
            vboxReg.setVisible(true);
        } else {
            notification.setText("");
            vboxReg.setVisible(false);
        }
    }

    @FXML
    private void send_reg() {   //metoda po kliknieciu Create w Register
        if(FTxtUsername.getText() != "" && FTxtPassword.getText() != "" && FTxtToken.getText() != "") {
            try {
                if(DbStatements.getIdUser(c,FTxtToken.getText())!=0) {
                    DbStatements.registerUser(c, FTxtUsername.getText(), FTxtPassword.getText(), FTxtToken.getText());
                    notification.setFill(Color.GREEN);
                    notification.setText(App.getString("registered"));   //tutaj można wpisać co wyskoczy po wysłaniu
                    vboxReg.setVisible(false);
                    System.out.println("New user. Login:" + FTxtUsername.getText() + " | Password: " + FTxtPassword.getText());
                } else {
                    notification.setFill(Color.FIREBRICK);
                    notification.setText(App.getString("badToken"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            notification.setFill(Color.FIREBRICK);
            notification.setText(App.getString("fillAllFields"));
        }
    }

}
