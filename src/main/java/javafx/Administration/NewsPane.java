package javafx.Administration;

import database.DbConnector;
import database.DbStatements;
import javafx.App;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewsPane {

    class Visit{
        private StringProperty name = new SimpleStringProperty();
        private StringProperty date = new SimpleStringProperty();
        private StringProperty amount = new SimpleStringProperty();
        int id;

        public Visit(String name, String date, String amount, int id) {
            this.name.setValue(name);
            this.date.setValue(date);
            this.amount.setValue(amount);
            this.id = id;
        }

        public String getName() {
            return name.get();
        }

        public String getDate() {
            return date.get();
        }

        public String getAmount() {
            return amount.get();
        }

        public StringProperty nameProperty() {
            return name;
        }

        public StringProperty dateProperty() {
            return date;
        }

        public StringProperty amountProperty() {
            return amount;
        }

        public int getId(){return id;}

        @Override
        public String toString() {
            return name.get() + " " + date.get();
        }
    }

    @FXML
    private TableView<Visit> tableView;
    @FXML
    private TableColumn<Visit,String> visitName;
    @FXML
    private TableColumn<Visit,String> visitDate;
    @FXML
    private TableColumn<Visit,String> amount;
    @FXML
    private TextArea selected;
    @FXML
    private TextField amountField;
    @FXML
    private Button ok_btn;

    Connection db;
    int curr_id;

    @FXML
    private void initialize() throws SQLException {
        db = DbConnector.connect();
        tableView.setItems(getVisits());
        visitName.setCellValueFactory(cellData ->cellData.getValue().nameProperty());
        visitDate.setCellValueFactory(cellData ->cellData.getValue().dateProperty());
        amount.setCellValueFactory(cellData ->cellData.getValue().amountProperty());

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Visit>() {
            @Override
            public void changed(ObservableValue<? extends Visit> observableValue, Visit person, Visit t1) {
                if(tableView.getSelectionModel().getSelectedItem() != null)
                {
                    Visit v = tableView.getSelectionModel().getSelectedItem();
                    selected.setText(v.toString());
                    curr_id = v.getId();
                }
            }

        });
    }

    @FXML
    private void back(){
        App.backBtn();
    }

    @FXML
    private void ok() throws SQLException {
        if(!amountField.getText().isBlank()){
            DbStatements.updateVisitCost(db,curr_id, Float.parseFloat(amountField.getText()));
            DbStatements.updateVisitPaid(db,curr_id,0);
            tableView.setItems(getVisits());
        }else{
            ok_btn.setFont(Font.font("red", FontWeight.BOLD, FontPosture.REGULAR, 20));
        }
    }

    @FXML
    private void discard() throws SQLException {
        if(!selected.getText().isBlank()) {
            DbStatements.deleteUnacceptedVisit(db, curr_id);
            tableView.setItems(getVisits());
        }
    }


    private ObservableList<Visit> getVisits() throws SQLException {
        ObservableList<Visit> list = FXCollections.observableArrayList();
        ResultSet r = DbStatements.getUnacceptedVisit(db);

        while(r.next()){
            list.add(new Visit(r.getString("visit_name"), r.getString("visit_date"),"0", r.getInt("id")));
        }

        return list;
    }
}
