package main;
import javax.swing.JFrame;

import serveur.*;
public class Main extends JFrame{
    public static void main(String[] args)throws Exception, ClassNotFoundException{
        try{
            Serveur serveur=new Serveur();
            serveur.getServeur();
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
}