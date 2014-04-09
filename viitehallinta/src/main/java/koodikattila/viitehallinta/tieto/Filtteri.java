package koodikattila.viitehallinta.tieto;

import koodikattila.viitehallinta.domain.Viite;

/**
 *
 * @author Koodikattila
 * @param <T>
 */
public interface Filtteri<T> {

    public static final Filtteri<Viite> KAIKKI = new Filtteri<Viite>() {
        @Override
        public boolean testaa(Viite testattava) {
            return true;
        }
    };

    boolean testaa(T testattava);
}
