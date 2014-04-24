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

    @Override
    public String toString() {
        return tyyppi + " (" + maara + ")";
    }

}
