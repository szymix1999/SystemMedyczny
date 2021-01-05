module javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens javafx to javafx.fxml;
    exports javafx;
    opens javafx.Administration to javafx.fxml;

}
