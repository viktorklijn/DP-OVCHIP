package dao;

import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOsql implements ReizigerDAO {
    private Connection connection;

    public ReizigerDAOsql(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO reiziger values (?, ?, ? , ?, ?)");
            ps.setInt(1, reiziger.getId());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setDate(5, reiziger.getGeboortedatum());
            ps.executeQuery();

            ps.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?");
            ps.setInt(5, reiziger.getId());
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, reiziger.getGeboortedatum());

            ResultSet rs = ps.executeQuery();

            ps.close();
            rs.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");
            ps.setInt(1, reiziger.getId());
            ps.executeQuery();

            ps.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Reiziger reiziger = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));

            ps.close();
            rs.close();
            return reiziger;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?");
            ps.setDate(1, Date.valueOf(datum));
            ResultSet rs = ps.executeQuery();

            List<Reiziger> reizigerList = new ArrayList<>();

            while (rs.next()) {
                Reiziger reiziger = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
                reizigerList.add(reiziger);
            }

            ps.close();
            rs.close();
            return reizigerList;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM reiziger");
            ResultSet rs = ps.executeQuery();

            List<Reiziger> reizigerList = new ArrayList<>();

            while(rs.next()) {
                Reiziger reiziger = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
                reizigerList.add(reiziger);
            }

            ps.close();
            rs.close();
            return reizigerList;
        } catch (SQLException e) {
            return null;
        }
    }
}
