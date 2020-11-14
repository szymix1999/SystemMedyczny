package javafx;

import database.DbConnector;
import database.DbStatements;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private GridPane grid;
    private Text formTitle, notofication;
    private Label userLogin, userPass;
    private TextField userLoginField;
    private PasswordField userPassField;
    private Button btnLogin, btnReg;
    private HBox hbtnLogin, hbtnReg;
    private Image image;

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("login_pane"));
//        image = new Image("https://sm.newsecond.se/2019/08/2019-08-02_14.20.33.png");
//        grid.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
//        scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }


    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        JSONObject o;
        launch();

    }

}