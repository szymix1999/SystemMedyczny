package javafx;

import database.DbConnector;
import javafx.Medicines.Medicines;
import javafx.Medicines.MedicinesModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.sql.Connection;

public class compositionShowEditController {
    @FXML
    private TextArea compTextArea;
    @FXML
    private Button confirmButton;

    private MedicinesModel medicinesModel=new MedicinesModel();
    private Medicines med=null;
    Connection c;

    public void setC(Connection c) {
        this.c = c;
    }

    public Medicines getMed() {
        return med;
    }

    public void setMed(Medicines med) {
        this.med = med;
    }

    public MedicinesModel getMedicinesModel() {
        return medicinesModel;
    }

    public void compositionMedLoad(){
        if(med!=null){
            System.out.println("Wyświetlam dla dodwania leku");
            this.compTextArea.setText(this.med.getComposition());
        }else{
            System.out.println("Wyświetlam skład "+this.medicinesModel.getMedicinesFxObjectProperty().getName());
            this.compTextArea.setText(this.medicinesModel.getMedicinesFxObjectProperty().getComposition());
        }
    }

    public void compConfirmButtonOnAction(){
        if(med!=null){
            System.out.println("Potwierdzam dla dodwania");
            confirmButton.setTextFill(Paint.valueOf("#008000"));
            this.med.setComposition(compTextArea.getText());
        }else{
            System.out.println("Potwierdzam dla przycisku w tabeli");
            try{
                this.medicinesModel.getMedicinesFxObjectProperty().setComposition(this.compTextArea.getText());
                this.medicinesModel.editMed(c);
                confirmButton.setTextFill(Paint.valueOf("#008000"));
            }catch (IllegalArgumentException ex){
                System.out.println("Błąd");
            }
        }
    }
}

