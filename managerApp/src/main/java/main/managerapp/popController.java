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
import static java.util.Collections.reverseOrder;
import java.time.LocalDate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

                // gets the dates from the user
                String fromDate = fromField.getText();
                LocalDate fromDateREAL = LocalDate.parse(fromDate);
                String toDate = toField.getText();
                LocalDate toDateREAL = LocalDate.parse(toDate);

                //Queries db for orders
                ArrayList<String> columns = new ArrayList<String>(Arrays.asList("order_id", "order_date"));
                ArrayList<HashMap<String, String>> orderData = db.getColumns("\"order\"", columns);

                //Queries db for the data linked to the order
                ArrayList<String> linkColumns = new ArrayList<String>(Arrays.asList("order_id", "menu_id"));
                ArrayList<HashMap<String, String>> linkData = db.getColumns("order_menu_link", linkColumns);

                // Queries db for menu data
                ArrayList<String> menuColumn = new ArrayList<String>(Arrays.asList("menu_id", "menu_name"));
                ArrayList<HashMap<String, String>> menuData = db.getColumns("menu", menuColumn);

                HashMap<String, Integer> popData = new HashMap<String, Integer>();

                // adds menuIDs into popData to count occurence of later
                for (HashMap<String, String> ID : menuData){
                        String menuID = ID.get("menu_id");
//                        System.out.println(menuID);
                        popData.put(menuID, 0);
                }

                // need to use queries, temporary "fix"
                // loops through the dates taken from user
                for (LocalDate date = fromDateREAL; date.isBefore(toDateREAL.plusDays(1)); date = date.plusDays(1)) {
                        // look through the initial orderData to find the current date
                        for (HashMap<String, String> m : orderData) {
                                LocalDate currDate = LocalDate.parse(m.get("order_date"));
                                if (currDate.isEqual(date)) {
                                        String currOrder = m.get("order_id");

                                        // once order_id is found, find the menu_id associated with it
                                        for (HashMap<String, String> o : linkData) {
                                                String nextOrder = o.get("order_id");
                                                if (currOrder.equals(nextOrder)) {
                                                        String currMenu = o.get("menu_id");

                                                        // find the corresponding menu_id in popData
                                                        // and increment value for population
                                                        for (HashMap.Entry<String, Integer> p : popData.entrySet()) {
                                                                String dataMenu = p.getKey();
                                                                int dataValue = p.getValue();
                                                                int incByOne = 1;
                                                                if (currMenu.equals(dataMenu)) {
                                                                        popData.put(dataMenu, dataValue + incByOne);

                                                                }
                                                        }

                                                }
                                        }
                                }
                        }
                }
                // sorts the hashmap in descending order
                List<HashMap.Entry<String, Integer>> sorted_map = popData.entrySet()
                        .stream()
                        .sorted(reverseOrder(HashMap.Entry.comparingByValue()))
                        .collect(Collectors.toList());

                // limits list to 10 menu items
                int counter = 0;
                for (Map.Entry<String, Integer> i : sorted_map) {
                        if (counter > 9) {
                                break;
                        }
                        for (HashMap<String, String> ID : menuData){
                                String currID = i.getKey();
                                //System.out.println(currID);
                                if (currID.equals(ID.get("menu_id"))){
                                        //System.out.println(currID);
                                        String foodName = ID.get("menu_name");
                                        popList.getItems().add(i.getKey() + " | " + foodName + " | "  + i.getValue());
                                }
                        }
                        counter++;
                }
        }

        public void initialize(){
                db = new dbConnections();



                submitButton.setOnMouseClicked(mouseEvent -> submitHandler(mouseEvent));

        }

}
