package javafx.Administration;

import database.DbConnector;
import database.DbStatements;
import javafx.App;
import javafx.fxml.FXML;
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
    }
}
