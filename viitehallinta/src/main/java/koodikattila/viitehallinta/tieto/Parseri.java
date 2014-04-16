package koodikattila.viitehallinta.tieto;

import java.util.List;
import koodikattila.viitehallinta.domain.Viite;

public interface Parseri {
    
    public List<Viite> viitekokoelmaksi(String teksti);
    
    public String tekstiksi(List<Viite> viitteet);
    
}
