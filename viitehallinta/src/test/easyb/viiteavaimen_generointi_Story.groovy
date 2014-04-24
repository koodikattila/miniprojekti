import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import koodikattila.viitehallinta.domain.Attribuutti;
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.ViiteTyyppi;
import koodikattila.viitehallinta.hallinta.Kontrolleri;
import koodikattila.viitehallinta.tieto.BibTeXTiedonsaanti;
import koodikattila.viitehallinta.tieto.JsonTiedonsaanti;

description 'Viitteelle generoidaan uniikki viiteavain'

scenario "Uudelle viitteelle generoidaan uniikki viiteavain", {
    given 'Käyttäjä lisää uuden viitteen', {
        //palautetaan tiedosto alkutilaan
        alkutila = new String("");
        try {
            kirjoittaja = new FileWriter(new File("test.json"));

            kirjoittaja.write(alkutila);
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        k = new Kontrolleri(new JsonTiedonsaanti(), new BibTeXTiedonsaanti(), new File("test.json"));

    }
    when 'Käyttäjä on antanut pakolliset tiedot', {
        
        
        k.lisaaViite(new Viite(ViiteTyyppi.article));
        viitteet = k.hae(ViiteTyyppi.article, "");
        
        viitteet.get(0).asetaArvo(Attribuutti.author, "author_uusi");
        viitteet.get(0).asetaArvo(Attribuutti.publisher, "publisher_uusi");
        viitteet.get(0).asetaArvo(Attribuutti.title, "title_uusi");
        viitteet.get(0).asetaArvo(Attribuutti.year, "0000");
        viitteet.get(0).setAvain(k.generoiViiteavain(viitteet.get(0)));
        k.tallenna();
    }
    then 'Viitteelle generoidaan uniikki viiteavain annettujen tietojen perusteella', {
        viitteet.get(0).getAvain().shouldEqual("au00")
    }
}

scenario "Viiteavaimet ovat uniikkeja", {
    given 'Järjestelmään on lisätty viitteitä', {
        //palautetaan tiedosto alkutilaan
        alkutila = new String("{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\"author1\",\"journal\":\"journal1\",\"title\":\"title1\",\"year\":\"1111\"},\"avain\":\"au11\"}\n"
            + "{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\" author2\",\"journal\":\" journal2\",\"title\":\" title2\",\"year\":\" 2222\"},\"avain\":\"au22\"}\n"
            + "{\"tyyppi\":\"book\",\"attribuutit\":{\"author\":\" author3\",\"publisher\":\" publisher3\",\"title\":\" title3 \",\"year\":\" 3333\"},\"avain\":\"au3\"}\n");
        try {
            kirjoittaja = new FileWriter(new File("test.json"));

            kirjoittaja.write(alkutila);
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        k = new Kontrolleri(new JsonTiedonsaanti(), new BibTeXTiedonsaanti(), new File("test.json"));

    }
    when 'Käyttäjä lisää viitteen, jonka avaimeen vaikuttavat tiedot ovat samat kuin jo olemassa olevalla viitteellä', {
        
        k.lisaaViite(new Viite(ViiteTyyppi.article));
        viitteet = k.hae(ViiteTyyppi.article, "");
        
        viitteet.get(2).asetaArvo(Attribuutti.author, "author1");
        viitteet.get(2).asetaArvo(Attribuutti.publisher, "publisher_uusi");
        viitteet.get(2).asetaArvo(Attribuutti.title, "title_uusi");
        viitteet.get(2).asetaArvo(Attribuutti.year, "1111");
        viitteet.get(2).setAvain(k.generoiViiteavain(viitteet.get(2)));

        k.tallenna();
    }
    then 'Uudelle viitteelle generoidaan uniikki viiteavain', {
        viitteet.get(2).getAvain().shouldEqual("au112")
    }
}