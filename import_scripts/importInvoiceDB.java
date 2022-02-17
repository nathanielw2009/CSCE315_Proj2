package import_scripts;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.sql.*;

public class importInvoiceDB {
    public static int importFile(Connection conn, String filename) {
        ArrayList<ArrayList<String>> matrix = Csv2Array(filename);

        // TODO: Parse matrix and send to the database
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
        int row =0;
        while(fileReader.hasNext()){
            String currentLine = fileReader.nextLine();

            matrix.add(new ArrayList<>(Arrays.asList(currentLine.split(","))));
        }
        return matrix;
    }
}
