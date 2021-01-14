package database;

import javafx.Medicines.Medicines;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DbStatements {

    public static int type = -2;
    public static int id = -1;

    // ----------- Users --------------

    public static void addUser(Connection conn, String name, String pass, int id) throws SQLException {
        String query = "insert into users (id_type, login, password)" + " values (?, ?, ?)";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt (1, id);
        preparedStmt.setString (2, name);
        preparedStmt.setString  (3, String.valueOf(pass.hashCode()));

        // execute the preparedstatement
        preparedStmt.execute();
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

    // ----------- ICD10 --------------

    public static void addICD_10(Connection conn, String index, String desc, String cat) throws SQLException {
        String query = "insert into icd10 (indx, descript, category)" + " values (?, ?, ?)";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString (1, index);
        preparedStmt.setString  (2, desc);
        preparedStmt.setString  (3, cat);

        // execute the preparedstatement
        preparedStmt.execute();
    }

    // ----------- Patients --------------

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

    // ----------- Medicines --------------


    public static void editMedicines(Connection conn, Medicines med) throws SQLException {
        String query;
        if(med.getAlternative()<1){
            query = "update medicines set name=?, price=?, prescription=?, quantity=?, ordered=?, sold=?, returns=?, disposed_of=?, image=? where id=?";
        }else{
            query = "update medicines set name=?, price=?, prescription=?, quantity=?, ordered=?, sold=?, returns=?, disposed_of=?, image=? alternative=? where id=?";
        }
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, med.getName());
        preparedStmt.setFloat(2, med.getPrice());
        preparedStmt.setBoolean(3, med.isPrescription());
        preparedStmt.setInt(4, med.getQuantity());
        preparedStmt.setInt(5, med.getOrdered());
        preparedStmt.setInt(6, med.getSold());
        preparedStmt.setInt(7, med.getReturns());
        preparedStmt.setInt(8, med.getDisposed_of());
        preparedStmt.setString(9, med.getImage());
        if(med.getAlternative()<1){
            preparedStmt.setInt(10, med.getId());
        }else{
            preparedStmt.setInt(10, med.getAlternative());
            preparedStmt.setInt(11, med.getId());
        }
        preparedStmt.execute();

    }

    public static ResultSet SearchNameMedicines(Connection conn, String name) throws SQLException {
        String query = "select id, name, price, prescription, quantity, ordered, sold, returns, disposed_of, alternative, image from medicines where name LIKE ? ";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, "%"+name+"%");
        ResultSet rs = preparedStmt.executeQuery();

        return rs;
    }

    public static ResultSet SearchIdMedicines(Connection conn, int id) throws SQLException {
        String query = "select id, name, price, prescription, quantity, ordered, sold, returns, disposed_of, alternative, image from medicines where id LIKE ? ";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id);
        ResultSet rs = preparedStmt.executeQuery();

        return rs;
    }

    public static void AddMedicine(Connection conn, Medicines med) throws  SQLException {
        String query ="";
        if(med.getAlternative()<1){
            query = "insert into medicines (name, price, prescription, quantity, ordered, sold, returns, disposed_of)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";
        }else{
            query = "insert into medicines (name, price, prescription, quantity, ordered, sold, returns, disposed_of, alternative)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, med.getName());
        preparedStmt.setFloat(2, med.getPrice());
        preparedStmt.setBoolean(3, med.isPrescription());
        preparedStmt.setInt(4, med.getQuantity());
        preparedStmt.setInt(5, med.getOrdered());
        preparedStmt.setInt(6, med.getSold());
        preparedStmt.setInt(7, med.getReturns());
        preparedStmt.setInt(8, med.getDisposed_of());
        if(!(med.getAlternative()<1)){
            preparedStmt.setInt(9, med.getAlternative());
        }
        preparedStmt.execute();
    }

    public static String getMedicineName(Connection conn, int id) throws SQLException {
        String query = "select name from medicines where id = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id);

        ResultSet rs = preparedStmt.executeQuery();

        String name = "";
        while (rs.next()) {
            name = rs.getString("name");
        }
        return name;
    }

    public static boolean updateMedicineQuantity(Connection conn, int id) throws SQLException {
        String getQuery = "select quantity from medicines where id = ?";
        PreparedStatement getPreparedStmt = conn.prepareStatement(getQuery);
        getPreparedStmt.setInt(1, id);

        ResultSet rs = getPreparedStmt.executeQuery();
        int quantity = 0;
        while (rs.next()) {
            quantity = rs.getInt("quantity");
        }

        if(quantity == 0) return false;

        String setQuery = "update medicines set quantity = ? where id = ?";

        PreparedStatement setPreparedStmt = conn.prepareStatement(setQuery);
        setPreparedStmt.setInt(1, (quantity-1));
        setPreparedStmt.setInt(2, id);

        //setPreparedStmt.executeQuery(); --- zeby nie usuwac przy testach z bazy

        return true;
    }

    // ----------- Visits --------------

    public static void addVisit(Connection conn, int id_patients, int id_personel, String visit_name, String change_name, Date visit_date, Date change_date, float cost) throws  SQLException {
        String query = "insert into visits (id_patients, id_personel, visit_name, change_name, visit_date, change_date, cost)" + " values (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id_patients);
        preparedStmt.setInt(2, id_personel);
        preparedStmt.setString(3, visit_name);
        preparedStmt.setString(4, change_name);
        preparedStmt.setDate(5, visit_date);
        preparedStmt.setDate(6, change_date);
        preparedStmt.setFloat(7, cost);

        preparedStmt.execute();
    }

    public static ResultSet getVisitDate(Connection conn, int id) throws SQLException {
        String query = "select id, id_personel, visit_name, change_name, visit_date, change_date, cost from visits where id_patients = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id);

        ResultSet rs = preparedStmt.executeQuery();
        return rs;
    }

    public static void updateVisitCost(Connection conn, int id, float value) throws SQLException {
        String query = "update visits set cost = ? where id = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setFloat(1, value);
        preparedStmt.setInt(2, id);

        //preparedStmt.execute();   --żeby nie zmieniać co chwilę rekordów
    }

    public static void updateVisitName(Connection conn, int id, String old_name, String new_name) throws SQLException {
        String query = "update visits set visit_name = ?, change_name = ? where id = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, new_name);
        preparedStmt.setString(2, old_name);
        preparedStmt.setInt(3, id);

        preparedStmt.execute();
    }

    public static void updateVisitDate(Connection conn, int id, Date old_date, Date new_date) throws SQLException {
        String query = "update visits set visit_date = ?, change_date = ? where id = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setDate(1, new_date);
        preparedStmt.setDate(2, old_date);
        preparedStmt.setInt(3, id);

        preparedStmt.execute();
    }


    // ----------- Presciptions --------------

    public static void addPrescription (Connection conn, int id_patients, int id_personel, int id_medicine, String name, float cost, Date end_date) throws  SQLException {
        String query = "insert into prescriptions (id_patients, id_personel, id_medicine, name, cost, end_date)" + " values (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id_patients);
        preparedStmt.setInt(2, id_personel);
        preparedStmt.setInt(3, id_medicine);
        preparedStmt.setString(4, name);
        preparedStmt.setFloat(5, cost);
        preparedStmt.setDate(6, end_date);

        preparedStmt.execute();
    }

    public static ResultSet getPrescriptionDate(Connection conn, int id) throws SQLException {
        String query = "select id, id_personel, id_medicine, name, cost, end_date from prescriptions where id_patients = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id);

        ResultSet rs = preparedStmt.executeQuery();
        return rs;
    }

    public static void updatePrescriptionCost(Connection conn, int id, float value) throws SQLException {
        String query = "update prescriptions set cost = ? where id = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setFloat(1, value);
        preparedStmt.setInt(2, id);

        //preparedStmt.execute();   --żeby nie zmieniać co chwilę rekordów
    }

    // ----------- Personel --------------
    
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

    public static String getPersonelName(Connection conn, int id) throws SQLException {
        String query = "select first_name, last_name from personel where id = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id);

        ResultSet rs = preparedStmt.executeQuery();

        String name = "";
        while (rs.next()) {
            name = rs.getString("first_name") + " " + rs.getString("last_name");
        }
        return name;
    }

    public static ArrayList<String> getPersonel(Connection conn) throws  SQLException{
        String query = "SELECT id, first_name ,last_name, profession FROM personel";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        ResultSet result = preparedStmt.executeQuery();

        ArrayList<String> list = new ArrayList<>();
        while(result.next()){
            list.add(result.getString("first_name"));
            list.add(result.getString("last_name"));
            list.add(result.getString("profession"));
            list.add(String.valueOf(result.getInt("id")));
        }

        return list;
    }
    public static ResultSet getPersonel(Connection conn, String id) throws  SQLException{
        String query = "SELECT * FROM personel WHERE id=?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, Integer.parseInt(id));

        ResultSet r = preparedStmt.executeQuery();
        return r;
    }



    // ----------- Tables --------------

    public static void deleteTable(Connection conn, String name) throws SQLException {
        String query = "drop table if exists ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, name);

        preparedStmt.execute();
    }

    public static void createTable(Connection conn, String Q) throws SQLException {
        String query = "?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, Q);

        preparedStmt.execute();
    }

}
