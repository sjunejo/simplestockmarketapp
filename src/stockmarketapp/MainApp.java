package stockmarketapp;

import javax.swing.JFrame;

import stockmarketapp.gui.MainWindow;

/**
 * The purpose of this program is to display company and shares data requested by the user.
 * It uses an on-screen keyboard to enter stock symbols.
 * The stock symbols are then searched for in Yahoo Quotes' database, and the relevant data is processed and output. 
 * The MainApp class contains the "main" method that runs when the program is started.
 * It creates the user interface window.
 * @author Sadruddin Junejo
 */
public class MainApp  {
	
	/** the window that forms the basis of the program **/
	static MainWindow program; 
	
	/**
	 * Serves as the starting point for the program, creating the window the program runs in.
	 * @param args
	 */
	public static void main(String[] args){
		program = new MainWindow(); // See MainWindow class for more info.
		program.setTitle("Simple Stock Market App");
		program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		program.pack();
		program.setVisible(true);
	}
	
	/**
	 * Method for setting the input whenever the user presses an input button.
	 * @param str the text from the input button pressed
	 */
	public static void setInputString(String str){
		program.inputStringLabel.setText(str);
	}
	
	/**
	 * Retrieves the current input.
	 * @return the current input
	 */
	public static String getInputString(){
		return program.inputStringLabel.getText();
	}
	
	/**
	 * Used for setting the output (stock data)
	 * @param str the stock data
	 */
	public static void setOutputString(String str){
		program.outputStringLabel.setText(str);
	}
	
}
