package client;
import play.Sound;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.*;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;


import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;


public class Client extends JFrame{


    private JButton btn = new JButton("image");
    private JButton btn2=new JButton("mp3");
    private JButton btn3=new JButton("video");
    private JPanel pan = new JPanel();
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    Socket soc=null;


    public void getClient(Socket soc)throws IOException, ClassNotFoundException{

        InputStream inputStream=soc.getInputStream();
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

    public void getVideoclient(Socket soc){
        try { 
            dataInputStream = new DataInputStream(soc.getInputStream());
            dataOutputStream = new DataOutputStream(soc.getOutputStream());
            receiveFile("dancing.mp4");
            dataInputStream.close();
            dataInputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void receiveFile(String fileName)throws Exception
    {
        int bytes = 0;
        FileOutputStream fileOutputStream= new FileOutputStream(fileName);
        long size= dataInputStream.readLong(); 
        byte[] buffer = new byte[4 * 1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0,(int)Math.min(buffer.length, size)))!= -1) {
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes; 
        }
        System.out.println("File is Received");
        fileOutputStream.close();
    }

        
        public final static int FILE_SIZE = 107347149; 
                                                     
    public void getSongcliennt (Socket soc) throws IOException{

        try {
        
            byte [] mybytearray  = new byte [FILE_SIZE];
      
            InputStream is = soc.getInputStream();
            DataInputStream data=new DataInputStream(is);

            JFrame frame=new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setSize(100,100);
            JLabel jLabel=new JLabel();
            jLabel.setText("Playing the song");
            frame.add(jLabel);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  
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
        component.mediaPlayer().media().play("dancing.mp4");
    
    }

    public Client(){
            
        try {

            soc=new Socket("localhost",9000);
            this.setTitle("mp4");
            this.setSize(300, 150);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);
            //Ajout du bouton à notre content pane
            pan.add(btn);
            pan.add(btn2);
            pan.add(btn3);
            this.setContentPane(pan);
            this.setVisible(true);
        
            btn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    try {
                    
                        ObjectOutputStream objout=new ObjectOutputStream(soc.getOutputStream());
                        objout.writeObject("image");

                    } catch (Exception ex) {
                    }
                }
            });

            btn2.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    try {
                
                        ObjectOutputStream objout=new ObjectOutputStream(soc.getOutputStream());
                        objout.writeObject("mp3");
            
                    } catch (Exception ex) {
                    }
                }
            });

            btn3.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    try {
                
                        ObjectOutputStream objout=new ObjectOutputStream(soc.getOutputStream());
                        objout.writeObject("video"); 

                    } catch (Exception ex) {
                    }
                }
            });

                ObjectInputStream objinp=new ObjectInputStream(soc.getInputStream());
                String input=(String)objinp.readObject();
                System.out.println(input);

                if(input.equals("image")){
                    getClient(soc);
                }if(input.equals("mp3")){
                    getSongcliennt(soc);
                }if(input.equals("mp4")){
                    getVideoclient(soc);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                        String[] agrs=null;
                        MediaPanel(agrs);
                        }
                    });
           
                }
        
                } catch (Exception e) {
                    // TODO: handle exception
                }
    }

}