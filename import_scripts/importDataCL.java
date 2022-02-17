package import_scripts;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.sql.*;

public class importDataCL {
    public static void main(String[] args){
        // Show Files available
        File directory = new File(".");
        System.out.println("Select Invoice File to Import: ");

        int counter = 0;
        for(File file : Objects.requireNonNull(directory.listFiles())){
            System.out.println("["+counter+"] "+ file.getName());
            counter++;
        }

        // File Selection
        Scanner inputRead = new Scanner(System.in);
        System.out.print("\nSelection Number: ");
        String fileName = Objects.requireNonNull(directory.listFiles())[inputRead.nextInt()].getName();

        // Database Information
        System.out.print("Connection URL: ");
        String dbUrl = inputRead.nextLine();
        System.out.print("UserName: ");
        String dbUser = inputRead.nextLine();
        System.out.print("Password: ");
        String dbPass = inputRead.nextLine();
        inputRead.close();

        // Attempt Connection to Database

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        }catch (Exception e){
            System.out.println("Connection Failed :(");
            System.exit(0);
        }
        System.out.println("\n Connection Succeeded!");

        // Use that Connection to Parse the file

        if(importInvoiceDB.importFile(conn, fileName) >= 0){
            System.out.println("Finished Successfully!");
        }else{
            System.out.println("An issue has occured!");
        }
        System.exit(0);
    }
}
