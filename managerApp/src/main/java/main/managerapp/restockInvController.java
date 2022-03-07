package main.managerapp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.managerapp.dbConnections.dbConnections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class restockInvController {

    @FXML
    TableView mainTable;

    @FXML
    HBox inputBox;



    private dbConnections db;
    private ArrayList<String> colList = new ArrayList<String>(Arrays.asList("sku", "quantity", "price", "quantity_per_order", "usage_category", "fill_level"));
    private invEntry currentEntry = null;
    private HashSet<invEntry> updatedEntries = new HashSet<invEntry>();

    public void submitHandler(MouseEvent e){
        JFXTextField skuField = (JFXTextField) inputBox.lookup("#skuInput");
        JFXTextField quantityField = (JFXTextField) inputBox.lookup("#quantityInput");
        JFXTextField priceField = (JFXTextField) inputBox.lookup("#priceInput");
        JFXTextField quantity_per_orderField = (JFXTextField) inputBox.lookup("#quantity_per_orderInput");
        JFXTextField usage_categoryField = (JFXTextField) inputBox.lookup("#usage_categoryInput");
        JFXTextField fill_levelField = (JFXTextField) inputBox.lookup("#fill_levelInput");

        currentEntry.setSku(skuField.getText());
        currentEntry.setQuantity(Double.parseDouble(quantityField.getText()));
        currentEntry.setPrice(priceField.getText());
        currentEntry.setQuantity_per_order(quantity_per_orderField.getText());
        currentEntry.setUsage_category(usage_categoryField.getText());
        currentEntry.setFill_level(Double.parseDouble(fill_levelField.getText()));

        skuField.clear();
        quantityField.clear();
        priceField.clear();
        quantity_per_orderField.clear();
        usage_categoryField.clear();
        fill_levelField.clear();
        mainTable.getSelectionModel().clearSelection();
        updatedEntries.add(currentEntry);
        currentEntry = null;
    }

    public void changedSelection(invEntry entry){
        currentEntry = entry;
        JFXTextField skuField = (JFXTextField) inputBox.lookup("#skuInput");
        JFXTextField quantityField = (JFXTextField) inputBox.lookup("#quantityInput");
        JFXTextField priceField = (JFXTextField) inputBox.lookup("#priceInput");
        JFXTextField quantity_per_orderField = (JFXTextField) inputBox.lookup("#quantity_per_orderInput");
        JFXTextField usage_categoryField = (JFXTextField) inputBox.lookup("#usage_categoryInput");
        JFXTextField fill_levelField = (JFXTextField) inputBox.lookup("#fill_levelInput");
        skuField.setText(entry.getSku());
        quantityField.setText(String.valueOf(entry.getQuantity()));
        priceField.setText(entry.getPrice());
        quantity_per_orderField.setText(entry.getQuantity_per_order());
        usage_categoryField.setText(entry.getUsage_category());
        fill_levelField.setText(String.valueOf(entry.getFill_level()));
    }


    public void initialize(){
        db = new dbConnections();

        ArrayList<HashMap<String, String>> data = db.getColumns("inventory_items",
                colList);

        for(String item : colList){
            TableColumn col = new TableColumn(item);
            col.setCellValueFactory(new PropertyValueFactory<invEntry, String>(item));
            mainTable.getColumns().add(col);
        }

        ArrayList<invEntry> invEntries1 = new ArrayList<>();

        for(HashMap<String, String> entry : data){
            invEntries1.add(new invEntry(entry.get("sku"), entry.get("quantity"), entry.get("price"), entry.get("quantity_per_order"),
                    entry.get("usage_category"), entry.get("fill_level")));
        }
        ObservableList<invEntry> invEntries = FXCollections.observableArrayList(invEntries1);
        mainTable.setItems(invEntries);
        mainTable.setEditable(true);
        mainTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change change) {
                if(change.getList().size() > 0){
                    changedSelection((invEntry) change.getList().get(0));
                }
            }
        });

        inputBox.spacingProperty().set(10);
        for(String item : colList){
            VBox entryBox = new VBox();
            entryBox.setAlignment(Pos.CENTER);
            Label entryLabel = new Label(item);
            JFXTextField entryField = new JFXTextField();
            inputBox.getChildren().add(entryBox);
            entryField.setId(item+"Input");
            entryBox.getChildren().addAll(entryLabel, entryField);
        }
        VBox submitBox = new VBox();
        JFXButton submitButton = new JFXButton("Submit");
        submitBox.getChildren().add(submitButton);
        submitBox.setAlignment(Pos.CENTER);
        submitBox.setMaxWidth(Double.MAX_VALUE);
        inputBox.getChildren().add(submitBox);
        inputBox.setPadding(new Insets(0, 10, 10, 10));
        submitButton.setOnMouseClicked(mouseEvent -> submitHandler(mouseEvent));

    }




    public static class invEntry{
        public invEntry(String sku, String quantity, String price, String quantity_per_order, String usage_category, String fill_level) {
            this.sku = new SimpleStringProperty(sku);
            this.quantity = new SimpleDoubleProperty(Double.parseDouble(quantity));
            this.price = new SimpleStringProperty(price);
            this.quantity_per_order = new SimpleStringProperty(quantity_per_order);
            this.usage_category = new SimpleStringProperty(usage_category);
            this.fill_level = new SimpleDoubleProperty(Double.parseDouble(fill_level));
        }

        public String getSku() {
            return sku.get();
        }

        public SimpleStringProperty skuProperty() {
            return sku;
        }

        private final SimpleStringProperty sku;
        private final SimpleDoubleProperty quantity;
        private final SimpleStringProperty price;
        private final SimpleStringProperty quantity_per_order;

        public void setSku(String sku) {
            this.sku.set(sku);
        }

        public void setQuantity(double quantity) {
            this.quantity.set(quantity);
        }

        public void setPrice(String price) {
            this.price.set(price);
        }

        public void setQuantity_per_order(String quantity_per_order) {
            this.quantity_per_order.set(quantity_per_order);
        }

        public void setUsage_category(String usage_category) {
            this.usage_category.set(usage_category);
        }

        public void setFill_level(double fill_level) {
            this.fill_level.set(fill_level);
        }

        private final SimpleStringProperty usage_category;
        private final SimpleDoubleProperty fill_level;

        public invEntry(SimpleStringProperty sku, SimpleDoubleProperty quantity, SimpleStringProperty price, SimpleStringProperty quantity_per_order, SimpleStringProperty usage_category, SimpleDoubleProperty fill_level) {
            this.sku = sku;
            this.quantity = quantity;
            this.price = price;
            this.quantity_per_order = quantity_per_order;
            this.usage_category = usage_category;
            this.fill_level = fill_level;
        }


        public String getUsage_category() {
            return usage_category.get();
        }

        public SimpleStringProperty usage_categoryProperty() {
            return usage_category;
        }

        public String getQuantity_per_order() {
            return quantity_per_order.get();
        }

        public SimpleStringProperty quantity_per_orderProperty() {
            return quantity_per_order;
        }

        public double getFill_level() {
            return fill_level.get();
        }

        public SimpleDoubleProperty fill_levelProperty() {
            return fill_level;
        }

        public String getPrice() {
            return price.get();
        }

        public SimpleStringProperty priceProperty() {
            return price;
        }

        public double getQuantity() {
            return quantity.get();
        }

        public SimpleDoubleProperty quantityProperty() {
            return quantity;
        }
    }
}

