package koodikattila.viitehallinta.tieto;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import koodikattila.viitehallinta.domain.Viite;

/**
 *
 * @author Koodikattila
 */
public class JsonTiedonsaanti extends ParseavaTiedonsaanti<Viite> implements Parseri {

    private final Gson json;

    public JsonTiedonsaanti() {
        this.json = new Gson();
    }

    @Override
    public void lueTiedot(Scanner lukija, Collection<Viite> tiedot) {
        String teksti = "";
        while (lukija.hasNextLine()) {
            teksti += lukija.nextLine() + "\n";
        }
        tiedot.addAll(viitekokoelmaksi(teksti));
    }

    @Override
    public void kirjoitaTieto(Writer kirjoittaja, Viite tieto) throws IOException {
        kirjoittaja.write(json.toJson(tieto) + "\n");
    }

    @Override
    public List<Viite> viitekokoelmaksi(String teksti) {
        List<Viite> lista = new ArrayList<>();
        for(String rivi : teksti.split("\n")) {
            lista.add(json.fromJson(rivi, Viite.class));
        }
        return lista;
    }

    @Override
    public String tekstiksi(List<Viite> viitteet) {
        StringBuilder teksti = new StringBuilder();
        for (Viite viite: viitteet){
            teksti.append(json.toJson(viite)).append("\n");
        }
        return teksti.toString();
    }
}
