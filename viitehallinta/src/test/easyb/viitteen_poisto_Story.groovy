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

description 'Järjestelmästä pystyy poistamaan viitteen'

scenario "Järjestelmästä voi poistaa halutun viitteen", {
    given 'Järjestelmässä on lista viitteistä', {
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
        
        k = new Kontrolleri(new JsonTiedonsaanti(), new BibTeXTiedonsaanti(), new File("test.json"));
    }
    when 'valitaan viite poistettavaksi', {
        List<Viite> viitteet = k.hae(ViiteTyyppi.article, "");
        k.poista(0);
        k.tallenna();
        
    }
    then 'viite poistuu järjestelmästä', {
        //tiedoston lukeminen stringiksi
        teksti = null;
        try {
            teksti = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(Files.readAllBytes(Paths.get("test.json")))).toString();
        } catch (IOException ex) {

        }
        
        haluttu = new String("{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\" author2\",\"journal\":\" journal2\",\"title\":\" title2\",\"year\":\" year2\"},\"avain\":\"avain2\"}\n"
            + "{\"tyyppi\":\"book\",\"attribuutit\":{\"author\":\" author3\",\"publisher\":\" publisher3\",\"title\":\" title3 \",\"year\":\" year3\"},\"avain\":\"avain3\"}\n");

        teksti.equals(haluttu).shouldBe(true);
    }
}

scenario "Järjestelmästä ei poistu viitteitä, jos viitettä ei poisteta", {
    given 'Järjestelmässä on lista viitteistä', {
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
        
        k = new Kontrolleri(new JsonTiedonsaanti(), new BibTeXTiedonsaanti(), new File("test.json"));
    }
    when 'listataan tietyn tyyppiset viitteet, mutta ei poisteta mitään', {
        List<Viite> viitteet = k.hae(ViiteTyyppi.article, "");
        k.tallenna();
        
    }
    then 'viite ei poistu järjestelmästä', {
        //tiedoston lukeminen stringiksi
        teksti = null;
        try {
            teksti = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(Files.readAllBytes(Paths.get("test.json")))).toString();
        } catch (IOException ex) {

        }
        
        haluttu = new String("{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\"author1\",\"journal\":\"journal1\",\"title\":\"title1\",\"year\":\"year1\"},\"avain\":\"avain1\"}\n"
            + "{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\" author2\",\"journal\":\" journal2\",\"title\":\" title2\",\"year\":\" year2\"},\"avain\":\"avain2\"}\n"
            + "{\"tyyppi\":\"book\",\"attribuutit\":{\"author\":\" author3\",\"publisher\":\" publisher3\",\"title\":\" title3 \",\"year\":\" year3\"},\"avain\":\"avain3\"}\n");

        teksti.equals(haluttu).shouldBe(true);
    }
}

scenario "Järjestelmään ei jää viitteitä, jos kaikki viitteet poistetaan", {
    given 'Järjestelmässä on lista viitteistä', {
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
        
        k = new Kontrolleri(new JsonTiedonsaanti(), new BibTeXTiedonsaanti(), new File("test.json"));
    }
    when 'järjestelmästä poistetaan kaikki viitteet', {
        List<Viite> viitteet = k.hae(ViiteTyyppi.article, "");
        k.poista(1);
        k.poista(0);
        viitteet = k.hae(ViiteTyyppi.book, "");
        k.poista(0);
        k.tallenna();
        
    }
    then 'järjestelmässä ei ole viitteitä', {
        //tiedoston lukeminen stringiksi
        teksti = null;
        try {
            teksti = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(Files.readAllBytes(Paths.get("test.json")))).toString();
        } catch (IOException ex) {

        }
        haluttu = new String("");

        teksti.equals(haluttu).shouldBe(true);
    }
}