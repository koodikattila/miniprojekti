package koodikattila.viitehallinta.gui;

import koodikattila.viitehallinta.domain.ViiteTyyppi;

/**
 *
 * @author Koodikattila
 */
class ViiteTyyppiSailo {

    private final ViiteTyyppi tyyppi;
    private int maara;

    public ViiteTyyppiSailo(ViiteTyyppi tyyppi) {
        this.tyyppi = tyyppi;
        maara = -1;
    }

    public ViiteTyyppi getTyyppi() {
        return tyyppi;
    }

    public int getMaara() {
        return maara;
    }

    public void setMaara(int maara) {
        this.maara = maara;
    }

    public void nollaaMaara() {
        maara = -1;
    }

    @Override
    public String toString() {
        String stringi = "" + tyyppi;
        if (maara != -1) {
            stringi += " (" + maara + ")";
        }
        return stringi;
    }

}
