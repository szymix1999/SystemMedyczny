package javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class PatientController {

    ObservableList list = FXCollections.observableArrayList();

    @FXML
    private ListView<String> vpList;
    @FXML
    private Text txtList;
    @FXML
    private TextField FTxtName;
    @FXML
    private Text txtName;
    @FXML
    private Button btnName;
    @FXML
    private Text txtCon;
    @FXML
    private TextArea ATxtCon;

    @FXML
    private void initialize(){
        changeOnVisits();
    }

    @FXML
    private void openWeb() throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URL("https://google.com").toURI());
    }

    @FXML
    private void displaySelectedVisitDetails() {
        String name = vpList.getSelectionModel().getSelectedItem();
        System.out.println("Selected visit: " + name);
        if(name!=null || !name.isEmpty()) {
            FTxtName.setText(name);
        }
    }

    @FXML
    private void changeOnVisits() {
        txtList.setText(App.getString("yourVisits"));
        txtName.setText(App.getString("nameVisit"));
        btnName.setVisible(true);
        FTxtName.setEditable(true);
        FTxtName.clear();
        txtCon.setVisible(false);
        ATxtCon.setVisible(false);
        loadVisits();
    }

    @FXML
    private void changeOnPrescriptions() {
        txtList.setText(App.getString("prescriptions"));
        txtName.setText(App.getString("prescriptionName"));
        FTxtName.setEditable(false);
        btnName.setVisible(false);
        FTxtName.clear();
        txtCon.setVisible(true);
        ATxtCon.setVisible(true);
        loadPrescriptions();

    }

    private void loadVisits() {
        list.clear(); vpList.getItems().clear();
        list.addAll("Wizyta1", "test1", "test2", "test3", "test2", "test3", "test2", "test3", "test2", "test3");
        vpList.getItems().addAll(list);
    }

    private void loadPrescriptions() {
        list.clear(); vpList.getItems().clear();
        list.addAll("Recepta1", "test1", "test2", "test3", "test2", "test3", "test2", "test3", "test2", "test3");
        vpList.getItems().addAll(list);
    }

}
