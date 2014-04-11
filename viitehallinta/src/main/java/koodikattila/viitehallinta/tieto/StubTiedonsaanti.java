package koodikattila.viitehallinta.tieto;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import koodikattila.viitehallinta.domain.Viite;

public class StubTiedonsaanti<T> extends ParseavaTiedonsaanti<Viite> {
    
    private final Collection<Viite> lisattavat;
    private final Collection<Viite> kirjoitetut;
    public StubTiedonsaanti(Viite... viitteet) {
        this.kirjoitetut = new ArrayList<>();
        this.lisattavat = Arrays.asList(viitteet);
    }

    @Override
    public void lueTiedot(Scanner lukija, Collection<Viite> tiedot) {
        tiedot.addAll(lisattavat);
    }

    @Override
    public void kirjoitaTieto(Writer kirjoittaja, Viite tieto) throws IOException {
        this.kirjoitetut.add(tieto);
    }

}
