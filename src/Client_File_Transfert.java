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
	
	public Client_File_Transfert(int init_port_dest ,User init_user_src){
        this.port_dest = init_port_dest;
        this.user_src = init_user_src;
        this.user_dest = null;
    }
	
	
	public void run(){
		InetAddress adresseIP;
		if(this.user_dest != null) {
			try {
				adresseIP = InetAddress.getByName(user_dest.get_IP());
				DatagramSocket socket = new DatagramSocket();
				FileInputStream stream = new FileInputStream(file_to_send);
				byte[] contenu = new byte[(int) file_to_send.length()];
				stream.read(contenu);
				DatagramPacket packet = new DatagramPacket(contenu, contenu.length,adresseIP, this.port_dest);
				socket.send(packet);
				System.out.println("Envoi terminé \n");
				socket.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
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
