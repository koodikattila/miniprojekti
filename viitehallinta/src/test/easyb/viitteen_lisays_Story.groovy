import koodikattila.viitehallinta.*
import koodikattila.viitehallinta.domain.Viite;
import koodikattila.viitehallinta.domain.*
import koodikattila.viitehallinta.hallinta.*
import koodikattila.viitehallinta.tieto.*
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

description 'Järjestelmään pystyy lisäämään uuden viitteen'

scenario "Viitteen lisääminen järjestelmään onnistuu, kun annetaan kaikki vaaditut tiedot", {
    given 'Yritetään lisätä uusi viite', {
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
    when 'Viitteeseen vaaditut tiedot on annettu', {
        
        k.hae(ViiteTyyppi.article, "");
        k.lisaaViite(new Viite(ViiteTyyppi.article));
        List<Viite> viitteet = k.hae(ViiteTyyppi.article, "");
        viitteet.get(2).setAvain("article_uusi");

        viitteet.get(2).asetaArvo(Attribuutti.author, "author_uusi");
        viitteet.get(2).asetaArvo(Attribuutti.publisher, "publisher_uusi");
        viitteet.get(2).asetaArvo(Attribuutti.title, "title_uusi");
        viitteet.get(2).asetaArvo(Attribuutti.year, "year_uusi");

        k.tallenna();
        
    }
    then 'Viite tallentuu järjestelmään', {
        //tiedoston lukeminen stringiksi
        teksti = null;
        try {
            teksti = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(Files.readAllBytes(Paths.get("test.json")))).toString();
        } catch (IOException ex) {

        }
        
        haluttu = new String("{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\"author1\",\"journal\":\"journal1\",\"title\":\"title1\",\"year\":\"year1\"},\"avain\":\"avain1\"}\n"
                + "{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\" author2\",\"journal\":\" journal2\",\"title\":\" title2\",\"year\":\" year2\"},\"avain\":\"avain2\"}\n"
                + "{\"tyyppi\":\"book\",\"attribuutit\":{\"author\":\" author3\",\"publisher\":\" publisher3\",\"title\":\" title3 \",\"year\":\" year3\"},\"avain\":\"avain3\"}\n"
                + "{\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\"author_uusi\",\"publisher\":\"publisher_uusi\",\"title\":\"title_uusi\",\"year\":\"year_uusi\"},\"avain\":\"article_uusi\"}\n");

        teksti.equals(haluttu).shouldBe(true);
        //"kissa".equals("kissa").shouldBe(true);
    }
}
