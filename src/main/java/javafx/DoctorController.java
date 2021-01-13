package javafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;

public class DoctorController {
    /*
    @FXML private Label label;
    @FXML private TextField filterField;
    @FXML private TableView<Patient> tableView;
    @FXML private TableColumn<Patient, Integer> patID;
    @FXML private TableColumn<Patient, String> patFirstName;
    @FXML private TableColumn<Patient, String> patLastName;
    @FXML private TableColumn<Patient, String> patBirthDate;
    @FXML private TableColumn<Patient, String> patSex;
    */

    /*
     * CZEGO POTRZEBUJEMY ???
     * 1. kontakt z baza
     *  1.1 polaczyc sie z tabela pacjenta
     *      1.1.1 select z bazy wszystkie info o pacjentach
     *  1.2 kontakt z lekarzami
     *      1.2.1 select info o konkretnym lekarzu powiazanym ze skierowaniem
     *  1.3 kontakt z receptami
     *  1.4 kontakt ze skierowaniami
     *  1.5 kontakt z diagnozami
     *
     *  CO TO ZNACZY WYBRAC PACJENTA ???
     *
     * #if nie wybrany pacjent
     *  nie mozna dodac diagnozy / skierowania / recepty
     * #else
     *  #diagnoza
     *      tylko nadpisac (dla okreslonego pacjenta)
     *  #recepta
     *      zawsze dodajemy (nie nadpisujemy / usuwaja aptekarze)
     *  #skierowanie
     *      mozna usunac, po realizacji
     *      mozna dodac, do konretnego lekarza (trzeba uzyc polaczenia z lekarzami)
     *
     * CHYBA TYLE ???
     */


}
