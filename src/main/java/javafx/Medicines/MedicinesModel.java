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

    private ObservableList<MedicinesFx> medicinesFxObservableList= FXCollections.observableArrayList();

    Connection c = DbConnector.connect();

    public void idSearchTable(int id){
        medicinesFxObservableList.clear();
        try {
            ResultSet rs=DbStatements.SearchIdMedicines(c, id);
            this.showMedicinesTable(rs);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void nameSearchTable(String s){
        medicinesFxObservableList.clear();
        try {
            ResultSet rs=DbStatements.SearchNameMedicines(c, s);
            this.showMedicinesTable(rs);
        } catch (SQLException ex){
            ex.printStackTrace();
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
                this.medicinesFxObservableList.add(medl);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
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
}
