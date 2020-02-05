import java.net.*;
import java.io.*;

//ivstypsa@laas.fr

public class Client_Udp extends Thread{
    private int port_dest;
    private User user_src;
    private User user_dest;
    private String addr_ip_dest="";
    private Message message_to_send;

    //Client permettant tout type d'envoi excepté les fichiers
    public Client_Udp(int init_port_dest,User init_user_src){
        this.port_dest = init_port_dest;
        this.user_src = init_user_src;
        this.user_dest = null;
    }



    public void run(){
    	
            try {
            	
            	//préparation à l'envoi
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(message_to_send);
                byte[] buff = baos.toByteArray();
                
                //On initialise la connexion côté client
                DatagramSocket client = new DatagramSocket();

                //Si le message a envoyé est du type BROADCAST ou DISCONNECT OU UPDATE
                //alors l'adresse IP devient celle du broadcast
                if (message_to_send.get_type().equals("BROADCAST") || message_to_send.get_type().equals("DISCONNECT") || message_to_send.get_type().contentEquals("UPDATE")) {
                	//test
                	System.out.println("Envoi en broadcast \n");
                	
                	addr_ip_dest = "255.255.255.255";
            
                	//Sinon elle redevient celle du destinataire
                }else if (message_to_send.get_type().equals("NORMAL")) {
                	
                	addr_ip_dest = user_dest.get_IP();
                	
                	//Test
                	System.out.println("adresse ip destinataire" + addr_ip_dest);
                }
                
                InetAddress adresse = InetAddress.getByName(addr_ip_dest);
                DatagramPacket packet = new DatagramPacket(buff, buff.length, adresse, port_dest);
                
                //On lui affecte les données à envoyer
                packet.setData(buff);
                
                //SI LE CLIENT EST NULL, ON LE GÈRE COTÉ INTERFACE_ACCUEIL lors du click sur le bouton d'envoi
                client.send(packet);   
                
                //Test
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
    
    //Méthode d'envoi en broadcast quand on vient de se connecter
    public void Send_Broadcast() {
    	//Message message_broadcast = new Message("BROADCAST",this.user_src, null, 0);
    	Message message_broadcast = new Message("BROADCAST",this.user_src, null);
		message_broadcast.set_data("Automatique");
		this.set_message(message_broadcast);
		this.run();
    }
    
    //Méthode d'envoi en broadcast quand le pseudo a changé
    public void Send_Update_Pseudo(User U_src) {
    	//Message message_broadcast = new Message("UPDATE",U_src, null, 0);
    	Message message_broadcast = new Message("UPDATE",U_src, null);
		message_broadcast.set_data("Changement Pseudo");
		this.set_message(message_broadcast);
		this.run();
    }
    
    
    //Lorsqu'on set le destinataire, on change l'IP de destination
    public void set_dest(User U) {
    	this.user_dest = U;
    	this.addr_ip_dest = U.get_IP();
    }
    
    public User get_dest() {
    	return this.user_dest;
    }

    public void set_message(Message m) {
    	this.message_to_send=m;
    }
}
