/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koodikattila.viitehallinta.hallinta;

import java.util.List;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mikko
 */
public class KontrolleriTest {

    private Kontrolleri kontrolleri;

    @Before
    public void setUp() {
        this.kontrolleri = new Kontrolleri();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void jarjestelmassaOnYksiViiteKunYksiViiteOnLisatty() {
        System.out.println("lisaaViite");
        Viite lisattava = new Viite(ViiteTyyppi.book);
        kontrolleri.lisaaViite(lisattava);

        assertEquals(1, kontrolleri.getViitteet().size());
    }

    @Test
    public void jarjestelmastaLoytyySeViiteJokaOnLisatty() {
        System.out.println("lisaaViite");
        Viite lisattava = new Viite(ViiteTyyppi.book);
        lisattava.asetaArvo(Attribuutti.author, "kumikumi");
        kontrolleri.lisaaViite(lisattava);

        assertEquals("kumikumi", kontrolleri.getViitteet().get(0).haeArvo(Attribuutti.author));
    }

    @Test
    public void kirjojenHakeminenTyhjallaHakusanallaPalauttaaKaksiKirjaa() {
        System.out.println("hae");

        Viite lisattava = new Viite(ViiteTyyppi.book);
        lisattava.asetaArvo(Attribuutti.author, "author1");
        kontrolleri.lisaaViite(lisattava);

        Viite lisattava2 = new Viite(ViiteTyyppi.book);
        lisattava.asetaArvo(Attribuutti.author, "author2");
        kontrolleri.lisaaViite(lisattava2);

        Viite lisattava3 = new Viite(ViiteTyyppi.article);
        lisattava.asetaArvo(Attribuutti.author, "author3");
        kontrolleri.lisaaViite(lisattava3);

        String hakusana = "";

        assertEquals(2, kontrolleri.hae(ViiteTyyppi.book, hakusana).size());
    }

    /**
     * Test of poista method, of class Kontrolleri.
     */
    @Test
    public void poistamisenJalkeenKirjojenHakeminenTyhjallaHakusanallaPalauttaaJaljelleJaaneenKirjan() {
        System.out.println("hae");
        
        Viite lisattava = new Viite(ViiteTyyppi.book);
        lisattava.asetaArvo(Attribuutti.author, "author1");
        kontrolleri.lisaaViite(lisattava);

        Viite lisattava2 = new Viite(ViiteTyyppi.book);
        lisattava2.asetaArvo(Attribuutti.author, "author2");
        kontrolleri.lisaaViite(lisattava2);

        Viite lisattava3 = new Viite(ViiteTyyppi.article);
        lisattava3.asetaArvo(Attribuutti.author, "author3");
        kontrolleri.lisaaViite(lisattava3);

        String hakusana = "";
        kontrolleri.hae(ViiteTyyppi.book, hakusana); //palauttaa listan jossa on molemmat kirjat
        kontrolleri.poista(0); //poistaa kirjoista ensimmäisen
        
        //testi tarkastaa että jäi vain yksi kirja ja että se on kirjoista jälkimmäinen
        assertEquals(1, kontrolleri.hae(ViiteTyyppi.book, hakusana).size());
        assertEquals("author2", kontrolleri.hae(ViiteTyyppi.book, hakusana).get(0).haeArvo(Attribuutti.author));

    }

}
