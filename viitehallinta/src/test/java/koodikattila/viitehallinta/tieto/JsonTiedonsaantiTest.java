package koodikattila.viitehallinta.tieto;

import java.io.File;
import java.util.Collection;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Koodikattila
 */
public class JsonTiedonsaantiTest {

    private Viite A = new Viite(ViiteTyyppi.article);
    private Viite B = new Viite(ViiteTyyppi.book);
    private Viite AA = new Viite(ViiteTyyppi.article);

    private Tiedonsaanti<Viite> tiedonsaanti;
    private File testiTiedosto;
    private Filtteri<Viite> kaikki = new Filtteri<Viite>() {
        @Override
        public boolean testaa(Viite testattava) {
            return true;
        }
    };
    private final Filtteri<Viite> sisaltaaA = new Filtteri<Viite>() {
        @Override
        public boolean testaa(Viite testattava) {
            return testattava.getTyyppi() == ViiteTyyppi.article;
        }
    };

    @Before
    public void setUp() throws Exception {
        testiTiedosto = new File("testiTiedosto");
        AA.asetaArvo(Attribuutti.author, "XD");
        tiedonsaanti = new JsonTiedonsaanti(testiTiedosto);
        tiedonsaanti.lisaaTieto(A, B, AA);
    }

    @After
    public void tearDown() {
        testiTiedosto.delete();
    }

    @Test
    public void testHaeTiedot() {
        Collection<Viite> stringit = tiedonsaanti.haeTiedot(sisaltaaA, Viite.class);
        assertTrue(stringit.contains(A));
        assertTrue(stringit.contains(AA));
    }

    @Test
    public void testTallenna() throws Exception {
        Collection<Viite> viitteet1 = tiedonsaanti.haeTiedot(kaikki, Viite.class);
        tiedonsaanti.tallenna();
        tiedonsaanti.lataa();
        Collection<Viite> viitteet2 = tiedonsaanti.haeTiedot(kaikki, Viite.class);
        for (Viite viite : viitteet1) {
            boolean flag = false;
            for (Viite viite2 : viitteet2) {
                if (viite.getTyyppi() == viite2.getTyyppi() && viite.asetetutAttribuutit().equals(viite.asetetutAttribuutit())) {
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                fail();
            }
        }
    }

}
