
package client;
import play.Sound;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.awt.image.BufferedImage;


import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.binding.lib.LibVlc;
import uk.co.caprica.vlcj.binding.support.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.fullscreen.windows.Win32FullScreenStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
public class Client {
  
  private static DataOutputStream dataOutputStream = null;
  private static DataInputStream dataInputStream = null;

  public Socket getconecction()throws IOException, ClassNotFoundException{
    Socket socket=null;
    return socket = new Socket("localhost",9000);

  }

    public void getClient()throws IOException, ClassNotFoundException{

       
        InputStream inputStream=this.getconecction().getInputStream();
        System.out.println("Reading"+System.currentTimeMillis());
        System.out.println("File is Received");
        byte[] sizear=new byte[4];
        inputStream.read(sizear);
        int size=ByteBuffer.wrap(sizear).asIntBuffer().get();
        byte[] imagear=new byte[size];
        inputStream.read(imagear);
        BufferedImage image=ImageIO.read(new ByteArrayInputStream(imagear));
        ImageIcon imageIcon=new ImageIcon(image);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(500,500);
        JLabel jLabel=new JLabel();
        jLabel.setIcon(imageIcon);
        frame.add(jLabel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

  public void getVideoclient(){
        // Create Client Socket connect to port 900
        try {
            
            
          dataInputStream = new DataInputStream(
                this.getconecction().getInputStream());
            dataOutputStream = new DataOutputStream(
                this.getconecction().getOutputStream());
    
          // Call receive File Method
          receiveFile(
                "C:\\Intel\\thore.mp4");
 
            dataInputStream.close();
            dataInputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void receiveFile(String fileName)
        throws Exception
    {
        int bytes = 0;
        FileOutputStream fileOutputStream
            = new FileOutputStream(fileName);
 
        long size
            = dataInputStream.readLong(); // read file size
        byte[] buffer = new byte[4 * 1024];
        while (size > 0
               && (bytes = dataInputStream.read(
                       buffer, 0,
                       (int)Math.min(buffer.length, size)))
                      != -1) {
            // Here we write the file using write method
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes; // read upto file size
        }
        // Here we received file
        System.out.println("File is Received");
        fileOutputStream.close();
    }

        public final static String FILE_TO_RECEIVED = "C:\\Intel\\see.mp3";  
        public final static int FILE_SIZE = 107347149; 
                                                     
        public void getSongcliennt () throws IOException{

    
          try {
        
      
            byte [] mybytearray  = new byte [FILE_SIZE];
      
            InputStream is = this.getconecction().getInputStream();
            DataInputStream data=new DataInputStream(is);
  
      
            while(true){
                data.read(mybytearray,0,FILE_SIZE);
                System.out.println("playing the song ...");
                Thread pay=new Thread(new Sound(mybytearray));
                pay.start();
                play(mybytearray);
             
            } 
      
          }catch(Exception e){
            System.out.println(e.getMessage());
          }
      
        }

        public static void play(byte[] data) throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
            Player player = new Player(in);
            player.play();
        }

        public void  MediaPanel(String[] args) {
            EmbeddedMediaPlayerComponent component = new EmbeddedMediaPlayerComponent();
    
            JFrame frame = new JFrame("video player");
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
            component.mediaPlayer().media().play("C:\\Intel\\thore.mp4");
    
        }
}