package client;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;

public class Client {
  
  private static DataOutputStream dataOutputStream = null;
  private static DataInputStream dataInputStream = null;

    public void getClient()throws IOException, ClassNotFoundException{

        Socket socket=null;
        socket = new Socket("localhost",9000);
        InputStream inputStream=socket.getInputStream();
        System.out.println("Reading"+System.currentTimeMillis());
        System.out.println("image recu");
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
}