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

description 'Järjestelmästä voi hakea viitteitä hakusanalla'

scenario "Järjestelmä listaa tietyn tyyppiset viitteet, jotka sisältävät haetun merkkijonon", {
    given 'järjestelmässä on tietyn tyyppisiä viitteitä', {
        //palautetaan tiedosto alkutilaan
        alkutila = new String("{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\"author1\",\"journal\":\"journal1\",\"title\":\"title1\",\"year\":\"year1\"},\"avain\":\"avain1\"}\n"
            + "{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\" author2\",\"journal\":\" journal2\",\"title\":\" title2\",\"year\":\" year2\"},\"avain\":\"avain2\"}\n"
            + "{\"tyyppi\":\"book\",\"attribuutit\":{\"author\":\" author3\",\"publisher\":\" publisher3\",\"title\":\" title3 \",\"year\":\" year3\"},\"avain\":\"avain3\"}\n" 
            + "{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\"kirjoittaja\",\"journal\":\"journal1\",\"title\":\"title1\",\"year\":\"year1\"},\"avain\":\"avain1\"}\n"
            + "{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\" kirjoittaja2\",\"journal\":\" journal2\",\"title\":\" title2\",\"year\":\" year2\"},\"avain\":\"avain2\"}\n"
            + "{\"tyyppi\":\"book\",\"attribuutit\":{\"author\":\" kirjoittaja3\",\"publisher\":\" publisher3\",\"title\":\" title3 \",\"year\":\" year3\"},\"avain\":\"avain3\"}\n");
        try {
            kirjoittaja = new FileWriter(new File("test.json"));

            kirjoittaja.write(alkutila);
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        k = new Kontrolleri(new File("test.json"));
    }
    when 'käyttäjä kirjoittaa hakusanan', {
        viitteet = k.hae(ViiteTyyppi.article, "kirjoittaja");
    }
    then 'järjestelmä listaa halutun tyyppiset viitteet, jotka sisältävät hakusanan', {
        viitteet.size().shouldBe(2);
        viitteet.get(0).haeArvo(Attribuutti.author).shouldEqual("kirjoittaja");
    }
}

scenario "Järjestelmä ei näytä viitteitä, jos halutun hakusanan sisältäviä viitteitä ei ole lisätty", {
    given 'järjestelmässä ei ole halutun tyyppisiä viitteitä', {
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
    }
    when 'käyttäjä antaa hakusanan', {
        viitteet = k.hae(ViiteTyyppi.book, "kirjoittaja");
    }
    then 'hakusanan sisältävien viitteiden lista on tyhjä', {
        viitteet.size().shouldBe(0);
    }
}