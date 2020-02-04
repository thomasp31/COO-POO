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
    public JList JLUsers;
    public DefaultListModel DLM; //permet de relier au model dans INterface_Accueil
    public User selected_user;
    
    //Serveur permettant la réception de tous types de messages excepté les fichiers
    public server_udp(int init_p_local,User init_u_source, JTextArea jta, DefaultListModel model, JList JL, User init_selected_user){
        this.port_local= init_p_local;
        this.ulocal = init_u_source; 
        //On récupère la zone d'affichage des messages pour y ajouter ce qu'on reçoit
        this.display_zone = jta;
        //idem pour le model, il permettra la modification de la list des gens connecté lors de la réception d'un BROACAST de connexion ou déconnexion
        this.DLM=model;
        this.JLUsers=JL;
        this.selected_user = init_selected_user;
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
                
                //Deserialization du message 
                ByteArrayInputStream baos = new ByteArrayInputStream(packet.getData());
                ObjectInputStream oos = new ObjectInputStream(baos);
                Message m = (Message)oos.readObject();
                
                //SI message normal et qu'un user parmis les users connectés est bien selectioné(=> on pourra afficher le message)
                if (m.get_type().equals("NORMAL") && selected_user != null) {
                	
                	System.out.println("selected user");
                	//Si le message reçu vient de la personne sélectionnée sur la conversation courante on l'affiche
                	if (selected_user.get_login().equals(m.get_user_src().get_login())){
                    	display_zone.append("Message reçu : " + m.get_data() + "\n");
                    	display_zone.append(m.get_date() + "\n \n");                   	
                	}
                	
                	//Cette ligne est inutile
                	User user_dest = m.get_user_dst();
               
                	
                	//Si on reçoit un BROADCAST, alors on répond que nous aussi on est connecté
                }else if (m.get_type().equals("BROADCAST")){
                	//user_src du message reçu correspond au destinataire du message a renvoyer
                	if(m.get_user_src().get_login()!=ulocal.get_login()) {
	                	System.out.println("avant if BROADCAST ");
	                	//On prépare un message du type REP_BROADCAST
	                	Message mes_reponse = new Message("REP_BROADCAST",ulocal,m.get_user_src(),0);
	                	mes_reponse.set_data("Automatique");//inutile mais avait généré une erreur si pas de texte
	                	
	                	//On prépare le message de réponse à envoyer
	                	ByteArrayOutputStream baosBroadcast = new ByteArrayOutputStream();
	                    ObjectOutputStream oosBroadcast = new ObjectOutputStream(baosBroadcast);
	                    oosBroadcast.writeObject(mes_reponse);
	                    byte[] buffBroadcast = baosBroadcast.toByteArray();
	                    DatagramSocket client = new DatagramSocket();
	                    
	                    //On envoie la réponse uniquement à la personne ayant envoyé le broadcast
	                    InetAddress adresse = InetAddress.getByName(m.get_user_src().get_IP());
	                    DatagramPacket packetBroadcast = new DatagramPacket(buffBroadcast, buffBroadcast.length, adresse, m.get_user_src().get_port_ecoute());
	                    
	                    //le serveur envoie la réponse
	                    packet.setData(buffBroadcast);
	                    client.send(packetBroadcast);
	                    
	                    //Lignes suivantes uniquement pour tester
	                    System.out.println("avant if BROADCAST ");
	                    ulocal.display_List();
	                    
	                    //Si le User ayant envoyé le broadcats n'est pas dans la liste des users déjà connectés alors on le rajoute à la liste des Users
	                	if (ulocal.isInside(m.get_user_src().get_login())!= true && m.get_user_src().get_login()!=ulocal.get_login()) {
	                		// on rajoute l'utilisateur src du message dans la liste des utilisateurs connectés
	                		//On l'ajoute à l'arrayList des users connectés de l'utilisateur local
	                		ulocal.Connected_Users.add(m.get_user_src());
	                		System.out.println("mise a jour broadcast list 1");
	                		//Et on l'ajoute dans le model de la JList pour mettre à jour l'affichage
	                		this.DLM.addElement(m.get_user_src().get_pseudo());
	                	}
	                	
	                	//Test une fois de plus
	                	System.out.println("après if BROADCAST ");
	                    ulocal.display_List();
                	}
                	
                	//ulocal.display_List();
                    
                    System.out.println("Broadcast recu \n");
                    
                    //Lorsque l'on reçoit une réponse de broadcast, on ajoute l'utilisateur si il n'était pas déjà dans la liste
                }else if (m.get_type().equals("REP_BROADCAST")){
                	
                	User user_src_RepBrdcst = m.get_user_src();
                	
                	//Quelques messages de tests
                	System.out.println("Pseudo de la source du message reçu : " + m.get_user_src().get_pseudo());
                	System.out.println("type du message : " + m.get_type());
                	System.out.println("avant if REPBROADCAST ");
                    ulocal.display_List();
                    
                    //Si il n'est pas déjà dans liste users connectés, on le rajoute
                	if (ulocal.isInside(m.get_user_src().get_login())!= true && m.get_user_src().get_login()!=ulocal.get_login()) {
                		ulocal.Connected_Users.add(m.get_user_src());
                		this.DLM.addElement(m.get_user_src().get_pseudo());
                		System.out.println("mise a jour broadcast list 2");
                	}
                	
                	//Tests
                	System.out.println("Rep broadcast recu \n"); 	
                	System.out.println("après if REPBROADCAST ");
                    ulocal.display_List();
                    
                    //Si on reçoit un message de type UPDATE, cela signifie qu'un user a changé son pseudo, donc on le met à jour
                }else if(m.get_type().equals("UPDATE")) {
                	//ON récupère l'index du user dont on vient de recevoir le message dans l'arrayList des uses connectés
                	int index_connected_user = ulocal.index_of(m.get_user_src());
                	//Grâce à cet index, on récupère l'ancien pseudo de ce user ayant changé de pseudo
                	int index_model = DLM.indexOf(ulocal.Connected_Users.get(index_connected_user).get_pseudo());
                	//Et on met à jour dans le model pour changer le pseudo dans la JList des Users conectés
                	DLM.set(index_model,m.get_user_src().get_pseudo());
                	//Puis on modifie le pseudo dans l'ArrayList des Users connectés propre au user local
                	ulocal.Connected_Users.get(index_connected_user).set_pseudo(m.get_user_src().get_pseudo());
                	
                	//Test
                	System.out.println("reçu nouveau pseudo pour :" + m.get_user_src().get_login() + " new pseudo = " + m.get_user_src().get_pseudo());
                	
                	
                	//Lorsque quelqu'un se déconnecte, on le supprime de l'arrayList et la JList des users connectés
                }else if (m.get_type().equals("DISCONNECT")) {
                	//Test
	            	System.out.println("User disconnected : " + m.get_user_src().get_pseudo());
	            	
	            	//On désélectionne le user au cas ou on serait en train de lui parler au moment ou il se déconnecte
	            	//permet d'éviter d'avoir la JList pointant sur un pseudo ayant disparu suite à une déconnexion
	            	JLUsers.setSelectedIndex(0);
	            	
	            	//on récupère l'index de la personne venant de se décinnecter dans l'arraylist du user_local
	            	int i = ulocal.index_of(m.get_user_src());
	            	//Test
	            	System.out.println("index : " + i);
	            	//On supprime cet utilisateur de l'arayList du user local
	            	ulocal.Connected_Users.remove(i);
	            	
	            	//On supprime aussi dans le model relié à la JList pour mettre à jour l'affichage des users connectés
	            	this.DLM.removeElement(m.get_user_src().get_pseudo());
	            	
	            	//Test
	            	ulocal.display_List();
	            }
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