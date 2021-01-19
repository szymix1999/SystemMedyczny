package javafx.Administration;

import database.DbConnector;
import database.DbStatements;
import javafx.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryPane {
    @FXML
    private TextArea personel;
    @FXML
    private TextArea disposal;
    @FXML
    private TextArea orderDrugs;
    @FXML
    private TextArea soldDrugs;
    @FXML
    private TextArea visits;
    @FXML
    private TextField money;
    @FXML
    private TextField descript;
    @FXML
    private VBox boxList;

    Connection db;

    @FXML
    private void initialize() throws SQLException {
        db = DbConnector.connect();
        float salaries = DbStatements.getAllSalary(db);
        personel.setText(String.valueOf(salaries/20));


        ResultSet r = DbStatements.getCost(db);
        float sold = 0;
        float ordered = 0;
        float disposed = 0;
        float price = 1;
        while(r.next()){
            price = r.getFloat("price");
            sold += r.getFloat("sold")*price;
            ordered += r.getFloat("ordered")*price;
            disposed += r.getFloat("disposed_of")*price;
        }

        disposal.setText(String.valueOf(disposed));
        orderDrugs.setText(String.valueOf(ordered));
        soldDrugs.setText(String.valueOf(sold));
    }

    @FXML
    private void back(){
        App.backBtn();
    }

    @FXML
    private void add(){
        if(descript.getText().isBlank() || money.getText().isBlank())
            return;

        Label l = new Label();
        l.setText(descript.getText());
        boxList.getChildren().add(l);

        TextArea t = new TextArea();
        t.setText(money.getText());
        if(Float.parseFloat(money.getText())>0)
            t.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        else
            t.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        boxList.getChildren().add(t);
    }


}
