package database;

import java.sql.*;

public class DbStatements {

    public static int type = -2;
    public static int id = -1;

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

    public static void deletePatient(Connection conn) throws SQLException {
        String query = "delete from patients";
        String queryAI = "alter table patients AUTO_INCREMENT = 1";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.execute();

        PreparedStatement preparedStmtAI = conn.prepareStatement(queryAI);
        preparedStmtAI.execute();
    }

    public static ResultSet getPatientData(Connection conn) throws SQLException {
        String query = "select id, first_name, last_name, birth_date, sex, health from patients where id_users = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id);

        ResultSet rs = preparedStmt.executeQuery();
        return rs;
    }

    public static ResultSet SearchMedicines(Connection conn, String name) throws SQLException {
        String query = "select name, price, quantity from medicines where name LIKE ? ";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, "%"+name+"%");
        ResultSet rs = preparedStmt.executeQuery();

        return rs;
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
        System.out.println("User id: " + id + " | User type: " + type);

        return type;
    }

    public static void addVisit(Connection conn, int id_patients, String visit_name, String change_name, Date visit_date, Date change_date, int cost) throws  SQLException {
        String query = "insert into visits (id_patients, visit_name, change_name, visit_date, change_date, cost)" + " values (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id_patients);
        preparedStmt.setString(2, visit_name);
        preparedStmt.setString(3, change_name);
        preparedStmt.setDate(4, visit_date);
        preparedStmt.setDate(5, change_date);
        preparedStmt.setInt(6, cost);

        preparedStmt.execute();
    }

    public static ResultSet getVisitDate(Connection conn, int id) throws SQLException {
        String query = "select visit_name, change_name, visit_date, change_date, cost from visits where id_patients = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id);

        ResultSet rs = preparedStmt.executeQuery();
        return rs;
    }

    public static void addPrescription (Connection conn, int id_patients, int id_personel, String presc_name, int cost, String content, Date end_date) throws  SQLException {
        String query = "insert into prescriptions (id_patients, id_personel, presc_name, cost, content, end_date)" + " values (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id_patients);
        preparedStmt.setInt(2, id_personel);
        preparedStmt.setString(3, presc_name);
        preparedStmt.setInt(4, cost);
        preparedStmt.setString(5, content);
        preparedStmt.setDate(6, end_date);

        preparedStmt.execute();
    }

    public static ResultSet getPrescriptionDate(Connection conn, int id) throws SQLException {
        String query = "select presc_name, cost, content, end_date from prescriptions where id_patients = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id);

        ResultSet rs = preparedStmt.executeQuery();
        return rs;
    }

    public static void addPersonel (Connection conn, int id_users, String first_name, String last_name, String profession, String salary) throws  SQLException {
        String query = "insert into personel (id_users, first_name, last_name, profession, salary)" + " values (?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id_users);
        preparedStmt.setString(2, first_name);
        preparedStmt.setString(3, last_name);
        preparedStmt.setString(4, profession);
        preparedStmt.setString(5, salary);

        preparedStmt.execute();
    }

}
