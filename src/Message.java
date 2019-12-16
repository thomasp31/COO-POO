import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;




public class Message implements Serializable{
	
	
	private  static  final  long serialVersionUID =  1;
	private User user_src;
	private User user_dest;
	private int idu_src = 0;
	private int idu_dest = 0;
	private String data ="";
	private String type = "default";
	private int id_conv;
	
	public Message(String init_type,User init_user_src,User init_user_dest,int init_id_conv) {
		this.user_src = init_user_src;
		this.user_dest = init_user_dest;
		this.type = init_type;
		this.id_conv = init_id_conv;
		
	}
	
	
	public void set_data(String D){
		this.data = D;
	}
	
	public String get_data() {
		return this.data;
	}
	
	public String get_type() {
		return this.type;
	}
	public User get_user_src() {
		return this.user_src;
	}
	
	public User get_user_dst() {
		return this.user_dest;
	}
	
	public int get_id_conv() {
		return this.id_conv;
	}
	
}
