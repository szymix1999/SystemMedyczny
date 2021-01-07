package javafx.Administration;

import javafx.App;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ProfilPane {
    @FXML
    private Text profil_name;
    @FXML
    private Text profil_surname;
    @FXML
    private Text profil_post;

    @FXML
    private void back(){
        App.backBtn();
    }

    @FXML
    private void initialize(){
        setDate();
    }

    private void setDate(){
        profil_name.setText("Jan");
        profil_surname.setText("Kowalski");
    }
}
