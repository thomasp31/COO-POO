import java.sql.*;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;

//import com.sun.jdi.connect.spi.Connection;

public class ConnectionDB {
	public Class c;
	public String dbURL;
	public String ip;
	public String port;
	public String nomBase;
	public String conString;
	public String nomConnexion;
	public String motDePasse;
	public Connection con;
	
	public ConnectionDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//this.dbURL="jdbc:mysql://srv-bdens.insa-toulouse.fr/tpservlet_03";//si connection à l'INSA
			//this.con = DriverManager.getConnection(dbURL, "tpservlet_03", "phahNgo4");
			this.dbURL="jdbc:mysql://localhost:3306/poo_chat_db";//Bdd locale pour travailler depuis l'extérieur de l'INSA
			this.con = DriverManager.getConnection(dbURL,"userChat","userChat");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean Connection_Users(String login, String password) throws SQLException {
		boolean res = false;
		String sql="select password from Users where login=\"" + login + "\";";
		Statement smt = con.createStatement();
		ResultSet rs = smt.executeQuery(sql);
		while (rs.next()) {
			if(rs.getString("password").equals(password)) {
				System.out.println("Password OK");
				res = true;
			}
		}
		return res;
	}
	
	
	// fonction permettant de créer une instance de la classe User en fonction du login passée en paramètre (la fonction va chercher les attributs dans la base
	// de donnée et crée l'user correspondant
	public User Create_User(String log) throws SQLException {
		String sql="select * from Users where login=\"" + log + "\";";
		Statement smt = con.createStatement();
		ResultSet rs = smt.executeQuery(sql);
		
		rs.next();
		int ID = Integer.parseInt(rs.getString("id_user"));
		String pseudo = rs.getString("pseudo");
		String login = rs.getString("login");
		int port_ecoute = 9998;	
		int port_envoi = 9999;
		User User_ret = new User(pseudo,login,"CONNECTED",port_ecoute, port_envoi, ID);
		System.out.println("IP USER LOCAL : " + User_ret.get_IP());
		return User_ret;
	}
	
	
	//méthode permettant d'insérer le message dans la base de donnée
	public boolean Insert_messBDD(Message message) throws SQLException {
		int id_src = message.get_user_src().get_id();
		int id_dest = message.get_user_dst().get_id();
		String type = message.get_type();
		String data = message.get_data();
		String date = message.get_date();
		String sql="INSERT INTO Messages (id_user, id_dest, type, data, date_message) VALUES ("  + id_src + ", " + id_dest + ", \"" + type + "\", \"" + data + "\", " +"\""+ date + "\");";
		Statement smt = con.createStatement();
		smt.executeUpdate(sql);
		return true;
	}
	
	//methode permettant de charger l'historique de la conversation entre le user local et le user dest_dest
	
	public String get_historique(User user_local, User user_dest) throws SQLException {
		
		String resultat ="";
		String sql="select * from Messages where (id_user = "  + user_local.get_id() + " and id_dest = " + user_dest.get_id() + ") OR (id_user = "  + user_dest.get_id() + " and id_dest = " + user_local.get_id() + ");";
		Statement smt = con.createStatement();
		ResultSet rs = smt.executeQuery(sql);
		while (rs.next()) {
			String data = rs.getString("data");
			String date = rs.getString("date_message");
			if(Integer.parseInt(rs.getString("id_user"))==user_local.get_id()) {
				resultat = resultat + "Message envoyé : " + data + "\n" + date + "\n \n";
			}else {
				resultat = resultat + "Message reçu : " + data + "\n" + date+ "\n \n";
			}
		}
		return resultat;
	}
	
	//Check du pseudo lorsqu'un user change de pseudo OU quand un Admin créé un nouvel User
	public boolean check_new_pseudo(String NP) throws SQLException {
		boolean res = true;
		String sql="select * from Users;";
		Statement smt = con.createStatement();
		ResultSet rs = smt.executeQuery(sql);
		while (rs.next()) {
			if(rs.getString("pseudo").equals(NP)) {
				System.out.println("Pseudo déjà pris");
				res = false;
			}
		}
		return res;
	}
	
	
	//Méthode pour la création d'un user lorsqu'un Admin en créé un nouveau uniquement
	public boolean check_new_login(String NL) throws SQLException {
		boolean res = true;
		String sql="select * from Users;";
		Statement smt = con.createStatement();
		ResultSet rs = smt.executeQuery(sql);
		while (rs.next()) {
			if(rs.getString("login").equals(NL)) {
				System.out.println("Login déjà pris");
				res = false;
			}
		}
		return res;
	}
	
	//Méthode envoyant la modification de pseudo a la BDD en renvoyant true si OK et False I echec
	public void Update_Pseudo(String NP, User U) {	
		try {
			String sql="UPDATE Users SET pseudo=\""+ NP + "\" WHERE login=\"" + U.get_login() + "\";";
			Statement smt = con.createStatement();
			smt.executeUpdate(sql);
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	//Retourne tous les User en modifiant un model dans l'interface Admin pour avoir la list de tous les users
	public void Select_All_Users(DefaultListModel model) {
		ArrayList<User> Result = new ArrayList<User>();
		String sql="select * from Users;";
		Statement smt;
		try {
			smt = con.createStatement();
			ResultSet rs = smt.executeQuery(sql);
			while(rs.next()) {
				String l = rs.getString("login");
				model.addElement(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Permet de voir les infos sur un User après l'avoir sélectionné dans la list des Users de l'Admin
	public void getUserInformations(String loginSelected, JLabel loginLabel, JLabel pseudoLabel, JLabel passwordLabel) throws SQLException {
		String sql = "select * from Users where login=\"" + loginSelected + "\";" ;
		Statement smt = con.createStatement();
		ResultSet rs = smt.executeQuery(sql);
		while(rs.next()) {
			loginLabel.setText("Login : " + rs.getString("login"));
			pseudoLabel.setText("Pseudo : " + rs.getString("pseudo"));
			passwordLabel.setText("Password : " + rs.getString("password"));
		}
	}
	
	//Permet d'insérer un nouvel user dans la bdd quand un Admin en créé un
	public void Create_User_AdminBDD(String Login, String Pseudo, String Password) throws SQLException {
		String sql="INSERT INTO Users (login, password, pseudo) VALUES('" + Login +"', '" + Password +"', '" + Pseudo + "');";
		Statement smt = con.createStatement();
		smt.executeUpdate(sql);
	}
	
}
