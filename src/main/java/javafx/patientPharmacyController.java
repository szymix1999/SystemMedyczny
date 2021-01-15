package javafx;

import database.DbConnector;
import javafx.Medicines.MedicinesFx;
import javafx.Medicines.MedicinesModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

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
        App.setRoot("guest_pane");
    }

    private Button createButton(String s){
        Button addButton= new Button();
        addButton.setText(s);

        return addButton;
    }

}
