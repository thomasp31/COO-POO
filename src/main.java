import java.net.*;

import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;


public class main{

    public static void main(String args[]){
    	
    	//User Pierre = new User("Pierrot","pcloury","BG",0);
    	//User Thomas = new User("Toto","tpelous","Ultra-BG",0);
    	//User Jack =new User("Ja","Jack","u",0);
    	//Conversation convpierre = new Conversation(0,Thomas,Pierre);
    	//Thomas.ajouter_conversation(convpierre);
        
    	//int port_ecoute = Integer.parseInt(args[0]);
        //int port_dest = Integer.parseInt(args[1]);
        //int port_dest=1998;
        //server_udp server = new server_udp(port_server,Thomas);
        
        //Les 2 if ne sont plus forcément indispensables, ils sont la juste parce que je n'ai pas encore réfléchi au moyen de mettre Pierre 
        //ou Thomas en user dest ou local dans mon code de connexion 
        
        /*if (Integer.parseInt(args[2]) == Pierre.get_id()) {
	        //Pierre.set_port_ecoute(port_ecoute);
	        Connection_Interface CF = new Connection_Interface(port_ecoute,port_dest,Pierre,Thomas);
	        //Interface_accueil I = new Interface_accueil("PIERRE", port_ecoute,port_dest,Pierre,Thomas);
        	//server_udp server = new server_udp(port_ecoute,Pierre);
	        //client_udp client1 = new client_udp(port_dest,Pierre,Thomas);
	        
	        
	        System.out.println("adresse IP source : " +Thomas.get_local_ip() + "\n");
	        
        }
	        
        if (Integer.parseInt(args[2]) == Thomas.get_id()) {
	        
        	//Conversation convpierre = new Conversation(0,Thomas,Pierre);
        	//Thomas.ajouter_conversation(convpierre);
        	//System.out.println("Conversation ajouté");
        	Thomas.set_port_ecoute(port_ecoute);
        	Connection_Interface CF = new Connection_Interface(port_ecoute,port_dest,Thomas,Pierre);
        	//Interface_accueil I = new Interface_accueil("THOMAS", port_ecoute,port_dest,Thomas,Pierre);
        	//server_udp server = new server_udp(port_ecoute,Thomas);
	        //client_udp client2 = new client_udp(port_dest,Thomas,Pierre);
	        //System.out.println("adresse IP source : " + Pierre.get_local_ip() +"\n");
	        
	        
	        //System.out.println(Pierre.get_local_ip());
        
        }
        
        if (Integer.parseInt(args[2])== Jack.get_id()){
        	Jack.set_port_ecoute(port_ecoute);
        	Connection_Interface CF = new Connection_Interface(port_ecoute,port_dest,Jack,Pierre);
        }*/
    	
    	Connection_Interface CF = new Connection_Interface();
        
        
    }
  
}