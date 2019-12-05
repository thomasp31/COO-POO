import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;

public class client_udp extends Thread{
    private int port_dest;

    public client_udp(int init_port_dest){
        this.port_dest = init_port_dest;
        start();
    }



    public void run(){
        int nbre = 0;

        while(true){
            Scanner input = new Scanner(System.in);
            String envoi = input.nextLine();

            byte[] buff = envoi.getBytes();   
            try {
            //On initialise la connexion côté client
                DatagramSocket client = new DatagramSocket();
                
                //On crée notre datagramme
                InetAddress adresse = InetAddress.getByName("127.0.0.1");
                DatagramPacket packet = new DatagramPacket(buff, buff.length, adresse, port_dest);
                
                //On lui affecte les données à envoyer
                packet.setData(buff);
                
                //On envoie au serveur
                client.send(packet);
                    
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }      

}
