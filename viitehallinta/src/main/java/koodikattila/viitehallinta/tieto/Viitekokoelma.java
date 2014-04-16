package koodikattila.viitehallinta.tieto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import koodikattila.viitehallinta.domain.Viite;

/**
 *
 * @author Koodikattila
 */
public class Viitekokoelma {

    private final List<Viite> viitteet;

    public Viitekokoelma() {
        viitteet = new ArrayList<>();
    }

    public void lisaa(Viite... viitteet) {
        this.viitteet.addAll(Arrays.asList(viitteet));
    }

    public Collection<Viite> hae(Filtteri<Viite> filtteri) {
        Collection<Viite> oliot = new ArrayList<>();
        for (Viite olio : viitteet) {
            if (filtteri.testaa(olio)) {
                oliot.add(olio);
            }
        }
        return oliot;
    }

    public void poista(Filtteri<Viite> filtteri) {
        for (Iterator<Viite> it = viitteet.iterator(); it.hasNext();) {
            if (filtteri.testaa(it.next())) {
                it.remove();
            }
        }
    }
}
