package serveur;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.imageio.ImageIO;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.awt.image.BufferedImage;
import java.lang.*;

public class Serveur {

  private static DataOutputStream dataOutputStream = null;
  private static DataInputStream dataInputStream = null;
  private static Socket s=null;

    public void getServeur() throws IOException, ClassNotFoundException,InterruptedException {

        ServerSocket serverSocket=new ServerSocket(9000);
        Socket socket=null;
        socket = serverSocket.accept();
        OutputStream outputStream=socket.getOutputStream();
        BufferedImage image=ImageIO.read(new File("C:/img/sary.jpg"));
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ImageIO.write(image, "jpg",byteArrayOutputStream );
        byte[]size =ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        outputStream.write(size);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        System.out.println("Sending image......");
        System.out.println("Flushed"+System.currentTimeMillis());
        Thread.sleep(120000);
        System.out.println("Closing"+System.currentTimeMillis());
        socket.close();


    }
}