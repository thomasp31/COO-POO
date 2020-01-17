import java.awt.GridLayout;
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
import javax.swing.JPanel;

public class Change_Pseudo_Window extends JFrame implements ActionListener{
	
	private User user_to_change;
	public JPanel mainPanel;
	public JLabel pseudo_label;
	public JTextField userPseudo_text;
	public JButton submit;
	public  JLabel message_erreur;
	public ConnectionDB CDB2= new ConnectionDB();
	public client_udp C;
	
	public Change_Pseudo_Window(User init_u_to_change, client_udp init_client){
		this.user_to_change = init_u_to_change;
		this.C=init_client;
		
		mainPanel = new JPanel(new GridLayout(2, 1));
		
		pseudo_label = new JLabel();
        pseudo_label.setText("User Pseudo : " + this.user_to_change.get_pseudo());
        userPseudo_text = new JTextField();
        
        submit = new JButton("CHANGE");
        
        mainPanel.add(pseudo_label);
        mainPanel.add(userPseudo_text);
        message_erreur = new JLabel();
        message_erreur.setForeground(Color.RED);
        mainPanel.add(message_erreur);
        mainPanel.add(submit);
        

        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        submit.addActionListener(this);
        add(mainPanel, BorderLayout.CENTER);
        setTitle("Please Change Pseudo Here !");
        setSize(300, 100);
        setVisible(true);
        mainPanel.getRootPane().setDefaultButton(submit);
        
        
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		String inputPseudo = userPseudo_text.getText();
		try {
			if(CDB2.check_new_pseudo(inputPseudo, user_to_change)) {
				message_erreur.setText("Pseudo OK");
				user_to_change.set_pseudo(inputPseudo);
				C.Send_Update_Pseudo(user_to_change);
				CDB2.Update_Pseudo(inputPseudo, user_to_change);
				this.dispose();
				
			}else {
				message_erreur.setText("Pseudo déjà pris");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
