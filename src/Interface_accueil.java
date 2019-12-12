//importations
import java.awt.*;
import javax.swing.*;


public class Interface_accueil {
	//fenêtre principale englobant le tout
	JFrame main_frame;
	//sous-fenêtre où nous afficherons la liste des users connectés
    JPanel list_user_panel;
    //sous-fenêtre ou nous afficherons la conversation en cours
    JPanel conv_panel;
    JTextField textField;
    JButton button;
    Container contentPane;
	
	private static void createAndShowGUI() {
		
		JFrame frame = new JFrame("Accueil");
		frame.setDefaultLookAndFeelDecorated(true);
		
		//si on veut l'avoir en plein ecran directement
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		//Si on veut choisir une taille à l'ouverture
		//frame.setSize(1000,800);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
        
		JPanel panel = new JPanel();
		
		
    }
	
	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
