package koodikattila.viitehallinta.tieto;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Scanner;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;

/**
 *
 * @author Koodikattila
 */
public class BibTeXTiedonsaanti extends ParseavaTiedonsaanti<Viite> {

    public BibTeXTiedonsaanti() {
    }

    @Override
    public void lueTiedot(Scanner lukija, Collection<Viite> tiedot) {
        //TODO
    }

    @Override
    public void kirjoitaTieto(Writer kirjoittaja, Viite tieto) throws IOException {
        StringBuilder rakentaja = new StringBuilder();
        rakentaja.append("@").append(tieto.getTyyppi()).append("{").append(tieto.getAvain()).append(",").append("\n");
        for (Attribuutti attribuutti : tieto.asetetutAttribuutit()) {
            rakentaja.append(" ").append(attribuutti);
            int maara = Attribuutti.maksimiPituus() - attribuutti.toString().length() + 1;
            System.out.println("");
            for (int n = 0; n < maara; n++) {
                rakentaja.append(" ");
            }
            rakentaja.append("= \"").append(tieto.haeArvo(attribuutti)).append("\",\n");
        }
        rakentaja.setLength(rakentaja.length() - 2);
        kirjoittaja.write(rakentaja.append("\n}").toString());
    }
}
