package javafx;

import database.DbStatements;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class AdministrationPane {

    //General
    @FXML
    private void initialize(){
    }

    @FXML
    private void back(){
        App.backBtn();
    }

    //Main Pane
    @FXML
    private void hire_btn(){
        App.addToBtnStack("administration_pane");
        App.setRoot("hire_employ");
    }

    @FXML
    private void staff_btn(){
        App.addToBtnStack("administration_pane");
        App.setRoot("personel_list");
    }

    //Hire Pane
    @FXML
    private TextField hire_name;
    @FXML
    private TextField hire_surname;
    @FXML
    private TextField hire_post;
    @FXML
    private TextField hire_salary;

    @FXML
    private void hire_ok_btn(){//walidacja wszyskie pola musza byc pelne
        String[] arr = new String[4];
        arr[0] = hire_name.getText();
        arr[1] = hire_surname.getText();
        arr[2] = hire_post.getText();
        arr[3] = hire_salary.getText();
        System.out.println("DODAWANIE PRACOWNIKA DO BAZY");
        DbStatements.addPersonel(arr);
    }

    //Personel Pane
    @FXML
    private TableColumn name_list;
    @FXML
    private TableColumn surname_list;
    @FXML
    private TableColumn post_list;




}
