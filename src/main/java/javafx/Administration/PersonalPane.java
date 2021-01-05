package javafx.Administration;

import javafx.App;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;

public class PersonalPane {
    @FXML
    private TableColumn name_list;
    @FXML
    private TableColumn surname_list;
    @FXML
    private TableColumn post_list;
    @FXML
    private TextArea select_profil;

    @FXML
    private void back(){
        App.backBtn();
    }

    @FXML
    private void refresh_btn(){
        System.out.println("ODSWIEZANIE BAZY");
    }

    @FXML
    private void profil_btn(){
        App.addToBtnStack("personel_list");
        App.setRoot("personel_profil");
    }
}
