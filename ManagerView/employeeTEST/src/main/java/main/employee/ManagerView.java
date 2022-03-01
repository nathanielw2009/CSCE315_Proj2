package main.employee;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ManagerView {
    @FXML
    private Label welcomeText;


    @FXML
    protected void onEntreeButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onDrinkButtonClick() {

    }
}
