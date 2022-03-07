package main.managerapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.managerapp.dbConnections.dbConnections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class ImpTenderController {


    @FXML
    private TextField categoryField;

    @FXML
    private TextField foodDescField;

    @FXML
    private TextField foodIDField;

    @FXML
    private TextField foodNameField;

    @FXML
    private TextField menuDescField;

    @FXML
    private TextField menuIDField;

    @FXML
    private TextField entryIDField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField menuNameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quanUsedField;

    @FXML
    private TextField skuField;

    @FXML
    private Button submitButton;

    private dbConnections db;

    public void submitHandler(MouseEvent e) {

        HashMap<String, String> food_and_drink_fields = new HashMap<String, String>();
        food_and_drink_fields.put("food_description", foodDescField.getText());
        food_and_drink_fields.put("food_id", foodIDField.getText());
        food_and_drink_fields.put("food_name", foodNameField.getText());

        ArrayList<HashMap<String, String>> fd_table = new ArrayList<HashMap<String, String>>();
        fd_table.add(food_and_drink_fields);
        ArrayList<String> nothing = new ArrayList<String>();
        db.insertData("food_and_drinks", fd_table, nothing);

        HashMap<String, String> food_inventory_fields = new HashMap<String, String>();
        food_inventory_fields.put("sku", skuField.getText());
        food_inventory_fields.put("food_id", foodIDField.getText());
        food_inventory_fields.put("quantity_used_per_food", quanUsedField.getText());

        ArrayList<HashMap<String, String>> fi_table = new ArrayList<HashMap<String, String>>();
        fi_table.add(food_inventory_fields);
        db.insertData("food_inventory", fi_table, nothing);

        HashMap<String, String> menu_fields = new HashMap<String, String>();
        menu_fields.put("menu_description", menuDescField.getText());
        menu_fields.put("menu_id", menuIDField.getText());
        menu_fields.put("menu_name", menuNameField.getText());
        menu_fields.put("price", priceField.getText());
        menu_fields.put("category", categoryField.getText());

        ArrayList<HashMap<String, String>> menu_table = new ArrayList<HashMap<String, String>>();
        menu_table.add(menu_fields);
        db.insertData("menu", menu_table, nothing);

        HashMap<String, String> menu_food_link_fields = new HashMap<String, String>();
        menu_food_link_fields.put("food_id", foodIDField.getText());
        menu_food_link_fields.put("entry_id", entryIDField.getText());
        menu_food_link_fields.put("menu_id", menuIDField.getText());
        menu_food_link_fields.put("quantity", quantityField.getText());

        ArrayList<HashMap<String, String>> mfl_table = new ArrayList<HashMap<String, String>>();
        mfl_table.add(menu_food_link_fields);
        db.insertData("menu_food_link", mfl_table, nothing);

        db.closeConnection();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Successfully added new menu item");
        alert.setHeaderText("Successfully added new menu item");
        alert.setContentText("Successfully added new menu item");
        alert.showAndWait();

    }

    public void initialize() {
        db = new dbConnections();

        submitButton.setOnMouseClicked(mouseEvent -> submitHandler(mouseEvent));

    }
}

