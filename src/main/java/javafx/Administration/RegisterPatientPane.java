package javafx.Administration;

import database.DbConnector;
import database.DbStatements;
import javafx.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;


public class RegisterPatientPane {

    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField cause;
    @FXML
    private TextField birthday;
    @FXML
    private ChoiceBox gender;
    @FXML
    private Label infoLabel;

    Connection db;


    @FXML
    private void initialize(){
        gender.setItems(FXCollections.observableArrayList(App.getString("woman"), App.getString("man")));
    }

    public RegisterPatientPane() {
        db = DbConnector.connect();
    }

    @FXML
    private void back(){
        App.backBtn();
    }

    @FXML
    private void ok() throws SQLException {
        ArrayList<String> list = new ArrayList<>();

        if(!name.getText().isBlank())
            list.add(name.getText());

        if(!surname.getText().isBlank())
            list.add(surname.getText());

        if(!cause.getText().isBlank())
            list.add(cause.getText());

        if(!birthday.getText().isBlank())
            list.add(birthday.getText());

        if(gender.getValue() != null)
            list.add(String.valueOf(gender.getValue()));

        if(list.size()==5){
            System.out.println("DOADAWANIE PACJENTA");
            DbStatements.addPatient(db,-1, list.get(0), list.get(2), Date.valueOf(list.get(4)), list.get(5),list.get(3));
        }else{
            infoLabel.setVisible(true);
            System.out.println("POPRAW DANE");
        }
    }
}
