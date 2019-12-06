import java.lang.reflect.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Enumeration;
import java.net.NetworkInterface; 
import java.util.*;
import java.io.Serializable;

public class User implements Serializable{
	
	 private static  final  long serialVersionUID =  1L;
	 private static int current_ID =0;
	 private int personnal_ID;
	 private String pseudo; 
	 private String login;
	 private String state; 
	 //private ArrayList<User> Connected_Users = new ArrayList<User>();
	 private String ip_addr;
	 
	 
	 public User(String init_pseudo,String init_login,String init_state) {
		 current_ID++;
		 this.personnal_ID=current_ID;
		 this.pseudo= init_pseudo;
		 this.login=init_login;
		 this.ip_addr = this.get_local_ip();
		 
	 }
	 
	 public void ListUpdate() {
		 //TODO
		 
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
	 
	 
	 
	 
	 
}
