package javafx.Patient;

import database.DbStatements;
import javafx.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SignUpVisitController {

    ObservableList<DoctorsController.Doctor> doctorsList = FXCollections.observableArrayList();

    @FXML
    private TextField FTxtName;
    @FXML
    private ChoiceBox<DoctorsController.Doctor> choiceBoxDoctor;
    @FXML
    private TextField FTxtDate;

    @FXML
    private void initialize() {
        doctorsList.clear();
        choiceBoxDoctor.getItems().clear();

        try {
            ResultSet rs = DbStatements.getOnlyDoctors(PatientController.c);
            while(rs.next()) {
                ResultSet rsP = DbStatements.getDoctorData(PatientController.c, rs.getInt("id"));
                System.out.println("Doctor id: " + rs.getInt("id"));
                while(rsP.next()) {
                    DoctorsController.Doctor d = new DoctorsController.Doctor(rsP.getInt("id"), rsP.getString("first_name"),
                            rsP.getString("last_name"), rsP.getString("profession"));
                    doctorsList.add(d);
                }
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }

        choiceBoxDoctor.setItems(doctorsList);
    }

    @FXML
    private void sendSignVisit() {
        if(FTxtName.getText().equals("") && choiceBoxDoctor.getValue() != null && FTxtDate.getText().equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            try {
                java.util.Date utilDate = format.parse(FTxtDate.getText());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                DbStatements.addVisit(PatientController.c, PatientController.curr_patient.id, choiceBoxDoctor.getValue().id,
                        FTxtName.getText(), null, sqlDate, null, 0, -1);

                System.out.println("Visit added.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ParseException ex) {
                FTxtDate.clear();

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning \"" + App.getString("visits") + "\"");
                alert.setHeaderText(null);
                alert.setContentText(App.getString("badDataFormat"));

                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning \"" + App.getString("visits") + "\"");
            alert.setHeaderText(null);
            alert.setContentText(App.getString("couldSendVisit"));

            alert.showAndWait();
        }
    }
}
