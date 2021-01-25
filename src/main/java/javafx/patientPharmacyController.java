package javafx;

import database.DbConnector;
import database.DbStatements;
import javafx.Medicines.MedicinesFx;
import javafx.Medicines.MedicinesModel;
import javafx.Patient.PatientController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class patientPharmacyController {
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
    private TableColumn<MedicinesFx, String> alternativeColumn;
    @FXML
    private TableColumn<MedicinesFx, MedicinesFx> imageColumn;
    @FXML
    private TableColumn<MedicinesFx, MedicinesFx> compositionColumn;
    @FXML
    private TableColumn<MedicinesFx, MedicinesFx> addColumn;
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
    private TextField urlTextAddition;
    @FXML
    private Button addMedicineButton;
    //koszyk
    @FXML
    private TableView<MedicinesFx> medicinesShopTab;
    @FXML
    private TableColumn<MedicinesFx, String> idShopColumn;
    @FXML
    private TableColumn<MedicinesFx, String> nameShopColumn;
    @FXML
    private TableColumn<MedicinesFx, String> priceShopColumn;
    @FXML
    private TableColumn<MedicinesFx, String> prescriptionShopColumn;
    @FXML
    private TableColumn<MedicinesFx, String> quantityShopColumn;
    @FXML
    private TableColumn<MedicinesFx, String> availabilityShopColumn;
    @FXML
    private TableColumn<MedicinesFx, MedicinesFx> removeShopColumn;
    @FXML
    private Text totalPrice;

    private MedicinesModel medicinesModelList;
    private MedicinesModel medicinesModelShopList;
    Connection c = DbConnector.connect();
    private List<PatientController.Prescription> patientPrescriptionList=new ArrayList<PatientController.Prescription>();

    @FXML
    void initialize(){
        //załadowanie tabeli
        this.idColumn.setCellValueFactory(cellData->cellData.getValue().idProperty().asString());
        this.nameColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        this.priceColumn.setCellValueFactory(cellData->cellData.getValue().priceProperty().asString());
        this.prescriptionColumn.setCellValueFactory(cellData->cellData.getValue().prescriptionProperty().asString());
        this.quantityColumn.setCellValueFactory(cellData->cellData.getValue().quantityProperty().asString());
        this.alternativeColumn.setCellValueFactory(cellData->cellData.getValue().alternativeProperty().asString());
        this.medicinesModelList=new MedicinesModel();
        this.medicinesTab.setItems(this.medicinesModelList.getMedicinesFxObservableList());
        this.medicinesModelList.nameSearchTable(c,"");

        //koszyk
        this.idShopColumn.setCellValueFactory(cellData->cellData.getValue().idProperty().asString());
        this.nameShopColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        this.priceShopColumn.setCellValueFactory(cellData->cellData.getValue().priceProperty().asString());
        this.prescriptionShopColumn.setCellValueFactory(cellData->cellData.getValue().prescriptionProperty().asString());
        this.availabilityShopColumn.setCellValueFactory(cellData->cellData.getValue().quantityProperty().asString());
        this.quantityShopColumn.setCellValueFactory(cellData->cellData.getValue().shopQuantityProperty().asString());
        this.medicinesModelShopList=new MedicinesModel();
        this.medicinesShopTab.setItems(this.medicinesModelShopList.getMedicinesFxObservableList());
        totalPrice.setText("0.00");

        //edycja kolumny w koszyku
        this.quantityShopColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.medicinesShopTab.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            this.medicinesModelShopList.setMedicinesFxObjectPropertyEdit(newValue);
        });

        //dodawanie do koszyka
        this.addColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

        this.addColumn.setCellFactory(param -> new TableCell<MedicinesFx, MedicinesFx>(){
            Button addButton=createButton("+");
            @Override
            protected void updateItem(MedicinesFx medicine, boolean empty) {
                super.updateItem(medicine, empty);

                if(empty)
                    setGraphic(null);
                if(!empty){
                    setGraphic(addButton);
                    setAlignment(Pos.CENTER);
                    addButton.setOnAction(event ->{
                        System.out.println("Dodaje lek o id: "+ medicine.getId());
                        medicine.setShopQuantity(1);
                        medicinesModelShopList.getMedicinesFxObservableList().remove(medicine);
                        medicinesModelShopList.getMedicinesFxObservableList().add(medicine);
                        totalPrice.setText(medicinesModelShopList.shopTotalPriceUpdate());
                    });
                }
            }
        });

        //usuwanie z koszyka
        this.removeShopColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

        this.removeShopColumn.setCellFactory(param -> new TableCell<MedicinesFx, MedicinesFx>(){
            Button removeButton=createButton("-");
            @Override
            protected void updateItem(MedicinesFx medicine, boolean empty) {
                super.updateItem(medicine, empty);

                if(empty)
                    setGraphic(null);
                if(!empty){
                    setGraphic(removeButton);
                    setAlignment(Pos.CENTER);
                    removeButton.setOnAction(event ->{
                        System.out.println("Usuwam lek o id: "+ medicine.getId());
                        medicinesModelShopList.getMedicinesFxObservableList().remove(medicine);
                        totalPrice.setText(medicinesModelShopList.shopTotalPriceUpdate());
                    });
                }
            }
        });

        //image
        this.imageColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

        this.imageColumn.setCellFactory(param -> new TableCell<MedicinesFx, MedicinesFx>(){
            Button imgButton=createButton("img");
            @Override
            protected void updateItem(MedicinesFx medicine, boolean empty) {
                super.updateItem(medicine, empty);

                if(empty)
                    setGraphic(null);
                if(!empty){
                    setGraphic(imgButton);
                    setAlignment(Pos.CENTER);
                    imgButton.setOnAction(event ->{
                        System.out.println("Wyświetlam grafikę dla leku o id: "+ medicine.getId());
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("imageShow_pane.fxml"));
                        fxmlLoader.setResources(App.getBundle());
                        Scene scene=null;
                        try {
                            scene=new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageShowEditController cont=fxmlLoader.getController();
                        cont.getMedicinesModel().setMedicinesFxObjectProperty(medicine);
                        cont.ImageMedLoad();
                        Stage stage=new Stage();
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    });
                }
            }
        });

        //composition
        this.compositionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

        this.compositionColumn.setCellFactory(param -> new TableCell<MedicinesFx, MedicinesFx>(){
            Button txtButton=createButton("txt");
            @Override
            protected void updateItem(MedicinesFx medicine, boolean empty) {
                super.updateItem(medicine, empty);

                if(empty)
                    setGraphic(null);
                if(!empty){
                    setGraphic(txtButton);
                    setAlignment(Pos.CENTER);
                    txtButton.setOnAction(event ->{
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("compositionShow_pane.fxml"));
                        fxmlLoader.setResources(App.getBundle());
                        Scene scene=null;
                        try {
                            scene=new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        compositionShowEditController cont=fxmlLoader.getController();
                        cont.getMedicinesModel().setMedicinesFxObjectProperty(medicine);
                        cont.compositionMedLoad();
                        Stage stage=new Stage();
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    });
                }
            }
        });

        try {
            ResultSet rs=DbStatements.getPrescriptionData(c, PatientController.getCurr_patient().id);
            while (rs.next()) {
                System.out.println(rs.getInt("id_medicine"));
                patientPrescriptionList.add(new PatientController.Prescription(rs.getInt("id"), rs.getInt("id_personel"),
                        rs.getInt("id_medicine"), 0, rs.getString("name"),
                        rs.getDate("end_date").toString(), rs.getInt("amount")));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void SearchAction(){
        if(idSearchField.getText().isEmpty()){
            System.out.println("Search: "+nameSearchField.getText());
            this.medicinesModelList.nameSearchTable(c,nameSearchField.getText());
        }else if(!idSearchField.getText().isEmpty()){
            nameSearchField.clear();
            this.medicinesModelList.idSearchTable(c,Integer.parseInt(idSearchField.getText()));
        }
    }
    //edycja tabeli z koszyka
    public void OnEditCommitShopQuantity(TableColumn.CellEditEvent<MedicinesFx, String> medicinesFxIntegerCellEditEvent){
        if(Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue())>=1 && Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue())<=this.medicinesModelShopList.getMedicinesFxObjectPropertyEdit().getQuantity()){
            this.medicinesModelShopList.getMedicinesFxObjectPropertyEdit().setShopQuantity(Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue()));
            System.out.println("Zmieniono Quantity w koszyku: "+medicinesFxIntegerCellEditEvent.getNewValue());
            totalPrice.setText(medicinesModelShopList.shopTotalPriceUpdate());
        }else if(Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue())==0){
            medicinesModelShopList.getMedicinesFxObservableList().remove(medicinesFxIntegerCellEditEvent.getRowValue());
            System.out.println("Usuniecie z koszyka przez ustawienie 0");
            totalPrice.setText(medicinesModelShopList.shopTotalPriceUpdate());
        }else{
            medicinesModelShopList.reloadTable();
        }
    }

    @FXML
    private void clearShopOnAction(){
        medicinesModelShopList.getMedicinesFxObservableList().clear();
        totalPrice.setText(medicinesModelShopList.shopTotalPriceUpdate());
    }

    @FXML
    private void back(){
        App.setRoot("patient_pane");
    }

    private Button createButton(String s){
        Button addButton= new Button();
        addButton.setText(s);

        return addButton;
    }

    @FXML
    private void buyButtonOnAction() throws SQLException {
        List<PatientController.Prescription> patientPrescriptionListMinus=new ArrayList<PatientController.Prescription>();
        boolean exists=true, sell=true;
        String medicines="";
        for(int i=0; i<medicinesModelShopList.getMedicinesFxObservableList().size(); i++){
            if(medicinesModelShopList.getMedicinesFxObservableList().get(i).isPrescription()){
                exists=false;
                for(int j=0; j<patientPrescriptionList.size(); j++){
                    if(patientPrescriptionList.get(j).id_medicine==medicinesModelShopList.getMedicinesFxObservableList().get(i).getId()
                            && patientPrescriptionList.get(j).amount>=medicinesModelShopList.getMedicinesFxObservableList().get(i).getShopQuantity()){
                        patientPrescriptionListMinus.add(new PatientController.Prescription(patientPrescriptionList.get(j).id, 0,
                                patientPrescriptionList.get(j).id_medicine, 0, patientPrescriptionList.get(j).name,
                                patientPrescriptionList.get(j).date, patientPrescriptionList.get(j).amount-medicinesModelShopList.getMedicinesFxObservableList().get(i).getShopQuantity()));
                        exists=true;
                        break;
                    }
                }
                if(!exists){
                    medicines+=medicinesModelShopList.getMedicinesFxObservableList().get(i).getName()+", ";
                }
            }
        }
        if(!exists){
            //tutaj wyświetli błąd i nie pozwoli sprzedać
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog \"" + App.getString("prescription") + "\"");
            alert.setHeaderText(null);
            alert.setContentText(App.getString("noPrescription")+medicines);
            alert.showAndWait();
            sell=false;
        }

        if(sell){
            System.out.println("Sprzedaje leki");
            String content="";
            if(Locale.getDefault().getDisplayLanguage()=="polski"){
                content="Sprzedawca: MedApp Sp.Z.O.O\nKupujący: "+PatientController.getCurr_patient().first_name+" "+PatientController.getCurr_patient().last_name+"\n\nNazwa\tIlość\tCena\n\n";
            }else{
                content="Seller: MedApp Sp.Z.O.O\nBuyer: "+PatientController.getCurr_patient().first_name+" "+PatientController.getCurr_patient().last_name+"\n\nName\tAmount\tPrice\n\n";
            }

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            for(int i=0; i<medicinesModelShopList.getMedicinesFxObservableList().size(); i++){
                content+=medicinesModelShopList.getMedicinesFxObservableList().get(i).getName()+"\t"+medicinesModelShopList.getMedicinesFxObservableList().get(i).getShopQuantity()+"\t"+medicinesModelShopList.getMedicinesFxObservableList().get(i).getShopQuantity()*medicinesModelShopList.getMedicinesFxObservableList().get(i).getPrice()+"\n";
                medicinesModelShopList.getMedicinesFxObservableList().get(i).setQuantity(medicinesModelShopList.getMedicinesFxObservableList().get(i).getQuantity()-medicinesModelShopList.getMedicinesFxObservableList().get(i).getShopQuantity());
                medicinesModelShopList.getMedicinesFxObservableList().get(i).setSold(medicinesModelShopList.getMedicinesFxObservableList().get(i).getSold()+medicinesModelShopList.getMedicinesFxObservableList().get(i).getShopQuantity());
            }
            for (int j = 0; j < patientPrescriptionListMinus.size(); j++) {
                DbStatements.updatePrescriptionAmount(c, patientPrescriptionListMinus.get(j).id, patientPrescriptionListMinus.get(j).amount);
            }
            medicinesModelShopList.sellMed(c);
            patientPrescriptionList.clear();
            medicinesModelShopList.getMedicinesFxObservableList().clear();
            if(Locale.getDefault().getDisplayLanguage()=="polski"){
                content+="\n\nSuma: "+totalPrice.getText()+" PLN";
            }else{
                content+="\n\nTotal: "+totalPrice.getText()+" PLN";
            }
            totalPrice.setText("0.00");

            try{
                File file = fileChooser.showSaveDialog(null);
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println(content);
                writer.close();
            }catch (FileNotFoundException ex){
                System.out.println(ex);
            }catch(RuntimeException ex){
                System.out.println(ex);
            }
        }
    }
}
