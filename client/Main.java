package main;
import client.*;

public class Main{
    public static void main(String[] args)throws Exception, ClassNotFoundException{

        try{
            Client client=new Client();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}