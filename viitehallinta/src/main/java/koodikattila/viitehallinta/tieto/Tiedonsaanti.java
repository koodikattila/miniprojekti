package koodikattila.viitehallinta.tieto;

import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author Koodikattila
 */
public interface Tiedonsaanti {

    <T> Collection<T> haeTiedot(Filtteri<T> filtteri, Class<T> clazz);

    <T> void lisaaTieto(T lisattava);

    public void tallenna() throws IOException;

    public void lataa() throws IOException;
}
