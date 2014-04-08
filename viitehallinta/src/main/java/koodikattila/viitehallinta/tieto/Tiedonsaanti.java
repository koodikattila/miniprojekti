package koodikattila.viitehallinta.tieto;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author Koodikattila
 * @param <T>
 */
public interface Tiedonsaanti<T> extends Closeable {

    /**
     * Käy läpi ladatut tiedot ja käyttää niihin filtteriä ja palauttaa
     * hyväksytyt
     *
     * @param filtteri
     * @param clazz
     * @return
     */
    Collection<T> haeTiedot(Filtteri<T> filtteri, Class<T> clazz);

    /**
     * Lisää tiedot
     *
     * @param lisattavat
     */
    void lisaaTieto(T... lisattavat);

    /**
     * Tallentaa tiedot tiedostoon
     *
     * @throws IOException
     */
    public void tallenna() throws IOException;

    /**
     * Lataa tiedot tiedostosta
     *
     * @throws IOException
     */
    public void lataa() throws IOException;

    public static class ParseException extends RuntimeException {

        public ParseException(String string) {
            super(string);
        }
    }
}
