package javafx.Medicines;

import database.DbConnector;
import database.DbStatements;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.Connection;
import java.sql.SQLException;

public class MedicinesModel {
    private ObjectProperty<MedicinesFx> MedicinesFxObjectProperty = new SimpleObjectProperty<>(new MedicinesFx());

    public void addMedicineInDataBase() {
        Medicines medicines = new Medicines();
        medicines.setName(this.MedicinesFxObjectProperty.get().getName());
        medicines.setPrice(this.MedicinesFxObjectProperty.get().getPrice());
        medicines.setQuantity(this.MedicinesFxObjectProperty.get().getQuantity());
        medicines.setPrescription(this.MedicinesFxObjectProperty.get().isPrescription());
        medicines.setAlternative(this.MedicinesFxObjectProperty.get().getAlternative());

        Connection c = DbConnector.connect();
        try {
            if (!medicines.getName().equals("")) {
                DbStatements.AddMedicine(c, medicines);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

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
