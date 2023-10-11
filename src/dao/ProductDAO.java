package dao;

import domain.OvChipkaart;
import domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    public boolean save(Product product) throws SQLException;
    public boolean update(Product product) throws SQLException;
    public boolean delete(Product product) throws SQLException;
    public List<Product> findByOVChipkaart(OvChipkaart ovChipkaart) throws SQLException;

    public Product findById(int i) throws SQLException;

    public List<Product> findAll() throws SQLException;
}
