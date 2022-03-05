package main.managerapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.managerapp.dbConnections.dbConnections;
import java.time.LocalDate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class popController{

        @FXML
        private TextField fromField;
        @FXML
        private TextField toField;
        @FXML
        private ListView<String> popList;
        @FXML
        private Button submitButton;

        private dbConnections db;

        public void submitHandler(MouseEvent e) {

                String fromDate = fromField.getText();
                LocalDate fromDateREAL = LocalDate.parse(fromDate);

                String toDate = toField.getText();
                LocalDate toDateREAL = LocalDate.parse(toDate);


                ArrayList<String> columns = new ArrayList<String>(Arrays.asList("order_id", "order_date"));
                ArrayList<HashMap<String, String>> orderData = db.getColumns("\"order\"", columns);

                for(HashMap<String, String> m : orderData){
                        popList.getItems().add(m.get("order_id") + " | " + m.get("order_date"));
                }

                ArrayList<String> linkColumns = new ArrayList<String>(Arrays.asList("order_id", "menu_id"));
                ArrayList<HashMap<String, String>> linkData = db.getColumns("order_menu_link", linkColumns);

//                for(HashMap<String, String> m : linkData){
//                        popList.getItems().add(m.get("order_id") + " | " + m.get("menu_id"));
//                }
                // Hashmap of the menu_id, to increment popularity later
//                ArrayList<String> popColumns = new ArrayList<String>(Arrays.asList("menu_id"));
//                ArrayList<HashMap<String, Integer>> popData = new HashMap<String, Integer>();
//                HashMap<String, Integer> popData = new HashMap<String, Integer>();
//                popData.put("501", 0);
//                popData.put("502", 0);
                // loops through the dates taken from user
                for (LocalDate date = fromDateREAL; date.isBefore(toDateREAL); date = date.plusDays(1)){
                        //System.out.println(date);
                        System.out.println("TEST2222");
                        // look through the initial orderData to find the current Data
                        for(HashMap<String, String> m : orderData){
                               LocalDate currDate = LocalDate.parse(m.get("order_date"));
//                                System.out.println(currDate);
                               if (currDate.isEqual(date)){
                                       System.out.println(currDate);
                                       String currOrder = m.get("order_id");
                                       // once order_id is found, find the menu_id associated with it and add to hashmap?
                                       for(HashMap<String, String> o : linkData){
                                                if (currOrder == o.get("order_id")){
                                                        String currMenu = o.get("menu_id");
                                                        if (currMenu == "501"){
                                                                System.out.println("501");
                                                        }
//                                                        for(HashMap<String, Integer> p : popData){
//                                                                if (currMenu == 0){
//
//                                                                }
//                                                        }

                                                }
                                       }
                               }
                        }
                }

//                ArrayList<String> columns = new ArrayList<String>(Arrays.asList("menu_id", "menu_name", "menu_description", "price"));
//                ArrayList<HashMap<String, String>> menuData = db.getColumns("menu1", columns);
//
//                for(HashMap<String, String> m : menuData){
//                        popList.getItems().add(m.get("menu_id") + " | " + m.get("menu_name") + " | " + m.get("price") + " | " + m.get("menu_description"));
//                }
//                fromField.getText();
//                HashMap<String, String> fields =  new HashMap<String, String>();
//
//
//                if(!nameField.getText().isEmpty()){
//                        fields.put("menu_name", nameField.getText());
//                }
//                if(!priceField.getText().isEmpty()){
//                        fields.put("price", priceField.getText());
//                }
//                if(!descriptionField.getText().isEmpty()){
//                        fields.put("menu_description", descriptionField.getText());
//                }
//
//                db.updateIndividual("menu", fields, "menu_id", idField.getText());
//
//                db.closeConnection();
        }

        public void initialize(){
                db = new dbConnections();



                submitButton.setOnMouseClicked(mouseEvent -> submitHandler(mouseEvent));

        }

}
