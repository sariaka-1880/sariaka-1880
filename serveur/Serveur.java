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
        s = serverSocket.accept();
        System.out.println("connecté");

        ObjectInputStream objinp=new ObjectInputStream(s.getInputStream());
        String input=(String)objinp.readObject();
        System.out.println(input);

        if(input.equals("image")){
          ObjectOutputStream objout=new ObjectOutputStream(s.getOutputStream());
          objout.writeObject("image");
          this.getServeur(s);
        }
        if(input.equals("mp3")){
          ObjectOutputStream objout=new ObjectOutputStream(s.getOutputStream());
          objout.writeObject("mp3");
          this.getSongserver(s);
        }
        if(input.equals("video")){
          ObjectOutputStream objout=new ObjectOutputStream(s.getOutputStream());
          objout.writeObject("mp4");
          this.getVidserver(s);
        }

    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.getMessage());
    }
    
  }
 
  public void getServeur(Socket s) throws IOException, ClassNotFoundException,InterruptedException {
  
        ///image////
        System.out.println("Accepted connection : " + s);
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
           

  }

  public void getVidserver(Socket s){
        
        try {
            System.out.println("Accepted connection : " + s);
            System.out.println("Connected");
            System.out.println( "Sending the video to the Client");
            dataInputStream = new DataInputStream(s.getInputStream());
            dataOutputStream = new DataOutputStream(s.getOutputStream());
      
            sendFile("C:\\Users\\finar\\Videos\\film\\thor.mp4");
 
            dataInputStream.close();
            dataOutputStream.close();
       
        }
        catch (Exception e) {
            e.printStackTrace();
        }
  }

  private static void sendFile(String path)throws Exception{
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

  public void getSongserver(Socket s)throws IOException {

    while (true) {
        System.out.println("Accepted connection : " + s);
        File myFile = new File (FILE_TO_SEND);
        fis = new FileInputStream(myFile);
        byte [] mybytearray =fis.readAllBytes();
        dataOutputStream = new DataOutputStream( s.getOutputStream());
        System.out.println("sending file");
        dataOutputStream .write(mybytearray);
        System.out.println("Done.");  
    }
  }


}