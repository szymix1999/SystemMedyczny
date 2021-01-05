package database;

import java.sql.*;

public class DbStatements {

    public static int type = 1;
    public static int id = 1;

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

    public static void addPatient(Connection conn, int id_users, String first_name, String last_name, Date birth_date, String sex, String health) throws  SQLException {
        String query = "insert into patients (id_users, first_name, last_name, birth_date, sex, health)" + " values (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id_users);
        preparedStmt.setString(2, first_name);
        preparedStmt.setString(3, last_name);
        preparedStmt.setDate(4, birth_date);
        preparedStmt.setString(5, sex);
        preparedStmt.setString(6, health);

        preparedStmt.execute();
    }

    public static int SearchMedicines(Connection conn, String name) throws SQLException {
        String query = "select name, price, quantity from medicines where name LIKE ? ";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, "%"+name+"%");
        ResultSet rs = preparedStmt.executeQuery();


        return 0;
    }

    public static int checkUser(Connection conn, String log, String pass) throws SQLException {
        String query = "select id, id_type from users where login = ? and password = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, log);
        preparedStmt.setString(2, String.valueOf(pass.hashCode()));

        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            id = rs.getInt("id");
            type = rs.getInt("id_type");
        }
        System.out.println("User id: " + id);
        System.out.println("User type: " + type);

        return type;
    }

    public static void getVisits(Connection conn) throws SQLException {
        String query = "select id, id_type from users where login = ? and password = ?";
    }

}
