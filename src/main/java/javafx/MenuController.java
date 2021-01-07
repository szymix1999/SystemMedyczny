package javafx;

import database.DbStatements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
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

    private void check_language() {
        System.out.println("Language: " + Locale.getDefault().toString());
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
            System.out.println("Init -2. User type: " + DbStatements.type + " User id: " + DbStatements.id);
        } else if(DbStatements.type==-1) {  //kiedy jesteś na logowaniu
            btnLogInOut.setVisible(false);
            System.out.println("Init -1. User type: " + DbStatements.type + " User id: " + DbStatements.id);
        } else {    //kiedy jestes na innym panelu
            btnLogInOut.setVisible(true);
            btnLogInOut.setText(App.getString("logOut"));
            System.out.println("Init 0. User type: " + DbStatements.type + " User id: " + DbStatements.id);
        }
    }

}