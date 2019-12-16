import java.net.*;

import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;


public class main{

    public static void main(String args[]){
    	User Pierre = new User("Pierrot","pcloury","BG");
    	User Thomas = new User("Toto","tpelous","Ultra-BG");
        int port_client = Integer.parseInt(args[0]);
        int port_server = Integer.parseInt(args[1]);
        
        server_udp server = new server_udp(port_server);
        
        if (Integer.parseInt(args[2]) == Pierre.get_id()) {
	        
        	
	        client_udp client1 = new client_udp(port_client,Pierre,Thomas);
	        
	        
	        System.out.println(Thomas.get_local_ip());
	        
        }
	        
        if (Integer.parseInt(args[2]) == Thomas.get_id()) {
	        
        	Conversation convpierre = new Conversation(0,Pierre,Thomas);
        	Thomas.ajouter_conversation(convpierre);
        	
	        client_udp client2 = new client_udp(port_client,Thomas,Pierre);
	        System.out.println(Pierre.get_local_ip());
	        
	        
	        //System.out.println(Pierre.get_local_ip());
        
        }
    }
  
}