import java.net.*;

import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;


public class main{

    public static void main(String args[]){
    	
    	User Pierre = new User("Pierrot","pcloury","BG",0);
    	User Thomas = new User("Toto","tpelous","Ultra-BG",0);
    	Conversation convpierre = new Conversation(0,Thomas,Pierre);
    	Thomas.ajouter_conversation(convpierre);
        
    	int port_ecoute = Integer.parseInt(args[0]);
        int port_dest = Integer.parseInt(args[1]);
        
        //server_udp server = new server_udp(port_server,Thomas);
        
        if (Integer.parseInt(args[2]) == Pierre.get_id()) {
	        Pierre.set_port_ecoute(port_ecoute);
        	server_udp server = new server_udp(port_ecoute,Pierre);
	        client_udp client1 = new client_udp(port_dest,Pierre,Thomas);
	        
	        
	        System.out.println("adresse IP source : " +Thomas.get_local_ip() + "\n");
	        
        }
	        
        if (Integer.parseInt(args[2]) == Thomas.get_id()) {
	        
        	//Conversation convpierre = new Conversation(0,Thomas,Pierre);
        	//Thomas.ajouter_conversation(convpierre);
        	System.out.println("Conversation ajouté");
        	Thomas.set_port_ecoute(port_ecoute);
        	
        	server_udp server = new server_udp(port_ecoute,Thomas);
	        client_udp client2 = new client_udp(port_dest,Thomas,Pierre);
	        System.out.println("adresse IP source : " + Pierre.get_local_ip() +"\n");
	        
	        
	        //System.out.println(Pierre.get_local_ip());
        
        }
    }
  
}