package stockmarketapp.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import stockmarketapp.MainApp; 
import stockmarketapp.gui.buttons.InputButton;
import stockmarketapp.gui.buttons.KeyboardButton;
import stockmarketapp.staticlogic.ReturnButtonLogic;

/**
 * Constructs the window which contains, from top to bottom:
 * (1) The output that is displayed once the user presses the "Return Button".
 * (2) A label displaying the current input
 * (3) An on-screen keyboard consisting of the letters A-Z, the numbers 0-9, '.' and a spacebar to allow for multiple
 * 	   symbol input.
 *	   Additionally, a "Delete" button is included, as well as a "Return" button. 
 * @author Sadruddin Junejo
 *
 */
public class MainWindow extends JFrame {
	/** This label contains the input string that is added to when the user clicks an input button */
	public JLabel inputStringLabel; 
	/** This label contains the output string that displays data (or an error message) after "return" key is pressed */
	public JLabel outputStringLabel; 
	/** The panel which, directly or indirectly, contains all the widgets of the program */
	private JPanel mainPanel; 
	/** Contains both the input and output panels */
	private JPanel inputOutputPanel; 
	private JPanel outputPanel;
	private JPanel inputPanel;
	/** the panel which contains the on-screen keyboard buttons */
	private JPanel keyPanel; 
	
	/**
	 * Constructor class. This runs each creation step in turn.
	 */
	public MainWindow(){
		this.setMinimumSize(new Dimension(554,349));
		this.createMainPanel();
		this.createInputOutputPanel();
		this.createKeyPanel();
	}
	
	/**
	 * Creates the main panel.
	 * The main panel utilises a grid layout consisting of 2 equal-sized rows.
	 * The 1st row is for the input and output fields, while the 2nd is for the on-screen keyboard. 
	 */
	private void createMainPanel(){
		mainPanel = new JPanel(new GridLayout(2,1));
		this.add(mainPanel);
	}
	
	/**
	 * Creates the input/output panel which forms the 1st row of the program window.
	 */
	private void createInputOutputPanel(){
		inputOutputPanel = new JPanel(new BorderLayout());
		
		// Construction of output subpanel
		outputPanel = new JPanel(new BorderLayout());
		outputPanel.add(new JLabel(" Output:    "), BorderLayout.WEST);
		outputStringLabel = new JLabel();
		JScrollPane outputScrollPane = new JScrollPane(); // implementing a scroll bar
		outputScrollPane.getViewport().add(outputStringLabel);
		outputPanel.add(outputScrollPane, BorderLayout.CENTER);
		inputOutputPanel.add(outputPanel);
		
		// construction of input subpanel
		inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		inputPanel.add(new JLabel("Input: "));
		inputStringLabel = new JLabel();
		inputPanel.add(inputStringLabel);
		inputOutputPanel.add(inputPanel, BorderLayout.SOUTH);
		
		mainPanel.add(inputOutputPanel);
	} // End of createInputOutputPanel definition
	
	/**
	 * Creates the on-screen keyboard which forms the 2nd row of the program window.
	 */
	private void createKeyPanel(){
		keyPanel = new JPanel(new GridLayout(5,1)); // The keyboard consists of five rows.
	
		// Row of Numbers 0-9
		JPanel numbersRow = new JPanel(new FlowLayout());
		for (int i = 48; i <= 57 ; i++){
			numbersRow.add(new InputButton(Character.toString ((char) i)));
		}
		keyPanel.add(numbersRow);
		
		String[] jButtonSet1 = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"}; // Buttons for row 2
		String[] jButtonSet2 = {"A", "S", "D", "F", "G", "H", "I", "J", "K", "L"}; // Buttons for row 3 
		String[] jButtonSet3 = {"Z", "X", "C", "V", "B", "N", "M", "."}; // Buttons for row 4
		
		// Row of Letters QWERTYUIOP + Delete Key
		JPanel lettersRow1 = new JPanel(new FlowLayout());
		for (String input: jButtonSet1){
			lettersRow1.add(new InputButton(input));
		}
		lettersRow1.add(new KeyboardButton("Delete") { // Addition of delete button as anonymous inner class
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (MainApp.getInputString().length() > 0){ // checks that input is NOT empty before executing deletion
					MainApp.setInputString(MainApp.getInputString().substring(0, MainApp.getInputString().length()-1)); // ACTUAL DELETION
				}
			}
		}); // end of anonymous class definition
		keyPanel.add(lettersRow1);
		
		// Row of Letters ASDFGHIJKL + Return Key
		JPanel lettersRow2 = new JPanel(new FlowLayout());
		for (String input: jButtonSet2){
			lettersRow2.add(new InputButton(input));
		}
		lettersRow2.add(new KeyboardButton("Return") { // Addition of return button key (with definition as an anonymous inner class)
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ReturnButtonLogic.buttonPressed(); 
			}			
		}); // End of return button definition
		keyPanel.add(lettersRow2);
		
		// Row of Letters ZXCVBNM + '.' key
		JPanel lettersRow3 = new JPanel(new FlowLayout());
		for (String input: jButtonSet3){
			lettersRow3.add(new InputButton(input));
		}
		keyPanel.add(lettersRow3);
		
		// Creation of spacebar in row 5
		JPanel lettersRow4 = new JPanel(new FlowLayout());
		lettersRow4.add(new InputButton("Spacebar"));
		keyPanel.add(lettersRow4);
		
		mainPanel.add(keyPanel);
	} // End of createKeyPanel definition

} // End of Main Window class definition
