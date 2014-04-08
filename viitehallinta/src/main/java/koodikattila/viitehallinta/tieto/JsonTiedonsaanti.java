package koodikattila.viitehallinta.tieto;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import koodikattila.viitehallinta.domain.Viite;

/**
 *
 * @author Koodikattila
 */
public class JsonTiedonsaanti implements Tiedonsaanti<Viite> {

    private final Collection<Viite> tiedot;
    private final Gson json;
    private final File tiedosto;
    private final String kirjainjarjestelma = "UTF-8";

    public JsonTiedonsaanti(File tiedosto) {
        this.json = new Gson();
        this.tiedot = new ArrayList<>();
        this.tiedosto = tiedosto;
    }

    @Override
    public Collection<Viite> haeTiedot(Filtteri<Viite> filtteri, Class<Viite> clazz) {
        Collection<Viite> oliot = new ArrayList<>();
        for (Viite olio : tiedot) {
            if (clazz.isAssignableFrom(olio.getClass()) && filtteri.testaa(olio)) {
                oliot.add(olio);
            }
        }
        return oliot;
    }

    @Override
    public void lisaaTieto(Viite... lisattavat) {
        tiedot.addAll(Arrays.asList(lisattavat));
    }

    @Override
    public void tallenna() throws IOException {
        varmistaTiedosto();
        try (Writer kirjoittaja = new FileWriter(tiedosto)) {
            for (Object tieto : tiedot) {
                kirjoittaja.write(json.toJson(tieto) + "\n");
            }
        }
    }

    @Override
    public void lataa() throws IOException {
        varmistaTiedosto();
        tiedot.clear();
        try (Scanner lukija = new Scanner(tiedosto, kirjainjarjestelma)) {
            while (lukija.hasNextLine()) {
                tiedot.add(json.fromJson(lukija.nextLine(), Viite.class));
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

    @Override
    public void close() throws IOException {
        tallenna();
    }
}
