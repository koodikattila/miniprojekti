package koodikattila.viitehallinta.domain;

/**
 *
 * @author kumikumi
 */
public enum Attribuutti {

    address,
    annote,
    author,
    booktitle,
    chapter,
    crossref,
    edition,
    editor,
    eprint,
    howpublished,
    institution,
    journal,
    key,
    month,
    note,
    number,
    organization,
    pages,
    publisher,
    school,
    series,
    title,
    type,
    url,
    volume,
    year;

    private static int maksimiPituus;

    public static int maksimiPituus() {
        if (maksimiPituus == Integer.MIN_VALUE) {
            for (Attribuutti attribuutti : Attribuutti.values()) {
                int pituus = attribuutti.toString().length();
                if (maksimiPituus < pituus) {
                    maksimiPituus = pituus;
                }
            }
        }
        return maksimiPituus;
    }
}
