package javafx.Medicines;

import database.DbConnector;
import database.DbStatements;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MedicinesModel {
    private ObjectProperty<MedicinesFx> MedicinesFxObjectProperty = new SimpleObjectProperty<>(new MedicinesFx());
    private ObjectProperty<MedicinesFx> MedicinesFxObjectPropertyEdit = new SimpleObjectProperty<>(new MedicinesFx());

    private ObservableList<MedicinesFx> medicinesFxObservableList= FXCollections.observableArrayList();

    public void idSearchTable(Connection c, int id){
        medicinesFxObservableList.clear();
        try {
            ResultSet rs=DbStatements.SearchIdMedicines(c, id);
            this.showMedicinesTable(rs);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void nameSearchTable(Connection c, String s){
        medicinesFxObservableList.clear();
        try {
            ResultSet rs=DbStatements.SearchNameMedicines(c, s);
            this.showMedicinesTable(rs);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void editTable(Connection c){
        try {
            Medicines med= new Medicines();
            med.setId(this.MedicinesFxObjectPropertyEdit.getValue().getId());
            med.setName(this.MedicinesFxObjectPropertyEdit.getValue().getName());
            med.setPrice(this.MedicinesFxObjectPropertyEdit.getValue().getPrice());
            med.setPrescription(this.MedicinesFxObjectPropertyEdit.getValue().isPrescription());
            med.setQuantity(this.MedicinesFxObjectPropertyEdit.getValue().getQuantity());
            med.setOrdered(this.MedicinesFxObjectPropertyEdit.getValue().getOrdered());
            med.setSold(this.MedicinesFxObjectPropertyEdit.getValue().getSold());
            med.setReturns(this.MedicinesFxObjectPropertyEdit.getValue().getReturns());
            med.setDisposed_of(this.MedicinesFxObjectPropertyEdit.getValue().getDisposed_of());
            med.setAlternative(this.MedicinesFxObjectPropertyEdit.getValue().getAlternative());
            DbStatements.editMedicines(c, med);
            medicinesFxObservableList.clear();
            this.nameSearchTable(c,"");
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void editImage(Connection c){
        try {
            Medicines med= new Medicines();
            med.setId(this.MedicinesFxObjectProperty.getValue().getId());
            med.setName(this.MedicinesFxObjectProperty.getValue().getName());
            med.setPrice(this.MedicinesFxObjectProperty.getValue().getPrice());
            med.setPrescription(this.MedicinesFxObjectProperty.getValue().isPrescription());
            med.setQuantity(this.MedicinesFxObjectProperty.getValue().getQuantity());
            med.setOrdered(this.MedicinesFxObjectProperty.getValue().getOrdered());
            med.setSold(this.MedicinesFxObjectProperty.getValue().getSold());
            med.setReturns(this.MedicinesFxObjectProperty.getValue().getReturns());
            med.setDisposed_of(this.MedicinesFxObjectProperty.getValue().getDisposed_of());
            med.setAlternative(this.MedicinesFxObjectProperty.getValue().getAlternative());
            System.out.println(this.MedicinesFxObjectProperty.getValue().getImage());
            med.setImage(this.MedicinesFxObjectProperty.getValue().getImage());
            DbStatements.editMedicines(c, med);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void reloadTable(){
        ObservableList<MedicinesFx> medicinesList= FXCollections.observableArrayList();
        for(int i=0; i<medicinesFxObservableList.size(); i++){
            medicinesList.add(medicinesFxObservableList.get(i));
        }
        medicinesFxObservableList.clear();
        for(int i=0; i<medicinesList.size(); i++){
            medicinesFxObservableList.add(medicinesList.get(i));
        }
    }

    public void showMedicinesTable(ResultSet rs){
        try {
            List<Medicines> medicinesList;
            while (rs.next()) {
                //id, name, price, prescription, quantity, ordered, sold, returns, disposed_of, alternative
                MedicinesFx medl= new MedicinesFx();
                medl.setId(rs.getInt("id"));
                medl.setName(rs.getString("name"));
                medl.setPrice(rs.getFloat("price"));
                medl.setPrescription(rs.getBoolean("prescription"));
                medl.setQuantity(rs.getInt("quantity"));
                medl.setOrdered(rs.getInt("ordered"));
                medl.setSold(rs.getInt("sold"));
                medl.setReturns(rs.getInt("returns"));
                medl.setDisposed_of(rs.getInt("disposed_of"));
                medl.setAlternative(rs.getInt("alternative"));
                medl.setImage(rs.getString("image"));
                this.medicinesFxObservableList.add(medl);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public String shopTotalPriceUpdate(){
        String s="";
        float price=0;
        for(int i=0; i<medicinesFxObservableList.size(); i++){
            price+=medicinesFxObservableList.get(i).getPrice()*medicinesFxObservableList.get(i).getShopQuantity();
        }
        s=String.format("%.2f",price);
        return s;
    }

    public ObservableList<MedicinesFx> getMedicinesFxObservableList() {
        return medicinesFxObservableList;
    }

    public void setMedicinesFxObservableList(ObservableList<MedicinesFx> medicinesFxObservableList) {
        this.medicinesFxObservableList = medicinesFxObservableList;
    }

    public MedicinesFx getMedicinesFxObjectProperty() {
        return MedicinesFxObjectProperty.get();
    }

    public ObjectProperty<MedicinesFx> medicinesFxObjectPropertyProperty() {
        return MedicinesFxObjectProperty;
    }

    public void setMedicinesFxObjectProperty(MedicinesFx medicinesFxObjectProperty) {
        this.MedicinesFxObjectProperty.set(medicinesFxObjectProperty);
    }

    public MedicinesFx getMedicinesFxObjectPropertyEdit() {
        return MedicinesFxObjectPropertyEdit.get();
    }

    public ObjectProperty<MedicinesFx> medicinesFxObjectPropertyEditProperty() {
        return MedicinesFxObjectPropertyEdit;
    }

    public void setMedicinesFxObjectPropertyEdit(MedicinesFx medicinesFxObjectPropertyEdit) {
        this.MedicinesFxObjectPropertyEdit.set(medicinesFxObjectPropertyEdit);
    }
}
