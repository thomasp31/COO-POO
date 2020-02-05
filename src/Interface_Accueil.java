import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTarget;
import java.util.List;

public class Interface_Accueil {
    
	public User user_local;
	public JTextArea textAreaMessage;
	public JTextField inputMessage;
	public ConnectionDB CDBI = new ConnectionDB();
	public User selected_user;
	public String selected_file;
	
	
	//Une fois que le User a rentré son login et password, le User est créé en java et cette fenêtre est lancé
	//Ce sera donc la fenêtre de chat
    public Interface_Accueil(String l, User u_local){ 
    	//on passe le user créé dans l'interface de connection en paramètre pour récupérer les infos
    	user_local = u_local;
    	
    	//Création de la partie graphique
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
        //Button permettant de changer son pseudo
        JButton updateBtn = new JButton("Pseudo");
        userPanel.add(updateBtn,BorderLayout.NORTH);
        
        //Création d'un JScrollPane pour que notre list User soit scrollable
        JScrollPane JSPList = new JScrollPane();
        //Model nous servant à la mise à jour de la liste des users connectés
        DefaultListModel model = new DefaultListModel();
        JList listUsers = new JList(model);
       
		listUsers.setBackground(Color.cyan); //Couleur choisie arbitrairement
		JSPList.setViewportView(listUsers);
		
		userPanel.add(JSPList);
        
		//Zone ou l'on verra l'affichage des messages
        textAreaMessage = new JTextArea();
        //Scroll bar permettant de voir tous les messages quand ça dépasse la taille du text area
        JScrollPane scroll = new JScrollPane(textAreaMessage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        conversationPanel.add(scroll, BorderLayout.CENTER);
        textAreaMessage.setEditable(false);
        textAreaMessage.setLineWrap(true);
        textAreaMessage.setDragEnabled(true);
  
        //Endroit ou l'on doit glisser déposer le fichier
        JTextArea inputFile = new JTextArea();
        inputFile.setLineWrap(true);
        JScrollPane scroll2 = new JScrollPane(inputFile, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        //permet d'adapterce qui est déposé pour avoir le path du fichier
        inputFile.setDropTarget(new DropTarget() {
        	public synchronized void drop(DropTargetDropEvent evt) {
	        	try {
		        	evt.acceptDrop(DnDConstants.ACTION_COPY);
		        	List<File> droppedFiles = (List<File>) evt
		        	.getTransferable().getTransferData(
		        	DataFlavor.javaFileListFlavor);
		        	for (File file : droppedFiles) {
			        	inputFile.setText(file.getAbsolutePath());
		        	}
	        	}catch(Exception e) {
	        		
	        	}
	        }
        });
        	//Bouton d'envoi du fichier
        JButton sendFileButton = new JButton("FILE");
        
        //ajout des éléments dans le panel
        //fileSending.add(inputFile,BorderLayout.CENTER);
        fileSending.add(scroll2, BorderLayout.CENTER);
        fileSending.add(sendFileButton,BorderLayout.EAST);
        //ajout du panel d'envou de fichier dans le panel de l'envoi des messages
        envoiMessage.add(fileSending,BorderLayout.NORTH);
        
        //Zone d'input des messages que l'on écrit
        inputMessage = new JTextField();
        inputMessage.setDragEnabled(false);
        envoiMessage.add(inputMessage,BorderLayout.CENTER);
        //Bouton d'envoi du message
        JButton sendButton = new JButton("SEND");
        envoiMessage.add(sendButton,BorderLayout.EAST);
        
        //Ajout du panel d'envoi des messages au panel de conversation (contenant le jtext area aussi)
        conversationPanel.add(envoiMessage,BorderLayout.SOUTH);
        
        //Liste affichant les fichiers reçus
        DefaultListModel modelFile = new DefaultListModel();
        JList listFiles = new JList(modelFile);
		listFiles.setBackground(Color.orange);
		filePanel.add(listFiles);
        
        //Ajout des différents panel dns le panel englobant le tout
        mainPanel.add(userPanel,BorderLayout.LINE_START);
        mainPanel.add(conversationPanel,BorderLayout.CENTER);
        mainPanel.add(filePanel,BorderLayout.LINE_END);
        
        //lancement de la frame global
        f.setContentPane(mainPanel);
        f.setVisible(true);           
        //Fait réagir le bouton "submit" lors du click sur le bouton ENTREE
        conversationPanel.getRootPane().setDefaultButton(sendButton); 
        
        //Initialisation du serveur et du client en même temps que l'interface se lance
        
        //Le serveur prend en paramètre le port où il écoute, le user_local (user connecté à notre interface), le text area ou on affiche les messages pour qu'on puisse le modifer
        //La listUsers, son model ainsi que le user selected pour pouvoir vérifier si on doit afficher les messages ou non lorsqu'on les reçoit 
        Server_Udp server = new Server_Udp(user_local.get_port_ecoute(), user_local, textAreaMessage, model, listUsers, selected_user);
        
        //Le client n'a besoin que deu port d'écoute et du user_local
        Client_Udp client1 = new Client_Udp(user_local.get_port_ecoute(),user_local);
        
        //Dès que l'on créé l'interface, on lance un BROADCAST pour prévenir qu'on s'est connecté
        client1.Send_Broadcast();
        
        //Test envoi fichier client et serveur
        //Client et Serveur permettant d'envyer et recevoir un fichier de 60K MAXIMUM
        Server_File_Transfert SFT = new Server_File_Transfert(user_local.get_port_ecoute()+2, user_local, textAreaMessage, modelFile, listUsers, selected_user);
        Client_File_Transfert CFT = new Client_File_Transfert(user_local.get_port_ecoute()+2,user_local);
        
        
        //Permet de réagir au bouton d'envoi d'un message
        sendButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ev) {
        		
        		//Ici on vérifie qu'un User a bien était selectionné avant de parler
        		//Sert surtout juste après s'être connecté lorsqu'on n'a parlé a personne
        		
        		if(client1.get_dest()!=null) {
        			//On récupère ce qui a été écrit
	        		String s = inputMessage.getText();
	        		//On créé un Message de type NORMAL
	        		//Message m = new Message("NORMAL", user_local, client1.get_dest(), 0 );
	        		Message m = new Message("NORMAL", user_local, client1.get_dest());
	        		//On y rajoute la data et et la date
	        		m.set_data(s);
	        		m.set_date();
	        		
	        		//On ajoute le message à l'écran de la conversation
	        		textAreaMessage.append("Message envoyé : " + s + "\n");
	        		textAreaMessage.append(m.get_date() + "\n\n");
	        		//Préparation du message a envoyé dans le Client
	        		client1.set_message(m);
	        		//Envoi du message
	        		client1.run();
	        		System.out.println("Envoi du message à la base de donnée \n");
	        		try {
	        			//On insert ensuite ce message dans la base de donnée pour le retrouver avec l'historique
						CDBI.Insert_messBDD(m);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		inputMessage.setText("");
        		}
        	}
        });
        
        //Buton permettant de mettre à jour le pseudo
        updateBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ev) {
        		//On ouvre une fenêtre permettant de changer le pseudo à laquelle on passe le user_local pour modifier le pseudo
        		//ainsi que le client pour renvoyer un broadcast lorsque le pseudo a été changé
        		Change_Pseudo_Window CSend_Update_PseudoPW = new Change_Pseudo_Window(u_local, client1);
        	}
        });
        
        //Listener permettant d'exécuter une action lors d'un clique sur un pseudo de la liste des persones connectées
        listUsers.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				//JList1ValudChanged prend tous ces paramètres pour récuperer le pseudo de la personne sélectionnée
				//et la mettre en nouvelle personne destinataire dans le client
				//Le serveur est passé pour notifier que le user_selected n'est plus le même => il ne doit plus afficher les messages reçu du user précédent
				jList1ValueChanged(e,listUsers,client1,server, CFT);
				
				//Une fois ces valeurs sélectionnée et mise a jour on charge l'historique
				String hist="";
				try {
					hist = CDBI.get_historique(u_local, selected_user);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//et on met le String de l'historique dans le text area pour afficher les messages
				textAreaMessage.setText(hist);
			}
        });
        
        //Listener premettant de réagir au click sur un fichier de la liste
        listFiles.addListSelectionListener(new ListSelectionListener() {
        	
    		@Override
    		public void valueChanged(ListSelectionEvent e) {
    			//Ici on va aller récupérer le nom du fichier séletionné
    			String numFileStr = jList2ValueChanged(e, listFiles, selected_file);
    			//on récupère son numéro dans la liste 
    			int numFileInt=Integer.parseInt(numFileStr.substring(4));
    			
    			System.out.println("Fichier séléctionné : " + numFileInt);
    			
    			//Puis on ouvre ce fichier lorsqu'on l'a sélectioné
    			if(Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)){
    			    try {
    			        java.awt.Desktop.getDesktop().open(new File("../reception_fichiers/"+numFileInt));
    			    } catch (Exception ex) {
    			       ex.printStackTrace();
    			    }
			    }
    		}
    			
		});
      
        
        //Permet de demander confirmation avant de fermer la fenëtre mais SURTOUT envoyer un broadcast lorsque l'on se déconecte
        f.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e){
        		//Ouverture de la demande de confirmation
        		//int reponse = JOptionPane.showConfirmDialog(f,"Voulez-vous quitter l'application","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        		
        		//Si oui, on ferme et on envoi un broadcast de déconnexion
        		//if (reponse==JOptionPane.YES_OPTION){
        			//Message message_deco= new Message("DISCONNECT",user_local, null, 0);
        			Message message_deco= new Message("DISCONNECT",user_local, null);
            		message_deco.set_data("Automatique");
            		client1.set_message(message_deco);
            		client1.run();
            		
            		//Ici on stop le processus pour tout terminer une fois l'application fermée
            		try {
            			long pid = ProcessHandle.current().pid();
						Runtime.getRuntime().exec("kill -SIGINT "+pid);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			f.dispose();
        		//}else {
        			//Sinon on garde la fenẗre ouverte mais ne fonctionne pas...
        			//f.setVisible(true);
        		//}
        		
        	}
        });
        
        //Réaction au bouton d'nvoi d'un fichier
        sendFileButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ev) {
        		
        		if(CFT.get_dest()!=null && client1.get_dest()!=null) {
        			//On récupère le path du fichier glissé et déposé
	        		String testArea = inputFile.getText();
	        		
	        		//on prépare le chemin du fichier et on le met dans notre client d'envoi de fichier
	        		CFT.set_file(testArea);
	        		//CFT.set_file(sous_chaine);
	        		//On envoie le fichier
	        		CFT.run();
	        		//on envoie aussi un message au destinataire de type NORMAL pour notifier qu'il a reçu un fichier
	        		
	        		Message file_message = new Message("NORMAL", user_local, client1.get_dest());
	        		
	        		//On vérifie que l'envoi du fichier a bien eu lieu
	        		if(CFT.get_envoi_ok()) {
		        		file_message.set_data("Fichier envoyé par " + file_message.get_user_src().get_pseudo());
		        		client1.set_message(file_message);
		        		client1.run();
		        		//On ajoute ce nouveau message dans le text area
		        		textAreaMessage.append(file_message.get_data() + "\n");
		        		textAreaMessage.append(file_message.get_date() + "\n\n");
		        		//On remet le JTextField vide
		        		inputFile.setText("");
	        		}else {
	        			inputFile.setText("Envoi echoué, fichier non trouvé, veuillez réessayer svp");
	        		}
	        		//Et on l'insère dans la BDD aussi
	        		try {
						CDBI.Insert_messBDD(file_message);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	}
        });
    } 
    
    //Permet de récupérer les valeurs sélectionnées  lors d'un click sur un user de la liste des users connectés
    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt, JList listUsers,Client_Udp c, Server_Udp s, Client_File_Transfert CFT_param) {
    	
        //set text on right here
    	if(listUsers.getModel().getSize()!=0) {
    		//On récupère la valeure du pseudo sélectionné
	        String str = (String) listUsers.getSelectedValue();
	        //Puis on cherche parmis les Users connectés de l'ArrayList à qui correspond ce pseudo
	        for(User u : user_local.Connected_Users) {
	        	
	        	if (str.equals(u.get_pseudo())) {
	        		//Une fois le user trouvé, on le marque comme user sélectioné
	        		selected_user=u;
	        		//On notifie le serveur en modifiant le user sélectionné
	        		s.selected_user=u;
	        		//On set le nouveau destinataire du Client 
	        		c.set_dest(u);
	        		//idem pour le client de l'envoi de fichier
	        		CFT_param.set_dest(u);
	        		System.out.println("changement de destinataire" + u.get_IP());
	        	}
	            
	        }
    	}
	}
    
    
    //Permet de récupérer le nom du fichier sélectionné dans la liste des fichiers
    private String jList2ValueChanged(ListSelectionEvent e, JList fileList, String Selected_File) {
    	String str="";
		if(fileList.getModel().getSize()!=0) {
	        str = (String) fileList.getSelectedValue();
	        Selected_File = str;
		}
		return str;
	}
    
    
}
