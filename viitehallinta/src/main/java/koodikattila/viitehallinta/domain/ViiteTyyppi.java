package koodikattila.viitehallinta.domain;

import java.util.ArrayList;
import java.util.List;
import static koodikattila.viitehallinta.domain.Attribuutti.*;

/**
 * Wiki http://en.wikipedia.org/wiki/BibTeX
 * 
 * Pakolliset attribuutit on nyt toteutettu.
 *
 * @author kumikumi
 */
public enum ViiteTyyppi {

    article(luoLista(author, title, journal, year)),
    book(luoLista(author, title, publisher, year)),
    booklet(luoLista(title)),
    conference(luoLista(author, title, booktitle, year)),
    inbook(luoLista( author, title, chapter, publisher, year)),
    incollection(luoLista( author, title, booktitle, publisher, year)),
    inproceedings(luoLista(author, title, booktitle, year)),
    manual(luoLista(title)),
    mastersthesis(luoLista(author, title, school, year)),
    misc(luoLista()),
    phdthesis(luoLista(author, title, school, year)),
    proceedings(luoLista(title, year)),
    techreport(luoLista( author, title, institution, year)),
    unpublished(luoLista(author, title, note));

    private final List<Attribuutti> pakolliset;

    ViiteTyyppi(List<Attribuutti> pakolliset) {
        this.pakolliset = pakolliset;
    }

    public List<Attribuutti> haePakolliset() {
        return pakolliset;
    }
    
    private static List<Attribuutti> luoLista(Attribuutti... attribuuttit) {
        List<Attribuutti> lista = new ArrayList<Attribuutti>();
        for(Attribuutti attribuutti : attribuuttit) {
            lista.add(attribuutti);
        }
        return lista;
    }
}
