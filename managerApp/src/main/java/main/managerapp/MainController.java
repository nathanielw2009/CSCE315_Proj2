package main.managerapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import main.managerapp.dbConnections.dbConnections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class MainController {

    @FXML
    private Button addMenuButton;

    @FXML
    private Button updateMenuButton;

    @FXML
    private Button addInventoryButton;

    @FXML
    private Button updateInventoryButton;

    @FXML
    private ListView<String> menuList;

    @FXML
    private ListView<String> inventoryList;


    private dbConnections db;



    public void addMenuHandler(MouseEvent e){

    }

    public void updateMenuHandler(MouseEvent e){

    }

    public void addInventoryHandler(MouseEvent e){

    }

    public void updateInventoryHandler(MouseEvent e){

    }


    public void initialize(){
        // Start Connection with Database
        db = new dbConnections();

        ArrayList<String> columns = new ArrayList<String>(Arrays.asList("menu_id", "menu_name", "menu_description"));
        ArrayList<HashMap<String, String>> menuData = db.getColumns("menu", columns);

        addMenuButton.setOnMouseClicked(mouseEvent -> addMenuHandler(mouseEvent));
        updateMenuButton.setOnMouseClicked(mouseEvent -> updateMenuHandler(mouseEvent));
        addInventoryButton.setOnMouseClicked(mouseEvent -> addInventoryHandler(mouseEvent));
        updateInventoryButton.setOnMouseClicked(mouseEvent -> updateInventoryHandler(mouseEvent));

        for(HashMap<String, String> m : menuData){
            menuList.getItems().add(m.get("menu_id") + " | " + m.get("menu_name") + " | " + m.get("menu_description"));
        }

        columns = new ArrayList<String>(Arrays.asList("sku", "quantity", "category", "price"));
        ArrayList<HashMap<String, String>> inventroyData = db.getColumns("inventory_items", columns);

        for(HashMap<String, String> m : inventroyData){
            inventoryList.getItems().add(m.get("sku") + " | "+ m.get("quantity") + " | " + m.get("category") + " | " + m.get("price"));
        }

    }


}
