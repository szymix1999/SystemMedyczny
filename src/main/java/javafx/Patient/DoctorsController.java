package javafx.Patient;

import database.DbStatements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorsController {

    ObservableList<Doctor> doctorsList = FXCollections.observableArrayList();

    @FXML
    private ListView<Doctor> doctorsListView;

    public static class Doctor {
        public int id;
        public String first_name;
        public String last_name;
        public String profession;

        public Doctor(int id, String first_name, String last_name, String profession) {
            this.id = id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.profession = profession;
        }

        @Override
        public String toString() {
            return profession + " - " + first_name + " " + last_name;
        }

    }

    @FXML
    private void initialize() {
        doctorsList.clear();
        doctorsListView.getItems().clear();

        try {
            ResultSet rs = DbStatements.getOnlyDoctors(PatientController.c);
            while(rs.next()) {
                ResultSet rsP = DbStatements.getDoctorData(PatientController.c, rs.getInt("id"));
                //System.out.println("Doctor id: " + rs.getInt("id"));
                while(rsP.next()) {
                    Doctor d = new Doctor(rsP.getInt("id"), rsP.getString("first_name"),
                            rsP.getString("last_name"), rsP.getString("profession"));
                    doctorsList.add(d);
                }
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        doctorsListView.getItems().addAll(doctorsList);
    }

}
