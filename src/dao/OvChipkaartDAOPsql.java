package dao;

import domain.Adres;
import domain.OvChipkaart;
import domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OvChipkaartDAOPsql implements OvChipkaartDAO {
    private Connection connection;

    public OvChipkaartDAOPsql(Connection connection) {
        this.connection = connection;
    }

    public boolean save(OvChipkaart ovChipkaart) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO ov_chipkaart  (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, ovChipkaart.getKaart_nummer());
        ps.setDate(2, ovChipkaart.getGeldig_tot());
        ps.setInt(3, ovChipkaart.getKlasse());
        ps.setDouble(4, ovChipkaart.getSaldo());
        ps.setInt(5, ovChipkaart.getReiziger_id());

        ps.executeUpdate();

        ps.close();

        return true;
    }

    @Override
    public boolean update(OvChipkaart ovChipkaart) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?");
        ps.setInt(5, ovChipkaart.getKaart_nummer());
        ps.setDate(1, ovChipkaart.getGeldig_tot());
        ps.setInt(2, ovChipkaart.getKlasse());
        ps.setDouble(3, ovChipkaart.getSaldo());
        ps.setInt(4, ovChipkaart.getReiziger_id());

        ps.executeUpdate();

        ps.close();

        return true;
    }

    @Override
    public boolean delete(OvChipkaart ovChipkaart) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");
        ps.setInt(1, ovChipkaart.getKaart_nummer());
        ps.executeUpdate();

        ps.close();
        return true;
    }

    @Override
    public OvChipkaart findById(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        rs.next();

        OvChipkaart ovChipkaart = new OvChipkaart(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getDouble(4), rs.getInt(5));

        ps.close();
        rs.close();
        return ovChipkaart;
    }

    @Override
    public List<OvChipkaart> findAll() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM ov_chipkaart");
        ResultSet rs = ps.executeQuery();

        List<OvChipkaart> ovChipkaartList = new ArrayList<>();

        while(rs.next()) {
            OvChipkaart ovChipkaart = new OvChipkaart(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getDouble(4), rs.getInt(5));
            ovChipkaartList.add(ovChipkaart);
        }

        ps.close();
        rs.close();
        return ovChipkaartList;
    }

    @Override
    public List<OvChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id = ?");
        ps.setInt(1, reiziger.getId());
        ResultSet rs = ps.executeQuery();

        rs.next();

        List<OvChipkaart> ovChipkaartList = new ArrayList<>();

        while(rs.next()) {
            OvChipkaart ovChipkaart = new OvChipkaart(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getDouble(4), rs.getInt(5));
            ovChipkaartList.add(ovChipkaart);
        }

        ps.close();
        rs.close();
        return ovChipkaartList;
    }


}
