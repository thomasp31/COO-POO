import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Connection_Interface extends JFrame implements ActionListener {

    JPanel panel;
    JLabel user_label, password_label, message;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit, cancel;

    
    public ConnectionDB CDB = new ConnectionDB();
    //public int port_src;
	//public int port_dest;
	//public User user_local;
	//public User user_dest;// A changer quand on aura plusieurs utilisateurs
	public JTextArea textAreaMessage;
	public JTextField inputMessage;
	
    public Connection_Interface(/*int init_port_src, int init_port_dest, User u_local/*, User u_dest*/) { //A degager car pas besoin des ports etc
        //initialisation des paramètres
    	//port_src = init_port_src;
    	//port_dest = init_port_dest;
    	//user_local = u_local;
    	//user_dest = u_dest;
    	
        // User Label
        user_label = new JLabel();
        user_label.setText("User Name :");
        userName_text = new JTextField();
        
        // PasswordSQLException

        password_label = new JLabel();
        password_label.setText("Password :");
        password_text = new JPasswordField();

        // Submit

        submit = new JButton("SUBMIT");
        panel = new JPanel(new GridLayout(3, 1));

        panel.add(user_label);
        panel.add(userName_text);
        panel.add(password_label);
        panel.add(password_text);
        message = new JLabel();
        message.setForeground(Color.RED);
        panel.add(message);
        panel.add(submit);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Adding the listeners to components..
        submit.addActionListener(this);
        //Supposé réagir au bouton ENTER main ne marche pas
        add(panel, BorderLayout.CENTER);
        setTitle("Please Login Here !");
        setSize(300, 100);
        setVisible(true);
        panel.getRootPane().setDefaultButton(submit); //Fait réagir le bouton "submit" lors du click sur le bouton ENTREE
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        String userName = userName_text.getText();
        String password = password_text.getText();
        User user_local;
        try {
			if(CDB.Connection_Users(userName, password)) {
				if (!userName.equals("Admin")) {
					user_local = CDB.Create_User(userName);
					//Interface_accueil I = new Interface_accueil(user_local.get_pseudo() + " Interface", user_local.get_port_ecoute(),1998,user_local,user_dest);
					Interface_accueil I = new Interface_accueil(user_local.get_pseudo() + " Interface",user_local);
					this.dispose();
				}else{
					System.out.println("user Name : Admin");
					Admin_Interface AI = new Admin_Interface();
					this.dispose();
				}
			}
			/*
			if (userName.trim().equals("Thomas") && password.trim().equals("Thomas")) {
				Interface_accueil I = new Interface_accueil("Thomas", port_src,port_dest,user_local,user_dest);
				this.dispose();
			}else if(userName.trim().equals("Pierre") && password.trim().equals("Pierre")) {
				Interface_accueil I = new Interface_accueil("Pierre", port_src,port_dest,user_local,user_dest);
				this.dispose();
			}else if(userName.trim().equals("Jack") && password.trim().equals("Jack")) {
				Interface_accueil I = new Interface_accueil("Jack", port_src,port_dest,user_local,user_dest);
				this.dispose();
			}*/
			else {
			    message.setText("Password incorrect !");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
   
}











