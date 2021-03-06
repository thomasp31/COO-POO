import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Change_Pseudo_Window extends JFrame implements ActionListener{
	
	private User user_to_change;
	public JPanel mainPanel;
	public JLabel pseudo_label;
	public JTextField userPseudo_text;
	public JButton submit;
	public  JLabel message_erreur;
	public ConnectionDB CDB2= new ConnectionDB();
	public Client_Udp C;
	
	public Change_Pseudo_Window(User init_u_to_change, Client_Udp init_client){
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

	//réaction lors du click pour valider le changement du pseudo
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		//On récupère le nouveau pseudo entré
		String inputPseudo = userPseudo_text.getText();
		try {
			//On vérifie dans la BDD qu'il n'existe pas déjà
			if(CDB2.check_new_pseudo(inputPseudo)) {
				//Si OK on l'affiche
				message_erreur.setText("Pseudo OK");
				//on change le pseudo du user_local passé en attribut
				user_to_change.set_pseudo(inputPseudo);
				//on envoi un broadcast pour que tous les Users update le nouveau pseudo
				C.Send_Update_Pseudo(user_to_change);
				//Et on le précise à la BDD aussi
				CDB2.Update_Pseudo(inputPseudo, user_to_change);
				this.dispose();
				
			}else {
				//Si déjà pris on prévient le User
				message_erreur.setText("Pseudo déjà pris");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
