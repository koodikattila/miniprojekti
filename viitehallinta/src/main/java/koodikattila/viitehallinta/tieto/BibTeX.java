package koodikattila.viitehallinta.tieto;

import java.util.HashMap;
import java.util.Map;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;

/**
 *
 * @author Koodikattila
 */
public class BibTeX implements Parseri {

    private final Map<Character, String> muunnos;

    public BibTeX() {
        muunnos = new HashMap<>();
        muunnos.put('ä', "\\\"{a}");
        muunnos.put('Ä', "\\\"{A}");
        muunnos.put('å', "\\aa");
        muunnos.put('Å', "\\AA");
        muunnos.put('ö', "\\\"{o}");
        muunnos.put('Ö', "\\\"{O}");
    }

    @Override
    public Viitekokoelma viitekokoelmaksi(String teksti) {
        throw new UnsupportedOperationException("Operation not supported.");
    }

    @Override
    public String tekstiksi(Viitekokoelma viitteet) {
        StringBuilder rakentaja = new StringBuilder();
        for (Viite viite : viitteet) {
            viiteTekstiksi(rakentaja, viite);
        }
        return rakentaja.toString();
    }

    private String viiteTekstiksi(StringBuilder rakentaja, Viite viite) {
        rakentaja.append("@").append(viite.getTyyppi()).append("{").append(viite.getAvain()).append(",").append("\n");

        for (Attribuutti attribuutti : viite.asetetutAttribuutit()) {
            rakentaja.append(" ").append(attribuutti);
            int maara = Attribuutti.maksimiPituus() - attribuutti.toString().length() + 1;
            for (int n = 0; n < maara; n++) {
                rakentaja.append(" ");
            }
            rakentaja.append("= \"").append(bibTexify(viite.haeArvo(attribuutti))).append("\",\n");
        }
        rakentaja.setLength(rakentaja.length() - 2);
        rakentaja.append("\n}\n");
        return rakentaja.toString();
    }

    private String bibTexify(String teksti) {
        StringBuilder rakentaja = new StringBuilder();
        for (char merkki : teksti.toCharArray()) {
            String muunnettu = muunnos.get(merkki);
            if (muunnettu == null) {
                muunnettu = "" + merkki;
            }
            rakentaja.append(muunnettu);
        }
        return rakentaja.toString();
    }
}
