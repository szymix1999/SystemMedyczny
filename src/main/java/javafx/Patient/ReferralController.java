package javafx.Patient;

import database.DbStatements;
import javafx.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReferralController {

    File source = null;

    @FXML
    private TextArea ATxtReferralContents;
    @FXML
    private TextField FTxtReferralDate;

    @FXML
    private void addReferral() {
        if(!ATxtReferralContents.getText().equals("") && !FTxtReferralDate.getText().equals("") && source != null) {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                java.util.Date utilDate = format.parse(FTxtReferralDate.getText());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                File destination = new File("src\\main\\resources\\images\\referrals\\" +
                        DbStatements.countReferralsRows(PatientController.c) + "_" + source.getName());
                Files.copy(source.toPath(), destination.toPath());

                System.out.println("File copied. Source: " + source + " Destination: " + destination);
                DbStatements.addReferral(PatientController.c, PatientController.curr_patient.id, sqlDate, ATxtReferralContents.getText(), destination.toString());
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            } catch (ParseException ex) {
                FTxtReferralDate.clear();

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning \"" + App.getString("referrals") + "\"");
                alert.setHeaderText(null);
                alert.setContentText(App.getString("badDataFormat"));

                alert.showAndWait();
                return;
            }

            ATxtReferralContents.clear();
            FTxtReferralDate.clear();
            source = null;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog \"" + App.getString("referrals") + "\"");
            alert.setHeaderText(null);
            alert.setContentText(App.getString("ReferralAdded"));

            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning \"" + App.getString("referrals") + "\"");
            alert.setHeaderText(null);
            alert.setContentText(App.getString("couldntSendReferral"));

            alert.showAndWait();
        }
    }

    @FXML
    private void addReferralPhoto() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.JPG", "*.jpg", "*.PNG", "*.png"));

        //Show open file dialog
        source = fileChooser.showOpenDialog(null);
    }

}
