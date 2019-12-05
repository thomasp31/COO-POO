import java.lang.reflect.*;
import java.util.*;

public class User {
	 private static int current_ID =0;
	 private int personnal_ID;
	 private String pseudo; 
	 private String login;
	 private String state;
	 private ArrayList<User> Connected_Users = new ArrayList<User>();
	 
	 
	 public User(String init_pseudo,String init_login,String init_state) {
		 current_ID++;
		 this.personnal_ID=current_ID;
		 this.pseudo= init_pseudo;
		 this.login=init_login;
	 }
	 
	 public void ListUpdate() {
		 //TODO
		 
	 }
	 
	 
	 
	 
	 public int get_personnal_ID() {
		 return this.personnal_ID;
	 }
	 
	 public String get_pseudo() {
		 return this.pseudo;
	 }
	 
	 public String get_login() {
		 return this.login;
	 }
	 
	 public String get_state() {
		 return this.state;
	 }
	 
	 public void set_pseudo(String new_pseudo) {
		this.pseudo = new_pseudo;
	 }
	 
	 public void set_login(String new_login) {
		 this.login = new_login;
	 }
	 
	 public void set_state(String new_state) {
		 this.state = new_state;
	 }
}
