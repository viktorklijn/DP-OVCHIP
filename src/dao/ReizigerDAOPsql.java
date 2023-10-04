package dao;

import domain.Adres;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection connection;

    public ReizigerDAOPsql(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO reiziger values (?, ?, ? , ?, ?)");
            ps.setInt(1, reiziger.getId());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setDate(5, reiziger.getGeboortedatum());

            ps.executeUpdate();

            ps.close();
            return true;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
            PreparedStatement ps = connection.prepareStatement("UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?");
            ps.setInt(5, reiziger.getId());
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, reiziger.getGeboortedatum());

            ps.executeUpdate();

            ps.close();
            return true;

    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");
            ps.setInt(1, reiziger.getId());
            ps.executeUpdate();

            if(reiziger.getAdres() != null) {
                ps = connection.prepareStatement("DELETE FROM adres WHERE adres_id = ?");
                ps.setInt(1, reiziger.getAdres().getAdres_id());
                ps.executeUpdate();
            }

            ps.close();
            return true;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Reiziger reiziger = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
            List<Adres> adressen = new AdresDAOPsql(connection).findAll();

            for (Adres adres : adressen) {
                if (adres.getReiziger_id() == reiziger.getId()) {
                    reiziger.setAdres(adres);
                }
            }

            ps.close();
            rs.close();
            return reiziger;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?");
            ps.setDate(1, Date.valueOf(datum));
            ResultSet rs = ps.executeQuery();

            List<Adres> adressen = new AdresDAOPsql(connection).findAll();
            List<Reiziger> reizigerList = new ArrayList<>();

            while (rs.next()) {
                Reiziger reiziger = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
                reizigerList.add(reiziger);
            }

            for (Adres adres : adressen) {
                for (Reiziger reiziger : reizigerList) {
                    if (adres.getReiziger_id() == reiziger.getId()) {
                        reiziger.setAdres(adres);
                    }
                }
            }

            ps.close();
            rs.close();
            return reizigerList;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM reiziger");
            ResultSet rs = ps.executeQuery();

            List<Reiziger> reizigerList = new ArrayList<>();

            List<Adres> adressen = new AdresDAOPsql(connection).findAll();

            while(rs.next()) {
                Reiziger reiziger = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
                reizigerList.add(reiziger);
            }

            for (Adres adres : adressen) {
                for (Reiziger reiziger : reizigerList) {
                    if (adres.getReiziger_id() == reiziger.getId()) {
                        reiziger.setAdres(adres);
                    }
                }
            }

            ps.close();
            rs.close();
            return reizigerList;
    }
}
