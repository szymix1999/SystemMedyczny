package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbStatements {

    public static int type = -1;

    public static void addUser(Connection conn, String name, String pass, int id) throws SQLException {
        String query = "insert into users (id_type, login, password)" + " values (?, ?, ?)";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt (1, id);
        preparedStmt.setString (2, name);
        preparedStmt.setString  (3, String.valueOf(pass.hashCode()));

        // execute the preparedstatement
        preparedStmt.execute();
    }

    public static void addICD_10(Connection conn, String index, String desc, String cat) throws SQLException {
        String query = "insert into icd10 (indx, descript, category)" + " values (?, ?, ?)";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString (1, index);
        preparedStmt.setString  (2, desc);
        preparedStmt.setString  (3, cat);

        // execute the preparedstatement
        preparedStmt.execute();
    }

    public static int checkUser(Connection conn, String log, String pass) throws SQLException {
        String query = "select id_type from users where login = ? and password = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, log);
        preparedStmt.setString(2, String.valueOf(pass.hashCode()));

        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next())
            type = rs.getInt("id_type");
        System.out.println("User type: " + type);

        return type;
    }

}
