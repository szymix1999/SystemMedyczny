package javafx.Administration;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty surname = new SimpleStringProperty();
    private StringProperty post = new SimpleStringProperty();
    private StringProperty id = new SimpleStringProperty();


    public Person(String name, String surname, String post, String id) {
        this.name.setValue(name);
        this.surname.setValue(surname);
        this.post.setValue(post);
        this.id.setValue(id);
    }

    public String getName() {
        return name.get();
    }

    public String getSurname() {
        return surname.get();
    }

    public String getPost() {
        return post.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public StringProperty postProperty() {
        return post;
    }

    public String getId(){
        return id.get();
    }

    @Override
    public String toString() {
        return name.get() + " " + surname.get() + " " + id.get();
    }
}
