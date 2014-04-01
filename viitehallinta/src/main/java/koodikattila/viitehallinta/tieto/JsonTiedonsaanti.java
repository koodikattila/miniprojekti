package koodikattila.viitehallinta.tieto;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 *
 * @author Koodikattila
 */
public class JsonTiedonsaanti implements Tiedonsaanti {

    private final Collection<Object> tiedot;
    private final Gson json;
    private final File tiedosto;
    private final String kirjainjarjestelma = "UTF-8";

    public JsonTiedonsaanti(File tiedosto) {
        this.json = new Gson();
        this.tiedot = new ArrayList<>();
        this.tiedosto = tiedosto;
    }

    @Override
    public <T> Collection<T> haeTiedot(Filtteri<T> filtteri, Class<T> clazz) {
        Collection<Object> oliot = new ArrayList<>();
        for (Object olio : tiedot) {
            if (clazz.isAssignableFrom(olio.getClass()) && filtteri.testaa((T) olio)) {
                oliot.add(olio);
            }
        }
        return (Collection<T>) oliot;
    }

    @Override
    public <T> void lisaaTieto(T... lisattavat) {
        for (T lisattava : lisattavat) {
            tiedot.add(lisattava);
        }
    }

    @Override
    public void tallenna() throws IOException {
        varmistaTiedosto();
        try (Writer kirjoittaja = new FileWriter(tiedosto)) {
            for (Object tieto : tiedot) {
                kirjoittaja.write(tieto.getClass().getName() + "|");
                kirjoittaja.write(tieto + "\n");
            }
        }
    }

    @Override
    public void lataa() throws IOException {
        varmistaTiedosto();
        tiedot.clear();
        try (Scanner lukija = new Scanner(tiedosto, kirjainjarjestelma)) {
            while (lukija.hasNextLine()) {
                String[] rivi = lukija.nextLine().split("\\|");
                if (rivi.length == 2) {
                    try {
                        Class clazz = Class.forName(rivi[0]);
                        tiedot.add(json.fromJson(rivi[1], clazz));
                    } catch (ClassNotFoundException ex) {
                        throw new ParseException("Luokkaa " + rivi[0] + " ei löytynyt.");
                    }
                } else {
                    throw new ParseException("Väärän muotoinen rivi, jonka pituus on " + rivi.length);
                }
            }
        }
    }

    private void varmistaTiedosto() throws IOException {
        File vanhempi = tiedosto.getParentFile();
        if (vanhempi != null) {
            vanhempi.mkdirs();
        }
        tiedosto.createNewFile();
    }

    public static class ParseException extends RuntimeException {

        public ParseException(String string) {
            super(string);
        }
    }

}
