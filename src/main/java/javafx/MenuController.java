package javafx;

import database.DbStatements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;


public class MenuController {

    ObservableList<String> languList = FXCollections.observableArrayList(App.getString("eng"), App.getString("pl"));

    @FXML
    private ChoiceBox languChange;

    @FXML
    private ToggleGroup toggleMenu;

    @FXML
    private Button btnLogInOut;

    @FXML
    private void initialize() {
        languChange.setValue(App.getString("eng"));
        languChange.setItems(languList);
        start();
    }

    @FXML
    private void open_profil() {
        //tu bedzie wyswietlal sie profil
        check_toggle();
    }

    @FXML
    private void change_language() {
        if(languChange.getValue() == App.getString("pl")) {
            System.out.println("pl");
        } else {
            System.out.println("eng");
        }

    }

    public void check_toggle() {
        if(toggleMenu.getSelectedToggle()==null) {
            App.setRoot("login_window");
            //tutaj trzeba dać metode która sprawdzi do którego panelu cofnąć
            //bo nie zawsze będziesz cofać do login_pane
        }
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
        if(DbStatements.type==-2) { //kiedy jesteś gościem
            btnLogInOut.setText(App.getString("logIn"));
            System.out.println("Init -2. " + DbStatements.type + " " + DbStatements.id);
        } else if(DbStatements.type==-1) {  //kiedy jesteś na logowaniu
            btnLogInOut.setVisible(false);
            System.out.println("Init -1. " + DbStatements.type + " " + DbStatements.id);
        } else {    //kiedy jestes na jakims innym panelu
            btnLogInOut.setVisible(true);
            btnLogInOut.setText(App.getString("logOut"));
            System.out.println("Init 0. " + DbStatements.type + " " + DbStatements.id);
        }
    }

//    @FXML
//    private void switchToPrimary() throws IOException {
//        App.setRoot("login_pane");
//    }
}