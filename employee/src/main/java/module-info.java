module main.employee {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.postgresql.jdbc;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens main.employee to javafx.fxml;
    exports main.employee;
    exports main.employee.dbConnections;
    opens main.employee.dbConnections to javafx.fxml;
}