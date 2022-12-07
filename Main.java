package main;

import frame.Frame;
import serveur.Serveur;
import client.*;

import java.io.FileReader;

import javax.swing.SwingUtilities;

public class Main{
    public static void main(String[] args){
     Frame frame=new Frame();

     try{
      Serveur serveur=new Serveur();
      //serveur.getServeur();
      //serveur.getVidserver();
      serveur.getSongserver();

      Client client=new Client();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                client.MediaPanel(args);
                }
            });
  }
  catch(Exception e){
      System.out.println(e);
  }
  
}     
    } 