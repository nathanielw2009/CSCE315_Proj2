module main.managerapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires validatorfx;

    opens main.managerapp to javafx.fxml;
    exports main.managerapp;
}