package koodikattila.viitehallinta.tieto;

import java.util.Collection;
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

    private Viitekokoelma kokoelma;
    private final Viite A = new Viite(ViiteTyyppi.article);
    private final Viite B = new Viite(ViiteTyyppi.book);

    public ViitekokoelmaTest() {
    }

    @Before
    public void setUp() {
        kokoelma = new Viitekokoelma();
    }

    @Test
    public void filtteriPalauttaaVainHyvaksytyt() {
        kokoelma.lisaa(A, B);
        Collection<Viite> temp = kokoelma.hae(new Filtteri<Viite>() {
            @Override
            public boolean testaa(Viite testattava) {
                return testattava.getTyyppi() == ViiteTyyppi.article;
            }
        });
        assertEquals(1, temp.size());
        assertEquals(A, temp.toArray()[0]);
    }

    @Test
    public void filtteriPoistaaVainHyvaksytyt() {
        kokoelma.lisaa(A, B);
        kokoelma.poista(new Filtteri<Viite>() {
            @Override
            public boolean testaa(Viite testattava) {
                return testattava.getTyyppi() == ViiteTyyppi.article;
            }
        });
        Collection<Viite> temp = kokoelma.hae(Filtteri.KAIKKI);
        assertEquals(1, temp.size());
        assertEquals(B, temp.toArray()[0]);
    }

}
