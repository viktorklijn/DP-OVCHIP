import dao.*;
import domain.Adres;
import domain.OvChipkaart;
import domain.Product;
import domain.Reiziger;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
//        testReizigerDAO(new ReizigerDAOPsql(getConnection()));
//        testAdresDAO(new AdresDAOPsql(getConnection()));
//        testOvchipDAO(new OvChipkaartDAOPsql(getConnection()));
        testProductDAO(new ProductDAOPsql(getConnection()));
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

        adao.findById(100);
    }

    private static void testOvchipDAO(OvChipkaartDAO ovChipkaartDAO) throws SQLException {
        System.out.println("\n---------- Test OvChipkaartDAO -------------");

        // Haal alle ovchipkaarten op uit de database
        List<OvChipkaart> ovChipkaarten = ovChipkaartDAO.findAll();
        System.out.println("[TestFindAll] OvChipkaartDAO.findAll() geeft de volgende adressen:");
        for (OvChipkaart ovChipkaart : ovChipkaarten) {
            System.out.println(ovChipkaart);
        }
        System.out.println();

        // Maak een nieuwe ovchipkaart aan en persisteer deze in de database
        OvChipkaart ovChipkaart = new OvChipkaart(1, java.sql.Date.valueOf("2030-01-01"), 2, 3000.00, 6);
        System.out.print("[TestSave] Eerst " + ovChipkaarten.size() + " ovchipkaarten, na OvChipkaartDAO.save() ");
        ovChipkaartDAO.save(ovChipkaart);
        ovChipkaarten = ovChipkaartDAO.findAll();
        System.out.println(ovChipkaarten.size() + " ovchipkaarten\n");


        // verwijder ovchipkaart uit database
        System.out.print("[TestDelete] Eerst " + ovChipkaarten.size() + " ovchipkaarten, na OvChipkaartDAO.delete() ");
        ovChipkaartDAO.delete(ovChipkaart);
        ovChipkaarten = ovChipkaartDAO.findAll();
        System.out.println(ovChipkaarten.size() + " ovchipkaarten\n");

        // update ovchipkaart in database
        ovChipkaartDAO.save(ovChipkaart);
        System.out.print("[TestUpdate] ovchipkaart voor update: " + ovChipkaartDAO.findById(1) + " ");
        ovChipkaart = new OvChipkaart(1, java.sql.Date.valueOf("2035-01-04"), 1, 3333.00, 6);
        ovChipkaartDAO.update(ovChipkaart);
        System.out.print("ovchipkaart na update: " + ovChipkaartDAO.findById(1) + "\n\n");
        ovChipkaartDAO.delete(ovChipkaart);

        // vind ovchipkaart uit database door middel van id
        System.out.print("[TestFindById] Zoek een ovchipkaart met id 35283 :\n");
        OvChipkaart ovChipkaart1 = ovChipkaartDAO.findById(35283);
        System.out.println(ovChipkaart1 + "\n");

        // vind ovchipkaart uit database door middel van reiziger
        System.out.print("[TestFindByReiziger] vind ovchipkaart door middel van reiziger :\n");
        ovChipkaarten = ovChipkaartDAO.findByReiziger(new ReizigerDAOPsql(getConnection()).findById(2));
        for (OvChipkaart ovChipkaart2 : ovChipkaarten) {
            System.out.println(ovChipkaart);
        }
        System.out.println();

    }
    private static void testProductDAO(ProductDAO productDAO) throws SQLException {
        System.out.println("\n---------- Test ProductDAO -------------");

//        // Haal alle producten op uit de database
        List<Product> producten = productDAO.findAll();
        System.out.println("[TestFindAll] ProductDAO.findAll() geeft de volgende producten:");
        for (Product product : producten) {
            System.out.println(product);
        }
        System.out.println();

//        // Maak een nieuw product aan en persisteer deze in de database
        Product product = new Product(10, "test", "test", 1.00, new OvChipkaartDAOPsql(getConnection()).findById(35283));
        System.out.print("[TestSave] Eerst " + producten.size() + " producten, na ProductDAO.save() ");
        productDAO.save(product);
        producten = productDAO.findAll();
        System.out.println(producten.size() + " producten\n");
//
//
//        // verwijder product uit database
        System.out.print("[TestDelete] Eerst " + producten.size() + " producten, na ProductDAO.delete() ");
        productDAO.delete(product);
        producten = productDAO.findAll();
        System.out.println(producten.size() + " producten\n");
//
        // update product in database
        product = new Product(10, "test", "test", 1.00, new OvChipkaartDAOPsql(getConnection()).findById(35283));
        productDAO.save(product);

        System.out.print("[TestUpdate] product voor update: " + productDAO.findById(10) + " ");
        product = new Product(10, "test2", "test2", 100.00, new OvChipkaartDAOPsql(getConnection()).findById(35283));
        productDAO.update(product);
        System.out.println();
        System.out.print("product na update: " + productDAO.findById(10) + "\n\n");
        productDAO.delete(product);

        // vind producten uit database door middel van ovchipkaart
        System.out.print("[TestFindByOvChipkaart] vind producten door middel van ovchipkaart :\n");
        OvChipkaart ovChipkaart = new OvChipkaartDAOPsql(connection).findById(35283);
        List<Product> products = productDAO.findByOVChipkaart(ovChipkaart);
        for (Product p : products) {
            System.out.println(p);
        }
        System.out.println();

        // Ovchip en product toevoegen aan reiziger test
        System.out.print("[TestAddProductToReiziger] voeg product toe aan reiziger :\n");
        Reiziger reiziger = new Reiziger(10, "H", "", "Jonkers", java.sql.Date.valueOf("2002-12-03"));
        Adres adres = new Adres(10, "2341NV", "74", "Hugo de Vrieslaan", "Oegstgeest", 10);
        reiziger.setAdres(adres);
        new ReizigerDAOPsql(connection).save(reiziger);
        new AdresDAOPsql(connection).save(adres);
        ovChipkaart = new OvChipkaart(11111, java.sql.Date.valueOf("2030-01-01"), 2, 3000.00, 10);
        reiziger.addOvChipkaart(ovChipkaart);
        new OvChipkaartDAOPsql(connection).save(ovChipkaart);

        product = new Product(11, "test", "test", 1.00, ovChipkaart);
        productDAO.save(product);

        System.out.println(reiziger);

        new ProductDAOPsql(connection).delete(product);
        new OvChipkaartDAOPsql(connection).delete(ovChipkaart);
        new AdresDAOPsql(connection).delete(adres);
        new ReizigerDAOPsql(connection).delete(reiziger);


    }
}