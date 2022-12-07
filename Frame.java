package frame;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.*;

public class Frame extends JFrame {
  private JPanel pan = new JPanel();
  private JButton btn = new JButton("image");
  private JButton btn2=new JButton("song");
  private JButton btn3=new JButton("video");

  public Frame(){
    this.setTitle("Animation");
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
          
          Client client=new Client();
          client.getClient();
        } catch (Exception ex) {
          // TODO: handle exception
        }

          

                }
    });

    btn2.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){


      try {

        Client client=new Client();
        client.getSongcliennt();
      
        
      } catch (Exception ex) {
        // TODO: handle exception
      }
        
              }
    });

    btn3.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){

        try {

          Client client=new Client();
          client.getVideoclient();
     
          
        } catch (Exception ex) {
          // TODO: handle exception
        }
              

                }
            });

  }       
}