package koodikattila.viitehallinta.tieto;

import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author Koodikattila
 * @param <T>
 */
public interface Tiedonsaanti<T> {

    Collection<T> haeTiedot(Filtteri<T> filtteri, Class<T> clazz);

    void lisaaTieto(T... lisattavat);

    public void tallenna() throws IOException;

    public void lataa() throws IOException;

    public static class ParseException extends RuntimeException {

        public ParseException(String string) {
            super(string);
        }
    }
}
