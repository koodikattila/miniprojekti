package koodikattila.viitehallinta.tieto;

/**
 *
 * @author Koodikattila
 * @param <T>
 */
public interface Filtteri<T> {

    boolean testaa(T testattava);
}
