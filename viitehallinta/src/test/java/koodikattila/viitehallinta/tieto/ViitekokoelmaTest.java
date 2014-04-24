package koodikattila.viitehallinta.tieto;

import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Koodikattila
 */
public class ViitekokoelmaTest {

    private final Viite A = new Viite(ViiteTyyppi.article);
    private final Viite B = new Viite(ViiteTyyppi.book);

    public ViitekokoelmaTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void filtteriPalauttaaVainHyvaksytyt() {
        Viitekokoelma kokoelma = new Viitekokoelma().lisaa(A, B);
//        kokoelma.lisaa(A, B);
        Viitekokoelma temp = kokoelma.rajaa(new Filtteri<Viite>() {
            @Override
            public boolean testaa(Viite testattava) {
                return testattava.getTyyppi() == ViiteTyyppi.article;
            }
        });
        assertEquals(1, temp.keraa().size());
        assertEquals(A, temp.keraa().toArray()[0]);
    }
}
