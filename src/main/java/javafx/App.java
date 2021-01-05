package javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage curr_stage;
    private static ResourceBundle bundle;
    private static Stack<String> backStack;

    @Override
    public void start(Stage stage) throws IOException {
        //Ustawianie jezyka
        Locale.setDefault(new Locale("en"));

        //Scena starowa
        make_scene();
        stage.setScene(scene);

        stage.setTitle(App.getString("title"));
        stage.setResizable(false);
        stage.show();

        backStack = new Stack<String>();
        curr_stage = stage;
    }

    private static void make_scene() throws IOException {
        scene = new Scene(loadFXML("patient_pane"), 1280.0, 720.0);
    }

    public static void reopen() throws IOException {
        curr_stage.close();
        make_scene();
        curr_stage.setScene(scene);

        curr_stage.setTitle(App.getString("title"));
        curr_stage.setResizable(false);
        curr_stage.show();
    }

    //Załadowanie fxml na scene
    public static void setRoot(String fxml) {
        try{
            scene.setRoot(loadFXML(fxml));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Przygotowanie fxml do wyswietlania na scenie
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        loadResources(fxmlLoader);
        return fxmlLoader.load();
    }

    private static void loadResources(FXMLLoader fxmlLoader) throws IOException {
        bundle = ResourceBundle.getBundle("config.strings");
        fxmlLoader.setResources(bundle);
    }

    public static String getString(String key) {
        return bundle.getString(key);
    }

    public static void backBtn(){
        if(!backStack.empty())
            setRoot(backStack.pop());
    }

    public static void addToBtnStack(String fxml){
        backStack.push(fxml);
    }

    public static void main(String[] args) {
        launch();
    }

}