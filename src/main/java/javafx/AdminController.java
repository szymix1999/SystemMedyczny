package javafx;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class AdminController {

    @FXML
    private ListView<String> userList;


    private void loadVisits() {
        ObservableList list = FXCollections.observableArrayList();
        list.clear(); userList.getItems().clear();
        list.addAll("User1", "test1", "test2", "test3", "test4", "test5", "test1", "test2", "test3", "test4");
        userList.getItems().addAll(list);
    }
}