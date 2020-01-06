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
    private String addr_ip_dest="";
    private Message message_to_send;

    public client_udp(int init_port_dest,User init_user_src,User init_user_dest){
        this.port_dest = init_port_dest;
        this.user_src = init_user_src;
        this.user_dest = init_user_dest;
        
        //start();
    }



    public void run(){
        int nbre = 0;

        //while(true){
        	
        	
       
        	//Message message = new Message("NORMAL",this.user_src,this.user_dest,0);
        	//System.out.println("Tapez votre message : ");
            //Scanner input = new Scanner(System.in);
            
            //String envoi = input.nextLine();
            //message.set_data(envoi);
            //message.set_date();
            
            
              
            try {
            	
            	
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(message_to_send);
                byte[] buff = baos.toByteArray();
                
                
                //oos.writeInt(1);
                //oos.writeObject(message);
                //oos.flush();
                
                
                
                //On initialise la connexion côté client
                DatagramSocket client = new DatagramSocket();
                
                //Affichage adresse ip de la machine destinataire
                System.out.println("adresse Ip du destinataire " + this.user_dest.get_local_ip());
                
                //On crée notre datagramme
                //InetAddress adresse = InetAddress.getByName(this.user_dest.get_local_ip());
                if (message_to_send.get_type().equals("BROADCAST")) {
                	System.out.println(" Envoi en broadcast \n");
                	addr_ip_dest = "255.255.255.255";
            
                }else if (message_to_send.get_type().equals("NORMAL")) {
                	addr_ip_dest = user_dest.get_IP();
                }
                
                InetAddress adresse = InetAddress.getByName(addr_ip_dest);
                DatagramPacket packet = new DatagramPacket(buff, buff.length, adresse, port_dest);
                
                //On lui affecte les données à envoyer
                packet.setData(buff);
                
                //On envoie au serveur
                client.send(packet);
                
                
                
                System.out.println("DONE SENDING \n");
                    
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        //}
        
        
    }      
    
    public void set_dest(User U) {
    	this.user_dest = U;
    	this.addr_ip_dest = U.get_IP();
    }

    public void set_message(Message m) {
    	this.message_to_send=m;
    }
    
}
