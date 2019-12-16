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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.Scrollbar;

public class GUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		frame.setSize(1000,850);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panelUsers = new JPanel();
		panelUsers.setBounds(12, 12, 159, 795);
		panelUsers.setBackground(Color.GRAY);
		panel.add(panelUsers);
		panelUsers.setLayout(null);
		
		JList listUsers = new JList();
		listUsers.setBounds(12, 12, 135, 771);
		listUsers.setModel(new AbstractListModel() {
			String[] values = new String[] {"User 1", "User 2", "User 3", "User 4"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panelUsers.add(listUsers);
		
		JPanel panelFiles = new JPanel();
		panelFiles.setBounds(811, 12, 170, 795);
		panelFiles.setBackground(Color.LIGHT_GRAY);
		panel.add(panelFiles);
		panelFiles.setLayout(null);
		
		JList listFiles = new JList();
		listFiles.setBounds(12, 5, 146, 778);
		listFiles.setModel(new AbstractListModel() {
			String[] values = new String[] {"File 1", "File 2", "File 3", "File 4"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panelFiles.add(listFiles);
		
		JPanel panelSetText = new JPanel();
		panelSetText.setBounds(183, 726, 622, 72);
		panelSetText.setBackground(Color.RED);
		panel.add(panelSetText);
		panelSetText.setLayout(null);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSend.setBounds(508, 22, 114, 25);
		panelSetText.add(btnSend);
		
		JTextArea textInput = new JTextArea();
		textInput.setBounds(26, 12, 476, 48);
		panelSetText.add(textInput);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(183, 12, 617, 710);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(27, 33, 542, 60);
		panel_1.add(textPane);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
	}
}
