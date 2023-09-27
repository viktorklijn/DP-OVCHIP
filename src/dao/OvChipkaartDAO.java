package dao;

import domain.Adres;
import domain.OvChipkaart;
import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OvChipkaartDAO {
    public boolean save(OvChipkaart ovChipkaart) throws SQLException;
    public boolean update(OvChipkaart ovChipkaart) throws SQLException;
    public boolean delete(OvChipkaart ovChipkaart) throws SQLException;

    public OvChipkaart findById(int id) throws SQLException;

    public List<OvChipkaart> findAll() throws SQLException;

    public List<OvChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
}
