import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Color;
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
        
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BorderLayout());
        
        /*********************************************************************/
        JButton updateBtn = new JButton("UPDATE");
        userPanel.add(updateBtn,BorderLayout.NORTH);
        
        DefaultListModel model = new DefaultListModel();
        JList listUsers = new JList(model);
		listUsers.setBackground(Color.cyan);
		/*listUsers.setModel(new AbstractListModel() {
			String[] values = new String[] {"USER 1", "USER 2", "USER 3", "USER 4"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});*/
		userPanel.add(listUsers);
		
        
        textAreaMessage = new JTextArea();
        JScrollPane scroll = new JScrollPane(textAreaMessage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //conversationPanel.add(textAreaMessage,BorderLayout.CENTER);
        conversationPanel.add(scroll, BorderLayout.CENTER);
        textAreaMessage.setEditable(false);
        textAreaMessage.setLineWrap(true);
        
        inputMessage = new JTextField();
        envoiMessage.add(inputMessage,BorderLayout.CENTER);
        JButton sendButton = new JButton("SEND");
        envoiMessage.add(sendButton,BorderLayout.EAST);
        
        conversationPanel.add(envoiMessage,BorderLayout.SOUTH);
        
        JList listFiles = new JList();
		listFiles.setBackground(Color.orange);
		listFiles.setModel(new AbstractListModel() {
			String[] values = new String[] {"FILE 1", "FILE 2", "FILE 3", "FILE 4"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		filePanel.add(listFiles);
        
        
        mainPanel.add(userPanel,BorderLayout.LINE_START);
        mainPanel.add(conversationPanel,BorderLayout.CENTER);
        mainPanel.add(filePanel,BorderLayout.LINE_END);
        
        f.setContentPane(mainPanel);
        f.setVisible(true);                           
        conversationPanel.getRootPane().setDefaultButton(sendButton); //Fait réagir le bouton "submit" lors du click sur le bouton ENTREE
        
        //Initialisation du serveur et du client
        
        server_udp server = new server_udp(user_local.get_port_ecoute(), user_local, textAreaMessage, model);
        client_udp client1 = new client_udp(user_local.get_port_ecoute(),user_local/*,user_dest*/); //Peut-être mettre le port d'envoi en attribut à User
        client1.Send_Broadcast();
        
        sendButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ev) {
        		if(client1.get_dest()!=null) {
	        		String s = inputMessage.getText();
	        		Message m = new Message("NORMAL", user_local, client1.get_dest(), 0 );
	        		m.set_data(s);
	        		m.set_date();
	        		textAreaMessage.append("Message envoyé : " + s + "\n");
	        		textAreaMessage.append(m.get_date() + "\n");
	        		client1.set_message(m);
	        		client1.run();
	        		inputMessage.setText("");
        		}
        	}
        });
        
        updateBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ev) {
        		//listUsers.removeAll();
        		Message message_broadcast = new Message("BROADCAST",user_local, null, 0);
        		message_broadcast.set_data("Automatique");
        		client1.set_message(message_broadcast);
        		client1.run();
        	}
        	
        });
        
        listUsers.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				jList1ValueChanged(e,listUsers,client1);
				textAreaMessage.setText(null);
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
        		}	
        		else {
        			f.setVisible(true);
        		}
        		
        	}
        });
    } 
    
    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt, JList listUsers,client_udp c) {
        //set text on right here
    	if(listUsers.getModel().getSize()!=0) {
	        String s = (String) listUsers.getSelectedValue();
	        
	        for(User u : user_local.Connected_Users) {
	        	if (s.equals(u.get_pseudo())) {
	        		c.set_dest(u);
	        		System.out.println("changement de destinataire" + u.get_IP());
	        	}
	            
	        }
    	}
	}
    
}
