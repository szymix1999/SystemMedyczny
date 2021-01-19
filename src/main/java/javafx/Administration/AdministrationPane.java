package javafx.Administration;

import database.DbConnector;
import database.DbStatements;
import javafx.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.SQLException;


public class AdministrationPane {

    @FXML
    private Text postLabel;
    @FXML
    private Text name;
    @FXML
    private Button hire;
    @FXML
    private Button staff;

    Connection db;

    @FXML
    private void initialize() throws SQLException {
        db = DbConnector.connect();
        switch (DbStatements.type){
            case 3 :
                postLabel.setText(App.getString("accountant"));
                break;
            case 5 :
                postLabel.setText(App.getString("manager"));
                break;
            case 6 :
                postLabel.setText(App.getString("receptionist"));
                break;
        }
        name.setText(DbStatements.getPersonelName(db, DbStatements.getIdPersonel(db,DbStatements.id)));
    }

    @FXML
    private void hire_btn(){
        System.out.println("hire_employ");
        App.addToBtnStack("administration_pane");
        App.setRoot("hire_employ");
    }

    @FXML
    private void staff_btn(){
        System.out.println("personel_list");
        App.addToBtnStack("administration_pane");
        App.setRoot("personel_list");
    }

    @FXML
    private void inventory_btn(){
        System.out.println("inventory_pane");
        App.addToBtnStack("administration_pane");
        App.setRoot("inventory_pane");
    }

    @FXML
    private void news_btn(){
        System.out.println("news_pane");
        App.addToBtnStack("administration_pane");
        App.setRoot("news_pane");
    }

    @FXML
    private void reg_btn(){
        System.out.println("reg_patient_pane");
        App.addToBtnStack("administration_pane");
        App.setRoot("register_patient");
    }











}
