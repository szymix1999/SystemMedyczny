package database.creation;

import database.DbConnector;
import database.DbStatements;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;


public class CreateDataBase {

    public static void main(String[] args) {
        Connection c = DbConnector.connect();

        String tables[] = {"users", "personel", "patients", "visits", "notes", "medicines",
                "prescriptions", "referrals", "contracts", "icd10", "med_icd10", "illness",
                "diagnosis"};

        try {
            for (int i = 0; i < tables.length; i++) {
                System.out.println("Table deleted: " + tables[i]);
                DbStatements.deleteTable(c, tables[i]);
            }

            String data = "";
            data = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\txtfile\\Database\\SQL_tables.txt")));
            DbStatements.createTable(c, data);

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

}
