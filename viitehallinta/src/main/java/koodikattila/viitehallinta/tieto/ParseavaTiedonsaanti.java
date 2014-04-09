package koodikattila.viitehallinta.tieto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 *
 * @author Koodikattila
 */
public abstract class ParseavaTiedonsaanti<T> implements Tiedonsaanti<T> {

    private final Collection<T> tiedot;
    private final String kirjainjarjestelma = "UTF-8";

    public ParseavaTiedonsaanti() {
        this.tiedot = new ArrayList<>();
    }

    @Override
    public Collection<T> haeTiedot(Filtteri<T> filtteri, Class<T> clazz) {
        Collection<T> oliot = new ArrayList<>();
        for (T olio : tiedot) {
            if (olio != null && clazz.isAssignableFrom(olio.getClass()) && filtteri.testaa(olio)) {
                oliot.add(olio);
            }
        }
        return oliot;
    }

    @Override
    public void lisaaTieto(T... lisattavat) {
        tiedot.addAll(Arrays.asList(lisattavat));
    }

    @Override
    public void tallenna(File tiedosto) throws IOException {
        varmistaTiedosto(tiedosto);
        try (Writer kirjoittaja = new FileWriter(tiedosto)) {
            for (T tieto : tiedot) {
                kirjoitaTieto(kirjoittaja, tieto);
                //                kirjoittaja.write(luoStringi(tieto));
            }
        }
    }

    @Override
    public void lataa(File tiedosto) throws IOException {
        varmistaTiedosto(tiedosto);
        tiedot.clear();
        try (Scanner lukija = new Scanner(tiedosto, kirjainjarjestelma)) {
//            while (lukija.hasNextLine()) {
            lueTiedot(lukija, tiedot);
//            }
        }
    }

    private void varmistaTiedosto(File tiedosto) throws IOException {
        File vanhempi = tiedosto.getParentFile();
        if (vanhempi != null) {
            vanhempi.mkdirs();
        }
        tiedosto.createNewFile();
    }

    public abstract void lueTiedot(Scanner lukija, Collection<T> tiedot);

    public abstract void kirjoitaTieto(Writer kirjoittaja, T tieto) throws IOException;
}
