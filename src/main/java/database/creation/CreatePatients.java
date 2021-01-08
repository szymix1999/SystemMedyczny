package database.creation;

import database.DbConnector;
import database.DbStatements;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CreatePatients {

    public static void main(String[] args) {
        Connection c = DbConnector.connect();
        int user_index = 41;

        try{
            DbStatements.deletePatient(c);
            Scanner scanner = new Scanner(new File("src\\main\\resources\\txtfile\\tables\\patients.txt"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] arr = line.split(";");
                java.util.Date utilDate = format.parse(arr[2]);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                DbStatements.addPatient(c, user_index, arr[0], arr[1], sqlDate, arr[3], arr[4]);
                System.out.println(user_index + " " + arr[0] + " " + arr[1] + " " + sqlDate + " " + arr[3] + " " + arr[4]);
                user_index++;
            }

            c.close();
            System.out.println("THE END");

        }catch (SQLException | FileNotFoundException | ParseException e){
            e.printStackTrace();
        }

    }

//    public static void main(String[] args) {
//        Connection c = DbConnector.connect();
//
//        try {
//            DbStatements.deletePatient(c);
//            c.close();
//            System.out.println("THE END");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}
