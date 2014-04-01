package koodikattila.viitehallinta.tieto;

import java.io.File;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Koodikattila
 */
public class JsonTiedonsaantiTest {

    private Tiedonsaanti tiedonsaanti;

    @Before
    protected void setUp() throws Exception {
        tiedonsaanti = new JsonTiedonsaanti(new File("test/testFile"));
    }

    @Test
    public void testHaeTiedot() {
    }

    @Test
    public void testLisaaTieto() {
    }

    @Test
    public void testTallenna() throws Exception {
    }

    @Test
    public void testLataa() throws Exception {
    }

}
