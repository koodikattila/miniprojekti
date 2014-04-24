/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koodikattila.viitehallinta.gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;
import koodikattila.viitehallinta.hallinta.Kontrolleri;

/**
 *
 * @author mikko
 */
public class Taulukko extends AbstractTableModel {

    private Kontrolleri kontrolleri;
    private ViiteTyyppi valittuTyyppi;
    
    private List<Attribuutti> sarakkeet;
    private List<Viite> viitteet;
    
    public Taulukko (Kontrolleri k, ViiteTyyppi tyyppi) {
        super();
        this.kontrolleri = k;
        this.valittuTyyppi = tyyppi;
        
        this.sarakkeet = haeSarakkeet();
        this.viitteet = kontrolleri.hae(valittuTyyppi, "");
    }

    private List<Attribuutti> haeSarakkeet() {
        List<Attribuutti> pakolliset = valittuTyyppi.haePakolliset();
        List<Attribuutti> valinnaiset = valittuTyyppi.haeValinnaiset();
        List<Attribuutti> palautus = new ArrayList<>();
        palautus.addAll(pakolliset);
        palautus.addAll(valinnaiset);
        return palautus;
    }

    @Override
    public String getColumnName(int col) {
        if (col == 0) {
            return "avain";
        }
        return sarakkeet.get(col - 1).toString();
    }

    @Override
    public int getRowCount() {
        return viitteet.size();
    }

    @Override
    public int getColumnCount() {
        return sarakkeet.size() + 1;
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (col == 0) {
            return viitteet.get(row).getAvain();
        }
        return viitteet.get(row).haeArvo(sarakkeet.get(col - 1));
    }

    @Override
    public boolean isCellEditable(int row, int col) {
//                if (col == 0) {
//                    return false;
//                }
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        Viite viite = viitteet.get(row);
        if (col == 0) {
            viite.setAvain(kontrolleri.tarkistaViiteavain(viite, (String) value));
//            viitteet.get(row).setAvain((String) value);
            
            
        } else {
            boolean validi = viite.onkoValidi();
            viite.asetaArvo(sarakkeet.get(col - 1), (String) value);
            if (!validi) viite.setAvain(kontrolleri.generoiViiteavain(viite));
        }
    }

}
