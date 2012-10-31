package stockmarketapp.gui.buttons;
import java.awt.event.ActionEvent;

import stockmarketapp.MainApp;

/**
 * Button that, when pressed, adds a specific character to the input string.
 * Note that it extends the KeyboardButton class; please see the KeyboardButton.java for more info.
 * @author Sadruddin Junejo
 *
 */
public class InputButton extends KeyboardButton {
	
	/**
	 * Constructor method for input button.
	 * Uses the KeyboardButton's construction class
	 * @param str the label of the button
	 */
	public InputButton(String str){
		super(str); // Invokes KeyBoardButton's construction method.
	}
	
	/**
	 * Executed when the button is pressed.
	 */
	public void actionPerformed(ActionEvent e) { 
		// The below condition is used to make sure that there is no more than one space in succession.
		// This condition is necessary for testing the length of individual symbols.
		if (!(
				MainApp.getInputString().length() != 0	
				&& MainApp.getInputString().charAt(MainApp.getInputString().length()-1) == ' ' 
				&& this.getText().equals("Spacebar")
				)){
			if (this.getText().equals("Spacebar")){ // If the spacebar is used
				MainApp.setInputString(MainApp.getInputString() + " "); // add a space.
			}
			else if (MainApp.getInputString().length()-MainApp.getInputString().lastIndexOf(' ') != 9){ // checks if the current symbol is no longer than 8 characters
				// The line that is executed if the above condition if fulfilled.
				// Adds a character unique to the button pressed.
				MainApp.setInputString(MainApp.getInputString() + this.getText());
			}
		
		}
	} // End of actionPerformed
	
} // End of InputButton definition
