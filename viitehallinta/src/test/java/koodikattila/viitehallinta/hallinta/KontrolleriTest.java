/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koodikattila.viitehallinta.hallinta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;
import koodikattila.viitehallinta.tieto.Filtteri;
import koodikattila.viitehallinta.tieto.Tiedonsaanti;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author mikko
 */
public class KontrolleriTest {

    private Kontrolleri kontrolleri;
    private ArrayList<Viite> lista;
    private final Tiedonsaanti tiedonsaanti = new Tiedonsaanti<Viite>() {

        @Override
        public Collection<Viite> haeTiedot(Filtteri<Viite> filtteri, Class<Viite> clazz) {
            ArrayList<Viite> lista = new ArrayList<>();
//            lista.add(null);
            return lista;
        }

        @Override
        public void lisaaTieto(Viite... lisattavat) {
        }

        @Override
        public void tallenna(File tiedosto) throws IOException {
        }

        @Override
        public void lataa(File tiedosto) throws IOException {
        }

    };

    @Before
    public void setUp() {
        this.kontrolleri = new Kontrolleri(tiedonsaanti);
        lista = new ArrayList<>();
        lista.add(new Viite(ViiteTyyppi.article));
        lista.add(new Viite(ViiteTyyppi.book));
        lista.add(new Viite(ViiteTyyppi.conference));
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

    @Test
    @Ignore
    public void testaaPopuloiLista() {
        assertEquals(lista, kontrolleri.getViitteet());
    }
}
