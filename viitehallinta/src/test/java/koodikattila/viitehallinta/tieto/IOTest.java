package koodikattila.viitehallinta.tieto;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Koodikattila
 */
public class IOTest {

    private Parseri parseri;
    private IO io;
    private File tiedosto;

    @Before
    public void setUp() {
        tiedosto = new File("testfile");
        parseri = mock(Parseri.class);
        io = new IO(parseri);
    }

    @After
    public void tearDown() {
        tiedosto.delete();
    }

    @Test
    public void tallentaminenToimii() {
        when(parseri.tekstiksi(null)).thenReturn("stringi");
        try {
            io.tallenna(tiedosto, null);
        } catch (IOException ex) {
            fail("Tuli IOException vaikka ei pitäisi:" + ex);
        }
        verify(parseri).tekstiksi(isNull(Viitekokoelma.class));
        try {
            assertEquals("stringi", new String(Files.readAllBytes(tiedosto.toPath()), StandardCharsets.UTF_8));
        } catch (IOException ex) {
            fail("Tuli IOException vaikka ei pitäisi:" + ex);
        }
    }

    @Test
    public void lataaminenToimii() {
        Viite viite = new Viite(ViiteTyyppi.article);
        when(parseri.viitekokoelmaksi(any(String.class))).thenReturn(new Viitekokoelma().lisaa(viite));
        try {
            Files.write(tiedosto.toPath(), new BibTeX().tekstiksi(new Viitekokoelma().lisaa(viite)).getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            fail("Tuli IOException vaikka ei pitäisi:" + ex);
        }
        try {
            assertEquals(viite, io.lataa(tiedosto).keraa().get(0));
        } catch (IOException ex) {
            fail("Tuli IOException vaikka ei pitäisi:" + ex);
        }
        verify(parseri).viitekokoelmaksi(any(String.class));
    }

}
