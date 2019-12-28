import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

    public int port_src;
	public int port_dest;
	public User user_local;
	public User user_dest;// A changer quand on aura plusieurs utilisateurs
	public JTextArea textAreaMessage;
	public JTextField inputMessage;
    
    public Connection_Interface(int init_port_src, int init_port_dest, User u_local, User u_dest) {
        //initialisation des paramètres
    	port_src = init_port_src;
    	port_dest = init_port_dest;
    	user_local = u_local;
    	user_dest = u_dest;
    	
        // User Label
        user_label = new JLabel();
        user_label.setText("User Name :");
        userName_text = new JTextField();
        
        // Password

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
        panel.add(message);
        panel.add(submit);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Adding the listeners to components..
        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Please Login Here !");
        setSize(300, 100);
        setVisible(true);

    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        String userName = userName_text.getText();
        String password = password_text.getText();
        if (userName.trim().equals("Thomas") && password.trim().equals("Thomas")) {
        	Interface_accueil I = new Interface_accueil("Thomas", port_src,port_dest,user_local,user_dest);
        	this.dispose();
        }else if(userName.trim().equals("Pierre") && password.trim().equals("Pierre")) {
        	Interface_accueil I = new Interface_accueil("Pierre", port_src,port_dest,user_local,user_dest);
        	this.dispose();
        }
        else {
            message.setText(" Password incorrect, try again !");
        }

    }
}