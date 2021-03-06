package javafx.Patient;

import database.DbConnector;
import database.DbStatements;
import javafx.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    static Connection c = DbConnector.connect();
    ObservableList list = FXCollections.observableArrayList();
    static Patient curr_patient;
    int curr_list = 0;
    int adsIndex = 0;
    int lastChangeCount = 0;
    int referralCount = 0;

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
    private Button btnStartSignUp;
    @FXML
    private TextField FTxtPersonelName;

    //Klasa Wizyty
    private class Visit {
        public int id;      //index w bazie
        public int index;   //index w liscie
        public int id_personel;
        public String name_personel;
        public String name;
        public String change_name;
        public String date;
        public String change_date;
        public float cost;

        public Visit(int id, int index, int id_personel, String name, String change_name, String date, String change_date, float cost) {
            this.id = id;
            this.index = index;
            this.id_personel = id_personel;
            try {
                this.name_personel = DbStatements.getPersonelName(c, id_personel);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
    public static class Prescription {
        public int id;          //index w bazie
        public int index;       //index w liscie
        public String name_personel;
        public final int id_medicine;
        public String name_medicine;
        private float cost_medicine;
        public String name;
        public String date;
        public float cost;
        public int amount;

        public Prescription(int id, int id_personel, int id_medicine, int index, String name, String date, int amount) {
            this.id = id;
            this.index = index;
            try {
                this.name_personel = DbStatements.getPersonelName(c, id_personel);
                ResultSet rs = DbStatements.getMedicineData(c, id_medicine);
                while(rs.next()) {
                    this.name_medicine = rs.getString("name");
                    this.cost_medicine = rs.getFloat("price");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            this.id_medicine = id_medicine;
            this.name = name;
            this.date = date;
            this.amount  = amount ;
            cost = (float) (Math.round(cost_medicine * amount * 100.0) / 100.0);
        }

        @Override
        public String toString() {
            return (String.format("%03d", this.index) + " - " + this.name + " - " + this.name_medicine + " * " + this.amount);
        }
    }

    //Klasa pacjent
    public static class Patient {
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
        lastChangeCount = 0;
        curr_list = 0;
        referralCount = 0;

        vpList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        randomAds();

        try {
            ResultSet rs = DbStatements.getPatientData(c);
            while (rs.next()) {
                curr_patient = new Patient(rs.getInt("id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getDate("birth_date").toString(),
                        rs.getString("sex"), rs.getString("health").replace("\\n", "\n"));
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
            float sum = 0;
            for(Visit o : selectedItems){
                sum += o.cost;
            }
            FTxtAmount.setText(String.valueOf(sum));
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
            //doctor name
            if(selectedItems.size() > 1)
                FTxtPersonelName.setText(App.getString("moreThanOneSelected!"));
            else if(selectedItems.size() == 0)
                FTxtPersonelName.setText(App.getString("selectVisit"));
            else
                FTxtPersonelName.setText(selectedItems.get(0).name_personel);
        } else {
            ObservableList<Prescription> selectedItems = vpList.getSelectionModel().getSelectedItems();
            //cost
            float sum = 0;
            for(Prescription o : selectedItems){
                sum += o.cost;
            }
            FTxtAmount.setText(String.valueOf(sum));
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
            //doctor name
            if(selectedItems.size() > 1)
                FTxtPersonelName.setText(App.getString("moreThanOneSelected!"));
            else if(selectedItems.size() == 0)
                FTxtPersonelName.setText(App.getString("selectPrescription"));
            else
                FTxtPersonelName.setText(selectedItems.get(0).name_personel);
        }
    }

    @FXML
    private void changeOnVisits() {
        txtList.setText(App.getString("visits") + ":");
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
        FTxtPersonelName.setText(App.getString("selectVisit"));
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
        FTxtName.setEditable(false);
        FTxtDate.setEditable(false);
        FTxtName.setText(App.getString("selectPrescription"));
        FTxtAmount.setText(App.getString("selectPrescription"));
        FTxtDate.setText(App.getString("selectPrescription"));
        FTxtPersonelName.setText(App.getString("selectPrescription"));
        curr_list = 1;
        loadPrescriptions();
    }

    private void loadVisits() {
        list.clear();
        vpList.getItems().clear();
        int index = 0;

        try {
            ResultSet rs = DbStatements.getVisitData(c, curr_patient.id);
            while (rs.next()) {
                String stringCHD = "";
                Date change_date = rs.getDate("change_date");
                if(change_date != null)
                    stringCHD = change_date.toString();
                Visit v = new Visit(rs.getInt("id"), index, rs.getInt("id_personel"),
                        rs.getString("visit_name"), rs.getString("change_name"),
                        rs.getDate("visit_date").toString(), stringCHD, rs.getFloat("cost"));
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
            ResultSet rs = DbStatements.getPrescriptionData(c, curr_patient.id);
            while (rs.next()) {
                Prescription v = new Prescription(rs.getInt("id"), rs.getInt("id_personel"),
                        rs.getInt("id_medicine"), index, rs.getString("name"),
                        rs.getDate("end_date").toString(), rs.getInt("amount"));
                list.add(v);
                index++;
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        vpList.getItems().addAll(list);
    }

    //------------------------ Display last changes in visit -------------------------

    @FXML
    private void displayLastChange() {
        ObservableList<Visit> selectedItems = vpList.getSelectionModel().getSelectedItems();

        if(lastChangeCount == 0) {
            if (selectedItems.size() == 1) {
                if (!selectedItems.get(0).change_name.isEmpty() && selectedItems.get(0).change_name != null)
                    FTxtName.setText(selectedItems.get(0).change_name);
                else
                    FTxtName.setText(selectedItems.get(0).name);

                if (!selectedItems.get(0).change_date.isEmpty() && selectedItems.get(0).change_date != null)
                    FTxtDate.setText(selectedItems.get(0).change_date);
                else
                    FTxtDate.setText(selectedItems.get(0).date);
            } else if (selectedItems.size() == 0) {
                FTxtAmount.setText(App.getString("selectVisit"));
            } else {
                FTxtAmount.setText(App.getString("moreThanOneSelected!"));
            }
        } else {
            if (selectedItems.size() == 1) {
                FTxtName.setText(selectedItems.get(0).name);
                FTxtDate.setText(selectedItems.get(0).date);
            }
        }
        lastChangeCount = (lastChangeCount + 1)%2;
    }

    // -------------------------------- Pay for visits or prescriptions -------------------------

    @FXML
    private void payForObject() {
        if (curr_list == 0) {
            ObservableList<Visit> selectedItems = vpList.getSelectionModel().getSelectedItems();

            if (selectedItems.size() == 1) {
                try {
                    System.out.println("Paid");
                    DbStatements.updateVisitPaid(c, selectedItems.get(0).id, selectedItems.get(0).cost);
                    DbStatements.updateVisitCost(c, selectedItems.get(0).id, (float) 0);
                    selectedItems.get(0).cost = 0;
                    FTxtAmount.setText(String.valueOf(selectedItems.get(0).cost));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else if (selectedItems.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning \"" + App.getString("pay") + "\"");
                alert.setHeaderText(null);
                alert.setContentText(App.getString("chooseTheOption!"));

                alert.showAndWait();
            } else {
                try {
                    System.out.println("Paid");
                    for (Visit v : selectedItems) {
                        DbStatements.updateVisitPaid(c, v.id, v.cost);
                        DbStatements.updateVisitCost(c, v.id, (float) 0);
                        v.cost = 0;
                    }
                    FTxtAmount.setText("0.0");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            ObservableList<Prescription> selectedItems = vpList.getSelectionModel().getSelectedItems();

            if (selectedItems.size() == 1) {
                try {
                    if(DbStatements.updateMedicineQuantity(c, selectedItems.get(0).id_medicine, selectedItems.get(0).amount)) {
                        System.out.println("Paid");
                        DbStatements.updatePrescriptionAmount(c, selectedItems.get(0).id, 0);
                        selectedItems.get(0).cost = 0;
                        selectedItems.get(0).amount = 0;
                        FTxtAmount.setText("0.0");
                    } else  {
                        FTxtAmount.setText(App.getString("noDrug!"));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else if (selectedItems.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning \"" + App.getString("pay") + "\"");
                alert.setHeaderText(null);
                alert.setContentText(App.getString("chooseTheOption!"));

                alert.showAndWait();
            } else {
                try {
                    float sum = 0;
                    for (Prescription v : selectedItems) {
                        if(DbStatements.updateMedicineQuantity(c, v.id_medicine, v.amount)) {
                            System.out.println("Paid");
                            DbStatements.updatePrescriptionAmount(c, v.id, 0);
                            v.cost = 0;
                            v.amount = 0;
                        }
                        sum += v.cost;
                    }
                    FTxtAmount.setText(String.valueOf(sum));
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
                alert.setTitle("Warning \"" + App.getString("payForAll") + "\"");
                alert.setHeaderText(null);
                alert.setContentText(App.getString("chooseTheOption!"));

                alert.showAndWait();
            } else {
                try {
                    System.out.println("Paid");
                    for (Visit v : selectedItems) {
                        DbStatements.updateVisitPaid(c, v.id, v.cost);
                        DbStatements.updateVisitCost(c, v.id, (float) 0);
                        v.cost = 0;
                    }
                    FTxtAmount.setText("0.0");
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
                alert.setTitle("Warning \"" + App.getString("payForAll") + "\"");
                alert.setHeaderText(null);
                alert.setContentText(App.getString("chooseTheOption!"));

                alert.showAndWait();
            } else {
                try {
                    float sum = 0;
                    for (Prescription v : selectedItems) {
                        if(DbStatements.updateMedicineQuantity(c, v.id_medicine, v.amount)) {
                            System.out.println("Paid");
                            DbStatements.updatePrescriptionAmount(c, v.id, 0);
                            v.cost = 0;
                            v.amount = 0;
                        }
                        sum += v.cost;
                    }
                    FTxtAmount.setText(String.valueOf(sum));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // ---------------- Change visit name and date ------------------

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
            alert.setTitle("Warning \"" + App.getString("changeDate") + "\"");
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
                selectedItems.get(0).change_name = selectedItems.get(0).name;
                selectedItems.get(0).name = FTxtName.getText();

                FTxtName.setText(FTxtName.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning \"" + App.getString("changeName") + "\"");
            alert.setHeaderText(null);
            alert.setContentText(App.getString("nameCannotBeChanged!"));

            alert.showAndWait();
        }
    }

    // ---------------------- Sing up on visit ---------------------------

    private void openWindow(String fxml_name, String window_name) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml_name));
        fxmlLoader.setResources(App.getBundle());
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(App.getString(window_name));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void displaySignUpOnVisit() {
        openWindow("signUpVisit_window.fxml", "visits");
    }

    //--------------- Display referral ----------------------

    @FXML
    private void displayReferral() {
        openWindow("referral_window.fxml", "referrals");
    }

    // ------------------------- Go to apothecary -----------------------------

    @FXML
    private void goToApothecary() {
        App.setRoot("patientPharmacy_pane");
    }

    // --------- Display doctors window --------------

    @FXML
    private void displayDoctors() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("doctors_window.fxml"));
        fxmlLoader.setResources(App.getBundle());
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(App.getString("guestButtonDoctors"));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Patient getCurr_patient() {
        return curr_patient;
    }
}
