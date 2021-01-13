package javafx.Administration;

import database.DbConnector;
import database.DbStatements;
import javafx.App;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class PersonalPane {
    @FXML
    private TableColumn<Person,String> name_list;
    @FXML
    private TableColumn<Person,String> surname_list;
    @FXML
    private TableColumn<Person,String> post_list;
    @FXML
    private TextArea select_profil;
    @FXML
    private TableView<Person> table_view;

    @FXML
    private Button profile_btn;

    public static String currSelected;


    Connection db;
    @FXML
    private void initialize() throws SQLException {
        db = DbConnector.connect();
        table_view.setItems(getPesonObservableList());
        name_list.setCellValueFactory(cellData ->cellData.getValue().nameProperty());
        surname_list.setCellValueFactory(cellData ->cellData.getValue().surnameProperty());
        post_list.setCellValueFactory(cellData ->cellData.getValue().postProperty());
        table_view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observableValue, Person person, Person t1) {
                if(table_view.getSelectionModel().getSelectedItem() != null)
                {
                    Person p = table_view.getSelectionModel().getSelectedItem();
                    select_profil.setText(p.toString());
                    currSelected = p.getId();
                    profile_btn.setDisable(false);
                }
            }

        });

    }

    @FXML
    private void back(){
        App.backBtn();
    }

    @FXML
    private void refresh_btn(){
        System.out.println("ODSWIEZANIE BAZY");
    }

    @FXML
    private void profil_btn() throws SQLException {
        App.addToBtnStack("personel_list");
        App.setRoot("personel_profil");
    }

    private ObservableList<Person> getPesonObservableList() throws SQLException {
        ObservableList<Person> list = FXCollections.observableArrayList();
        ArrayList<String> arrList = DbStatements.getPersonel(db);

        for(int i=0; i<arrList.size(); i+=4){
            list.add(new Person(arrList.get(i),arrList.get(i+1),arrList.get(i+2), arrList.get(i+3)));
        }
        return list;
    }
}
