package koodikattila.viitehallinta.tieto;

/**
 *
 * @author Koodikattila
 */
public interface Filtteri<T> {

    boolean testaa(T testattava);
}
