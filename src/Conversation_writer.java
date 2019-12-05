//importations
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;



public class Conversation_writer extends Conversation implements Runnable{
	
    private Socket link=null;

	
	public Conversation_writer(User init_user1,User init_user2,int init_p1,int init_p2) throws Exception{
		super(init_user1,init_user2,init_p1,init_p2);
	    this.link = new Socket(this.get_address1(),init_p2);
	}
	
	
	//
	public void envoyer() throws IOException{
		
		Scanner scanner;
        scanner = new Scanner(System.in);
        String message=null;
        
        while (true)
        {
            message = scanner.nextLine();	//scan la ligne a envoyer
            PrintWriter out = new PrintWriter(this.link.getOutputStream(), true);		
            out.println(message);
            out.flush();			//envoie
        }
    }
	
	
	
	public void run() {
		 try{
		        envoyer();
		        }catch(Exception e)
		        {
		            System.out.println("erreur lors de l'envoi \n");
		        };
		
	}
	
	
}