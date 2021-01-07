package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {

    private static String URL ="jdbc:mysql://medyczne-systemy.mysql.database.azure.com:3306/med_database?useSSL=true&requireSSL=false&serverTimezone=UTC";
    private static String PASSWORD = "#cyberpunk2020";
    private static String USER = "med_admin@medyczne-systemy";

    public static Connection connect(){
        Connection connetion = null;
        System.out.println("Start");
        try {
            connetion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connetion;
    }
}
