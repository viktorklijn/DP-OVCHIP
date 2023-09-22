package domain;

import java.sql.Date;
import java.util.List;

public class Reiziger {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel = null;
    private String achternaam;
    private Date geboortedatum;
    private List<OvChipkaart> ov_chipkaarten;
    private Adres adres;

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.reiziger_id = id;
        this.voorletters = voorletters;
        this.achternaam = achternaam;        this.tussenvoegsel = tussenvoegsel;
        this.geboortedatum = geboortedatum;
    }

    public int getId() {
        return reiziger_id;
    }

    public void setId(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String getNaam() {
        if (tussenvoegsel == null) {
            return voorletters + " " + achternaam;
        }
        return voorletters + " " + tussenvoegsel + " " + achternaam;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public java.sql.Date getGeboortedatum() {
        return geboortedatum;
    }

    public List<OvChipkaart> getOv_chipkaarten() {
        return ov_chipkaarten;
    }

    @Override
    public String toString() {
        return "Reiziger{" +
                "reiziger_id=" + reiziger_id +
                ", voorletters='" + voorletters + '\'' +
                ", tussenvoegsel='" + tussenvoegsel + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboortedatum=" + geboortedatum +
                ", ov_chipkaarten=" + ov_chipkaarten +
                ", adres=" + adres +
                '}';
    }
}
