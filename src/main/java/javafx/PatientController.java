package javafx;

import database.DbConnector;
import database.DbStatements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class PatientController {

    Connection c = DbConnector.connect();
    ObservableList list = FXCollections.observableArrayList();
    int adIndex = 0;
    int id_patient = 0;
    String first_name, last_name, birth_date, gender, health;

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
    private Text txtDate;
    @FXML
    private Button btnDate;
    @FXML
    private Text txtCon;
    @FXML
    private TextArea ATxtCon;
    @FXML
    private ImageView imgAds;
    @FXML
    private TextArea ATxtHealth;

    private class Visit {
      public String vname;
      public String change_vname;
      public String vdate;
      public String change_vdate;
      public int vcost;

      public Visit(String name, String ch_name, String date, String ch_date, int cost) {
          vname = name;
          change_vname = ch_name;
          vdate = date;
          change_vdate = ch_date;
          vcost = cost;
      }
    }

    @FXML
    private void initialize() throws SQLException {
        randomAds();

        try {
            ResultSet rs = DbStatements.getPatientData(c);
            while (rs.next()) {
                id_patient = rs.getInt("id");
                first_name = rs.getString("first_name");
                last_name = rs.getString("last_name");
                birth_date = rs.getDate("birth_date").toString();
                gender = rs.getString("sex");
                health = rs.getString("health");
            }
            System.out.println("Id patient: " + id_patient);
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        setHealth();
        changeOnVisits();
    }

    private void setHealth() {
        ATxtHealth.setText(health);
    }

    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private void randomAds() {
        adIndex = getRandomNumber(0,4);
        imgAds.setImage(new Image(getClass().getResourceAsStream("/images/ads/" + adIndex + "_ads.gif")));
    }

    @FXML
    private void openWeb() throws IOException, URISyntaxException {
        String url[] = {"https://google.com", "https://facebook.com", "https://github.com", "https://youtube.com"};
        Desktop.getDesktop().browse(new URL(url[adIndex]).toURI());
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
        txtDate.setText(App.getString("visitDate"));
        btnName.setVisible(true);
        btnDate.setVisible(true);
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
        txtDate.setText(App.getString("expirationDate"));
        btnName.setVisible(false);
        btnDate.setVisible(false);
        FTxtName.setEditable(false);
        FTxtName.clear();
        txtCon.setVisible(true);
        ATxtCon.setVisible(true);
        loadPrescriptions();
    }

    private void loadVisits() {
        list.clear();
        vpList.getItems().clear();

        try {
            ResultSet rs = DbStatements.getVisitDate(c, id_patient);
            while (rs.next()) {
//                Visit v = new Visit(rs.getString("visit_name"), rs.getString("change_name"),
//                        rs.getDate("visit_date").toString(), rs.getDate("change_date").toString(),
//                        rs.getInt("cost"));
                String v = rs.getString("visit_name");
                list.add(v);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        vpList.getItems().addAll(list);
    }

    private void loadPrescriptions() {
        list.clear(); vpList.getItems().clear();
        list.addAll("Recepta1", "test1", "test2", "test3", "test2", "test3", "test2", "test3", "test2", "test3");
        vpList.getItems().addAll(list);
    }

}
