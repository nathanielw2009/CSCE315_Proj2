package main.employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main.employee.FoodIconBox.FoodIconBox;

import java.io.IOException;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private GridPane myGrid;

    public void initialize(){
        for(int i = 0; i < 3; i++){
            Pane mp = null;
            Pane mp1 = null;
            Pane mp2 = null;
            Pane mp3 = null;
            try {
                mp = FXMLLoader.load(FoodIconBox.class.getResource("FoodIconBox.fxml"));
                mp1 = FXMLLoader.load(FoodIconBox.class.getResource("FoodIconBox.fxml"));
                mp2 = FXMLLoader.load(FoodIconBox.class.getResource("FoodIconBox.fxml"));
                mp3 = FXMLLoader.load(FoodIconBox.class.getResource("FoodIconBox.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            myGrid.add(mp,0,0);
            myGrid.add(mp1,1,0);
            myGrid.add(mp2,2,0);
            myGrid.add(mp3,1,1);

        }
    }
}