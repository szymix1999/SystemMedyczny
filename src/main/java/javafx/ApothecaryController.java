package javafx;

import database.DbConnector;
import database.DbStatements;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ApothecaryController {

    @FXML
    private Button SearchButton;

    @FXML
    private TextField SearchField;
//Test
    @FXML
    private void SearchAction() throws IOException {
        Connection c = DbConnector.connect();
/*
        try {
            if((SearchField.getText() != null && !SearchField.getText().isEmpty()) &&  (DbStatements.SearchMedicines(c, SearchField.getText()) != -1)) {

            } else {

            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

 */
    }
/*
    @FXML
    void initialize(){
        Connection c = DbConnector.connect();
        try {
            DbStatements.SearchMedicines(c, "");


        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

 */
}
