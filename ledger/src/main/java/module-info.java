module com.csci {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.csci to javafx.fxml;
    exports com.csci;
}
