package javafx.Administration;

import database.DbStatements;
import javafx.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HirePane {
    @FXML
    private TextField hire_name;
    @FXML
    private TextField hire_surname;
    @FXML
    private TextField hire_post;
    @FXML
    private TextField hire_salary;

    @FXML
    private void back(){
        App.backBtn();
    }

    @FXML
    private void hire_ok_btn(){//walidacja wszyskie pola musza byc pelne
        String[] arr = new String[4];
        arr[0] = hire_name.getText();
        arr[1] = hire_surname.getText();
        arr[2] = hire_post.getText();
        arr[3] = hire_salary.getText();
        System.out.println("DODAWANIE PRACOWNIKA DO BAZY");
        //DbStatements.addPersonel(arr);
    }
}
