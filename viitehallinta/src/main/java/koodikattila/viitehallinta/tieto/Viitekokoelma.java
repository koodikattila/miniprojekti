package koodikattila.viitehallinta.tieto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import koodikattila.viitehallinta.domain.Viite;

/**
 *
 * @author Koodikattila
 */
public class Viitekokoelma implements Iterable<Viite> {

    private List<Viite> viitteet;

    public Viitekokoelma() {
        this.viitteet = new ArrayList();
    }

    public Viitekokoelma(Viitekokoelma viitekokoelma) {
        this();
        this.viitteet.addAll(viitekokoelma.viitteet);
    }

    public Viitekokoelma lisaa(Collection<Viite> viitteet) {
        this.viitteet.addAll(viitteet);
        return this;
    }

    public Viitekokoelma lisaa(Viitekokoelma kokoelma) {
        return lisaa(kokoelma.viitteet);
    }

    public Viitekokoelma lisaa(Viite... viite) {
        return lisaa(Arrays.asList(viite));
    }

    public Viitekokoelma rajaa(Filtteri<Viite> filtteri) {
        List<Viite> lista = new ArrayList<>();
        for (Viite olio : viitteet) {
            if (filtteri.testaa(olio)) {
                lista.add(olio);
            }
        }
        viitteet = lista;
        return this;
    }

    public List<Viite> keraa() {
        return Collections.unmodifiableList(viitteet);
    }

    @Override
    public Iterator<Viite> iterator() {
        return viitteet.iterator();
    }
}
