/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package koodikattila.viitehallinta.hallinta;

import java.util.ArrayList;
import java.util.List;
import koodikattila.viitehallinta.domain.Viite;

/**
 *
 * @author mikko
 */
public class Kontrolleri {
    private final List<Viite> viitteet;
    
    public Kontrolleri() {
        this.viitteet = new ArrayList<>();
        //TODO: fetchaa viitteet tiedostosta
    }
    
    public void lisaaViite(Viite lisattava) {
        this.viitteet.add(lisattava);
    }
    
    public List<Viite> getViitteet() {
        return this.viitteet;
    }
    
}
