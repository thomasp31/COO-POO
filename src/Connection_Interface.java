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
    char[] getPass;
    JPasswordField password_text;
    JButton submit, cancel;

    //On crée une instance de connection à la BDD pour checker les login/password lors de la connexion
    public ConnectionDB CDB = new ConnectionDB();
	public JTextArea textAreaMessage;
	public JTextField inputMessage;
	
    public Connection_Interface() {
    	//initialisation des éléments de l'interface de connexion
    	
        // User Label
        user_label = new JLabel();
        user_label.setText("Login :");
        userName_text = new JTextField();
        
        // Password Label

        password_label = new JLabel();
        password_label.setText("Password :");
        password_text = new JPasswordField();

        // Submit bouton pour se connecter

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
        
        //Fait réagir le bouton "submit" lors du click sur le bouton ENTREE	
        panel.getRootPane().setDefaultButton(submit);
    }
    
    
    //méthode réagissant au clic du bouton Submit
    
    @Override
    public void actionPerformed(ActionEvent ae) {
    	//On récupère les infos entrées dans les JtextField 
        String userName = userName_text.getText();
        //récupération du password
        getPass=password_text.getPassword();
        String password = String.copyValueOf(getPass);
        //String password = password_text.getText();
        User user_local;
        try {
        	//On vérifie que le username(=login) et password coincident
			if(CDB.Connection_Users(userName, password)) {
				//Si ce n'est pas l'admin, on créé le user et on lance l'interface_accueil du chat
				if (!userName.equals("Admin")) {
					user_local = CDB.Create_User(userName);
					Interface_accueil I = new Interface_accueil(user_local.get_pseudo() + " Interface",user_local);
					//On ferme l'interface de connection au lancement de l'interface d'accueil
					this.dispose();
				}else{
					//Si c'est l'admin pas besoin de créer un User, on lance juste l'interface Admin
					System.out.println("user Name : Admin");
					Admin_Interface AI = new Admin_Interface();
					//On ferme l'interface de connection au lancement de l'interface Admin
					this.dispose();
				}
			}
			else {
				//Si le password est faux on prévient l'utilisateur
			    message.setText("Password incorrect !");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
   
}











