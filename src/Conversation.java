//importation
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;

//CLASSE INUTILE A SUPPRIMER

public class Conversation implements Serializable{
	
	private int id_conv;
	private User user_src_conv;
	private User user_dest_conv;
	//attention : remettre en privé !!
	public ArrayList<Message> messages_echanges = new ArrayList<Message>();
	
	public Conversation(int init_ID_conv, User init_user_src, User init_user_dest) {
		this.id_conv= init_ID_conv;
		this.user_src_conv = init_user_src;
		this.user_dest_conv = init_user_dest;
	}
	
	
	public Conversation get_conversation() {
		return this;
	}
	
	public int get_id_conv(){
		return this.id_conv;
	}
	
	public void ajouter_message(Message message) {
		this.messages_echanges.add(message);
	}
	
	
	
	
}
