package koodikattila.viitehallinta.tieto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;

/**
 *
 * @author Koodikattila
 */
public class BibTeXTiedonsaanti implements Tiedonsaanti<Viite> {

    private final Collection<Viite> tiedot;
    private final String kirjainjarjestelma = "UTF-8";

    public BibTeXTiedonsaanti() {
        this.tiedot = new ArrayList<>();
    }

    @Override
    public Collection<Viite> haeTiedot(Filtteri filtteri, Class clazz) {
        Collection<Viite> oliot = new ArrayList<>();
        for (Viite olio : tiedot) {
            if (olio != null && clazz.isAssignableFrom(olio.getClass()) && filtteri.testaa(olio)) {
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
    public void tallenna(File tiedosto) throws IOException {
        varmistaTiedosto(tiedosto);
        try (Writer kirjoittaja = new FileWriter(tiedosto)) {
            for (Viite tieto : tiedot) {
                kirjoittaja.write(luoString(tieto) + "\n");
            }
        }
    }

    @Override
    public void lataa(File tiedosto) throws IOException {
        varmistaTiedosto(tiedosto);
        tiedot.clear();
        try (Scanner lukija = new Scanner(tiedosto, kirjainjarjestelma)) {
            while (lukija.hasNextLine()) {
                tiedot.add(parse(lukija.nextLine()));
            }
        }
    }

    private void varmistaTiedosto(File tiedosto) throws IOException {
        File vanhempi = tiedosto.getParentFile();
        if (vanhempi != null) {
            vanhempi.mkdirs();
        }
        tiedosto.createNewFile();
    }

    private Viite parse(String string) {
        //TODO
        return null;
    }

    private String luoString(Viite tieto) {
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
        return rakentaja.append("\n}").toString();
    }
}
