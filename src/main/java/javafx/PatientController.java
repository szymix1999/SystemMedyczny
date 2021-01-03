package javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class PatientController {

    ObservableList list = FXCollections.observableArrayList();

    @FXML
    private ListView<String> visitList;

    @FXML
    private void initialize(){
        loadData();
    }

    private void loadData() {
        list.removeAll(list);
        list.addAll("test1", "test2", "test3");
        visitList.getItems().addAll(list);
    }

}
