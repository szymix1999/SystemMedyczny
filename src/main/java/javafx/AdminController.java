package javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class AdminController {

    @FXML
    public ListView<String> listUsers;
    ObservableList list = FXCollections.observableArrayList();


    @FXML
    public TextField screen;

    @FXML
    public Button deleteUsers;

    @FXML
    public Button banButton;

    @FXML
    public Button doctorButton;

    @FXML
    public Button aptecButton;


    public void initialize(){
//        listUsers = new ListView<>();
//        listUsers.setItems(list);
        loadData();
    }
    private void loadData(){
        list.removeAll(list);
        String a ="Jan";
        String b ="Kuba";
        String c="Adam";
        list.addAll("MITCHELL" ,"PEREZ" ,"ROBERTS" ,"TURNER" ,"PHILLIPS" ,"CAMPBELL" ,"PARKER" ,"EVANS" ,"EDWARDS" ,"COLLINS", "STEWART" ,
                "SANCHEZ", "MORRIS","ROGERS", "SMITH","JOHNSON","WILLIAMS","JONES","CLARK","RODRIGUEZ","LEWIS","LEE","WALKER","HALL","ALLEN","YOUNG",
                "HERNANDEZ","KING","WRIGHT","LOPEZ","HILL","SCOTT","GREEN","ADAMS" ,"BAKER","GONZALEZ","NELSON","CARTER" );
        listUsers.getItems().addAll(list);

    }




    @FXML
    private void displaySelected(javafx.scene.input.MouseEvent mouseEvent) {
        String username = listUsers.getSelectionModel().getSelectedItem();
        if(username == null || username.isEmpty() ){
            screen.setText("...");
        }
        else{
            screen.setText(username);
        }
    }

    @FXML
    private void deleteUserFromList(javafx.event.ActionEvent actionEvent) {
        screen.setText("Usunięto " + listUsers.getSelectionModel().getSelectedItem() + "!");
        listUsers.getItems().removeAll(listUsers.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void banUser(javafx.event.ActionEvent actionEvent) {
        screen.setText("Zablokowano czynności " + listUsers.getSelectionModel().getSelectedItem() + "!");
    }

    @FXML
    private void doctorPermission(javafx.event.ActionEvent actionEvent) {
        screen.setText("Nadano uprawnienia lekarskie " + listUsers.getSelectionModel().getSelectedItem() + "!");
    }

    @FXML
    private void administartionPermission(javafx.event.ActionEvent actionEvent) {
        screen.setText("Nadano uprawnienia administracyjne " + listUsers.getSelectionModel().getSelectedItem() + "!");
    }



}

