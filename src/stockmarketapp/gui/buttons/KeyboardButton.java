package stockmarketapp.gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
/**
 * Superclass from which the input button, return button and delete buttons are derived.
 * See InputButton.java for more info on the input button and MainWindow.java for more info on the delete and return buttons.
 * @author Sadruddin Junejo
 *
 */
public abstract class KeyboardButton extends JButton implements ActionListener {
	/**
	 * The constructor method which every button uses.
	 * @param str the character (or characters) that are to depict the button.
	 */
	public KeyboardButton(String str){
		this.setText(str);
		/*The line below enables activation of actionPerformed method within the class.
		 Therefore there is no need to create an additional ActionListener class for every button. */
		this.addActionListener(this); 
	}
	
	/**
	 * The method that is executed when any button is pressed. Overridden by the subclasses of KeyBoardButton.
	 */
	@Override
	public abstract void actionPerformed(ActionEvent arg0); // Method that runs when the button is pressed.
}
