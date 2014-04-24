package koodikattila.viitehallinta.tieto;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author aopkarja
 */
public class BibTeXTiedonsaantiTest {

    private Viite A = new Viite(ViiteTyyppi.article);
    private Viitekokoelma kokoelma;
    private File testiTiedosto;
    private IO tiedonsaanti;

    @Before
    public void setUp() {
        testiTiedosto = new File("testiTiedosto");
        tiedonsaanti = new IO(new BibTeX());
        kokoelma = new Viitekokoelma().lisaa(A);
        A.asetaArvo(Attribuutti.author, "XD");
    }

    @After
    public void tearDown() {
//        testiTiedosto.delete();
    }

    @Test
    @Ignore
    public void attribuutillisenViitteenTallennus() throws IOException {
        tiedonsaanti.tallenna(testiTiedosto, kokoelma);
        String korrekti = "@article{,\n author = }";
        try (Scanner lukija = new Scanner(testiTiedosto)) {
            String luettu = "";
            while (lukija.hasNextLine()) {
                lukija.nextLine();
            }
        }
//        tiedonsaanti.lataa();
//        Collection<Viite> lista = tiedonsaanti.haeTiedot(Filtteri.KAIKKI, Viite.class);
//        assertTrue(lista.contains(A));
    }
}
