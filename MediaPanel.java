

import com.sun.jna.NativeLibrary;


import uk.co.caprica.vlcj.binding.support.runtime.RuntimeUtil;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.awt.*;

public class MediaPanel {
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MediaPanel(args);
            }
        });
    }

    public static void chargerLibrairie() {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcCoreLibraryName(),"C:/Program Files/VideoLAN/VLC");
    }

    private MediaPanel(String[] args) {
        EmbeddedMediaPlayerComponent component = new EmbeddedMediaPlayerComponent();

        JFrame frame = new JFrame("Enga anie ka hety");
        frame.setContentPane(component);
        frame.setLocation(100, 100);
        frame.setSize(1050, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Canvas c = new Canvas();
        c.setBackground(Color.black);
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(c,BorderLayout.CENTER);
        frame.add(p,BorderLayout.CENTER);
        component.mediaPlayer().media().play("mercredi.mp4");

    }
}