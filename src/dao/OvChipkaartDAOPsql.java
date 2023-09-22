package dao;

import domain.OvChipkaart;
import domain.Reiziger;

import java.sql.Connection;
import java.util.List;

public class OvChipkaartDAOPsql implements OvChipkaartDAO {
    private Connection connection;

    public OvChipkaartDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<OvChipkaart> findByReiziger(Reiziger reiziger) {
        return reiziger.getOv_chipkaarten();
    }
}
