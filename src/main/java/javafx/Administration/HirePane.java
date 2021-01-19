package javafx.Administration;

import database.DbConnector;
import database.DbStatements;
import javafx.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class HirePane {
    @FXML
    private TextField hire_name;
    @FXML
    private TextField hire_surname;
    @FXML
    private TextField hire_salary;
    @FXML
    private ChoiceBox postBox;
    @FXML
    private Label infoLabel;
    @FXML
    private TextArea token;

    ObservableList<String> postList;
    Connection db;

    @FXML
    private void initialize(){
        postList = FXCollections.observableArrayList(
                App.getString("doctor"),App.getString("manager"),App.getString("apothecary"),App.getString("accountant"),App.getString("receptionist"));
        postBox.setItems(postList);
        db = DbConnector.connect();
    }


    @FXML
    private void back(){
        App.backBtn();
    }

    @FXML
    private void hire_ok_btn() throws SQLException {//walidacja wszyskie pola musza byc pelne
        ArrayList<String> list = new ArrayList<>();

        if(!hire_name.getText().isBlank())
            list.add(hire_name.getText());

        if(!hire_surname.getText().isBlank())
            list.add(hire_surname.getText());

        if(postBox.getValue() != null)
            list.add(String.valueOf(postBox.getValue()));

        if(!hire_salary.getText().isBlank())
            list.add(hire_salary.getText());

        if(list.size()==4){
            System.out.println("DODAWANIE PRACOWNIKA DO BAZY");

            String ts = String.valueOf(list.hashCode());
            token.setText(ts);
            DbStatements.addUser(db, "NotRegistetYet", "ahf3467344g3hg4oeug", type(list.get(2)), ts);

            DbStatements.addPersonel(db,DbStatements.getIdUser(db,ts), list.get(0),list.get(1),list.get(2),list.get(3));
        }else{
            infoLabel.setVisible(true);
            System.out.println("NIE MA DANYCH");
        }

    }

    private int type(String s){
        if(s.equals(App.getString("manager")))
            return 5;
        if(s.equals(App.getString("doctor")))
            return 1;
        if(s.equals(App.getString("apothecary")))
            return 2;
        if(s.equals(App.getString("accountant")))
            return 3;
        if(s.equals(App.getString("receptionist")))
            return 6;
        return 0;
    }
}
