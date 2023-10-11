package dao;

import domain.OvChipkaart;
import domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO{

    private Connection connection;

    public ProductDAOPsql(Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean save(Product product) throws SQLException {
        PreparedStatement insertOVChipkaartProduct = connection.prepareStatement("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (?, ?, ?, ?)");
        PreparedStatement insertProduct = connection.prepareStatement("INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)");
        insertProduct.setInt(1, product.getProduct_nummer());
        insertProduct.setString(2, product.getNaam());
        insertProduct.setString(3, product.getBeschrijving());
        insertProduct.setDouble(4, product.getPrijs());
        insertProduct.executeUpdate();

        insertOVChipkaartProduct.setInt(1, product.getOvChipkaart().getKaart_nummer());
        insertOVChipkaartProduct.setInt(2, product.getProduct_nummer());
        insertOVChipkaartProduct.setString(3, "actief");
        insertOVChipkaartProduct.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
        insertOVChipkaartProduct.executeUpdate();

        insertOVChipkaartProduct.close();
        insertProduct.close();

        return true;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        PreparedStatement removeOVChipkaartProduct = connection.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
        removeOVChipkaartProduct.setInt(1, product.getProduct_nummer());
        removeOVChipkaartProduct.executeUpdate();

        PreparedStatement updateProduct = connection.prepareStatement("UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?");
        updateProduct.setString(1, product.getNaam());
        updateProduct.setString(2, product.getBeschrijving());
        updateProduct.setDouble(3, product.getPrijs());
        updateProduct.setInt(4, product.getProduct_nummer());
        updateProduct.executeUpdate();

        PreparedStatement insertOVChipkaartProduct = connection.prepareStatement("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (?, ?, ?, ?)");
        insertOVChipkaartProduct.setInt(1, product.getOvChipkaart().getKaart_nummer());
        insertOVChipkaartProduct.setInt(2, product.getProduct_nummer());
        insertOVChipkaartProduct.setString(3, "actief");
        insertOVChipkaartProduct.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
        insertOVChipkaartProduct.executeUpdate();

        removeOVChipkaartProduct.close();
        insertOVChipkaartProduct.close();
        updateProduct.close();
        return true;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        PreparedStatement deleteOVChipkaartProduct = connection.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
        PreparedStatement deleteProduct = connection.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
        deleteOVChipkaartProduct.setInt(1, product.getProduct_nummer());
        deleteProduct.setInt(1, product.getProduct_nummer());

        deleteOVChipkaartProduct.executeUpdate();
        deleteProduct.executeUpdate();

        deleteOVChipkaartProduct.close();
        deleteProduct.close();
        return false;
    }

    @Override
    public List<Product> findByOVChipkaart(OvChipkaart ovChipkaart) throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM product INNER JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer WHERE ov_chipkaart_product.kaart_nummer = ?");
        ps.setInt(1, ovChipkaart.getKaart_nummer());
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), ovChipkaart);
            products.add(product);
        }


        return products;
    }

    @Override
    public Product findById(int i) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM product INNER JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer WHERE ov_chipkaart_product.product_nummer = ?");
        ps.setInt(1, i);
        ResultSet rs = ps.executeQuery();

        Product product = null;

        while(rs.next()) {
            product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), new OvChipkaartDAOPsql(connection).findById(rs.getInt(5)));
        }
        return product;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM product INNER JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer");
        ResultSet rs = ps.executeQuery();

        Product product = null;

        while(rs.next()) {
            product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), new OvChipkaartDAOPsql(connection).findById(rs.getInt(5)));
            products.add(product);
        }

        return products;
    }
}
