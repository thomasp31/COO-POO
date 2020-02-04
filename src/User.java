import java.lang.reflect.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Enumeration;
import java.net.NetworkInterface; 
import java.util.*;
import java.io.Serializable;
import javax.swing.JList;

public class User implements Serializable{
	
	 private static  final  long serialVersionUID =  1L;
	 private int personnal_ID;
	 private String pseudo; 
	 private String login;
	 private transient String state; 
	 private int port_ecoute;
	 private int port_envoi;
	 //Chaque user a sa propre list d'user connecté
	 public transient ArrayList<User> Connected_Users = new ArrayList<User>();
	 private String ip_addr;
	 //Conversation inutile
	 //private ArrayList<Conversation> list_conversation = new ArrayList<Conversation>();
	 
	 
	 //Constructeur du User
	 public User(String init_pseudo,String init_login,String init_state, int init_port_ecoute,int init_port_envoi, int init_ID) {
		 this.personnal_ID=init_ID;
		 this.pseudo= init_pseudo;
		 this.login=init_login;
		 this.ip_addr = this.get_local_ip();
		 this.port_ecoute=init_port_ecoute;
		 this.port_envoi=init_port_envoi;
	 }
	 
	 //Permet d'afficher le list des users connectés
	 //Test uniquement
	 public void display_List() {
		 for(User u : this.Connected_Users) {
			 System.out.println("List des users connectés : " + u.get_pseudo()+ "\n");
		 }
	 }
	 
	 //Permet de tester si le User est déjà dans la list des users connectés ou non
	 public boolean isInside(String l) {
		 boolean result = false;
		 if (this.Connected_Users.isEmpty()!= true) {
			 for(User u : this.Connected_Users) {
				 System.out.println(u.get_login());
				 
				 if (u.get_login().equals(l)==true) {
					 result = true;
				 }
			 }
		 }
		 return result;
	 }
	 
	 //permet de remove un user mais non utilisé
	 public boolean remove_user(User u) {
		 boolean result = false;
		 if (this.Connected_Users.isEmpty()!= true) {
			 for(User user : this.Connected_Users) {
				 if (user.get_pseudo().equals(u.get_pseudo())) {
					 this.Connected_Users.remove(user);
				 }
			 }
		 }
		 return result;
	 }
	 
	 //Retourne l'index d'un user dans la liste des conecté
	 //Permet dans le seveur de le supprimer lorsqu'il se déconnecte
	 public int index_of(User user) {
		 int index = 0;
		 int res=0;
		 if (this.Connected_Users.isEmpty()==false) {
			 for (User u : this.Connected_Users) {
				 if (u.get_id() == user.get_id()) {
					 res = index;
				 }
				 index ++;
			 }
		 }
		 return res;
	 }
	 
	 
	 public int get_port_ecoute() {
		 return this.port_ecoute;
	 }
	 
	 public void set_port_ecoute(int p) {
		 this.port_ecoute=p;
	 }
	 
	 public String get_IP() {
		 return this.ip_addr;
	 }
	 
	 public int get_id() {
		 return this.personnal_ID;
	 }
	 
	 public String get_pseudo() {
		 return this.pseudo;
	 }
	 
	 public String get_login() {
		 return this.login;
	 }
	 
	 public String get_state() {
		 return this.state;
	 }
	 
	 public void set_pseudo(String new_pseudo) {
		this.pseudo = new_pseudo;
	 }
	 
	 public void set_login(String new_login) {
		 this.login = new_login;
	 }
	 
	 public void set_state(String new_state) {
		 this.state = new_state;
	 }
	 
	 //Méthode renvoyant l'adresse ip de la machine
	 public String get_local_ip() {
		 String local_ip_address ="";
		 try{
	            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
	            while (e.hasMoreElements()){
	                Enumeration<InetAddress> i = e.nextElement().getInetAddresses();
	                while (i.hasMoreElements()){
	                    InetAddress a = i.nextElement();
	                    if (a.isSiteLocalAddress()==true) {
	                    	local_ip_address = a.getHostAddress();
	                    }
	                 
	                    //System.out.println(a.getHostName()+" -> "+a.getHostAddress()+
	                     //   "\n\t isloopback? "+a.isLoopbackAddress()+
	                       // "\n\t isSiteLocalAddress? "+a.isSiteLocalAddress()+
	                        //"\n\t isIPV6? "+(a instanceof Inet6Address));
	                }
	            }
	        }catch(Exception ex){
	            System.out.println("erreurEnumeration");
	        }
		 return local_ip_address;
	 }
	  
	 //permet d'ajouter une conversation a la liste des conversations actives de l'user
	 //inutile car finalement pas utilisé
	 /*
	 public void ajouter_conversation(Conversation c) {
		 list_conversation.add(c);
	 }
	 */
	 //retourne une conversation de l'user grace a son id
	 //Inutile car Conversation pas utilisé
	 /*
	 public Conversation get_conversation_by_id(int id_conv) {
		 
		 Conversation returned_conversation = new Conversation(666,null,null);
		 //System.out.println("get_by_id 1... \n");
		 
		 if (this.list_conversation==null) {
				System.out.println("conversation non existante dans la liste"); 
				//System.out.println("get_by_id 2... \n");
		 }
		 
		 else { 
			for(int i = 0 ; i < this.list_conversation.size(); i++) {
				 if (this.list_conversation.get(i).get_id_conv()== id_conv) {
					 returned_conversation = list_conversation.get(i);
				 }
			 }
		 }
		 return returned_conversation;
		 
		 
	 }*/

	public int get_port_envoi() {
		return port_envoi;
	}

	public void set_port_envoi(int port_envoi) {
		this.port_envoi = port_envoi;
	}
}
