package database.creation;


import database.DbConnector;
import database.DbStatements;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateICD10 {

    public static void main(String[] args) {
        Connection c = DbConnector.connect();

        try{
            Scanner scanner = new Scanner(new File("src\\main\\resources\\ICD_10\\ICD_10v2.txt"));

            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] arr = line.split(";");
                DbStatements.addICD_10(c, arr[0], arr[2], arr[3]);
                System.out.println(arr[0]);
            }

            c.close();
            System.out.println("THE END");

        }catch (SQLException | FileNotFoundException e){
            e.printStackTrace();
        }

    }
}
