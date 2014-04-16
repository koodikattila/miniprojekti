package koodikattila.viitehallinta.tieto;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;

/**
 *
 * @author Koodikattila
 */
public class BibTeXTiedonsaanti extends ParseavaTiedonsaanti<Viite> implements Parseri {

    private Map<Character, String> muunnos;

    public BibTeXTiedonsaanti() {
        muunnos = new HashMap<>();
        muunnos.put('ä', "\\\"{a}");
        muunnos.put('Ä', "\\\"{A}");
        muunnos.put('å', "\\aa");
        muunnos.put('Å', "\\AA");
        muunnos.put('ö', "\\\"{o}");
        muunnos.put('Ö', "\\\"{O}");
    }

    @Override
    public void lueTiedot(Scanner lukija, Collection<Viite> tiedot) {
        //TODO
    }

    @Override
    public void kirjoitaTieto(Writer kirjoittaja, Viite tieto) throws IOException {
        kirjoittaja.write(viiteTekstiksi(new StringBuilder(), tieto));
    }

    @Override
    public List<Viite> viitekokoelmaksi(String teksti) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String tekstiksi(List<Viite> viitteet) {
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
