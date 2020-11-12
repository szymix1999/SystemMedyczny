package database.creation;


import database.DbConnector;
import database.DbStatements;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

//pacjent=0
//lekarz=1
//aptekarz=2
//gosc=3
//ksiegowy=4
//admin=5
//kierownik=6
//osobazrecepcji=7


public class CreateUserTable {

    public static void f(File f, Connection c) throws FileNotFoundException, SQLException{
        System.out.println("Teraz "+f.getName());
        Scanner scanner = new Scanner(f);
        String line = scanner.nextLine();
        String[] arr = line.split(" ");

        int id=-1;
        switch(arr[0]){
            case "pacjent": id=0; break;
            case "lekarz": id=1; break;
            case "aptekarz": id=2; break;
            case "gosc": id=3; break;
            case "ksiegowy": id=4; break;
            case "admin": id=5; break;
            case "kierownik": id=6; break;
            case "recepcja": id=7; break;
        }
        int i=0;
        while(true){
            System.out.println((i++) + " " + arr[0]);
            DbStatements.addUser(c, arr[0],arr[1], id);

            if(!scanner.hasNext()){
                break;
            }

            line = scanner.nextLine();
            arr = line.split(" ");
        }
    }


    public static void main(String[] args) {
        Connection c = DbConnector.connect();

        try{
            File directory = new File("src\\main\\resources\\users");

            System.out.println(directory.getAbsolutePath());
            File[] files = directory.listFiles();

            if(files!=null) {
                for (File file : files) {
                    f(file.getAbsoluteFile(), c);
                }
            }

            c.close();
            System.out.println("THE END");

        }catch (SQLException | FileNotFoundException e){
            e.printStackTrace();
        }

    }

}
