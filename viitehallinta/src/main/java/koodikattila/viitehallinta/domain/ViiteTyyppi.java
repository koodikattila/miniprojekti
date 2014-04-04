package koodikattila.viitehallinta.domain;

import java.util.ArrayList;
import java.util.Arrays;
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

    article(luoLista(author, title, journal, year), luoLista(volume, number, pages, month, note, key)),
    book(luoLista(author, title, publisher, year), luoLista(volume, number, series, address, edition, month, note, key)),
    booklet(luoLista(title), luoLista(author, howpublished, address, month, year, note, key)),
    conference(luoLista(author, title, booktitle, year), luoLista(editor, volume, number, series, pages, address, month, organization, publisher, note, key)),
    inbook(luoLista(author, title, chapter, publisher, year), luoLista(volume, number, series, type, address, edition, month, note, key)),
    incollection(luoLista(author, title, booktitle, publisher, year), luoLista(editor, volume, number, series, type, chapter, pages, address, edition, month, note, key)),
    inproceedings(luoLista(author, title, booktitle, year), luoLista(editor, volume, number, series, pages, address, month, organization, publisher, note, key)),
    manual(luoLista(title), luoLista(author, organization, address, edition, month, year, note, key)),
    mastersthesis(luoLista(author, title, school, year), luoLista(type, address, month, note, key)),
    misc(luoLista(), luoLista(author, title, howpublished, month, year, note, key)),
    phdthesis(luoLista(author, title, school, year), luoLista(type, address, month, note, key)),
    proceedings(luoLista(title, year), luoLista(editor, volume, number, series, address, month, publisher, organization, note, key)),
    techreport(luoLista(author, title, institution, year), luoLista(type, number, address, month, note, key)),
    unpublished(luoLista(author, title, note), luoLista(month, year, key));

    private final List<Attribuutti> pakolliset;
    private final List<Attribuutti> valinnaiset;

    ViiteTyyppi(List<Attribuutti> pakolliset, List<Attribuutti> valinnaiset) {
        this.pakolliset = pakolliset;
        this.valinnaiset = valinnaiset;
    }

    public List<Attribuutti> haePakolliset() {
        return pakolliset;
    }

    public List<Attribuutti> haeValinnaiset() {
        return valinnaiset;
    }

    private static List<Attribuutti> luoLista(Attribuutti... attribuuttit) {

        List<Attribuutti> lista = new ArrayList<Attribuutti>();
        lista.addAll(Arrays.asList(attribuuttit));
        return lista;
    }
}
