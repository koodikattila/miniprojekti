package koodikattila.viitehallinta;

import java.util.HashMap;
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
        this.attribuutit = new HashMap<Attribuutti, String>();
    }
    
    public void asetaArvo(Attribuutti attr, String arvo) {
        this.attribuutit.put(attr, arvo);
    }
    
    /*
     * onkoValidi() -metodi palauttaa true, jos tällä viiteoliolla on asetettu kaikki viitteen
     * tyypille spesifioidut pakolliset kentät, ja muussa tapauksessa false
     * 
     */
    public boolean onkoValidi() {
        return false;
    }
    
}
