import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import javax.swing.JList;
import java.util.*;

public class Client_File_Transfert extends Thread{
	
	private int port_dest;
    private User user_src;
    private User user_dest;
    private String addr_ip_dest="";
    private File file_to_send;
	
    //Client pour l'envoi de fichier
	public Client_File_Transfert(int init_port_dest ,User init_user_src){
        this.port_dest = init_port_dest;
        this.user_src = init_user_src;
        //Comme pour le Client normal, on set le destinataire lorsqu'on sélectionne un user dans la list des users connectés
        this.user_dest = null;
    }
	
	
	public void run(){
		InetAddress adresseIP;
		//On vérifie qu'on a bien un destinataire
		if(this.user_dest != null) {
			try {
				//On prépare l'envoi du fichier
				adresseIP = InetAddress.getByName(user_dest.get_IP());
				DatagramSocket socket = new DatagramSocket();
				//On met le fichier que l'on veut envoyer
				FileInputStream stream = new FileInputStream(file_to_send);
				byte[] contenu = new byte[(int) file_to_send.length()];
				stream.read(contenu);
				DatagramPacket packet = new DatagramPacket(contenu, contenu.length,adresseIP, this.port_dest);
				//On envoie le fichier quand tout est prêt
				
				socket.send(packet);
				socket.close();
				
				//Test
				System.out.println("Envoi terminé \n");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	//On paramètre le chemin pour trouver le fichier à envoyer
	public void set_file(String path) {
		this.file_to_send = new File(path);
	}
	
	public void set_dest(User U) {
		this.user_dest=U;
	}
	
	public User get_dest() {
		return this.user_dest;
	}
}
