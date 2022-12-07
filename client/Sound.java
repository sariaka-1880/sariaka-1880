package play;

import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Sound  implements Runnable{


    byte[] taille;

    public Sound(byte[] taille) {
        this.taille = taille;
    }

    @Override
    public void run() {
        try {
            lire();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void lire() throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException {
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(this.taille));
        Player player = new Player(data);
        player.play();
    }

}       