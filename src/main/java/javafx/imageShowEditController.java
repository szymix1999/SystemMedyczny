package javafx;

import database.DbConnector;
import javafx.Medicines.MedicinesModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.sql.Connection;

public class imageShowEditController {
    @FXML
    private ImageView ImageMed;
    @FXML
    private TextField urlTextField;

    private MedicinesModel medicinesModel=new MedicinesModel();

    public void setC(Connection c) {
        this.c = c;
    }

    Connection c;

    public MedicinesModel getMedicinesModel() {
        return medicinesModel;
    }

    public void ImageMedLoad(){
        System.out.println(this.medicinesModel.getMedicinesFxObjectProperty().getImage());
        if(this.medicinesModel.getMedicinesFxObjectProperty().getImage()!=null && !this.medicinesModel.getMedicinesFxObjectProperty().getImage().isEmpty()){
            try{
                ImageView im=new ImageView(this.medicinesModel.getMedicinesFxObjectProperty().getImage());
                ImageMed.setImage(im.getImage());
            }catch (IllegalArgumentException ex){
                ImageMed.setImage(null);
            }
            if(urlTextField!=null){
                urlTextField.setText(this.medicinesModel.getMedicinesFxObjectProperty().getImage());
            }
        }
    }

    public void confirmUrlButtonOnAction(){
        medicinesModel.getMedicinesFxObjectProperty().setImage(urlTextField.getText());
        medicinesModel.editMed(c);
        if(this.medicinesModel.getMedicinesFxObjectProperty().getImage()!=null && !this.medicinesModel.getMedicinesFxObjectProperty().getImage().isEmpty()){
            try{
                ImageView im=new ImageView(this.medicinesModel.getMedicinesFxObjectProperty().getImage());
                ImageMed.setImage(im.getImage());
            }catch (IllegalArgumentException ex){
                ImageMed.setImage(null);
            }
        }
        urlTextField.setText(this.medicinesModel.getMedicinesFxObjectProperty().getImage());
    }
}
