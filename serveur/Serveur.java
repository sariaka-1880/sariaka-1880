package serveur;
import java.io.*;
import java.net.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Serveur {

  private static DataOutputStream dataOutputStream = null;
  private static DataInputStream dataInputStream = null;
  private static FileInputStream fis = null;
  private static Socket s=null;
  
  public Serveur(){

    try {
        ServerSocket serverSocket=new ServerSocket(9000);
        System.out.println("waiting........");

        s = serverSocket.accept();
        System.out.println("connecté");

        ObjectInputStream objinp=new ObjectInputStream(s.getInputStream());
        String input=(String)objinp.readObject();

        System.out.println(input);

        if(input.equals("send me image")){
          ObjectOutputStream objout=new ObjectOutputStream(s.getOutputStream());
          objout.writeObject("sending image");
          this.SendImgServer(s);
        }
        if(input.equals("send me mp3")){
          ObjectOutputStream objout=new ObjectOutputStream(s.getOutputStream());
          objout.writeObject("sending mp3");
          this.SendSongServer(s);
        }
        if(input.equals("send me video")){
          ObjectOutputStream objout=new ObjectOutputStream(s.getOutputStream());
          objout.writeObject("sending video");
          this.SendVidServer(s);
        }

    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.getMessage());
    }
    
  }
 
  public void SendImgServer(Socket s) throws IOException, ClassNotFoundException,InterruptedException {
  
        ///image////
        System.out.println("Accepted connection : " + s);
        OutputStream outputStream=s.getOutputStream();
        BufferedImage image=ImageIO.read(new File("sary.jpg"));
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ImageIO.write(image, "jpg",byteArrayOutputStream );
        byte[]size =ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        outputStream.write(size);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        System.out.println("Sending image to the client");
        Thread.sleep(120000);
        
  }

  public void SendVidServer(Socket s){
        
        try {
            System.out.println("Accepted connection : " + s);
            System.out.println( "Sending the video to the Client");
            dataInputStream = new DataInputStream(s.getInputStream());
            dataOutputStream = new DataOutputStream(s.getOutputStream());
      
            sendFile("C:\\Users\\finar\\Downloads\\Video\\dancing.mp4");
 
            dataInputStream.close();
            dataOutputStream.close();
       
        }
        catch (Exception e) {
            e.printStackTrace();
        }
  }

  private static void sendFile(String path)throws Exception{
    //video
        int bytes = 0;
        File file = new File(path);
        fis= new FileInputStream(file);
        dataOutputStream.writeLong(file.length());
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fis.read(buffer))!= -1) {
          dataOutputStream.write(buffer, 0, bytes);
          dataOutputStream.flush();
        }
      
        fis.close();
  }

public final static String FILE_TO_SEND = "C:\\socket\\good.mp3"; 

  public void SendSongServer(Socket s)throws IOException {
//song 
    while (true) {
        System.out.println("Accepted connection : " + s);
        File myFile = new File (FILE_TO_SEND);
        fis = new FileInputStream(myFile);
        byte [] mybytearray =fis.readAllBytes();
        dataOutputStream = new DataOutputStream( s.getOutputStream());
        System.out.println("sending song to the ");
        dataOutputStream .write(mybytearray);
      
    }
  }


}