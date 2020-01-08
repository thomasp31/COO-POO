import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import javax.swing.JList;
import java.util.*;

public class server_udp extends Thread{
    public int port_local;
    public User ulocal;
    public JTextArea display_zone;
    //public JList<User> Connected_Users = new JList<User>();
    public DefaultListModel DLM; //permet de relier au model dans INterface_Accueil
    
    public server_udp(int init_p_local,User init_u_source, JTextArea jta, DefaultListModel model){
        this.port_local= init_p_local;
        this.ulocal = init_u_source; 
        this.display_zone = jta;
        //this.Connected_Users=Jl;
        this.DLM=model;
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
                Message m = (Message)oos.readObject();
                //System.out.println("message recu : " + m.get_data());
                //System.out.println("TYPE message : " +  m.get_type());
                
                //affichage du pseudo de la srce
                // ajoute le message dans la conversation voulu en regardant l'id_conv du message
                
                // remarque : ON A PAS ENCORE PRIS EN COMPTE LE CAS OU C'EST LE PREMIER MESSAGE ET OU LA CONVERSATION
                // N'A PAS ENCORE ETE CREE
                
                
                if (m.get_type().equals("NORMAL")) {
                	display_zone.append("Message reçu : " + m.get_data() + "\n");
                	display_zone.append(m.get_date() + "\n");
                	User user_dest = m.get_user_dst();
                	//System.out.println("Pseudo du destinataire: " + m.get_user_dst().get_pseudo());
                	Conversation conv = ulocal.get_conversation_by_id(m.get_id_conv());
                	conv.ajouter_message(m);
                	//System.out.println("ID de la conversation : " + conv.get_id_conv());
                	//System.out.println("Date de l'envoi du message : " + m.get_date());
                	
                }else if (m.get_type().equals("BROADCAST")){
                	//user_src du message reçu correspond au destinataire du message a renvoyer
                	
                	Message mes_reponse = new Message("REP_BROADCAST",ulocal,m.get_user_src(),0);
                	mes_reponse.set_data("Automatique");
                	ByteArrayOutputStream baosBroadcast = new ByteArrayOutputStream();
                    ObjectOutputStream oosBroadcast = new ObjectOutputStream(baosBroadcast);
                    oosBroadcast.writeObject(mes_reponse);
                    byte[] buffBroadcast = baosBroadcast.toByteArray();
                    DatagramSocket client = new DatagramSocket();
                    InetAddress adresse = InetAddress.getByName(m.get_user_src().get_IP());
                    DatagramPacket packetBroadcast = new DatagramPacket(buffBroadcast, buffBroadcast.length, adresse, m.get_user_src().get_port_ecoute());
                    
                    
                    packet.setData(buffBroadcast);
                    client.send(packetBroadcast);
                	if (ulocal.Connected_Users.contains(m.get_user_src())==false) {
                		ulocal.Connected_Users.add(m.get_user_src());
                		this.DLM.addElement(m.get_user_src().get_pseudo());
                	}
                    
                    System.out.println("Broadcast recu \n");
                    
                }else if (m.get_type().equals("REP_BROADCAST")){
                	User user_src_RepBrdcst = m.get_user_src();
                	System.out.println("Pseudo de la source du message reçu : " + m.get_user_src().get_pseudo());
                	System.out.println("type du message : " + m.get_type());
                	//Conversation conv = serveur_u_source.get_conversation_by_id(m.get_id_conv());
                	//conv.ajouter_message(m);
                	//System.out.println("ID de la conversation : " + conv.get_id_conv());
                	if (ulocal.Connected_Users.contains(m.get_user_src())==false) {
                		ulocal.Connected_Users.add(m.get_user_src());
                		this.DLM.addElement(m.get_user_src().get_pseudo());
                	}
                	
                	System.out.println("Rep broadcast recu \n");
                	
                	
                	
                }
                
                
                
                //Test login a afficher
                System.out.println("Pseudo de la source : " + m.get_user_src().get_pseudo() + "\n");
                
                
                //On réinitialise la taille du datagramme, pour les futures réceptions
                //packet.setLength(buffer.length);     
                
                
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