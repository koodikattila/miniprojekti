package koodikattila.viitehallinta.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Koodikattila
 */
public class Viite {

    private final Set<String> tagit;
    private final ViiteTyyppi tyyppi;
    private final Map<Attribuutti, String> attribuutit;
    private String avain;

    public Viite(ViiteTyyppi tyyppi) {
        avain = "";
        this.tyyppi = tyyppi;
        this.attribuutit = new EnumMap<>(Attribuutti.class);
        tagit = new HashSet<>();
    }

    /**
     * Hakee viitteen avaimen.
     *
     * @return avain
     */
    public String getAvain() {
        return avain;
    }

    /**
     * Asettaa viitteen avaimen.
     *
     * @param avain
     */
    public void setAvain(String avain) {
        this.avain = avain;
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
            return nimet.get(0).substring(0, 2);
        }
    }

    public void generoiViiteavain() {
        String avain = "";
        String kirjoittaja;
        String vuosiluku;
        if (attribuutit.containsKey(Attribuutti.author)) {
            kirjoittaja = attribuutit.get(Attribuutti.author);
        } else {
            kirjoittaja = "aaa and bbb and ccc";
        }
        if (attribuutit.containsKey(Attribuutti.year)) {
            vuosiluku = attribuutit.get(Attribuutti.year);
        } else {
            vuosiluku = "xxxx";
        }
        avain = avain.concat(etuosa(nimet(kirjoittaja))) + vuosiluku.substring(vuosiluku.length() - 2);

        this.setAvain(avain);
    }
    /*
     * Asettaa viiteoliolle parametrina annetun attribuutin arvoksi
     * parametrina annetun arvon.
     */
    public void asetaArvo(Attribuutti attr, String arvo) {
        if (arvo.isEmpty()) {
            this.attribuutit.remove(attr);
        } else {
            this.attribuutit.put(attr, arvo);
        }
    }

    /**
     * Hakee attribuuttimapista tietyn arvon
     *
     * @param attr
     * @return
     */
    public String haeArvo(Attribuutti attr) {
        if (this.attribuutit.containsKey(attr)) {
            return this.attribuutit.get(attr);
        }
        return "";
    }

    /*
     * onkoValidi() -metodi palauttaa true, jos tällä viiteoliolla on asetettu kaikki viitteen
     * tyypille spesifioidut pakolliset kentät, ja muussa tapauksessa false
     *
     */
    public boolean onkoValidi() {
        if (this.avain == null || this.avain.isEmpty()) {
            return false;
        }
        for (Attribuutti attribuutti : tyyppi.haePakolliset()) {
            if (!attribuutit.containsKey(attribuutti)) {
                return false;
            }
        }
        return true;
    }

    public Set<Attribuutti> asetetutAttribuutit() {
        return this.attribuutit.keySet();
    }

    public ViiteTyyppi getTyyppi() {
        return this.tyyppi;
    }

    public void lisaaTagi(String tagi) {
        tagit.add(tagi);
    }
    
    public Set<String> haeTagit() {
        return tagit;
    }
    
    public boolean tagitSisaltaa(String str) {
        return tagit.contains(str);
    }

    public void poistaTagi(String tagi) {
        tagit.remove(tagi);
    }

    // TODO: tagien vertailu
    @Override
    public boolean equals(Object obj) {
        return obj == this;
        /*
         if(obj == null) return false;
         if(!(obj instanceof Viite)) return false;
         Viite v = (Viite) obj;
         if(!avain.equals(v.getAvain())) return false;
         if(!attribuutit.equals(v.asetetutAttribuutit())) return false;
         return true;*/
    }
}
