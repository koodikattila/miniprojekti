package koodikattila.viitehallinta.domain;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kumikumi
 */
public class Viite {
    private List<String> tagit;
    private ViiteTyyppi tyyppi;
    private Map<Attribuutti, String> attribuutit;
    
    public Viite(ViiteTyyppi tyyppi) {
        this.tyyppi = tyyppi;
        this.attribuutit = new EnumMap<>(Attribuutti.class);
    }
    
    /*
     * Asettaa viiteoliolle parametrina annetun attribuutin arvoksi
     * parametrina annetun arvon.
     */
    public void asetaArvo(Attribuutti attr, String arvo) {
        this.attribuutit.put(attr, arvo);
    }
    
    /**
     * Hakee attribuuttimapista tietyn arvon
     * @param attr
     * @return 
     */
    public String haeArvo(Attribuutti attr) {
        return this.attribuutit.get(attr);
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
        for(Attribuutti attribuutti : attribuutit.keySet()) {
            if (!attribuutit.containsKey(attribuutti)) {
                return false;
            }
        }
        return true;
    }
    
}
