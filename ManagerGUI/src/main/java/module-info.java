module main.manager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires eu.hansolo.tilesfx;

    opens main.manager to javafx.fxml;
    exports main.manager;
}