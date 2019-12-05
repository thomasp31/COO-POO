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
                
                //On s'occupe maintenant de l'objet paquet
                byte[] buffer = new byte[8192];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                                
                //Cette méthode permet de récupérer le datagramme envoyé par le client
                //Elle bloque le thread jusqu'à ce que celui-ci ait reçu quelque chose.
                serv.receive(packet);
                
                //nous récupérons le contenu de celui-ci et nous l'affichons
                String str = new String(packet.getData());
                System.out.println("Message : Addresse " + packet.getAddress() 
                                + " sur le port " + packet.getPort() + " : ");
                System.out.println(str);
                
                //On réinitialise la taille du datagramme, pour les futures réceptions
                packet.setLength(buffer.length);
                                
                
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}