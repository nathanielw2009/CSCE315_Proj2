package import_scripts;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.sql.*;



public class importInvoiceDB {
    private static final int START_ROW = 2;
    private static final int SKU = 2;
    private static final int DESCRIPTION = 1;
    private static final int DELIVERED = 4;
    private static final int PRICE = 8;
    private static final int TOTAL = 9;
    private static final int CATEGORY = 10;
    private static final int LONG_DESCRIPTION = 12;

    public static int importFile(Connection conn, String filename) {
        ArrayList<ArrayList<String>> matrix = Csv2Array(filename);
        for(int i = START_ROW; i < matrix.size(); i++){

        }

        return 0;
    }

    public static int  importItemsFromInvoice(Connection conn, String filename){
        // Create Statement from database
        Statement stmt= null;
        StringBuilder sqlQ;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Parse the array and build an SQL command
        ArrayList<ArrayList<String>> matrix = Csv2Array(filename);

        sqlQ = new StringBuilder("""
                INSERT inventory_items (sku, quantity, category, price, description, quantity_per_order, usage_category)
                VALUES
                """);
        String CAT_CURRENT = "NONE";
        for(int i = START_ROW; i < matrix.size(); i++){
            ArrayList<String> currentRow = matrix.get(i);

            if(currentRow.size()< 2){
                if(currentRow.size() != 0){
                    CAT_CURRENT = currentRow.get(0);
                }
                continue;
            }
            if(currentRow.size()< 12){
                continue;
            }

            sqlQ.append("(").append(currentRow.get(SKU).replaceAll("\"", "")).append(", ").append(0).
                    append(", ").append(currentRow.get(CATEGORY).replaceAll("\"", "")).append(", ").
                    append(currentRow.get(PRICE).replaceAll("\"", "")).append(", ").
                    append(currentRow.get(DESCRIPTION).replaceAll("\"", "")).append(", ").
                    append(currentRow.get(LONG_DESCRIPTION).replaceAll("\"", "")).append(", ").
                    append(CAT_CURRENT).append("), \n");
        }
        sqlQ = new StringBuilder(sqlQ.substring(0, sqlQ.lastIndexOf(",")) + ";");

        // Verification
        System.out.println(sqlQ);
        System.out.println("Does this seem correct? (y/n): ");
        Scanner inputText = new Scanner(System.in);

        // Execution
        if(inputText.nextLine().equals("y")){
            try {
                stmt.executeUpdate(sqlQ.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Oh well, Have a nice Day!");
            return -1;
        }
        inputText.close();
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return 0;
    }


    private static ArrayList<ArrayList<String>> Csv2Array(String filename){
        // Read File
        File fileToRead = new File(filename);
        Scanner fileReader = null;

        try {
            fileReader = new Scanner(fileToRead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }


        // Port the file to array
        ArrayList<ArrayList<String>> matrix = new ArrayList<>();
        while(fileReader.hasNext()){
            String currentLine = fileReader.nextLine();
            matrix.add(new ArrayList<>(Arrays.asList(currentLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"))));
        }
        return matrix;
    }
}
