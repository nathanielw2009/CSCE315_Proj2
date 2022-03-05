package main.managerapp;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public class InvResultData {
        private FloatProperty  quantity;
        private StringProperty  name;

        InvResultData(String nameIn, Float quan) {
            name = nameProperty();
            name.set(nameIn);
            quantity = quantityProperty();
            quantity.set(quan.floatValue());
        }
        public float getQuantity() {
            return quantity.get();
        }
        public FloatProperty quantityProperty() {
            if (quantity == null) quantity = new SimpleFloatProperty(this, "quantity");
            return quantity;
        }
        public void setName(String val) {
            name.set(val);
        }
        public String getName() {
            return name.get();
        }
        public StringProperty nameProperty() {
            if (name == null ) name = new SimpleStringProperty(this, "name");
            return name;
        }
    }
    @FXML
    private TextField startDateField;
    @FXML
    private TextField endDateField;
    @FXML
    private Button submitButton;
    @FXML
    private TableView<InvResultData> resultTable;

    @FXML
    private TableColumn<InvResultData, String> nameColumn;
    @FXML
    private TableColumn<InvResultData, Float> quantityCol;


    private dbConnections db;

    public class resultData {
        private FloatProperty  quantity;
        private StringProperty  name;
        public float getQuantity() {
            return quantity.get();
        }
        public FloatProperty quantityProperty() {
            if (quantity == null) quantity = new SimpleFloatProperty(this, "quantity");
            return quantity;
        }
        public void setName(String val) {
            name.set(val);
        }
        public String getName() {
            return name.get();
        }
        public StringProperty nameProperty() {
            if (name == null ) name = new SimpleStringProperty(this, "name");
            return name;
        }
    }
    public void submitHandler(MouseEvent e) {
        ObservableList<Pair<String, Float>> results;
        ArrayList<Pair<String, Float>> dbResults;
        if(!startDateField.getText().isEmpty() && !endDateField.getText().isEmpty()) {
            dbResults = db.getQuantityUsed(startDateField.getText(), endDateField.getText());
            System.out.println("Finished the inventory work !");
            quantityCol.setCellValueFactory(new PropertyValueFactory<InvResultData, Float>("quantity"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<InvResultData, String>("name"));

            for (Pair<String, Float> ele : dbResults) {
                TableRow<InvResultData> row = new TableRow<>();
                row.setItem(new InvResultData(ele.getKey(), ele.getValue()));
                resultTable.getItems().add(new InvResultData(ele.getKey(), ele.getValue()));
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

