module com.csci {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires javafx.graphics;

    opens com.csci to javafx.fxml, com.google.gson;
    exports com.csci;
}
