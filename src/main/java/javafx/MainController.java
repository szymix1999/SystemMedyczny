package javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.ResourceBundle;


public class MainController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private MenuController topMenuController;

    @FXML
    private void initialize() {
        topMenuController.setMainController(this);
    }

    public void set_center(String fxmlPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath));
        ResourceBundle bundle = ResourceBundle.getBundle("config.strings");
        fxmlLoader.setResources(bundle);
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setCenter(parent);
    }

}
