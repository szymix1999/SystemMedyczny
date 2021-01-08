package javafx;

import javafx.Medicines.MedicinesModel;
import database.DbConnector;
import database.DbStatements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApothecaryController {

    @FXML
    private Button SearchButton;

    @FXML
    private Button addMedicineButton;

    @FXML
    private TextField SearchField;

    @FXML
    private TextField nameTextAddition;

    @FXML
    private TextField priceTextAddition;

    @FXML
    private TextField quantityTextAddition;

    @FXML
    private TextField alternativeTextAddition;

    @FXML
    private ChoiceBox prescriptionChoiceAddition;

    ObservableList<Boolean> prescriptionList = FXCollections.observableArrayList(true, false);

    private MedicinesModel MedicinesAddition;

    @FXML
    void initialize(){
        Connection c = DbConnector.connect();

        //dodawanie leków
        prescriptionChoiceAddition.setItems(prescriptionList);
        this.MedicinesAddition=new MedicinesModel();
        this.MedicinesAddition.medicinesFxObjectPropertyProperty().get().nameProperty().bind(this.nameTextAddition.textProperty());
        StringConverter converter=new NumberStringConverter();
        this.priceTextAddition.textProperty().bindBidirectional(this.MedicinesAddition.medicinesFxObjectPropertyProperty().get().priceProperty(), converter);
        this.quantityTextAddition.textProperty().bindBidirectional(this.MedicinesAddition.medicinesFxObjectPropertyProperty().get().quantityProperty(), converter);
        this.alternativeTextAddition.textProperty().bindBidirectional(this.MedicinesAddition.medicinesFxObjectPropertyProperty().get().alternativeProperty(), converter);
        this.MedicinesAddition.medicinesFxObjectPropertyProperty().get().prescriptionProperty().bind(this.prescriptionChoiceAddition.valueProperty());
        this.addMedicineButton.disableProperty().bind(this.nameTextAddition.textProperty().isEmpty());

        //załadowanie tabeli
        try {
            ResultSet rs=DbStatements.SearchMedicines(c, "");
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void SearchAction() throws IOException {
        Connection c = DbConnector.connect();

        try {
            if((SearchField.getText() != null && !SearchField.getText().isEmpty())) {
                ResultSet rs=DbStatements.SearchMedicines(c, SearchField.getText());

            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }


    }

    @FXML
    private void addMedicineAction(){
        System.out.println(this.MedicinesAddition.getMedicinesFxObjectProperty().getName()+
                " "+this.MedicinesAddition.getMedicinesFxObjectProperty().getPrice()+
                " "+this.MedicinesAddition.getMedicinesFxObjectProperty().getQuantity()+
                " "+this.MedicinesAddition.getMedicinesFxObjectProperty().getAlternative()+
                " "+this.MedicinesAddition.getMedicinesFxObjectProperty().isPrescription());

        this.MedicinesAddition.addMedicineInDataBase();
        this.nameTextAddition.clear();
        this.priceTextAddition.clear();
        this.quantityTextAddition.clear();
        this.alternativeTextAddition.clear();
    }

}
