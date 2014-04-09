package koodikattila.viitehallinta.tieto;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author Koodikattila
 * @param <T>
 */
public interface Tiedonsaanti<T> {

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
     * @param tiedosto
     * @throws IOException
     */
    public void tallenna(File tiedosto) throws IOException;

    /**
     * Lataa tiedot tiedostosta
     *
     * @param tiedosto
     * @throws IOException
     */
    public void lataa(File tiedosto) throws IOException;

    public static class ParseException extends RuntimeException {

        public ParseException(String string) {
            super(string);
        }
    }
}
