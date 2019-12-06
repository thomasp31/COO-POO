import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;

//ivstypsa@laas.fr

public class client_udp extends Thread{
    private int port_dest;
    private User user_src;
    private User user_dest;
    

    public client_udp(int init_port_dest,User init_user_src,User init_user_dest){
        this.port_dest = init_port_dest;
        this.user_src = init_user_src;
        this.user_dest = init_user_dest;
        
        start();
    }



    public void run(){
        int nbre = 0;

        while(true){
            Scanner input = new Scanner(System.in);
            String envoi = input.nextLine();
            Message message = new Message(user_src,user_dest,"NORMAL");
            message.set_data(envoi);
            
            
              
            try {
            	
            	
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                byte[] buff = baos.toByteArray();
                
                oos.writeObject(message);
                oos.flush();
                
                
                
            //On initialise la connexion côté client
                DatagramSocket client = new DatagramSocket();
                
                //On crée notre datagramme
                InetAddress adresse = InetAddress.getByName("127.0.0.1");
                DatagramPacket packet = new DatagramPacket(buff, buff.length, adresse, port_dest);
                
                //On lui affecte les données à envoyer
                packet.setData(buff);
                
                //On envoie au serveur
                client.send(packet);
                
                System.out.println("DONE SENDING");
                    
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
