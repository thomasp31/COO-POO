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
	 private static int current_ID =0;
	 private int personnal_ID;
	 private String pseudo; 
	 private transient String login;
	 private transient String state; 
	 private int port_ecoute;
	 public transient ArrayList<User> Connected_Users = new ArrayList<User>();
	 private String ip_addr;
	 private ArrayList<Conversation> list_conversation = new ArrayList<Conversation>();
	 
	 
	 
	 public User(String init_pseudo,String init_login,String init_state, int init_port_ecoute) {
		 current_ID++;
		 this.personnal_ID=current_ID;
		 this.pseudo= init_pseudo;
		 this.login=init_login;
		 this.ip_addr = this.get_local_ip();
		 this.port_ecoute=init_port_ecoute;
	 }
	 
	 /*public void ListUpdate() {
		 //TODO
		 
	 }*/
	 
	 
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
	 public void ajouter_conversation(Conversation c) {
		 
		 list_conversation.add(c);
		 
	 }
	 
	 //retourne une conversation de l'user grace a son id
	 //probleme quand il trouve pas la conv (
	 
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
		 
		 
	 }
	 
}
