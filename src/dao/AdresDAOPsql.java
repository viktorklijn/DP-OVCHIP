package dao;

import domain.Adres;
import domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection connection;
    private ReizigerDAO rdao;

    public AdresDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?, ?)");
        ps.setInt(1, adres.getAdres_id());
        ps.setString(2, adres.getPostcode());
        ps.setString(3, adres.getHuisnummer());
        ps.setString(4, adres.getStraat());
        ps.setString(5, adres.getWoonplaats());
        ps.setInt(6, adres.getReiziger_id());

        ps.executeUpdate();

        ps.close();

        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?");
        ps.setInt(6, adres.getReiziger_id());
        ps.setString(1, adres.getPostcode());
        ps.setString(2, adres.getHuisnummer());
        ps.setString(3, adres.getStraat());
        ps.setString(4, adres.getWoonplaats());
        ps.setInt(5, adres.getReiziger_id());

        ps.executeUpdate();

        ps.close();

        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM adres WHERE adres_id = ?");
        ps.setInt(1, adres.getAdres_id());
        ps.executeUpdate();

        ps.close();
        return true;
    }

    @Override
    public Adres findById(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM adres WHERE adres_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Adres adres = new Adres(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));

        ps.close();
        rs.close();
        return adres;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM adres WHERE reiziger_id = ?");
        ps.setInt(1, reiziger.getId());
        ResultSet rs = ps.executeQuery();
        rs.next();

        Adres adres = new Adres(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));

        ps.close();
        rs.close();

        return adres;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM adres");
        ResultSet rs = ps.executeQuery();

        List<Adres> adresList = new ArrayList<>();

        while(rs.next()) {
            Adres adres = new Adres(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
            adresList.add(adres);
        }

        ps.close();
        rs.close();
        return adresList;
    }
}
