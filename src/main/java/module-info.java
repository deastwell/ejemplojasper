module com.example.javafxjasper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;
    requires javafx.swing;
    opens utils;
    opens clase;


    opens com.example.javafxjasper to javafx.fxml;
    exports com.example.javafxjasper;
}