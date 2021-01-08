package database.creation;

import database.DbConnector;
import database.DbStatements;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class CreatePersonel {
    public static void main(String[] args) {
        Connection c = DbConnector.connect();
        int user_index = 192;

        try{
            Scanner scanner = new Scanner(new File("src\\main\\resources\\txtfile\\tables\\personel.txt"));

            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] arr = line.split(";");
                DbStatements.addPersonel(c, user_index, arr[0], arr[1], arr[2], arr[3]);
                System.out.println(user_index + " " + arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3]);
                user_index++;
            }

            c.close();
            System.out.println("THE END");

        }catch (SQLException | FileNotFoundException e){
            e.printStackTrace();
        }

    }
}
