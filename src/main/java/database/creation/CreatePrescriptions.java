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

public class CreatePrescriptions {

    public static void main(String[] args) {
        Connection c = DbConnector.connect();
        int patient_index = 1;
        int personel_index = 1;
        int medicine_index = 1;

        try{
            Scanner scanner = new Scanner(new File("src\\main\\resources\\txtfile\\tables\\prescriptions.txt"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] arr = line.split(";");
                java.util.Date utilDate = format.parse(arr[2]);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                DbStatements.addPrescription(c, patient_index, personel_index, medicine_index, arr[0], Integer.parseInt(arr[1]), sqlDate);
                System.out.println(patient_index + " " + medicine_index + " " + arr[0] + " " + arr[1] + " " + sqlDate);
                medicine_index++;
                personel_index++;
            }

            c.close();
            System.out.println("THE END");

        }catch (SQLException | FileNotFoundException | ParseException e){
            e.printStackTrace();
        }
    }

}
