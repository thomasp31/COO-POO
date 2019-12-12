import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;

public class server_udp extends Thread{
    public int port_local;

    public server_udp(int init_p_local){
        this.port_local= init_p_local;
        start();
    }

    public void run(){

        try {
          
            //Création de la connexion côté serveur, en spécifiant un port d'écoute
            DatagramSocket serv = new DatagramSocket(port_local);
            
            while(true){
                
                //On s'occupe de l'objet paquet
                byte[] buffer = new byte[100000];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                                
                //Cette méthode permet de récupérer le datagramme envoyé par le client
                //Elle bloque le thread jusqu'à ce que celui-ci ait reçu quelque chose.
                serv.receive(packet);
                
                //Test deserialization du message 
                ByteArrayInputStream baos = new ByteArrayInputStream(packet.getData());
                ObjectInputStream oos = new ObjectInputStream(baos);
                System.out.println("1 input stream ok ");
                Message m = (Message)oos.readObject();
                System.out.println("2 read Object ");
                System.out.println(m.get_data());
                System.out.println(m.get_type());
                //affichage du pseudo de la srce
                System.out.println(m.get_user_src().get_pseudo());
                //Test login a afficher
                System.out.println("Login de la source : " + m.get_user_src().get_login());
                
                
                //nous récupérons le contenu de celui-ci et nous l'affichons
                /*String str = new String(packet.getData());
                System.out.println("Message : Addresse " + packet.getAddress() 
                                + " sur le port " + packet.getPort() + " : ");
                System.out.println(str);*/
                
                //On réinitialise la taille du datagramme, pour les futures réceptions
                packet.setLength(buffer.length);     
                
                
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    }
}