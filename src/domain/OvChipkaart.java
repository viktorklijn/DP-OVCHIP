package domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OvChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private double saldo;
    private int reiziger_id;
    private List<Product> producten;

    public OvChipkaart(int kaart_nummer, Date geldig_tot, int klasse, double saldo, int reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
        this.producten = new ArrayList<>();
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }
    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public List<Product> getProducten() {
        return producten;
    }

    public void removeProduct(Product product) {
        producten.remove(product);
    }

    public int getKlasse() {
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void addProduct(Product product) {
        producten.add(product);
    }

    @Override
    public String toString() {
        return "OvChipkaart{" +
                "kaart_nummer=" + kaart_nummer +
                ", geldig_tot=" + geldig_tot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger_id=" + reiziger_id +
                ", producten=" + producten +
                '}';
    }
}
