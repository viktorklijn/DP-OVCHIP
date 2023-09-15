import dao.AdresDAO;
import dao.AdresDAOPsql;
import dao.ReizigerDAO;
import dao.ReizigerDAOPsql;
import domain.Adres;
import domain.Reiziger;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
//        testReizigerDAO(new ReizigerDAOPsql(getConnection()));
        testAdresDAO(new AdresDAOPsql(getConnection()));
        closeConnection();
    }

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            String url = "jdbc:postgresql://localhost:5433/ovchip";
            Properties properties = new Properties();
            properties.setProperty("user", "postgres");
            properties.setProperty("password", "admin");

            connection = DriverManager.getConnection(url, properties);

            System.out.println("[Main] Connected to database!");
        }

        return connection;
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[TestFindAll] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[TestSave] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // verwijder reiziger uit database
        System.out.print("[TestDelete] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // update reiziger in database
        Reiziger frank = new Reiziger(12, "f", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.save(frank);
        System.out.print("[TestUpdate] Achternaam voor update: " + rdao.findById(12).getAchternaam() + " ");
        frank = new Reiziger(12, "f", "", "Jansen", java.sql.Date.valueOf(gbdatum));
        rdao.update(frank);
        System.out.print("achternaam na update: " + rdao.findById(12).getAchternaam() + "\n\n");
        rdao.delete(frank);

        // vind reiziger uit database door middel van id
        System.out.print("[TestFindById] Zoek een reiziger met id 1 :\n");
        Reiziger reiziger = rdao.findById(1);
        System.out.println(reiziger + " met id " + reiziger.getId() + "\n");

        // vind reiziger uit database door middel van geboortedatum
        System.out.print("[TestFindByGbdatum] Zoek alle reizigers met 2002-12-03 als geboortedatum :\n");
        reizigers = rdao.findByGbdatum("2002-12-03");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();
    }

    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle reizigers op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[TestFindAll] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuw adres aan en persisteer deze in de database
        Adres a = new Adres(6, "2341NV", "74", "Hugo de Vrieslaan", "Oegstgeest", 6);
        System.out.print("[TestSave] Eerst " + adressen.size() + " reizigers, na AdresDAO.save() ");
        adao.save(a);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " reizigers\n");


        // verwijder adres uit database
        System.out.print("[TestDelete] Eerst " + adressen.size() + " reizigers, na ReizigerDAO.delete() ");
        adao.delete(a);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " reizigers\n");

        // update adres in database
        a = new Adres(6, "2341NV", "74", "Hugo de Vrieslaan", "Oegstgeest", 6);
        adao.save(a);
        System.out.print("[TestUpdate] adres voor update: " + adao.findById(6) + " ");
        a = new Adres(6, "3703SP", "48", "Ridderschapslaan", "Zeist", 6);
        adao.update(a);
        System.out.print("adres na update: " + adao.findById(6) + "\n\n");
        adao.delete(a);

        // vind reiziger uit database door middel van id
        System.out.print("[TestFindById] Zoek een adres met id 1 :\n");
        Adres adres = adao.findById(1);
        System.out.println(adres + "\n");

        // vind adres uit database door middel van reiziger
        System.out.print("[TestFindByGbdatum] Zoek adres van reiziger met id 1 :\n");
        a = adao.findByReiziger(new ReizigerDAOPsql(getConnection()).findById(1));
        System.out.println(a);
        System.out.println();
    }
}