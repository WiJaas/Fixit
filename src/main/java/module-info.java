module com.fixit {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    exports com.fixit.Model;


    opens com.fixit to javafx.fxml;
    exports com.fixit;
    exports com.fixit.Controller;
    opens com.fixit.Controller to javafx.fxml;
}