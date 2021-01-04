package javafx;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class PatientController {

    ObservableList list = FXCollections.observableArrayList();

    @FXML
    private ListView<String> visitList;

    @FXML
    private ListView<String> prescList;

    @FXML
    private TextField visitName;

    @FXML
    private void initialize(){
        loadData();
    }

    @FXML
    private void openWeb() throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URL("https://google.com").toURI());
    }

    @FXML
    private void changeCursor() {
        //
    }

    @FXML
    private void displaySelectedVisitDetails() {
        String name = visitList.getSelectionModel().getSelectedItem();
        System.out.println("Selected visit: " + name);
        if(name!=null || !name.isEmpty()) {
            visitName.setText(name);
        }
    }

    private void loadData() {
        list.clear();
        list.addAll("test1", "test2", "test3");
        visitList.getItems().addAll(list);
        prescList.getItems().addAll(list);
    }

}
