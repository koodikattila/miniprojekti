package koodikattila.viitehallinta.hallinta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;
import koodikattila.viitehallinta.tieto.BibTeX;
import koodikattila.viitehallinta.tieto.Filtteri;
import koodikattila.viitehallinta.tieto.IO;
import koodikattila.viitehallinta.tieto.Json;
import koodikattila.viitehallinta.tieto.Viitekokoelma;

/**
 *
 * @author Koodikattila
 */
public class Kontrolleri {

    private final Viitekokoelma viitteet;
    private List<Viite> viimeksiHaetut;
    private IO json;
    private IO bibtex;
    private File tiedosto;

    public Kontrolleri(IO json, IO bibtex, File tiedosto) {
        this.json = json;
        this.bibtex = bibtex;
        this.tiedosto = tiedosto;
        viimeksiHaetut = new ArrayList<>();
        viitteet = new Viitekokoelma();
        if (json != null) {
            populoiLista();
        }
    }

    public Kontrolleri() {
        this(new IO(new Json()), new IO(new BibTeX()), new File("viitehallinta.json"));
    }

    private void populoiLista() {
        try {
            viitteet.lisaa(json.lataa(tiedosto));
        } catch (IOException ex) {
            Logger.getLogger(Kontrolleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Tarkistaa annetusta viiteavaimesta onko jo jollain aiemmalla viitteellä
     * sama avain
     *
     * @param viiteavain
     * @return true jos avain on uniikki, muuten false
     */
    public boolean onkoViiteavainUniikki(final String viiteavain) {
        for (Viite v : viitteet) {
            if (v.getAvain().equalsIgnoreCase(viiteavain)) {
                return false;
            }
        }
        return true;
    }

    public void lisaaViite(Viite lisattava) {
        this.viitteet.lisaa(lisattava);
    }

    public List<Viite> getViitteet() {
        return viitteet.keraa();
    }

    public List<Viite> hae(final ViiteTyyppi tyyppi, final String hakusana) {
        viimeksiHaetut.clear();
        viimeksiHaetut.addAll(new Viitekokoelma(viitteet).rajaa(new Filtteri<Viite>() {
            @Override
            public boolean testaa(Viite testattava) {
                if (!testattava.getTyyppi().equals(tyyppi)) {
                    return false;
                }
                if (hakusana.isEmpty()) {
                    return true;
                }
                for (Attribuutti attr : testattava.asetetutAttribuutit()) {
                    if (testattava.haeArvo(attr).contains(hakusana)) {
                        return true;
                    }
                }
                return false;
            }

        }).keraa());
        return Collections.unmodifiableList(viimeksiHaetut);
    }

    /**
     * Poistaa viitteen järjestelmästä parametrina annetun indeksin perusteella
     * viimeksi haettujen listasta
     *
     * @param indeksi
     */
    public void poista(int indeksi) {
        if (this.viimeksiHaetut == null) {
            return;
        }
        final Viite viite = this.viimeksiHaetut.get(indeksi);
        this.viitteet.rajaa(new Filtteri<Viite>() {

            @Override
            public boolean testaa(Viite testattava) {
                return !testattava.equals(viite);
            }
        });
        this.viimeksiHaetut.remove(viite);
    }

    public boolean onkoValidi(int indeksi) {
        if (this.viimeksiHaetut == null) {
            return true;
        }
        return this.viimeksiHaetut.get(indeksi).onkoValidi();
    }

    public void talletaBibtexTiedostoon(File tiedosto) {
        try {
            bibtex.tallenna(tiedosto, viitteet);
        } catch (IOException ex) {
        }
    }

    public void tallenna() {
        try {
            json.tallenna(tiedosto, viitteet);
        } catch (IOException ex) {
            Logger.getLogger(Kontrolleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
