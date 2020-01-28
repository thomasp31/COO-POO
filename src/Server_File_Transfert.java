import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class Server_File_Transfert extends Thread{
	    public int port_local;
	    public User ulocal;
	    public JTextArea display_zone;
	    public JList JLUsers;
	    public DefaultListModel DLM; //permet de relier au model dans INterface_Accueil
	    public User selected_user;
	    private static final int TAILLE_MAXIMALE = 65536;
	    public static File repertoire = new File("../reception_fichiers");
	    
	    public Server_File_Transfert(int init_p_local,User init_u_source, JTextArea jta, DefaultListModel model, JList JL, User init_selected_user){
	        this.port_local= init_p_local;
	        this.ulocal = init_u_source; 
	        this.display_zone = jta;
	        //this.Connected_Users=Jl;
	        this.DLM=model;
	        this.JLUsers=JL;
	        this.selected_user = init_selected_user;
	       
	        start();
	        
	    }
	    
	    public void run(){
	    	try {
		    	DatagramSocket ds = new DatagramSocket(this.port_local);
				FileOutputStream fis;
				DatagramPacket dp;
				ds.setSoTimeout(10000);
				File f;
				byte[] b; 
				for(int i = 0 ; i < 10000000 ; i++){
					f = new File(repertoire.getAbsolutePath()+ File.separator +i);
					fis = new FileOutputStream(f);
					b = new byte[TAILLE_MAXIMALE];
					dp = new DatagramPacket(b,TAILLE_MAXIMALE);
					ds.receive(dp);
					fis.write(dp.getData(),0,dp.getLength());
				}
				ds.close();
				System.out.println("Fichier reçu");
	    	}catch(Exception e) {
	    		System.out.println(e);
	    	}
	    }
}
