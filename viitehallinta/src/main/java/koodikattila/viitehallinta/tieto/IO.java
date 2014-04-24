package koodikattila.viitehallinta.tieto;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class IO {

    private final Parseri parseri;

    public IO(Parseri parseri) {
        this.parseri = parseri;
    }

    public Viitekokoelma lataa(File tiedosto) throws IOException {
        varmistaTiedosto(tiedosto);
        return parseri.viitekokoelmaksi(new String(Files.readAllBytes(tiedosto.toPath()), StandardCharsets.UTF_8));
    }

    public void tallenna(File tiedosto, Viitekokoelma viitteet) throws IOException {
        varmistaTiedosto(tiedosto);
        Files.write(tiedosto.toPath(), parseri.tekstiksi(viitteet).getBytes(StandardCharsets.UTF_8));
    }

    private void varmistaTiedosto(File tiedosto) throws IOException {
        File vanhempi = tiedosto.getParentFile();
        if (vanhempi != null) {
            vanhempi.mkdirs();
        }
        tiedosto.createNewFile();
    }

    public Parseri getParseri() {
        return parseri;
    }
}
