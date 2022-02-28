module main.employee {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires eu.hansolo.tilesfx;

    opens main.employee to javafx.fxml;
    exports main.employee;
    exports main.employee.FoodIconBox;
}