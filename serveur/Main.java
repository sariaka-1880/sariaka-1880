package main;
import serveur.*;

public class Main{
    public static void main(String[] args)throws Exception, ClassNotFoundException{
        
        try{
            Serveur serveur=new Serveur();
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
}