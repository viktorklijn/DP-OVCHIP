package domain;

import java.util.Date;
import java.util.List;

public class OvChipkaart {
    private int kaart_nummer;
    private String voorletters;
    private String tussenvoesel;
    private String achternaam;
    private Date geboortedatum;
    private Reiziger reiziger;
    private List<OvChipkaartProduct> ov_chipkaart_producten;
}
