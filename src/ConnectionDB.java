import java.sql.*;

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
			this.dbURL="jdbc:mysql://srv-bdens.insa-toulouse.fr/tpservlet_03";
			this.con = DriverManager.getConnection(dbURL, "tpservlet_03", "phahNgo4");
			
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
	
}
