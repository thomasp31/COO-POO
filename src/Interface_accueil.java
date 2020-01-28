import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JSplitPane;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Scrollbar;

public class Interface_accueil {
    
	//public int port_src;
	//public int port_dest;
	public User user_local;
	//public User user_dest;// A changer quand on aura plusieurs utilisateurs
	public JTextArea textAreaMessage;
	public JTextField inputMessage;
	public ConnectionDB CDBI = new ConnectionDB();
	public User selected_user;
	public String selected_file;
	
    public Interface_accueil(String l, User u_local/*, User u_dest*//*, int init_port_src, int init_port_dest*/){ 
    	
    	//port_src = init_port_src;
    	//port_dest = init_port_dest;
    	user_local = u_local;
    	//user_dest = u_dest;
    	
    	JFrame f=new JFrame("my chat");
        f.setSize(400,400);        
        
        /*Définition des Panel*/
        
        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new BorderLayout());
            
        JPanel userPanel=new JPanel();
        userPanel.setLayout(new BorderLayout());
        
        JPanel conversationPanel = new JPanel();
        conversationPanel.setLayout(new BorderLayout());
        
        JPanel envoiMessage = new JPanel();
        envoiMessage.setLayout(new BorderLayout());
        
        JPanel fileSending = new JPanel();
        fileSending.setLayout(new BorderLayout());
        
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BorderLayout());
        
        /*********************************************************************/
        JButton updateBtn = new JButton("Pseudo");
        userPanel.add(updateBtn,BorderLayout.NORTH);
        
        JScrollPane JSPList = new JScrollPane();
        DefaultListModel model = new DefaultListModel();
        JList listUsers = new JList(model);
		listUsers.setBackground(Color.cyan);
		JSPList.setViewportView(listUsers);
		/*listUsers.setModel(new AbstractListModel() {
			String[] values = new String[] {"USER 1", "USER 2", "USER 3", "USER 4"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});*/
		userPanel.add(JSPList);
		
		//Pour plus tard si on a le temps pour différencier les envois
		//JTextPane textPaneMessage = new JTextPane();
		//Definition des attributs de style:
		//SimpleAttributeSet green
        
        textAreaMessage = new JTextArea();
        JScrollPane scroll = new JScrollPane(textAreaMessage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //conversationPanel.add(textAreaMessage,BorderLayout.CENTER);
        conversationPanel.add(scroll, BorderLayout.CENTER);
        textAreaMessage.setEditable(false);
        textAreaMessage.setLineWrap(true);
        textAreaMessage.setDragEnabled(true);
  
        JTextField inputFile = new JTextField();
        JButton sendFileButton = new JButton("FILE");
        fileSending.add(inputFile,BorderLayout.CENTER);
        fileSending.add(sendFileButton,BorderLayout.EAST);
        envoiMessage.add(fileSending,BorderLayout.NORTH);
        
        inputMessage = new JTextField();
        envoiMessage.add(inputMessage,BorderLayout.CENTER);
        JButton sendButton = new JButton("SEND");
        envoiMessage.add(sendButton,BorderLayout.EAST);
        
        conversationPanel.add(envoiMessage,BorderLayout.SOUTH);
        
        
        DefaultListModel modelFile = new DefaultListModel();
        JList listFiles = new JList(modelFile);
		listFiles.setBackground(Color.orange);
		/*listFiles.setModel(new AbstractListModel() {
			String[] values = new String[] {"FILE 1", "FILE 2", "FILE 3", "FILE 4"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});*/
		filePanel.add(listFiles);
        
        
        mainPanel.add(userPanel,BorderLayout.LINE_START);
        mainPanel.add(conversationPanel,BorderLayout.CENTER);
        mainPanel.add(filePanel,BorderLayout.LINE_END);
        
        f.setContentPane(mainPanel);
        f.setVisible(true);                           
        conversationPanel.getRootPane().setDefaultButton(sendButton); //Fait réagir le bouton "submit" lors du click sur le bouton ENTREE
        
        //Initialisation du serveur et du client
        //faire if dans le serveur pour vérifier que selected user n'est pas nul
        server_udp server = new server_udp(user_local.get_port_ecoute(), user_local, textAreaMessage, model, listUsers, selected_user);
        client_udp client1 = new client_udp(user_local.get_port_ecoute(),user_local/*,user_dest*/); //Peut-être mettre le port d'envoi en attribut à User
        client1.Send_Broadcast();
        
        //Test envoi fichier client et serveur
        Server_File_Transfert SFT = new Server_File_Transfert(user_local.get_port_ecoute()+1, user_local, textAreaMessage, modelFile, listUsers, selected_user);
        Client_File_Transfert CFT = new Client_File_Transfert(user_local.get_port_ecoute()+1,user_local);
        
        sendButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ev) {
        		if(client1.get_dest()!=null) {
	        		String s = inputMessage.getText();
	        		Message m = new Message("NORMAL", user_local, client1.get_dest(), 0 );
	        		m.set_data(s);
	        		m.set_date();
	        		
	        		textAreaMessage.append("Message envoyé : " + s + "\n");
	        		textAreaMessage.append(m.get_date() + "\n\n");
	        		client1.set_message(m);
	        		client1.run();
	        		System.out.println("Envoi du message à la base de donnée \n");
	        		try {
						CDBI.Insert_messBDD(m);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		inputMessage.setText("");
        		}
        	}
        });
        
        updateBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ev) {
        		//listUsers.removeAll();
        		//Message message_broadcast = new Message("BROADCAST",user_local, null, 0);
        		//message_broadcast.set_data("Automatique");
        		//client1.set_message(message_broadcast);
        		//client1.run();
        		Change_Pseudo_Window CPW = new Change_Pseudo_Window(u_local, client1);
        	}
        });
        
        listUsers.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				jList1ValueChanged(e,listUsers,client1,server, CFT);
				String hist="";
				try {
					hist = CDBI.get_historique(u_local, selected_user);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textAreaMessage.setText(hist);
			}
        });
        
        listFiles.addListSelectionListener(new ListSelectionListener() {
        	
    		@Override
    		public void valueChanged(ListSelectionEvent e) {
    			String numFileStr = jList2ValueChanged(e, listFiles, selected_file);
    			int numFileInt=Integer.parseInt(numFileStr.substring(4));
    			System.out.println("Fichier séléctionné : " + numFileInt);
    			
    			//ouverture du fichier
    			if(Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)){
    			    try {
    			        java.awt.Desktop.getDesktop().open(new File("../reception_fichiers/"+numFileInt));
    			    } catch (Exception ex) {
    			       ex.printStackTrace();
    			    }
			    }
    		}
    			
		});
      
        
        f.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e){
        		int reponse = JOptionPane.showConfirmDialog(f,"Voulez-vous quitter l'application","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

        		if (reponse==JOptionPane.YES_OPTION){
        			Message message_deco= new Message("DISCONNECT",user_local, null, 0);
            		message_deco.set_data("Automatique");
            		client1.set_message(message_deco);
            		client1.run();
        			f.dispose();
        			server.stop();
        		}	
        		else {
        			f.setVisible(true);
        		}
        		
        	}
        });
        
        sendFileButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ev) {
        		//Ici on doit couper la première partie du drag and drop => File:// et ne garder que ce qui suit pour envoyer le fichier
        		String sous_chaine = inputFile.getText().substring(6);
        		CFT.set_file(sous_chaine);
        		CFT.run();
        		Message file_message = new Message("NORMAL", user_local, client1.get_dest(), 0);
        		file_message.set_data("Fichier envoyé par " + file_message.get_user_src().get_pseudo() + "\n");
        		client1.set_message(file_message);
        		client1.run();
        		textAreaMessage.append(file_message.get_data() + "\n");
        		textAreaMessage.append(file_message.get_date() + "\n\n");
        	}
        });
    } 
    
    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt, JList listUsers,client_udp c, server_udp s, Client_File_Transfert CFT_param) {
        //set text on right here
    	if(listUsers.getModel().getSize()!=0) {
	        String str = (String) listUsers.getSelectedValue();
	        
	        for(User u : user_local.Connected_Users) {
	        	if (str.equals(u.get_pseudo())) {
	        		selected_user=u;
	        		s.selected_user=u;
	        		c.set_dest(u);
	        		CFT_param.set_dest(u);
	        		System.out.println("changement de destinataire" + u.get_IP());
	        	}
	            
	        }
    	}
	}
    
    private String jList2ValueChanged(ListSelectionEvent e, JList fileList, String Selected_File) {
    	String str="";
		if(fileList.getModel().getSize()!=0) {
	        str = (String) fileList.getSelectedValue();
	        Selected_File = str;
		}
		return str;
	}
    
    
}
