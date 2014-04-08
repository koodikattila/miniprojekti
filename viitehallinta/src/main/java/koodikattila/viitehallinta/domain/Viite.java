package koodikattila.viitehallinta.domain;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author kumikumi
 */
public class Viite {

    private List<String> tagit;
    private Set<Attribuutti> asetetutAttribuutit;
    private ViiteTyyppi tyyppi;
    private Map<Attribuutti, String> attribuutit;
    private String avain;

    public Viite(ViiteTyyppi tyyppi) {
        this.tyyppi = tyyppi;
        this.attribuutit = new EnumMap<>(Attribuutti.class);
        this.asetetutAttribuutit = new HashSet<Attribuutti>();
    }

    /**
     * Hakee tämän viitteen avaimen.
     * @return avain
     */
    public String getAvain() {
        return avain;
    }

    /**
     * Asettaa tämän viitteen avaimen.
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
            this.asetetutAttribuutit.remove(attr);
        } else {
            this.attribuutit.put(attr, arvo);
            this.asetetutAttribuutit.add(attr);
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
        List<Attribuutti> pakollisetAttribuutit = tyyppi.haePakolliset();
        if (attribuutit.keySet().isEmpty()) {
            if (!pakollisetAttribuutit.isEmpty()) {
                return false;
            }
        }
        for (Attribuutti attribuutti : attribuutit.keySet()) {
            if (!attribuutit.containsKey(attribuutti)) {
                return false;
            }
        }
        return true;
    }
    
    public Set<Attribuutti> asetetutAttribuutit() {
        return this.asetetutAttribuutit;
    }
    
    public ViiteTyyppi getTyyppi() {
        return this.tyyppi;
    }

}
