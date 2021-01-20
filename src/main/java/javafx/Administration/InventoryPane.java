package javafx.Administration;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DbConnector;
import database.DbStatements;
import javafx.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
    @FXML
    private Button generate_btn;
    @FXML
    private Label staffLabel;
    @FXML
    private Label orderLabel;
    @FXML
    private Label disLabel;
    @FXML
    private Label soldLabel;
    @FXML
    private Label visitLabel;

    Connection db;
    List<Pair<String, String>> labels;

    @FXML
    private void initialize() throws SQLException {
        db = DbConnector.connect();
        float salaries = DbStatements.getAllSalary(db);
        personel.setText(String.valueOf(salaries/-20));


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

        ordered*=-1;
        disposed*=-1;

        disposal.setText(String.valueOf(disposed));
        orderDrugs.setText(String.valueOf(ordered));
        soldDrugs.setText(String.valueOf(sold));

        labels = new ArrayList<Pair<String, String>>();

        labels.add(new Pair<>(staffLabel.getText(),personel.getText()));
        labels.add(new Pair<>(disLabel.getText(),disposal.getText()));
        labels.add(new Pair<>(orderLabel.getText(),orderDrugs.getText()));
        labels.add(new Pair<>(soldLabel.getText(),soldDrugs.getText()));
        labels.add(new Pair<>(visitLabel.getText(),visits.getText()));
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

        labels.add(new Pair<>(l.getText(),t.getText()));
        boxList.getChildren().add(t);
    }


    @FXML
    private void generate() throws FileNotFoundException, DocumentException {
        generate_btn.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


        String data = LocalDate.now().toString();
        var doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream("src//main//resources//raports//" + data + ".pdf"));
        doc.open();

        var bold = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD);
        var paragraph = new Paragraph(App.getString("inventoryRaport")+data);

        var table = new PdfPTable(2);

        Stream.of(App.getString("item"), App.getString("amount")).forEach(table::addCell);

        labels.forEach(val -> {
                    table.addCell(val.getKey());
                    table.addCell(val.getValue());
                });

        float income = 0;
        float cost = 0;

        for(Pair<String, String> v : labels){
            if(!v.getValue().isBlank()) {
                if (Float.parseFloat(v.getValue()) > 0) {
                    income += Float.parseFloat(v.getValue());
                } else {
                    cost += Float.parseFloat(v.getValue()) * -1;
                }
                System.out.println("al am akotat");
            }
        }

        var paragraph1 = new Paragraph(App.getString("summary"));
        var table1 = new PdfPTable(2);

        Stream.of(App.getString("income"), App.getString("cost")).forEach(table1::addCell);
        table1.addCell(String.valueOf(income));
        table1.addCell(String.valueOf(cost));

        paragraph.add(table);
        paragraph1.add(table1);
        doc.add(paragraph);
        doc.add(paragraph1);
        doc.close();
        generate_btn.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));



    }
}
