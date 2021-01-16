package javafx.Administration;

import database.DbConnector;
import database.DbStatements;
import javafx.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @FXML
    private TextArea token;

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
    private void ok() throws SQLException, ParseException {
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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            java.util.Date utilDate = format.parse(list.get(3));
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            String sex = String.valueOf(list.get(4).charAt(0));
            
            String s = list.get(0) + list.get(1) + list.get(2);
            String ts = String.valueOf(s.hashCode());
            DbStatements.addUser(db, "NotRegistetYet", "ahf3467344g3hg4oeug", 0, ts);

            DbStatements.addPatient(db,DbStatements.getIdUser(db,ts), list.get(0), list.get(1), sqlDate, sex ,list.get(2));
            token.setText(ts);
            name.clear();
            surname.clear();
            birthday.clear();
            cause.clear();
        }else{
            infoLabel.setVisible(true);
            System.out.println("POPRAW DANE");
        }
    }
}
