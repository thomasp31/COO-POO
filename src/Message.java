import java.io.Serializable;



public class Message implements Serializable{
	
	
	private  static  final  long serialVersionUID =  1L;
	private User user_src;
	private User user_dest;
	private int idu_src = 0;
	private int idu_dest = 0;
	private String data ="";
	private String type;
	
	public Message(User init_user_src, User init_user_dest,String init_type) {
		this.user_src = init_user_src;
		this.user_dest = init_user_dest;
		this.idu_src = user_src.get_id();
		this.idu_dest = user_dest.get_id();
		this.type = init_type;
		
	}
	
	
	public void set_data(String D){
		this.data = D;
	}
	
	public String get_data() {
		return this.data;
	}
	
	
	
	
}
