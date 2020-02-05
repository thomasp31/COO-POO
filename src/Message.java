import java.io.*;
import java.util.*;
import java.text.*;


public class Message implements Serializable{
	
	
	private  static  final  long serialVersionUID =  1;
	private User user_src;
	private User user_dest;
	private String data ="";
	private String type = "default";
	private String Stringdate;
	
	//Constructeur d'un message
	public Message(String init_type,User init_user_src,User init_user_dest) {
		this.user_src = init_user_src;
		this.user_dest = init_user_dest;
		this.type = init_type;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.Stringdate = format.format(new Date());
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
	
	public String get_date() {
		return Stringdate;
	}
	
	//Méthode ajoutant la date au message
	public void set_date() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.Stringdate = format.format(new Date());
	}
}
