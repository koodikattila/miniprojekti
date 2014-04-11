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

description 'Järjestelmään syötetyistä viitteistä voi generoida bibtex-tiedoston'

scenario "Viitteistä voi generoida uuden tiedoston", {
    given 'Järjestelmään on lisätty viitteitä', {
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
    when 'Käyttäjä haluaa generoida bibtex-tiedoston', {
        k.talletaBibtexTiedostoon(new File("test.bibtex"));
    }
    then 'Tiedosto generoidaan käyttäjän valitsemaan sijaintiin', {
        
        String haluttu = new String("@article{avain1,\n"
        + " author       = \"author1\",\n"
        + " journal      = \"journal1\",\n"
        + " title        = \"title1\",\n"
        + " year         = \"year1\"\n"
        + "}\n"
        + "@article{avain2,\n"
        + " author       = \" author2\",\n"
        + " journal      = \" journal2\",\n"
        + " title        = \" title2\",\n"
        + " year         = \" year2\"\n"
        + "}\n"
        + "@book{avain3,\n"
        + " author       = \" author3\",\n"
        + " publisher    = \" publisher3\",\n"
        + " title        = \" title3 \",\n"
        + " year         = \" year3\"\n"
        + "}\n");

        //tiedoston lukeminen stringiksi
        String teksti = null;
        try {
            teksti = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(Files.readAllBytes(Paths.get("test.bibtex")))).toString();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        teksti.shouldEqual(haluttu);
    }
}