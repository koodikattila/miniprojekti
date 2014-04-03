/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package koodikattila.viitehallinta.domain;

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
        viite.asetaArvo(Attribuutti.publisher,"df");
        viite.asetaArvo(Attribuutti.year, "kjk");
        assertTrue(viite.onkoValidi());
    }
    
}
