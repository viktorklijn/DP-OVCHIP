package dao;

import domain.OvChipkaart;
import domain.Reiziger;

import java.util.List;

public interface OvChipkaartDAO {
    public List<OvChipkaart> findByReiziger(Reiziger reiziger);
}
