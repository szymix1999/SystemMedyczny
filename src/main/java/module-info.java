module javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires json.simple;

    opens javafx to javafx.fxml;
    exports javafx;
}