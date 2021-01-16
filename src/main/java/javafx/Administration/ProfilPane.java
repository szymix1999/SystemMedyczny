package javafx.Administration;

import database.DbConnector;
import database.DbStatements;
import javafx.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfilPane {
    @FXML
    private Text profil_name;
    @FXML
    private Text profil_surname;
    @FXML
    private Text profil_post;
    @FXML
    private TextArea salary;

    Connection db;

    @FXML
    private void back(){
        App.backBtn();
    }

    @FXML
    private void initialize() throws SQLException {
        System.out.println(PersonalPane.currSelected);
        db = DbConnector.connect();
        setDate();
    }

    private void setDate() throws SQLException {
        ResultSet rs = DbStatements.getPersonel(db,PersonalPane.currSelected);
        rs.next();
        profil_name.setText(rs.getString("first_name"));
        profil_surname.setText(rs.getString("last_name"));
        profil_post.setText(rs.getString("profession"));
        salary.setText(rs.getString("salary"));
    }
    @FXML
    private void salary_ok() throws SQLException {
        //zapisanie pola salary do bazy danych
        DbStatements.setSalary(db,Integer.parseInt(PersonalPane.currSelected), salary.getText());
    }

    @FXML
    private void kick() throws SQLException {
        //wywalanie pracownika z bazy danych
        DbStatements.kickPerson(db, Integer.parseInt(PersonalPane.currSelected));
        System.out.println("ZWALNIANIE PRACOWNIKA");
    }
}
