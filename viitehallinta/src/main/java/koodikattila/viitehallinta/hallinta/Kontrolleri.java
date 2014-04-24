package koodikattila.viitehallinta.hallinta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private Set<String> kaikkiTagit;
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
            populoiTagit();
        }
    }

    public Kontrolleri(File file) {
        this(new IO(new Json()), new IO(new BibTeX()), file);
    }

    private void populoiLista() {
        try {
            viitteet.lisaa(json.lataa(tiedosto));
        } catch (IOException ex) {
            Logger.getLogger(Kontrolleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populoiTagit() {
        if (this.kaikkiTagit == null) {
            this.kaikkiTagit = new HashSet<>();
        }
        for (Viite v : viitteet) {
            if (v.haeTagit() == null) {
                System.out.println("NULL");
            } else {
                kaikkiTagit.addAll(v.haeTagit());
            }
        }
    }
    
    public void poistaTagiKaikista(String tagi) {
        for (Viite v : viitteet) {
            v.poistaTagi(tagi);
        }
        kaikkiTagit.remove(tagi);
    }

    public Set<String> getTagit() {
        return this.kaikkiTagit;
    }

    private List<String> nimet(String kirjoittajat) {
        return Arrays.asList(kirjoittajat.split(" and "));
    }

    private String etuosa(List<String> nimet) {
        if (nimet.size() > 1) {
            String etuosa = "";
            for (String nimi : nimet) {
                etuosa = etuosa.concat(nimi.substring(0, 1));
            }
            return etuosa;
        } else {
            String[] jaettuNimi = nimet.get(0).split(",");
            if (jaettuNimi[0].length() == 1) {
                return jaettuNimi[0];
            }
            return jaettuNimi[0].substring(0, 2);
        }
    }

    public String generoiViiteavain(Viite viite) {
        String kirjoittaja;
        String vuosiluku;
        if (viite.asetetutAttribuutit().contains(Attribuutti.author)) {
            kirjoittaja = viite.haeArvo(Attribuutti.author);
        } else {
            kirjoittaja = "aaa and bbb and ccc";
        }
        if (viite.asetetutAttribuutit().contains(Attribuutti.year)) {
            vuosiluku = viite.haeArvo(Attribuutti.year);
        } else {
            vuosiluku = "xxxx";
        }
        String leikattuvuosiluku;
        if (vuosiluku.length() == 1) {
            leikattuvuosiluku = vuosiluku;
        } else {
            leikattuvuosiluku = vuosiluku.substring(vuosiluku.length() - 2);
        }
        String avain = etuosa(nimet(kirjoittaja)) + leikattuvuosiluku;
        return tarkistaViiteavain(viite, avain);

    }

    public String tarkistaViiteavain(Viite viite, String avain) {
        int numerointi = 1;
        ulko:
        while (true) {
            for (Viite v : viitteet) {
                String talsi = avain;
                if (numerointi != 1) {
                    talsi = talsi + numerointi;
                }
                if (v.getAvain().equalsIgnoreCase(talsi) && !v.equals(viite)) {
                    numerointi++;
                    continue ulko;
                }
            }
            break;
        }
        if (numerointi == 1) {
            return avain;
        } else {
            return avain + numerointi;
        }
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
                    if (testattava.haeArvo(attr).toLowerCase().contains(hakusana.toLowerCase())) {
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
            Logger.getLogger(Kontrolleri.class.getName()).log(Level.SEVERE, null, ex);
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
