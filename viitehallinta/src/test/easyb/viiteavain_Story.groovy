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
import java.lang.String;

description 'Käyttäjä voi antaa viitteelle uniikin viiteavaimen'

scenario "Viitteeseen voi liittää viiteavaimen", {
    given 'Järjestelmään on lisätty viite', {
        //palautetaan tiedosto alkutilaan
        alkutila = new String("{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\"author1\",\"journal\":\"journal1\",\"title\":\"title1\",\"year\":\"year1\"},\"avain\":\"avain1\"}\n"
            + "{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\" author2\",\"journal\":\" journal2\",\"title\":\" title2\",\"year\":\" year2\"},\"avain\":\"avain2\"}\n"
            + "{\"tyyppi\":\"book\",\"attribuutit\":{\"author\":\" author3\",\"publisher\":\" publisher3\",\"title\":\" title3 \",\"year\":\" year3\"},\"avain\":\"avain3\"}\n");
        try {
            kirjoittaja = new FileWriter(new File("test.json"));

            kirjoittaja.write(alkutila);
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        k = new Kontrolleri(new File("test.json"));
        k.hae(ViiteTyyppi.article, "");
        k.lisaaViite(new Viite(ViiteTyyppi.article));
        viitteet = k.hae(ViiteTyyppi.article, "");

    }
    when 'Käyttäjä antaa viitteelle uniikin viiteavaimen', {
        viitteet.get(2).setAvain("article_uusi");
    }
    then 'Viitteellä on uniikki viiteavain', {
        viitteet.get(2).getAvain().shouldEqual("article_uusi")
    }
}

scenario "Viitteeltä voi poistaa viiteavaimen", {
    given 'Järjestelmään on lisätty viite', {
        //palautetaan tiedosto alkutilaan
        alkutila = new String("{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\"author1\",\"journal\":\"journal1\",\"title\":\"title1\",\"year\":\"year1\"},\"avain\":\"avain1\"}\n"
            + "{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\" author2\",\"journal\":\" journal2\",\"title\":\" title2\",\"year\":\" year2\"},\"avain\":\"avain2\"}\n"
            + "{\"tyyppi\":\"book\",\"attribuutit\":{\"author\":\" author3\",\"publisher\":\" publisher3\",\"title\":\" title3 \",\"year\":\" year3\"},\"avain\":\"avain3\"}\n");
        try {
            kirjoittaja = new FileWriter(new File("test.json"));

            kirjoittaja.write(alkutila);
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        k = new Kontrolleri(new File("test.json"));
        k.hae(ViiteTyyppi.article, "");
        k.lisaaViite(new Viite(ViiteTyyppi.article));
        viitteet = k.hae(ViiteTyyppi.article, "");

    }
    when 'Käyttäjä poistaa viitteeltä viiteavaimen', {
        viitteet.get(1).setAvain("");
    }
    then 'Viitteellä ei ole viiteavainta', {
        viitteet.get(1).getAvain().shouldEqual("")
    }
}