package main.managerapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import main.managerapp.dbConnections.dbConnections;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class InvUsageController {

    @FXML
    private TextField startDateField;
    @FXML
    private TextField endDateField;
    @FXML
    private Button submitButton;
    @FXML
    private TableView<Pair<String, Float>> resultTable;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn quantityCol;


    private dbConnections db;

    public void submitHandler(MouseEvent e) {
        //HashMap<String, String> fields =  new HashMap<String, String>();
        ObservableList<Pair<String, Float>> results;
        ArrayList<Pair<String, Float>> dbResults;
        if(!startDateField.getText().isEmpty() && !endDateField.getText().isEmpty()) {
            //results = FXCollections.observableList(db.getQuantityUsed(startDateField.getText(), endDateField.getText()));
            dbResults = db.getQuantityUsed(startDateField.getText(), endDateField.getText());
//            resultTable.setItems((ObservableList) results);
//            resultTable.refresh();
            for (Pair<String, Float> ele : dbResults) {
                //TableRow<Pair<String, Float>> row = new TableRow<>();
                //row.setItem(ele);
                resultTable.getItems().add(ele);
            }
        }
        // else {
        // TODO add error message
        // }

        db.closeConnection();

//        FXMLLoader fxmlLoader= new FXMLLoader(MainController.class.getResource("View.fxml"));
//        Parent root1 = null;
//        try {
//            root1 = (Parent) fxmlLoader.load();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        Stage stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setTitle("ABC");
//        stage.setScene(new Scene(root1));
//        stage.show();
    }



    public void initialize(){
        db = new dbConnections();

        submitButton.setOnMouseClicked(mouseEvent -> submitHandler(mouseEvent));

    }
}

