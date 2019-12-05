//importations
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;


public class Conversation_listener extends Conversation implements Runnable{

	Thread thread1;
	
	public Conversation_listener(User init_user1,User init_user2,int init_p1,int init_p2) {
		super(init_user1,init_user2,init_p1,init_p2);
	}
		
	@Override
	public void run() {
		// TODO Auto-generated method stub
        try{
            ServerSocket servSocket	 = new ServerSocket(1234);
            System.out.println("serveur socket ok");
            
            Socket link = servSocket.accept();
            System.out.println("lin en mode accept ok");
           
            //System.out.println(link.getLocalAddress());
            String data = null;
            /*
            BufferedReader in = new BufferedReader(
                new InputStreamReader(link.getInputStream()));
            while((data=in.readLine()) != null){
                System.out.println("message : " + data);
                System.out.println(data);
                if (data.equals("0"))
                {
                    System.out.println(data);
                    link.close();
                }
            }
            */
        }catch(Exception e){
            System.out.println("erreur socket serveur\n");
            System.out.println("erreur : " + e);
        } 
		
		
		
	} 

}
