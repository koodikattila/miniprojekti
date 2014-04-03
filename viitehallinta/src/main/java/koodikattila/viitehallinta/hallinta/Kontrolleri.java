/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package koodikattila.viitehallinta.hallinta;

import java.util.ArrayList;
import java.util.List;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;

/**
 *
 * @author mikko
 */
public class Kontrolleri {
    private final List<Viite> viitteet;
    private List<Viite> viimeksiHaetut;
    
    public Kontrolleri() {
        this.viitteet = new ArrayList<>();
        //this.populoiLista();
        //TODO: fetchaa viitteet tiedostosta
    }
    
    private void populoiLista() {
        Viite v1 = new Viite(ViiteTyyppi.book);
        v1.asetaArvo(Attribuutti.author, "kerola");
        v1.asetaArvo(Attribuutti.title, "titoilua");
        v1.asetaArvo(Attribuutti.publisher, "tktl");
        v1.asetaArvo(Attribuutti.year, "1991");
        this.viitteet.add(v1);
        Viite v2 = new Viite(ViiteTyyppi.book);
        this.viitteet.add(v2);
    }
    
    public void lisaaViite(Viite lisattava) {
        this.viitteet.add(lisattava);
    }
    
    public List<Viite> getViitteet() {
        return this.viitteet;
    }
    
    public List<Viite> hae(ViiteTyyppi tyyppi, String hakusana) {
        List<Viite> palautus = new ArrayList<Viite>();
        for (Viite v : this.viitteet) {
            if (v.getTyyppi().equals(tyyppi)) {
                if (hakusana.isEmpty()) {
                    palautus.add(v);
                } else {
                    for (Attribuutti attr : v.asetetutAttribuutit()) {
                        if (v.haeArvo(attr).contains(hakusana)) {
                            palautus.add(v);
                            break;
                        }
                    }
                }
            }
        }
        this.viimeksiHaetut = palautus;
        return palautus;
    }
    
    public void poista(int indeksi) {
        this.viitteet.remove(this.viimeksiHaetut.get(indeksi));
        this.viimeksiHaetut.remove(this.viimeksiHaetut.get(indeksi));
    }
    
}
