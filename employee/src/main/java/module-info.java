module main.employee {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.postgresql.jdbc;

    exports main.employee to javafx.graphics, javafx.fxml;
    opens main.employee to javafx.fxml;
    opens main.employee.dbConnections to javafx.fxml;
}