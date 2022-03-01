module main.manager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens main.manager to javafx.fxml;
    exports main.manager;
}