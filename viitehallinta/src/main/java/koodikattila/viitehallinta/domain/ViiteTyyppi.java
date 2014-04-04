package koodikattila.viitehallinta.domain;

import java.util.Arrays;
import java.util.List;
import static koodikattila.viitehallinta.domain.Attribuutti.address;
import static koodikattila.viitehallinta.domain.Attribuutti.author;
import static koodikattila.viitehallinta.domain.Attribuutti.booktitle;
import static koodikattila.viitehallinta.domain.Attribuutti.chapter;
import static koodikattila.viitehallinta.domain.Attribuutti.edition;
import static koodikattila.viitehallinta.domain.Attribuutti.editor;
import static koodikattila.viitehallinta.domain.Attribuutti.howpublished;
import static koodikattila.viitehallinta.domain.Attribuutti.institution;
import static koodikattila.viitehallinta.domain.Attribuutti.journal;
import static koodikattila.viitehallinta.domain.Attribuutti.key;
import static koodikattila.viitehallinta.domain.Attribuutti.month;
import static koodikattila.viitehallinta.domain.Attribuutti.note;
import static koodikattila.viitehallinta.domain.Attribuutti.number;
import static koodikattila.viitehallinta.domain.Attribuutti.organization;
import static koodikattila.viitehallinta.domain.Attribuutti.pages;
import static koodikattila.viitehallinta.domain.Attribuutti.publisher;
import static koodikattila.viitehallinta.domain.Attribuutti.school;
import static koodikattila.viitehallinta.domain.Attribuutti.series;
import static koodikattila.viitehallinta.domain.Attribuutti.title;
import static koodikattila.viitehallinta.domain.Attribuutti.type;
import static koodikattila.viitehallinta.domain.Attribuutti.volume;
import static koodikattila.viitehallinta.domain.Attribuutti.year;

/**
 * Wiki http://en.wikipedia.org/wiki/BibTeX
 *
 * Pakolliset attribuutit on nyt toteutettu.
 *
 * @author kumikumi
 */
public enum ViiteTyyppi {

    article(
            lista(author, title, journal, year),
            lista(volume, number, pages, month, note, key)),
    book(
            lista(author, title, publisher, year),
            lista(volume, number, series, address, edition, month, note, key)),
    booklet(
            lista(title),
            lista(author, howpublished, address, month, year, note, key)),
    conference(
            lista(author, title, booktitle, year),
            lista(editor, volume, number, series, pages, address, month, organization, publisher, note, key)),
    inbook(
            lista(author, title, chapter, publisher, year),
            lista(volume, number, series, type, address, edition, month, note, key)),
    incollection(
            lista(author, title, booktitle, publisher, year),
            lista(editor, volume, number, series, type, chapter, pages, address, edition, month, note, key)),
    inproceedings(
            lista(author, title, booktitle, year),
            lista(editor, volume, number, series, pages, address, month, organization, publisher, note, key)),
    manual(
            lista(title),
            lista(author, organization, address, edition, month, year, note, key)),
    mastersthesis(
            lista(author, title, school, year),
            lista(type, address, month, note, key)),
    misc(
            lista(),
            lista(author, title, howpublished, month, year, note, key)),
    phdthesis(
            lista(author, title, school, year),
            lista(type, address, month, note, key)),
    proceedings(
            lista(title, year),
            lista(editor, volume, number, series, address, month, publisher, organization, note, key)),
    techreport(
            lista(author, title, institution, year),
            lista(type, number, address, month, note, key)),
    unpublished(
            lista(author, title, note),
            lista(month, year, key));

    private final List<Attribuutti> pakolliset;
    private final List<Attribuutti> valinnaiset;

    ViiteTyyppi(Attribuutti[] pakolliset, Attribuutti[] valinnaiset) {
        this.pakolliset = Arrays.asList(pakolliset);
        this.valinnaiset = Arrays.asList(valinnaiset);
    }

    public List<Attribuutti> haePakolliset() {
        return pakolliset;
    }

    public List<Attribuutti> haeValinnaiset() {
        return valinnaiset;
    }

    private static Attribuutti[] lista(Attribuutti... attribuuttit) {
        return attribuuttit;
    }
}
