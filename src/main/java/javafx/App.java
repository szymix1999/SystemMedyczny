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

        stage.setTitle("Main Panel");

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));           //marginesy
        //grid.setGridLinesVisible(true);

        formTitle = new Text("Welcome");
        formTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(formTitle, 0, 0, 2, 1);

        userLogin = new Label("User Name:");
        grid.add(userLogin, 0, 1);

        userLoginField = new TextField();
        userLoginField.setPromptText("admin");
        grid.add(userLoginField, 1, 1);

        userPass = new Label("Password:");
        grid.add(userPass, 0, 2);

        userPassField = new PasswordField();
        userPassField.setPromptText("admin");
        grid.add(userPassField, 1, 2);

        btnLogin = new Button("Log in");
        hbtnLogin = new HBox(10);
        hbtnLogin .setAlignment(Pos.BOTTOM_RIGHT);
        hbtnLogin .getChildren().add(btnLogin);
        grid.add(btnLogin, 1, 4);
        //grid.setHalignment(btnLogin, HPos.RIGHT);

        btnReg = new Button("Register on");
        hbtnReg = new HBox(10);
        hbtnReg .setAlignment(Pos.BOTTOM_RIGHT);
        hbtnReg .getChildren().add(btnReg);
        grid.add(btnReg, 1, 4);
        grid.setHalignment(btnReg, HPos.RIGHT);

        notofication = new Text();
        grid.add(notofication, 1, 6);

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Connection c = DbConnector.connect();
                System.out.println(userLoginField.getText() + " " + userPassField.getText());
                try {
                    if((userLoginField.getText() != null && !userLoginField.getText().isEmpty() &&
                            userPassField.getText() != null && !userPassField.getText().isEmpty()) &&
                            (DbStatements.checkUser(c, userLoginField.getText(), userPassField.getText()) == true)) {
                        notofication.setFill(Color.GREEN);
                        notofication.setText("You logged in!");
                    } else {
                        notofication.setFill(Color.FIREBRICK);
                        notofication.setText("Bad login or password!");
                    }
                } catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });

        btnReg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                notofication.setFill(Color.BLUE);
                notofication.setText("To register, please \ncontact the administrator!");
            }
        });

        //scene = new Scene(loadFXML("primary"),1280, 720);
        image = new Image("https://sm.newsecond.se/2019/08/2019-08-02_14.20.33.png");
        grid.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        scene = new Scene(grid);
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
        launch();
    }

}