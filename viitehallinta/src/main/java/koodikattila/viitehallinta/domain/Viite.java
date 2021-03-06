package koodikattila.viitehallinta.domain;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Koodikattila
 */
public class Viite {

    private Set<String> tagit;
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
        if (this.tagit == null) {
            this.tagit = new HashSet<>();
        }
        tagit.add(tagi);
    }

    public Set<String> haeTagit() {
        if (this.tagit == null) {
            this.tagit = new HashSet<>();
        }
        return this.tagit;

    }

    public boolean tagitSisaltaa(String str) {
        return tagit.contains(str);
    }

    public void poistaTagi(String tagi) {
        if (tagit != null) {
            tagit.remove(tagi);
        }
    }
}
