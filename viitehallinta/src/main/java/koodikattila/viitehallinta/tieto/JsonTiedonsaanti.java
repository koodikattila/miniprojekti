package koodikattila.viitehallinta.tieto;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Scanner;
import koodikattila.viitehallinta.domain.Viite;

/**
 *
 * @author Koodikattila
 */
public class JsonTiedonsaanti extends ParseavaTiedonsaanti<Viite> {

    private final Gson json;

    public JsonTiedonsaanti() {
        this.json = new Gson();
    }

    @Override
    public void lueTiedot(Scanner lukija, Collection<Viite> tiedot) {
        while (lukija.hasNextLine()) {
            tiedot.add(json.fromJson(lukija.nextLine(), Viite.class));
        }
    }

    @Override
    public void kirjoitaTieto(Writer kirjoittaja, Viite tieto) throws IOException {
        kirjoittaja.write(json.toJson(tieto) + "\n");
    }
}
