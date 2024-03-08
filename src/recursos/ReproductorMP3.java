package recursos;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReproductorMP3 {

    private AudioInputStream audioInputStream;
    private Clip clip;

    public void reproducirSonido(String nombreSonido) {
        audioInputStream = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            audioInputStream = AudioSystem.getAudioInputStream(classLoader.getResource("recursos/" + nombreSonido));

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.start();
            clip.addLineListener(event -> {
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                    clip.close();
                }
            });
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        } finally {
            if (audioInputStream != null) {
                try {
                    audioInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pararSonido() {
        try {
            clip.close();
            audioInputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ReproductorMP3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
