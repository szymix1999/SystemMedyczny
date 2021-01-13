package javafx.Medicines;

import database.DbConnector;
import database.DbStatements;

import java.sql.Connection;
import java.sql.SQLException;

public class Medicines {
    private int id;
    private String name = "";
    private float price = 0;
    private boolean prescription = false;
    private int quantity = 0;
    private int ordered = 0;
    private int sold = 0;
    private int returns = 0;
    private int disposed_of = 0;
    private int alternative = -1;
    private String composition = "";

    public void addMedicineInDataBase(Connection c) {
        try {
            if (!this.getName().equals("")) {
                DbStatements.AddMedicine(c, this);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isPrescription() {
        return prescription;
    }

    public void setPrescription(boolean prescription) {
        this.prescription = prescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrdered() {
        return ordered;
    }

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getReturns() {
        return returns;
    }

    public void setReturns(int returns) {
        this.returns = returns;
    }

    public int getDisposed_of() {
        return disposed_of;
    }

    public void setDisposed_of(int disposed_of) {
        this.disposed_of = disposed_of;
    }

    public int getAlternative() {
        return alternative;
    }

    public void setAlternative(int alternative) {
        this.alternative = alternative;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }
}
