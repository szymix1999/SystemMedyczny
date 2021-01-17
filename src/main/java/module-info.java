module javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens javafx to javafx.fxml;
    exports javafx;
    opens javafx.Administration to javafx.fxml;
    opens javafx.Patient to javafx.fxml;
    opens javafx.Menu to javafx.fxml;

}
