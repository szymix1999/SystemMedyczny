package javafx.Administration;

import database.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL {
    public static void main(String[] args) throws SQLException {
        Connection c = DbConnector.connect();


        String query = "SELECT last_name FROM personel WHERE id_users = 207";

        PreparedStatement preparedStmt = c.prepareStatement(query);
        ResultSet r = preparedStmt.executeQuery();

        while(r.next()){
            System.out.println(r.getString(1));
            break;
            //System.out.println(" "+r.getString("login"));
        }

    }
}
