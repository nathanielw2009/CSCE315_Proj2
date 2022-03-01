package main.employee;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import main.employee.dbConnections.dbConnections;


public class MainController {
    @FXML
    private VBox catlist;

    @FXML
    private ListView<Integer> checkID;

    @FXML
    private ListView<String> checkName;

    @FXML
    private ListView<String> checkPrice;

    @FXML
    private Label totalLabel;

    @FXML
    private GridPane foodGrid;

    @FXML
    private Button SB;

    private dbConnections db;

    HashMap<Integer, HashMap<String, String>> menuItems = new HashMap<Integer, HashMap<String, String>>();


    public void buttonHandler(MouseEvent e){
        Button pressed = (Button) e.getSource();
        String id = pressed.getId();
        int index = id.indexOf("FB");
        if(index > -1){
            id = id.substring(index+2);
            addToList(Integer.parseInt(id));
        }
        index = id.indexOf("SB");
        System.out.println(index);
        if(index > -1){
            sendData();
        }
    }

    public void sendData(){
        // Request order_id
        ArrayList<HashMap<String, String>> dataSent = new ArrayList<>();
        HashMap<String, String> hashSent = new HashMap<String, String>();
        hashSent.put("total_amount", "0.00");
        ArrayList<String> returnCol= new ArrayList<String>(Arrays.asList("order_id"));
        dataSent.add(hashSent);

        ArrayList<HashMap<String, String>> rData = db.insertData("\"order\"", dataSent, returnCol);


        // Send Links
        dataSent.clear();
        System.out.println(rData.get(0).get("order_id"));
        for(int id : checkID.getItems()){
            HashMap<String, String> orderItem = new HashMap<String, String>();
            orderItem.put("order_id", rData.get(0).get("order_id"));
            orderItem.put("menu_id", String.valueOf(id));
            dataSent.add(orderItem);
        }
        returnCol.clear();
        db.insertData("order_menu_link", dataSent, returnCol);

        checkID.getItems().clear();
        checkName.getItems().clear();
        checkPrice.getItems().clear();
        totalLabel.setText("$0.00");
    }

    public void addToList(int id){
        checkID.getItems().add(id);
        checkName.getItems().add(menuItems.get(id).get("menu_name"));
        checkPrice.getItems().add("$"+menuItems.get(id).get("price"));
        totalLabel.setText("$"+ String.format("%.2f" ,(Double.parseDouble(totalLabel.getText().substring(1))
                +Double.parseDouble(menuItems.get(id).get("price")))) );
    }


    public void initialize(){
        // Start Connection with Database
        db = new dbConnections();

        // GridPane
        ArrayList<String> columns = new ArrayList<>(Arrays.asList("menu_id", "menu_name", "price"));
        ArrayList<HashMap<String, String>> data = db.getColumns("menu", columns);

        SB.setOnMouseClicked(mouseEvent -> buttonHandler(mouseEvent));

        int i = 0;
        for(HashMap<String,String> item : data){
            Button d = new Button("Add \n"+item.get("menu_name")+"\nPrice: " + item.get("price"));
            d.setId("FB"+item.get("menu_id"));
            d.setOnMouseClicked(mouseEvent -> buttonHandler(mouseEvent));

            d.setContentDisplay(ContentDisplay.CENTER);
            foodGrid.add(d, i%2, Math.floorDiv(i, 2));
            i++;
            HashMap<String, String> t = new HashMap<String, String>();
            t.put("price", item.get("price").substring(1) );
            t.put("menu_name", item.get("menu_name"));
            menuItems.put(Integer.parseInt(item.get("menu_id")), t);
        }
    }


}