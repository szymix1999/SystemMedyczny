package javafx.Menu;

import database.DbStatements;
import javafx.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;

public class MenuController {

    ObservableList<String> languList = FXCollections.observableArrayList(App.getString("eng"), App.getString("pl"));

    @FXML
    private ChoiceBox<String> languChange;
    @FXML
    private Button btnLogInOut;
    @FXML
    private Button btnNotes;
    @FXML
    private Button btnCalendar;

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

        File languageFile = new File("src\\main\\resources\\config\\language");
        FileWriter fileOut = new FileWriter(languageFile, false);
        if (result.get() == ButtonType.OK){
            if(languChange.getValue().equals(App.getString("pl"))) {
                Locale.setDefault(new Locale("pl"));
                fileOut.write("pl");
            } else {
                Locale.setDefault(new Locale("en"));
                fileOut.write("en");
            }
            fileOut.close();
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

    @FXML
    private void openNotes() {
        if(DbStatements.id == -2 || DbStatements.id == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog \"" + App.getString("notes") + "\"");
            alert.setHeaderText(null);
            alert.setContentText(App.getString("logInNotes"));

            alert.showAndWait();
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("notes_window.fxml"));
        fxmlLoader.setResources(App.getBundle());
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(App.getString("notes"));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}