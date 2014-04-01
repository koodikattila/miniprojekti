package koodikattila.viitehallinta.tieto;

import java.io.File;
import java.util.Collection;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Koodikattila
 */
public class JsonTiedonsaantiTest {

    private Tiedonsaanti tiedonsaanti;
    private File testiTiedosto;
    private Filtteri kaikki = new Filtteri<String>() {
        @Override
        public boolean testaa(String testattava) {
            return true;
        }
    };
    private final Filtteri sisaltaaA = new Filtteri<String>() {
        @Override
        public boolean testaa(String testattava) {
            return testattava.contains("A");
        }
    };

    @Before
    public void setUp() throws Exception {
        testiTiedosto = new File("testiTiedosto");
        tiedonsaanti = new JsonTiedonsaanti(testiTiedosto);
        tiedonsaanti.lisaaTieto("A", "B", "AA");
    }

    @After
    public void tearDown() {
        testiTiedosto.delete();
    }

    @Test
    public void testHaeTiedot() {
        Collection<String> stringit = tiedonsaanti.haeTiedot(sisaltaaA, String.class);
        assertTrue(stringit.contains("A"));
        assertTrue(stringit.contains("AA"));
    }

    @Test
    public void testTallenna() throws Exception {
        Collection<String> stringit1 = tiedonsaanti.haeTiedot(kaikki, String.class);
        tiedonsaanti.tallenna();
        tiedonsaanti.lataa();
        Collection<String> stringit2 = tiedonsaanti.haeTiedot(kaikki, String.class);
        assertEquals(stringit1, stringit2);
    }

}
