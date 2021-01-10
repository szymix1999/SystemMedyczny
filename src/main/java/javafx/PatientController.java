package javafx;

import database.DbConnector;
import database.DbStatements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PatientController {

    Connection c = DbConnector.connect();
    ObservableList list = FXCollections.observableArrayList();
    Patient curr_patient;
    int curr_list = 0;
    int adsIndex = 0;
    int signUpCount = 0;

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
    @FXML
    private TextField FTxtUser;
    @FXML
    private TextField FTxtGender;
    @FXML
    private TextField FTxtBirthdate;
    @FXML
    private Button btnLastChange;
    @FXML
    private GridPane gPaneSignUp;
    @FXML
    private Button btnStartSignUp;
    @FXML
    private TextField FTxtDoctorName;

    //Klasa Wizyty
    private class Visit {
        public int id;
        public int index;
        public String name;
        public String change_name;
        public String date;
        public String change_date;
        public int cost;

        public Visit(int id, int index, String name, String change_name, String date, String change_date, int cost) {
            this.id = id;
            this.index = index;
            this.name = name;
            this.change_name = change_name;
            this.date = date;
            this.change_date = change_date;
            this.cost = cost;
      }

        @Override
        public String toString() {
          return (String.format("%03d", this.index) + " - " + this.name);
      }
    }

    //Klasa recepty
    private class Prescription {
        public int id;
        public int index;
        public String name;
        public String content;
        public String date;
        public int cost;

        public Prescription(int id, int index, String name, String content, String date, int cost) {
            this.id = id;
            this.index = index;
            this.name = name;
            this.content = content;
            this.date = date;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return (String.format("%03d", this.index) + " - " +this.name);
        }
    }

    //Klasa pacjent
    private class Patient {
        public int id = 0;
        public String first_name;
        public String last_name;
        public String birth_date;
        public String gender;
        public String health;

        public Patient(int id, String first_name, String last_name, String birth_date, String gender, String health) {
            this.id = id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.birth_date = birth_date;
            this.gender = gender;
            this.health = health;
        }
    }

    @FXML
    private void initialize() {
        vpList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        randomAds();

        try {
            ResultSet rs = DbStatements.getPatientData(c);
            while (rs.next()) {
                curr_patient = new Patient(rs.getInt("id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getDate("birth_date").toString(),
                        rs.getString("sex"), rs.getString("health"));
            }
            System.out.println("Id patient: " + curr_patient.id);
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        setPatientData();
        changeOnVisits();
    }

    private void setPatientData() {
        ATxtHealth.setText(curr_patient.health);
        FTxtUser.setText(curr_patient.first_name + " " + curr_patient.last_name);
        FTxtBirthdate.setText(curr_patient.birth_date);
        if(curr_patient.gender.equals("M"))
            FTxtGender.setText(App.getString("man"));
        else
            FTxtGender.setText(App.getString("woman"));
    }

    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private void randomAds() {
        adsIndex = getRandomNumber(0,4);
        imgAds.setImage(new Image(getClass().getResourceAsStream("/images/ads/" + adsIndex + "_ads.gif")));
    }

    @FXML
    private void openWeb() throws IOException, URISyntaxException {
        String url[] = {"https://google.com", "https://facebook.com", "https://github.com", "https://youtube.com"};
        Desktop.getDesktop().browse(new URL(url[adsIndex]).toURI());
    }

    @FXML
    private void displaySelectedObjectDetails() {
        if (curr_list == 0) {
            ObservableList<Visit> selectedItems = vpList.getSelectionModel().getSelectedItems();
            //cost
            int sum = 0;
            for(Visit o : selectedItems){
                sum += o.cost;
            }
            FTxtAmount.setText(sum + ",00");
            //name
            if(selectedItems.size() > 1)
                FTxtName.setText(App.getString("moreThanOneSelected!"));
            else if(selectedItems.size() == 0)
                FTxtName.setText(App.getString("selectVisit"));
            else
                FTxtName.setText(selectedItems.get(0).name);
            //date
            if(selectedItems.size() > 1)
                FTxtDate.setText(App.getString("moreThanOneSelected!"));
            else if(selectedItems.size() == 0)
                FTxtDate.setText(App.getString("selectVisit"));
            else
                FTxtDate.setText(selectedItems.get(0).date);
        } else {
            ObservableList<Prescription> selectedItems = vpList.getSelectionModel().getSelectedItems();
            //cost
            int sum = 0;
            for(Prescription o : selectedItems){
                sum += o.cost;
            }
            FTxtAmount.setText(sum + ",00");
            //name
            if(selectedItems.size() > 1)
                FTxtName.setText(App.getString("moreThanOneSelected!"));
            else if(selectedItems.size() == 0)
                FTxtName.setText(App.getString("selectPrescription"));
            else
                FTxtName.setText(selectedItems.get(0).name);
            //date
            if(selectedItems.size() > 1)
                FTxtDate.setText(App.getString("moreThanOneSelected!"));
            else if(selectedItems.size() == 0)
                FTxtDate.setText(App.getString("selectPrescription"));
            else
                FTxtDate.setText(selectedItems.get(0).date);
            //content
            if(selectedItems.size() > 1)
                ATxtCon.setText(App.getString("moreThanOneSelected!"));
            else if(selectedItems.size() == 0)
                ATxtCon.setText(App.getString("selectPrescription"));
            else
                ATxtCon.setText(selectedItems.get(0).content);
        }
    }

    @FXML
    private void changeOnVisits() {
        txtList.setText(App.getString("yourVisits"));
        txtName.setText(App.getString("nameVisit"));
        txtDate.setText(App.getString("visitDate"));
        btnName.setVisible(true);
        btnDate.setVisible(true);
        btnLastChange.setVisible(true);
        btnStartSignUp.setVisible(true);
        FTxtName.setEditable(true);
        FTxtDate.setEditable(true);
        FTxtName.setText(App.getString("selectVisit"));
        FTxtAmount.setText(App.getString("selectVisit"));
        FTxtDate.setText(App.getString("selectVisit"));
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
        btnLastChange.setVisible(false);
        btnStartSignUp.setVisible(false);
        gPaneSignUp.setVisible(false);
        signUpCount = 0;
        FTxtName.setEditable(false);
        FTxtDate.setEditable(false);
        FTxtName.setText(App.getString("selectPrescription"));
        FTxtAmount.setText(App.getString("selectPrescription"));
        FTxtDate.setText(App.getString("selectPrescription"));
        ATxtCon.setText(App.getString("selectPrescription"));
        txtCon.setVisible(true);
        ATxtCon.setVisible(true);
        curr_list = 1;
        loadPrescriptions();
    }

    private void loadVisits() {
        list.clear();
        vpList.getItems().clear();
        int index = 0;

        try {
            ResultSet rs = DbStatements.getVisitDate(c, curr_patient.id);
            while (rs.next()) {
                String stringCHD = "";
                Date chd = rs.getDate("change_date");
                if(chd != null)
                    stringCHD = chd.toString();
                Visit v = new Visit(rs.getInt("id"), index, rs.getString("visit_name"), rs.getString("change_name"),
                        rs.getDate("visit_date").toString(), stringCHD, rs.getInt("cost"));
                //String v = rs.getString("visit_name");
                list.add(v);
                index++;
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        vpList.getItems().addAll(list);
    }

    private void loadPrescriptions() {
        list.clear();
        vpList.getItems().clear();
        int index = 0;

        try {
            ResultSet rs = DbStatements.getPrescriptionDate(c, curr_patient.id);
            while (rs.next()) {
                Prescription v = new Prescription(rs.getInt("id"), index, rs.getString("presc_name"), rs.getString("content"),
                        rs.getDate("end_date").toString(), rs.getInt("cost"));
                list.add(v);
                index++;
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        vpList.getItems().addAll(list);
    }

    @FXML
    private void displayLastName() {
        ObservableList<Visit> selectedItems = vpList.getSelectionModel().getSelectedItems();

        if(selectedItems.size() == 1) {
            if (!selectedItems.get(0).change_name.isEmpty() && selectedItems.get(0).change_name != null)
                FTxtName.setText(selectedItems.get(0).change_name);
            else
                FTxtName.setText(selectedItems.get(0).name);

            if (!selectedItems.get(0).change_date.isEmpty() && selectedItems.get(0).change_date != null)
                FTxtDate.setText(selectedItems.get(0).change_date);
            else
                FTxtDate.setText(selectedItems.get(0).date);
        } else if(selectedItems.size() == 0) {
            FTxtAmount.setText(App.getString("selectVisit"));
        } else {
            FTxtAmount.setText(App.getString("moreThanOneSelected!"));
        }
    }

    @FXML
    private void payForObject() {
        if (curr_list == 0) {
            ObservableList<Visit> selectedItems = vpList.getSelectionModel().getSelectedItems();

            if (selectedItems.size() == 1) {
                try {
                    System.out.println("Paid");
                    DbStatements.updateVisitCost(c, selectedItems.get(0).id, 0);
                    selectedItems.get(0).cost = 0;
                    FTxtAmount.setText("0,00");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else if (selectedItems.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information Dialog \"" + App.getString("pay") + "\"");
                alert.setHeaderText(null);
                alert.setContentText(App.getString("chooseTheOption!"));

                alert.showAndWait();
            } else {
                try {
                    System.out.println("Paid");
                    for (Visit v : selectedItems) {
                        DbStatements.updateVisitCost(c, v.id, 0);
                        v.cost = 0;
                    }
                    FTxtAmount.setText("0,00");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            ObservableList<Prescription> selectedItems = vpList.getSelectionModel().getSelectedItems();

            if (selectedItems.size() == 1) {
                try {
                    System.out.println("Paid");
                    DbStatements.updatePrescriptionCost(c, selectedItems.get(0).id, 0);
                    selectedItems.get(0).cost = 0;
                    FTxtAmount.setText("0,00");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else if (selectedItems.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information Dialog \"" + App.getString("pay") + "\"");
                alert.setHeaderText(null);
                alert.setContentText(App.getString("chooseTheOption!"));

                alert.showAndWait();
            } else {
                try {
                    System.out.println("Paid");
                    for (Prescription v : selectedItems) {
                        DbStatements.updatePrescriptionCost(c, v.id, 0);
                        v.cost = 0;
                    }
                    FTxtAmount.setText("0,00");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void payForMoreObjects() {
        if (curr_list == 0) {
            ObservableList<Visit> selectedItems = vpList.getSelectionModel().getSelectedItems();

            if (selectedItems.size() == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog \"" + App.getString("payForAll") + "\"");
                alert.setHeaderText(null);
                alert.setContentText(App.getString("takeMoreOptions"));

                alert.showAndWait();
            } else if (selectedItems.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information Dialog \"" + App.getString("payForAll") + "\"");
                alert.setHeaderText(null);
                alert.setContentText(App.getString("chooseTheOption!"));

                alert.showAndWait();
            } else {
                try {
                    System.out.println("Paid");
                    for (Visit v : selectedItems) {
                        DbStatements.updateVisitCost(c, v.id, 0);
                        v.cost = 0;
                    }
                    FTxtAmount.setText("0,00");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            ObservableList<Prescription> selectedItems = vpList.getSelectionModel().getSelectedItems();

            if (selectedItems.size() == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog \"" + App.getString("payForAll") + "\"");
                alert.setHeaderText(null);
                alert.setContentText(App.getString("takeMoreOptions"));

                alert.showAndWait();
            } else if (selectedItems.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information Dialog \"" + App.getString("payForAll") + "\"");
                alert.setHeaderText(null);
                alert.setContentText(App.getString("chooseTheOption!"));

                alert.showAndWait();
            } else {
                try {
                    System.out.println("Paid");
                    for (Prescription v : selectedItems) {
                        DbStatements.updatePrescriptionCost(c, v.id, 0);
                        v.cost = 0;
                    }
                    FTxtAmount.setText("0,00");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void changeVisitDate() {
        ObservableList<Visit> selectedItems = vpList.getSelectionModel().getSelectedItems();

        if(selectedItems.size() == 1) {
            try {
                System.out.println("Data changed");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                java.util.Date utilDate = format.parse(selectedItems.get(0).date);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                java.util.Date utilDate2 = format.parse(FTxtDate.getText());
                java.sql.Date sqlDate2 = new java.sql.Date(utilDate2.getTime());

                DbStatements.updateVisitDate(c, selectedItems.get(0).id, sqlDate, sqlDate2);
                selectedItems.get(0).change_date = sqlDate.toString();
                selectedItems.get(0).date = sqlDate2.toString();

                FTxtDate.setText(sqlDate2.toString());
            } catch (SQLException | ParseException ex) {
                ex.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information Dialog \"" + App.getString("changeDate") + "\"");
            alert.setHeaderText(null);
            alert.setContentText(App.getString("dateCannotBeChanged!"));

            alert.showAndWait();
        }
    }

    @FXML
    private void changeVisitName() {
        ObservableList<Visit> selectedItems = vpList.getSelectionModel().getSelectedItems();

        if(selectedItems.size() == 1) {
            try {
                System.out.println("Name changed");

                DbStatements.updateVisitName(c, selectedItems.get(0).id, selectedItems.get(0).name, FTxtName.getText());
                selectedItems.get(0).change_date = selectedItems.get(0).name;
                selectedItems.get(0).date = FTxtName.getText();

                FTxtName.setText(FTxtName.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information Dialog \"" + App.getString("changeName") + "\"");
            alert.setHeaderText(null);
            alert.setContentText(App.getString("nameCannotBeChanged!"));

            alert.showAndWait();
        }
    }

    @FXML
    private void startSignUpOnVisit() {
        if(signUpCount == 0) {
            signUpCount++;
            FTxtDoctorName.clear();
            gPaneSignUp.setVisible(true);
        } else {
            signUpCount--;
            gPaneSignUp.setVisible(false);
        }
    }

    @FXML
    private void signUpOnVisit() {
        System.out.println(FTxtDoctorName.getText());
        gPaneSignUp.setVisible(false);
        signUpCount--;

        // Można dopisać metodę wysyłania do lekarza
    }

}
