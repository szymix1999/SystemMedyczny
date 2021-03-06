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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class PharmacyGuestController {
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
    //wyszukiwanie leku
    @FXML
    private Button searchButton;
    @FXML
    private TextField nameSearchField;
    @FXML
    private TextField idSearchField;

    private MedicinesModel medicinesModelList;

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
