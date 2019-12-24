import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Scrollbar;

public class Interface_accueil {
    
	public int port_src;
	public int port_dest;
	public User user_local;
	public JTextArea textAreaMessage;
	
    public Interface_accueil(String l, int init_port_src, int init_port_dest, User u_local){
    	
    	port_src = init_port_src;
    	port_dest = init_port_dest;
    	user_local = u_local;
    	
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
        JList listUsers = new JList();
		listUsers.setBackground(Color.cyan);
		listUsers.setModel(new AbstractListModel() {
			String[] values = new String[] {"USER 1", "USER 2", "USER 3", "USER 4"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		userPanel.add(listUsers);
		
        
        textAreaMessage = new JTextArea();
        conversationPanel.add(textAreaMessage,BorderLayout.CENTER);
        textAreaMessage.setEditable(false);
       
        JTextField inputMessage = new JTextField();
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
        
        server_udp server = new server_udp(port_src, user_local, textAreaMessage);
    }
}
