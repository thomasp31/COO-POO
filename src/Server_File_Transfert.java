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
	    private static final int TAILLE_MAXIMALE = 2000000;//65536;
	    
	    //Destination des fichiers que l'on reçoit
	    public static File repertoire = new File("../reception_fichiers");
	    
	    //Classe permettant la réception de fichier de 60Ko MAXIMUM
	    public Server_File_Transfert(int init_p_local,User init_u_source, JTextArea jta, DefaultListModel model, JList JL, User init_selected_user){
	        this.port_local= init_p_local;
	        this.ulocal = init_u_source; 
	        this.display_zone = jta; //inutile
	        this.DLM=model; //Model passé en atribut pour pouoir ajouter le fichier à la liste des fichier reçus
	        this.JLUsers=JL;
	        this.selected_user = init_selected_user;
	       
	        start();
	        
	    }
	    
	    //Permet la réception d'un fichier
	    public void run(){
	    	//On atribue un numéro à chaque fichier reçu pour plus de facilité à les retrouver dans la list des fichiers
	    	int num_fichier = 0;
	    	try {
	    		//préparation du datagramme pour réception
		    	DatagramSocket ds = new DatagramSocket(this.port_local);
				FileOutputStream fis;
				DatagramPacket dp;
				File f;
				byte[] b; 
				//Récupération du fichier
				for(int i = 0 ; i < 10000000 ; i++){
					f = new File(repertoire.getAbsolutePath()+ File.separator +i);
					fis = new FileOutputStream(f);
					b = new byte[TAILLE_MAXIMALE];
					dp = new DatagramPacket(b,TAILLE_MAXIMALE);
					ds.receive(dp);
					fis.write(dp.getData(),0,dp.getLength());
					this.DLM.addElement("File" + num_fichier);
					num_fichier++;
				}
				ds.close();
				
				//Test
				System.out.println("Fichier reçu");
	    	}catch(Exception e) {
	    		System.out.println(e);
	    	}
	    }
}
