
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
  private static FileInputStream fis = null;
  private static OutputStream os = null;
  private static Socket s=null;



    public void getServeur() throws IOException, ClassNotFoundException,InterruptedException {

        ServerSocket serverSocket=new ServerSocket(9000);
        s = serverSocket.accept();
        ///image////
        OutputStream outputStream=s.getOutputStream();
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
        ////video///
        
        s.close();

    }

    public  void getVidserver()
    {
        // Here we define Server Socket running on port 900
        try (
            ServerSocket serverSocket= new ServerSocket(9000)) {
              System.out.println("Server is Starting in Port 9000");
            // Accept the Client request using accept method
            s = serverSocket.accept();
            System.out.println("Connected");
            System.out.println(
              "Sending the File to the Client");
            dataInputStream = new DataInputStream(
            s.getInputStream());
            dataOutputStream = new DataOutputStream(
            s.getOutputStream());
            // Here we call receiveFile define new for that
            // file
            sendFile("C:\\Users\\finar\\Videos\\film\\thor.mp4");
 
            dataInputStream.close();
            dataOutputStream.close();
            s.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(String path)
    throws Exception
{
    int bytes = 0;
    // Open the File where he located in your pc
    File file = new File(path);
    fis= new FileInputStream(file);

    // Here we send the File to Server
    dataOutputStream.writeLong(file.length());
    // Here we  break file into chunks
    byte[] buffer = new byte[4 * 1024];
    while ((bytes = fis.read(buffer))
           != -1) {
      // Send the file to Server Socket 
      dataOutputStream.write(buffer, 0, bytes);
      dataOutputStream.flush();
    }
    // close the file here
    fis.close();
}

public final static String FILE_TO_SEND = "C:\\socket\\good.mp3"; 

public void getSongserver()throws IOException {

    ServerSocket servsock=new ServerSocket(9000);

    while (true) {
        System.out.println("Waiting...");
        s = servsock.accept();
        System.out.println("Accepted connection : " + s);
        // send file
        File myFile = new File (FILE_TO_SEND);
        fis = new FileInputStream(myFile);
        byte [] mybytearray =fis.readAllBytes();
        dataOutputStream = new DataOutputStream( s.getOutputStream());
        System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
        dataOutputStream .write(mybytearray);
        System.out.println("Done.");  
    }
  }


}