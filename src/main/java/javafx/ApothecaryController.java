package javafx;

import javafx.Medicines.Medicines;
import javafx.Medicines.MedicinesFx;
import javafx.Medicines.MedicinesModel;
import database.DbConnector;
import database.DbStatements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ApothecaryController {
//tabela leków
    @FXML
    private TableView<MedicinesFx> medicinesTab;
    @FXML
    private TableColumn<MedicinesFx, String> idColumn;
    @FXML
    private TableColumn<MedicinesFx, String> nameColumn;
    @FXML
    private TableColumn<MedicinesFx, String> priceColumn;
    @FXML
    private TableColumn<MedicinesFx, String> prescriptionColumn;
    @FXML
    private TableColumn<MedicinesFx, String> quantityColumn;
    @FXML
    private TableColumn<MedicinesFx, String> orderedColumn;
    @FXML
    private TableColumn<MedicinesFx, String> soldColumn;
    @FXML
    private TableColumn<MedicinesFx, String> returnsColumn;
    @FXML
    private TableColumn<MedicinesFx, String> disposedOfColumn;
    @FXML
    private TableColumn<MedicinesFx, String> alternativeColumn;
    @FXML
    private TableColumn<MedicinesFx, String> imageColumn;
    @FXML
    private TableColumn<MedicinesFx, String> updateColumn;
    @FXML
    private TableColumn<MedicinesFx, String> addColumn;
//wyszukiwanie leku
    @FXML
    private Button searchButton;
    @FXML
    private TextField nameSearchField;
    @FXML
    private TextField idSearchField;
//dodawanie leku
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
    @FXML
    private Button addMedicineButton;

    ObservableList<Boolean> prescriptionList = FXCollections.observableArrayList(true, false);

    private Medicines MedicinesAddition;
    private MedicinesModel medicinesModelList;

    @FXML
    void initialize(){
        //dodawanie leków
        prescriptionChoiceAddition.setItems(prescriptionList);
        this.MedicinesAddition=new Medicines();
        this.addMedicineButton.disableProperty().bind(this.nameTextAddition.textProperty().isEmpty().or(this.prescriptionChoiceAddition.valueProperty().isNull()));

        //załadowanie tabeli
        this.idColumn.setCellValueFactory(cellData->cellData.getValue().idProperty().asString());
        this.nameColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        this.priceColumn.setCellValueFactory(cellData->cellData.getValue().priceProperty().asString());
        this.prescriptionColumn.setCellValueFactory(cellData->cellData.getValue().prescriptionProperty().asString());
        this.quantityColumn.setCellValueFactory(cellData->cellData.getValue().quantityProperty().asString());
        this.orderedColumn.setCellValueFactory(cellData->cellData.getValue().orderedProperty().asString());
        this.soldColumn.setCellValueFactory(cellData->cellData.getValue().soldProperty().asString());
        this.returnsColumn.setCellValueFactory(cellData->cellData.getValue().returnsProperty().asString());
        this.disposedOfColumn.setCellValueFactory(cellData->cellData.getValue().disposed_ofProperty().asString());
        this.alternativeColumn.setCellValueFactory(cellData->cellData.getValue().alternativeProperty().asString());
        this.medicinesModelList=new MedicinesModel();
        this.medicinesTab.setItems(this.medicinesModelList.getMedicinesFxObservableList());
        this.medicinesModelList.nameSearchTable("");
    }

    @FXML
    private void SearchAction(){
        Connection c = DbConnector.connect();
        if(idSearchField.getText().isEmpty()){
            System.out.println("Search: "+nameSearchField.getText());
            this.medicinesModelList.nameSearchTable(nameSearchField.getText());
        }else if(!idSearchField.getText().isEmpty()){
            nameSearchField.clear();
            this.medicinesModelList.idSearchTable(Integer.parseInt(idSearchField.getText()));
        }
    }

    @FXML
    private void addMedicineAction(){
        this.MedicinesAddition.setName(this.nameTextAddition.getText());
        if(!this.priceTextAddition.getText().isEmpty())
            this.MedicinesAddition.setPrice(Float.parseFloat(this.priceTextAddition.getText()));
        if(!this.quantityTextAddition.getText().isEmpty())
            this.MedicinesAddition.setAlternative(Integer.parseInt(this.alternativeTextAddition.getText()));
        if(!this.alternativeTextAddition.getText().isEmpty())
            this.MedicinesAddition.setQuantity(Integer.parseInt(this.quantityTextAddition.getText()));
        if(this.prescriptionChoiceAddition.getValue()!=null){
            System.out.println(this.prescriptionChoiceAddition.getValue().equals(true));
            this.MedicinesAddition.setPrescription(this.prescriptionChoiceAddition.getValue().equals(true));
        }

        System.out.println("Ilosc lekow w bazie danych "+medicinesModelList.getMedicinesFxObservableList().stream().count());
        if(this.MedicinesAddition.getAlternative()<1 || this.MedicinesAddition.getAlternative()>medicinesModelList.getMedicinesFxObservableList().stream().count()){
            System.out.println("Podano nie prawidłową alternatywę");
            this.MedicinesAddition.setAlternative(-1);
        }


        System.out.println(this.MedicinesAddition.getName()+
                " "+this.MedicinesAddition.getPrice()+
                " "+this.MedicinesAddition.getQuantity()+
                " "+this.MedicinesAddition.getAlternative()+
                " "+this.MedicinesAddition.isPrescription());

        this.MedicinesAddition.addMedicineInDataBase();
        this.nameTextAddition.clear();
        this.priceTextAddition.clear();
        this.quantityTextAddition.clear();
        this.alternativeTextAddition.clear();
        this.medicinesModelList.nameSearchTable("");
    }

}
