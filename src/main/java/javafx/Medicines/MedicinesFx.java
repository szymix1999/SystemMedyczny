package javafx.Medicines;

import javafx.beans.property.*;

public class MedicinesFx {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty("");
    private FloatProperty price = new SimpleFloatProperty(0);
    private BooleanProperty prescription = new SimpleBooleanProperty(false);
    private IntegerProperty quantity = new SimpleIntegerProperty(0);
    private IntegerProperty ordered = new SimpleIntegerProperty(0);
    private IntegerProperty sold = new SimpleIntegerProperty(0);
    private IntegerProperty returns = new SimpleIntegerProperty(0);
    private IntegerProperty disposed_of = new SimpleIntegerProperty(0);
    private IntegerProperty alternative = new SimpleIntegerProperty(-1);
    private StringProperty composition = new SimpleStringProperty("");


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public float getPrice() {
        return price.get();
    }

    public FloatProperty priceProperty() {
        return price;
    }

    public void setPrice(float price) {
        this.price.set(price);
    }

    public boolean isPrescription() {
        return prescription.get();
    }

    public BooleanProperty prescriptionProperty() {
        return prescription;
    }

    public void setPrescription(boolean prescription) {
        this.prescription.set(prescription);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public int getOrdered() {
        return ordered.get();
    }

    public IntegerProperty orderedProperty() {
        return ordered;
    }

    public void setOrdered(int ordered) {
        this.ordered.set(ordered);
    }

    public int getSold() {
        return sold.get();
    }

    public IntegerProperty soldProperty() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold.set(sold);
    }

    public int getReturns() {
        return returns.get();
    }

    public IntegerProperty returnsProperty() {
        return returns;
    }

    public void setReturns(int returns) {
        this.returns.set(returns);
    }

    public int getDisposed_of() {
        return disposed_of.get();
    }

    public IntegerProperty disposed_ofProperty() {
        return disposed_of;
    }

    public void setDisposed_of(int disposed_of) {
        this.disposed_of.set(disposed_of);
    }

    public int getAlternative() {
        return alternative.get();
    }

    public IntegerProperty alternativeProperty() {
        return alternative;
    }

    public void setAlternative(int alternative) {
        this.alternative.set(alternative);
    }

    public String getComposition() {
        return composition.get();
    }

    public StringProperty compositionProperty() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition.set(composition);
    }
}
