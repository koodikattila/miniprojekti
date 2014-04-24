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
import koodikattila.viitehallinta.hallinta.Kontrolleri;
import java.util.List;
import java.lang.String;

description 'Järjestelmään lisätyt viitteet tallentuvat tiedostoon'

scenario "Viite tallentuu järjestelmään, kun annetaan kaikki vaaditut tiedot", {
    given 'Järjestelmään lisätään uusi viite', {
        //palautetaan tiedosto alkutilaan

    alkutila = new String("");
        try {
            kirjoittaja = new FileWriter(new File("test.json"));

            kirjoittaja.write(alkutila);
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        k = new Kontrolleri(new File("test.json"));

    }
    when 'Kaikki uuteen viitteeseen tarvittavat tiedot on annettu', {
        
        k.hae(ViiteTyyppi.article, "");
        k.lisaaViite(new Viite(ViiteTyyppi.article));
        List<Viite> viitteet = k.hae(ViiteTyyppi.article, "");
        viitteet.get(0).setAvain("article1");

        viitteet.get(0).asetaArvo(Attribuutti.author, "author1");
        viitteet.get(0).asetaArvo(Attribuutti.publisher, "publisher1");
        viitteet.get(0).asetaArvo(Attribuutti.title, "title1");
        viitteet.get(0).asetaArvo(Attribuutti.year, "year1");

        k.tallenna();
        
    }
    then 'Viite tallentuu järjestelmään', {
        //tiedoston lukeminen stringiksi
        teksti = null;
        try {
            teksti = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(Files.readAllBytes(Paths.get("test.json")))).toString();
        } catch (IOException ex) {

        }

        haluttu = new String("{\"tagit\":[],\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\"author1\",\"publisher\":\"publisher1\",\"title\":\"title1\",\"year\":\"year1\"},\"avain\":\"article1\"}\n");

        teksti.equals(haluttu).shouldBe(true);
    }
}

scenario "Viite tallentuu järjestelmään, vaikka kaikkia vaadittuja tietoja ei ole vielä annettu", {
    given 'Aletaan lisämään uutta viitettä', {
        //palautetaan tiedosto alkutilaan
    alkutila = new String("");
        try {
            kirjoittaja = new FileWriter(new File("test.json"));

            kirjoittaja.write(alkutila);
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        k = new Kontrolleri(new File("test.json"));

    }
    when 'Viitteelle annetaan joitakin tietoja', {
        
        k.hae(ViiteTyyppi.article, "");
        k.lisaaViite(new Viite(ViiteTyyppi.article));
        List<Viite> viitteet = k.hae(ViiteTyyppi.article, "");
        viitteet.get(0).setAvain("article2");
        viitteet.get(0).asetaArvo(Attribuutti.author, "author2");
        k.tallenna();
        
    }
    then 'Viite tallentuu järjestelmään', {
        //tiedoston lukeminen stringiksi
        teksti = null;
        try {
            teksti = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(Files.readAllBytes(Paths.get("test.json")))).toString();
        } catch (IOException ex) {

        }
        haluttu = new String("{\"tagit\":[],\"tyyppi\":\"article\",\"attribuutit\":{\"author\":\"author2\"},\"avain\":\"article2\"}\n");
        teksti.equals(haluttu).shouldBe(true);
    }
}

scenario "Järjestelmään tallentuu uusi tyhjä viite, jos mitään tietoja ei anneta", {
    given 'Aletaan lisämään uutta viitettä', {
        //palautetaan tiedosto alkutilaan
    alkutila = new String("");
        try {
            kirjoittaja = new FileWriter(new File("test.json"));

            kirjoittaja.write(alkutila);
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        k = new Kontrolleri(new File("test.json"));

    }
    when 'Viitteelle ei anneta mitään tietoja', {
        
        k.hae(ViiteTyyppi.article, "");
        k.lisaaViite(new Viite(ViiteTyyppi.article));
        List<Viite> viitteet = k.hae(ViiteTyyppi.article, "");
        k.tallenna();
        
    }
    then 'Tyhjä viite tallentuu järjestelmään', {
        //tiedoston lukeminen stringiksi
        teksti = null;
        try {
            teksti = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(Files.readAllBytes(Paths.get("test.json")))).toString();
        } catch (IOException ex) {

        }
        haluttu = new String("{\"tagit\":[],\"tyyppi\":\"article\",\"attribuutit\":{},\"avain\":\"\"}\n");
        teksti.equals(haluttu).shouldBe(true);
    }
}
