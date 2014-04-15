package koodikattila.viitehallinta.hallinta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;
import koodikattila.viitehallinta.tieto.BibTeXTiedonsaanti;
import koodikattila.viitehallinta.tieto.Filtteri;
import koodikattila.viitehallinta.tieto.JsonTiedonsaanti;
import koodikattila.viitehallinta.tieto.Tiedonsaanti;

/**
 *
 * @author Koodikattila
 */
public class Kontrolleri {

    private final List<Viite> viitteet;
    private List<Viite> viimeksiHaetut;
    private Tiedonsaanti jsonTiedonsaanti;
    private Tiedonsaanti bibtexTiedonsaanti;
    private File tiedosto;

    public Kontrolleri(Tiedonsaanti json, Tiedonsaanti bibtex, File tiedosto) {
        this.viitteet = new ArrayList<>();
        this.jsonTiedonsaanti = json;
        this.bibtexTiedonsaanti = bibtex;
        this.tiedosto = tiedosto;
        if (json != null) {
            populoiLista();
        }
    }

    public Kontrolleri() {
        this(new JsonTiedonsaanti(), new BibTeXTiedonsaanti(), new File("viitehallinta.json"));
    }

    private void populoiLista() {
        try {
            jsonTiedonsaanti.lataa(tiedosto);
        } catch (IOException ex) {
            Logger.getLogger(Kontrolleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        Collection<Viite> tiedot = jsonTiedonsaanti.haeTiedot(Filtteri.KAIKKI, Viite.class);
        if (tiedot == null) {
            return;
        }
        for (Viite viite : tiedot) {
            viitteet.add(viite);
        }
    }

    /**
     * Tarkistaa annetusta viiteavaimesta onko jo jollain aiemmalla viitteellä
     * sama avain
     *
     * @param viiteavain
     * @return true jos avain on uniikki, muuten false
     */
    public boolean onkoViiteavainUniikki(String viiteavain) {
        for (Viite v : viitteet) {
            if (v.getAvain().equalsIgnoreCase(viiteavain)) {
                return false;
            }
        }
        return true;
    }


    public void lisaaViite(Viite lisattava) {
        this.viitteet.add(lisattava);
    }

    public List<Viite> getViitteet() {
        return this.viitteet;
    }

    public List<Viite> hae(ViiteTyyppi tyyppi, String hakusana) {
        List<Viite> palautus = new ArrayList<>();
        for (Viite v : this.viitteet) {
            if (v.getTyyppi().equals(tyyppi)) {
                if (hakusana.isEmpty()) {
                    palautus.add(v);
                } else {
                    for (Attribuutti attr : v.asetetutAttribuutit()) {
                        if (v.haeArvo(attr).contains(hakusana)) {
                            palautus.add(v);
                            break;
                        }
                    }
                }
            }
        }
        this.viimeksiHaetut = palautus;
        return palautus;
    }

    /*
     * Poistaa viitteen järjestelmästä parametrina annetun indeksin perusteella viimeksi haettujen listasta
     */
    public void poista(int indeksi) {
        if (this.viimeksiHaetut == null) {
            return;
        }
        this.viitteet.remove(this.viimeksiHaetut.get(indeksi));
        this.viimeksiHaetut.remove(this.viimeksiHaetut.get(indeksi));
    }

    public boolean onkoValidi(int indeksi) {
        if (this.viimeksiHaetut == null) {
            return true;
        }
        return this.viimeksiHaetut.get(indeksi).onkoValidi();
    }

    public void talletaBibtexTiedostoon(File tiedosto) {
        try {
            bibtexTiedonsaanti.tyhjenna();
            bibtexTiedonsaanti.lisaaTieto(viitteet.toArray());
            bibtexTiedonsaanti.tallenna(tiedosto);
        } catch (IOException ex) {
            System.err.println("Tiedostovika");
        }
    }

    public void tallenna() {
        jsonTiedonsaanti.tyhjenna();
        jsonTiedonsaanti.lisaaTieto(viitteet.toArray());
        try {
            jsonTiedonsaanti.tallenna(tiedosto);
        } catch (IOException ex) {
            Logger.getLogger(Kontrolleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
