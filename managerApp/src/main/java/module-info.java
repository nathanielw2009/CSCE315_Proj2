module main.managerapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens main.managerapp to javafx.fxml;
    exports main.managerapp;
}