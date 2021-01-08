package javafx;

import database.DbStatements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;

public class MenuController {

    ObservableList<String> languList = FXCollections.observableArrayList(App.getString("eng"), App.getString("pl"));

    @FXML
    private ChoiceBox languChange;
    @FXML
    private Button btnLogInOut;

    @FXML
    private void initialize() {
        check_language();
        languChange.setItems(languList);
        start();
    }

    @FXML
    private void change_language() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(App.getString("languageChange"));
        alert.setContentText(App.getString("langChangeCon"));
        alert.setHeaderText(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(languChange.getValue() == App.getString("pl"))
                Locale.setDefault(new Locale("pl"));
            else
                Locale.setDefault(new Locale("en"));
            App.reopen();
        } else {
            check_language();
        }
    }

    public void check_language() {
        if(Locale.getDefault().toString().equals("pl"))         //ustawiamy choicebox na poprawny język
            languChange.setValue(App.getString("pl"));
        else
            languChange.setValue(App.getString("eng"));
    }

    @FXML
    private void log_in_out(){
        if(DbStatements.type==-2) {  //niezalogowany, chce sie zalogowac
            DbStatements.type = -1;
            App.setRoot("login_pane");
            System.out.println("Login panel on. " + DbStatements.type + " " + DbStatements.id);
        } else {    //zalogowany, chce się wylogować
            DbStatements.id = -1;
            DbStatements.type = -2;
            App.setRoot("guest_pane");
            System.out.println("Guest panel on. " + DbStatements.type + " " + DbStatements.id);
        }
    }

    private void start() {
        if(DbStatements.type==-2) { //kiedy jesteś na gościu
            btnLogInOut.setText(App.getString("logIn"));
        } else if(DbStatements.type==-1) {  //kiedy jesteś na logowaniu
            btnLogInOut.setVisible(false);
        } else {    //kiedy jestes na innym panelu
            btnLogInOut.setVisible(true);
            btnLogInOut.setText(App.getString("logOut"));
        }
    }

    @FXML
    private void openCalendar() throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URL("https://calendar.google.com/calendar").toURI());
    }

}