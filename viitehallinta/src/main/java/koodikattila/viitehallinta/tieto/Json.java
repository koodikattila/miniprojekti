package koodikattila.viitehallinta.tieto;

import com.google.gson.Gson;
import koodikattila.viitehallinta.domain.Viite;

/**
 *
 * @author Koodikattila
 */
public class Json implements Parseri {

    private final Gson json;

    public Json() {
        this.json = new Gson();
    }

    @Override
    public Viitekokoelma viitekokoelmaksi(String teksti) {
        Viitekokoelma lista = new Viitekokoelma();
        for (String rivi : teksti.split("\n")) {
            lista.lisaa(json.fromJson(rivi, Viite.class));
        }
        return lista;
    }

    @Override
    public String tekstiksi(Viitekokoelma viitteet) {
        StringBuilder teksti = new StringBuilder();
        for (Viite viite : viitteet) {
            teksti.append(json.toJson(viite)).append("\n");
        }
        return teksti.toString();
    }
}
