import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Admin_Interface {

	
	
	public Admin_Interface() {
		JFrame f = new JFrame("Admin chat");
		f.setSize(500,200);
		
		//Connection à la BDD pour récupérer les infos sur les Users
		ConnectionDB CDBAdmin = new ConnectionDB();
		
		//Panel principal
		JPanel mainAdminPanel = new JPanel(new BorderLayout());
		//PAnel se situant à gauche de l'écran qui affichera la liste des user connectés
		JPanel listUsersPanel = new JPanel();
		listUsersPanel.setBackground(Color.RED);
		// Panel sur la partie droite de l'écran
		JPanel modifUserPanel = new JPanel(new BorderLayout());
		modifUserPanel.setBackground(Color.blue);
		
		
		//Panel dans partie de gauche supérieure
		JPanel createUserPanel = new JPanel(new GridLayout(1,1));
		createUserPanel.setBackground(Color.YELLOW);
		//Panel gauche partie inférieure
		JPanel disableUserPanel = new JPanel(new GridLayout(4,1));
		disableUserPanel.setBackground(Color.GREEN);
		
		JScrollPane JSP = new JScrollPane();
		//Model permettant de modifier la liste
		DefaultListModel modelUsers = new DefaultListModel();
        JList listUsersAdmin = new JList(modelUsers);
        JSP.setViewportView(listUsersAdmin);
        //listUsersPanel.add(listUsersAdmin);
        //listUsersPanel.add(JSP); // remplacé par ligne 90
        
        JTextField createLogin_text = new JTextField();
        JTextField createPassword_text = new JTextField();
        JTextField createPseudo_text = new JTextField();
        JButton createUserButton = new JButton();
        createUserButton.setText("CREATE");
        
        createUserPanel.add(createLogin_text);
        createUserPanel.add(createPassword_text);
        createUserPanel.add(createPseudo_text);
        createUserPanel.add(createUserButton);
        
        modifUserPanel.add(createUserPanel,BorderLayout.NORTH);
        
        JLabel loginLab = new JLabel();
        loginLab.setText("Login : ");
        //JLabel loginLabSelected = new JLabel();
        JLabel pseudoLab = new JLabel();
        pseudoLab.setText("Pseudo : ");
        //JLabel pseudoLabSelected = new JLabel();
        JLabel passwordLab = new JLabel();
        passwordLab.setText("Password : ");
        //JLabel passwordLabSelected = new JLabel();
        
        //CE BOUTON N'EST PAS IMPLEMENTE CAR MANQUE DE TEMPS ET NON DEMANDE
        //Pour désactiver un User il aurait fallu ajouter une table dans la classe User de la bdd en ajoutant "ENABLE" ou "DISABLE"
        //Un compte disable ne pourrait pas se connecter. Exmple : Un employé quitte l'entreprise
        JButton disableUserButton = new JButton();
        disableUserButton.setText("DISABLE");
        
        disableUserPanel.add(loginLab);
        //disableUserPanel.add(loginLabSelected);
        disableUserPanel.add(passwordLab);
        //disableUserPanel.add(passwordLabSelected);
        disableUserPanel.add(pseudoLab);
        //disableUserPanel.add(pseudoLabSelected);
        disableUserPanel.add(disableUserButton);
    
        modifUserPanel.add(disableUserPanel,BorderLayout.CENTER);
        
        //mainAdminPanel.add(listUsersPanel,BorderLayout.WEST); remplacé par ligne 90
        mainAdminPanel.add(JSP,BorderLayout.WEST);
        mainAdminPanel.add(modifUserPanel,BorderLayout.CENTER);
        
        f.setContentPane(mainAdminPanel);
        f.setVisible(true);
        
        //Initialisation de la liste des Users 
        CDBAdmin.Select_All_Users(modelUsers);
        
        //Réaction au bouton de création d'un nouvel User
        createUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				if(createLogin_text.getText() != null && createPseudo_text.getText() !=null && createPassword_text.getText() != null) {
					try {
						//Si tous les champs sont remplis on vérifie qu'il n'y ait pas déjà un user avec le même pseudo ou login
						if(CDBAdmin.check_new_login(createLogin_text.getText()) && CDBAdmin.check_new_pseudo(createPseudo_text.getText())) {
							//Une fous que c'est bon, on créé le User avec les infos entrées
							CDBAdmin.Create_User_AdminBDD(createLogin_text.getText(), createPseudo_text.getText(), createPassword_text.getText());
							
							//Et on rajoute ce nouvel User dans le model permettant l'affichage de tous les Users
							modelUsers.addElement(createLogin_text.getText());
							
							//Puis on remet les champs de saisie des infos d'un nouvel User à blanc pour éventuellement en créer un autre
							createLogin_text.setText("");
							createLogin_text.setBackground(Color.WHITE);
							createPassword_text.setText("");
							createPassword_text.setBackground(Color.WHITE);
							createPseudo_text.setText("");
						}else if (!CDBAdmin.check_new_login(createLogin_text.getText())){
							//Si le login est déjà pris on affiche en rouge la case
							createLogin_text.setBackground(Color.RED);
						}else {
							//Si e pseudo est déjà pris on affiche en rouge sa case
							createPseudo_text.setBackground(Color.RED);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
        });
        
        //Permet d'afficher les infos d'un User lorsque l'on clique dessus dans la liste
        listUsersAdmin.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				//jList1ValueChanged renverr le String du login sélectionné dans la liste
				String selectedValue = jList1ValueChanged(e, listUsersAdmin);
				
				try {
					//Une fois un User conecté on récupère ses infos et on les met dans les labels prévus à cet effet
					CDBAdmin.getUserInformations(selectedValue, loginLab, pseudoLab, passwordLab);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        });
        
	}
	private String jList1ValueChanged(javax.swing.event.ListSelectionEvent evt, JList listUsersAd) {
		String str="";
		//On récupère la valeur de la liste sélectionnée si la liste n'est pas vide
    	if(listUsersAd.getModel().getSize()!=0) {
	        str = (String) listUsersAd.getSelectedValue();             
    	}
    	return str;
	}
}
