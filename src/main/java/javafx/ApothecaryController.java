package javafx;

import javafx.Medicines.Medicines;
import javafx.Medicines.MedicinesFx;
import javafx.Medicines.MedicinesModel;
import database.DbConnector;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ApothecaryController{
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

    ObservableList<Boolean> prescriptionList = FXCollections.observableArrayList(true, false);

    private Medicines MedicinesAddition;
    private MedicinesModel medicinesModelList;
    private MedicinesModel medicinesModelShopList;

    Connection c = DbConnector.connect();

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
        this.medicinesModelList.nameSearchTable(c,"");

        //edycja kolumn
        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.prescriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.orderedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.soldColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.returnsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.disposedOfColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.alternativeColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.medicinesTab.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            this.medicinesModelList.setMedicinesFxObjectPropertyEdit(newValue);
        });

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
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("imageShowEdit_pane.fxml"));
                        fxmlLoader.setResources(App.getBundle());
                        Scene scene=null;
                        try {
                            scene=new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageShowEditController cont=fxmlLoader.getController();
                        cont.getMedicinesModel().setMedicinesFxObjectProperty(medicine);
                        cont.setC(c);
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
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("compositionShowEdit_pane.fxml"));
                        fxmlLoader.setResources(App.getBundle());
                        Scene scene=null;
                        try {
                            scene=new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        compositionShowEditController cont=fxmlLoader.getController();
                        cont.getMedicinesModel().setMedicinesFxObjectProperty(medicine);
                        cont.setC(c);
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

    @FXML
    private void addMedicineAction(){
        this.MedicinesAddition.setName(this.nameTextAddition.getText());
        if(!this.priceTextAddition.getText().isEmpty())
            this.MedicinesAddition.setPrice(Float.parseFloat(this.priceTextAddition.getText()));
        if(!this.quantityTextAddition.getText().isEmpty())
            this.MedicinesAddition.setQuantity(Integer.parseInt(this.quantityTextAddition.getText()));
        if(!this.alternativeTextAddition.getText().isEmpty())
            this.MedicinesAddition.setAlternative(Integer.parseInt(this.alternativeTextAddition.getText()));
        if(!this.urlTextAddition.getText().isEmpty())
            this.MedicinesAddition.setImage(this.urlTextAddition.getText());
        if(this.prescriptionChoiceAddition.getValue()!=null){
            System.out.println(this.prescriptionChoiceAddition.getValue().equals(true));
            this.MedicinesAddition.setPrescription(this.prescriptionChoiceAddition.getValue().equals(true));
        }

        System.out.println("Ilosc lekow w bazie danych "+medicinesModelList.getMedicinesFxObservableList().stream().count());
        if(this.MedicinesAddition.getAlternative()<1 || this.MedicinesAddition.getAlternative()>medicinesModelList.getMedicinesFxObservableList().stream().count()){
            System.out.println("Podano nie prawidłową alternatywę");
            this.MedicinesAddition.setAlternative(-1);
        }


        System.out.println("Name: "+this.MedicinesAddition.getName()+
                " Price: "+this.MedicinesAddition.getPrice()+
                " Quantity: "+this.MedicinesAddition.getQuantity()+
                " Alternative: "+this.MedicinesAddition.getAlternative()+
                " Prescription: "+this.MedicinesAddition.isPrescription()+
                " Img: "+this.MedicinesAddition.getImage()+
                " Composition: "+this.MedicinesAddition.getComposition());

        this.MedicinesAddition.addMedicineInDataBase(c);
        this.nameTextAddition.clear();
        this.priceTextAddition.clear();
        this.quantityTextAddition.clear();
        this.alternativeTextAddition.clear();
        this.urlTextAddition.clear();
        MedicinesAddition.setComposition("");
        this.medicinesModelList.nameSearchTable(c,"");
    }

    @FXML
    private void compositionAddButtonOnAction(){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("compositionShowEdit_pane.fxml"));
        fxmlLoader.setResources(App.getBundle());
        Scene scene=null;
        try {
            scene=new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        compositionShowEditController cont=fxmlLoader.getController();
        cont.setMed(MedicinesAddition);
        cont.compositionMedLoad();
        cont.setC(c);
        cont.compositionMedLoad();
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void OnEditCommitName(TableColumn.CellEditEvent<MedicinesFx, String> medicinesFxStringCellEditEvent){
        this.medicinesModelList.getMedicinesFxObjectPropertyEdit().setName(medicinesFxStringCellEditEvent.getNewValue());
        System.out.println("Zmieniono name: "+medicinesFxStringCellEditEvent.getNewValue());
        this.medicinesModelList.editTable(c);
    }
    public void OnEditCommitPrice(TableColumn.CellEditEvent<MedicinesFx, String> medicinesFxFloatCellEditEvent){
        this.medicinesModelList.getMedicinesFxObjectPropertyEdit().setPrice(Float.parseFloat(medicinesFxFloatCellEditEvent.getNewValue()));
        System.out.println("Zmieniono Price: "+medicinesFxFloatCellEditEvent.getNewValue());
        this.medicinesModelList.editTable(c);
    }
    public void OnEditCommitPrescription(TableColumn.CellEditEvent<MedicinesFx, String> medicinesFxIntegerCellEditEvent){
        this.medicinesModelList.getMedicinesFxObjectPropertyEdit().setPrescription(medicinesFxIntegerCellEditEvent.getNewValue().equals("true"));
        System.out.println("Zmieniono Prescription: "+medicinesFxIntegerCellEditEvent.getNewValue());
        this.medicinesModelList.editTable(c);
    }
    public void OnEditCommitQuantity(TableColumn.CellEditEvent<MedicinesFx, String> medicinesFxIntegerCellEditEvent){
        this.medicinesModelList.getMedicinesFxObjectPropertyEdit().setQuantity(Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue()));
        System.out.println("Zmieniono Quantity: "+medicinesFxIntegerCellEditEvent.getNewValue());
        this.medicinesModelList.editTable(c);
    }
    public void OnEditCommitOrdered(TableColumn.CellEditEvent<MedicinesFx, String> medicinesFxIntegerCellEditEvent){
        this.medicinesModelList.getMedicinesFxObjectPropertyEdit().setOrdered(Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue()));
        System.out.println("Zmieniono Ordered: "+medicinesFxIntegerCellEditEvent.getNewValue());
        this.medicinesModelList.editTable(c);
    }
    public void OnEditCommitSold(TableColumn.CellEditEvent<MedicinesFx, String> medicinesFxIntegerCellEditEvent){
        this.medicinesModelList.getMedicinesFxObjectPropertyEdit().setSold(Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue()));
        System.out.println("Zmieniono Sold: "+medicinesFxIntegerCellEditEvent.getNewValue());
        this.medicinesModelList.editTable(c);
    }
    public void OnEditCommitReturns(TableColumn.CellEditEvent<MedicinesFx, String> medicinesFxIntegerCellEditEvent){
        if(Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue())<=this.medicinesModelList.getMedicinesFxObjectPropertyEdit().getSold() && Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue())>0){
            this.medicinesModelList.getMedicinesFxObjectPropertyEdit().setQuantity(this.medicinesModelList.getMedicinesFxObjectPropertyEdit().getQuantity()+Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue()));
            this.medicinesModelList.getMedicinesFxObjectPropertyEdit().setReturns(Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue()));
            this.medicinesModelList.getMedicinesFxObjectPropertyEdit().setSold(this.medicinesModelList.getMedicinesFxObjectPropertyEdit().getSold()-Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue()));
            System.out.println("Zmieniono Returns: "+medicinesFxIntegerCellEditEvent.getNewValue());
            this.medicinesModelList.editTable(c);
        }else{
            System.out.println("Zwrócono więcej leków niż sprzedano");
            this.medicinesModelList.nameSearchTable(c,"");
        }
    }
    public void OnEditCommitDisposedOf(TableColumn.CellEditEvent<MedicinesFx, String> medicinesFxIntegerCellEditEvent){
        if(Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue())<=this.medicinesModelList.getMedicinesFxObjectPropertyEdit().getQuantity() && Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue())>0){
            this.medicinesModelList.getMedicinesFxObjectPropertyEdit().setDisposed_of(Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue()));
            System.out.println("Zmieniono DisposedOf: "+medicinesFxIntegerCellEditEvent.getNewValue());
            this.medicinesModelList.editTable(c);
            this.medicinesModelList.getMedicinesFxObjectPropertyEdit().setQuantity(this.medicinesModelList.getMedicinesFxObjectPropertyEdit().getQuantity()-Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue()));
        }
    }
    public void OnEditCommitAlternative(TableColumn.CellEditEvent<MedicinesFx, String> medicinesFxIntegerCellEditEvent){
        System.out.println("Ilosc lekow w bazie danych "+medicinesModelList.getMedicinesFxObservableList().stream().count());
        if(Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue())<1 || Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue())>medicinesModelList.getMedicinesFxObservableList().stream().count()){
            System.out.println("Podano nie prawidłową alternatywę");
            this.medicinesModelList.nameSearchTable(c,"");
        }else{
            this.medicinesModelList.getMedicinesFxObjectPropertyEdit().setAlternative(Integer.parseInt(medicinesFxIntegerCellEditEvent.getNewValue()));
            System.out.println("Zmieniono Alternative: "+medicinesFxIntegerCellEditEvent.getNewValue());
            this.medicinesModelList.editTable(c);
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

    private Button createButton(String s){
        Button addButton= new Button();
        addButton.setText(s);

        return addButton;
    }
}
