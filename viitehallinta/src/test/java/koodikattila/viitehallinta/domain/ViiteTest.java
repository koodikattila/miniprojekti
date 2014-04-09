/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koodikattila.viitehallinta.domain;

import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author abmo
 */
public class ViiteTest {

    private Viite viite;

    public ViiteTest() {
    }

    @Before
    public void setUp() {
        viite = new Viite(ViiteTyyppi.book);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of asetaArvo method, of class Viite.
     */
    @Test
    public void testAsetaArvoJaHaeArvo() {
        viite.asetaArvo(Attribuutti.author, "Henna Ruohonen");
        assertEquals("Henna Ruohonen", viite.haeArvo(Attribuutti.author));
    }

    /**
     * Test of onkoValidi method, of class Viite.
     */
    @Test
    public void testOnkoTyhjaValidi() {
        assertFalse(viite.onkoValidi());
    }

    @Test
    public void testOnkoKirjaOikeillaArvoillaValidi() {
        //author, title, publisher, year
        viite.asetaArvo(Attribuutti.author, "fsjd");
        viite.asetaArvo(Attribuutti.title, "kjkf");
        viite.asetaArvo(Attribuutti.publisher, "df");
        viite.asetaArvo(Attribuutti.year, "kjk");
        assertTrue(viite.onkoValidi());
    }

    @Test
    public void kysyttaessaAsettamatontaArvoaPalautetaanTyhjaMerkkijono() {
        assertEquals("", viite.haeArvo(Attribuutti.title));
    }

    @Test
    public void asetetutAttribuutitPalautetaanOikein() {
        viite.asetaArvo(Attribuutti.author, "fsjd");
        viite.asetaArvo(Attribuutti.title, "kjkf");
        viite.asetaArvo(Attribuutti.publisher, "df");
        viite.asetaArvo(Attribuutti.year, "kjk");
        Set<Attribuutti> result = new HashSet<>();
        result.add(Attribuutti.author);
        result.add(Attribuutti.title);
        result.add(Attribuutti.publisher);
        result.add(Attribuutti.year);
        assertEquals(result, viite.asetetutAttribuutit());
    }
    @Test
    public void asetaArvoPoistaaJosArvoOnTyhja() {
        viite.asetaArvo(Attribuutti.author, "");
        assertTrue("".equals(viite.haeArvo(Attribuutti.author)));
    }
}
