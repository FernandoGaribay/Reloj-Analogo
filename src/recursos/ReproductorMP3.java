package recursos;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReproductorMP3 {

    private Clip clip;

    public ReproductorMP3() {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(ReproductorMP3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reproducirSonido(String nombreSonido) {
        try {
            if (!clip.isOpen() || !clip.isRunning()) {
                AudioInputStream audioInputStream = obtenerAudioInputStream(nombreSonido);
                if (audioInputStream != null) {
                    clip.open(audioInputStream);
                    clip.start();
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                }
            }
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private AudioInputStream obtenerAudioInputStream(String nombreSonido) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            return AudioSystem.getAudioInputStream(classLoader.getResource("recursos/" + nombreSonido));
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void pararSonido() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
