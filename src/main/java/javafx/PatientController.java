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
import java.util.Date;
import java.util.Random;

public class PatientController {

    Connection c = DbConnector.connect();
    ObservableList list = FXCollections.observableArrayList();
    int curr_list = 0;
    int adIndex = 0;
    int id_patient = 0;
    String first_name, last_name, birth_date, gender, health;

    @FXML
    private ListView vpList;
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
    @FXML
    private TextField FTxtAmount;
    @FXML
    private TextField FTxtDate;

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

      @Override
      public String toString() {
          return (this.vname);
      }
    }

    private class Prescription {
        public String ppresc_name;
        public String pcontent;
        public String pdate;
        public int pcost;

        public Prescription(String name, String content, String date, int cost) {
            ppresc_name = name;
            pcontent = content;
            pdate = date;
            pcost = cost;
        }

        @Override
        public String toString() {
            return (this.ppresc_name);
        }

    }

    @FXML
    private void initialize() {
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
        if(curr_list == 0) {
            Visit selectedItem = (Visit) vpList.getSelectionModel().getSelectedItem();
            System.out.println("Selected visit: " + selectedItem.vname);
            if (selectedItem.vname != null || !selectedItem.vname.isEmpty()) {
                FTxtName.setText(selectedItem.vname);
                FTxtAmount.setText(selectedItem.vcost + ",00");
                FTxtDate.setText(selectedItem.vdate);
            }
        } else {
            Prescription selectedItem = (Prescription) vpList.getSelectionModel().getSelectedItem();
            System.out.println("Selected prescription: " + selectedItem.ppresc_name);
            if (selectedItem.ppresc_name != null || !selectedItem.ppresc_name.isEmpty()) {
                FTxtName.setText(selectedItem.ppresc_name);
                FTxtAmount.setText(selectedItem.pcost + ",00");
                FTxtDate.setText(selectedItem.pdate);
                ATxtCon.setText(selectedItem.pcontent);
            }
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
        FTxtAmount.clear();
        FTxtDate.clear();
        ATxtCon.clear();
        txtCon.setVisible(false);
        ATxtCon.setVisible(false);
        curr_list = 0;
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
        FTxtAmount.clear();
        FTxtDate.clear();
        ATxtCon.clear();
        txtCon.setVisible(true);
        ATxtCon.setVisible(true);
        curr_list = 1;
        loadPrescriptions();
    }

    private void loadVisits() {
        list.clear();
        vpList.getItems().clear();

        try {
            ResultSet rs = DbStatements.getVisitDate(c, id_patient);
            while (rs.next()) {
                String stringCHD = "";
                Date chd = rs.getDate("change_date");
                if(chd != null)
                    stringCHD = chd.toString();
                Visit v = new Visit(rs.getString("visit_name"), rs.getString("change_name"),
                        rs.getDate("visit_date").toString(), stringCHD, rs.getInt("cost"));
                //String v = rs.getString("visit_name");
                list.add(v);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        vpList.getItems().addAll(list);
    }

    private void loadPrescriptions() {
        list.clear();
        vpList.getItems().clear();
        try {
            ResultSet rs = DbStatements.getPrescriptionDate(c, id_patient);
            while (rs.next()) {
                Prescription v = new Prescription(rs.getString("presc_name"), rs.getString("content"),
                        rs.getDate("end_date").toString(), rs.getInt("cost"));
                list.add(v);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        vpList.getItems().addAll(list);
    }

}
