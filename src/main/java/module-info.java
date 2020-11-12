module javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens javafx to javafx.fxml;
    exports javafx;
}