//importation
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;


public class Conversation extends Thread {
	
	private User user1;
	private User user2;
	//port de communication des deux users

	private int port_user1;
	private int port_user2;
	private String addr_1 = "localhost";
	private String addr_2 = "localhost";
	
	
	public Conversation(User init_user1,User init_user2,int init_p1,int init_p2) {
		this.user1 = init_user1;
		this.user2 = init_user2;
		this.port_user1 = init_p1;
		this.port_user2 = init_p2;
		start();
		
	}
	
	public void run() {
		
		//lancement du thread converstion listener permettant d'écouter 
		Conversation_listener cl = new Conversation_listener(this.user1,this.user2,this.port_user1,this.port_user2);
		Thread th_cl = new Thread(cl);
		th_cl.start();
		
		
		/*try {
			Conversation_writer cw = new Conversation_writer(this.user1,this.user2,this.port_user1,this.port_user2);
			Thread th_cw= new Thread(cw);
			th_cw.start();
		}
		catch (Exception e){
			System.out.println("cw1 erreur");
		}
		*/
		
		
		
		
	}
	
	public int get_p1(){
		return this.port_user1;
	}
	
	public int get_p2() {
		return this.port_user2;
	}
	
	public String get_address1() {
		return this.addr_1;
	}
	
	public String get_address2() {
		return this.addr_2;
	}
	
	
	

}
