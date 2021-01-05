package javafx.Administration;

import database.DbStatements;
import javafx.App;
import javafx.fxml.FXML;


public class AdministrationPane {

    @FXML
    private void initialize(){
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
        App.setRoot("reg_patient_pane");
    }











}
