package stockmarketapp.staticlogic;

import java.io.IOException;

import stockmarketapp.MainApp;

/**
 * Handles what occurs when the return button is pressed.
 * Note that most of the methods in this class are *only* executed if the input is successful, i.e. does not contain any errors.
 * The first method deals with what happens when the return button is pressed, including checking for any input.
 * @author Sadruddin Junejo
 *
 */
public class ReturnButtonLogic {
	
	/** added to the front of the user input */
	private final static String URLPREFIX = "http://finance.yahoo.com/d/quotes.csv?s="; 
	/** added to the end of the user input */
	private final static String URLPOSTFIX = "&f=nl1pdj1xe1s"; 
	
	/**
	 * This method carries out input validation and executes the rest of the methods in this class if the input tests positive.
	 */
	public static void buttonPressed() {
		String inputString = MainApp.getInputString(); // input taken as a string
		MainApp.setInputString(""); // clears input
		// The code below checks for valid input.
		if (inputString.length() > 0 && !inputString.equals(" ")){
			try {
				// See "mainProcessing"  for more info on the below piece of code.
				// Also, note the replacing of spaces with '+' signs. This is necessary for the URL setup done later.
				MainApp.setOutputString(ReturnButtonLogic.mainProcessing(inputString.replaceAll(" ", "+")));
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		else { // print error message resulting from lack of input
			MainApp.setOutputString("No input detected. Please enter at least one symbol.");
		}
	}	
	
	/**
	 * Runs each of the other methods in this class in turn. In order, the steps are as follows:
	 * (1) Converts user input to a URL (using this class's setupURL method) and obtains data from Yahoo Quotes using this URL.
	 * 	   Accomplished via the URLReader.readURL() method.
	 * (2) Splits the output into an array with each row corresponding to data for a specific company
	 * (3) Splits each individual row into columns. The data then forms a 2-dimensional array, 
	 * 	   with rows corresponding to company data and columns corresponding to the type of data.
	 * 	   This is accomplished using the splitter() method.
	 * (4) For each company's set of data, performs necessary calculations for price change and determines 
	 *     the currency of the stock.
	 * 	   Then combines the data into a string and concatenates it to a large string (processedDataString) that contains the whole
	 *     output.
	 * 	   This is accomplished using the processData() method found in this class.
	 * @param inputString the string that is input using the keyboard
	 * @return a large string containing the processed data. Uses HTML for presentation purposes.
	 * @throws IOException in case URLReader fails
	 */
	static String mainProcessing(String inputString) throws IOException {
			String[] rawDataArray;
			String[][] processedDataArray = null;
			String processedDataString = ""; // the string that contains all the stock data
			String URL = setupURL(inputString);
			String outputString = URLReader.readURL(URL); // see URLReader class for more info
			rawDataArray = outputString.split("\n"); // splits data according to each specific company
			processedDataArray = new String[rawDataArray.length][8];
			for (int i = 0; i < rawDataArray.length; i++){
				processedDataArray[i] = splitter(rawDataArray[i]);
			}
			for (int j = 0; j < rawDataArray.length; j++){
				 processedDataString = processedDataString + processData(processedDataArray[j]);
			}
			return "<html>" + processedDataString + "</html>";
	} // End of Main Processing
	
	/**
	 * Creates the URL that is used to obtain data from Yahoo! quotes.
	 * @param str The input from the user
	 * @return A complete URL
	 */
	static String setupURL(String str){
		String URL = URLPREFIX + str + URLPOSTFIX;
		return URL;
	}
	
	/**
	 * This method takes *one* set of data taken from Yahoo! Quotes, performs the necessary calculations
	 * and returns what is processed in the form of a string which can be output.
	 * It returns stock information contained in a string.
	 * This information includes (1) Company name (2) Price of stock (3) Absolute price change + percentage price change 
	 * (3) Dividends (5) Market Capitalisation (6) Stock Exchange company is listed in 
	 * If the data is erroneous (for example, the stock symbol requested was not found in Yahoo! Quotes),
	 * the method returns a string explaining that the symbol was not found.
	 * @param strArray Contains the set of data for one stock symbol.
	 * @return a string containing the relevant information. If a stock symbol is not valid, returns "Queried symbol not found".
	 * @throws IOException
	 */
	static String processData(String[] strArray) { 
		// headers for data values
		String strPrice = "Price: ";
		String strDividend = "Dividends: ";
		String strMarketCap = "Market Cap: ";
		String strStockExchange = "Stock Exchange: ";
		String strChange = "Change: ";
		
		String mainString = ""; // the string that is returned at the end of the method execution
		
		
		 // first, checks for error indication from Yahoo Quotes.
		if (strArray[6].equals("\"N/A\"")){ // Executed if there are no errors
			try {
				String currency = detCurrency(strArray[5]);
				
				// Calculation of Change in Price
				double currentPrice = Double.parseDouble(strArray[1]);
				double previousPrice = Double.parseDouble(strArray[2]);
				double absolutePriceChange = currentPrice - previousPrice;
				double absolutePriceChangePercent = (absolutePriceChange/previousPrice) * 100;
				// End of Price Change Calculation
			
				// rounds both the below numbers to a suitable degree of accuracy. See 'round' method for more info.
				absolutePriceChange = round(absolutePriceChange);
				absolutePriceChangePercent = round(absolutePriceChangePercent);
		
				// display name of stock (without quotes)
				mainString = "<u>" + strArray[0].substring(1,strArray[0].length()-1) + "</u><br />";
		
				// display current price per share and currency (determined by detCurrency method)
				mainString = mainString + strPrice + strArray[1] + currency + "<br />";
					
				mainString = mainString + strChange;
				if (absolutePriceChange < 0){
					// price change is negative, therefore displayed in red
					mainString = mainString + "<font color = '#FF0000'>" + 
					absolutePriceChange + " (" + absolutePriceChangePercent + "%)</font><br />";	
				}
				else if (absolutePriceChange > 0){
					// price change is positive, therefore displayed in green
					mainString = mainString + "<font color = '#4AA02C'>" + absolutePriceChange +
						" (+" + absolutePriceChangePercent + "%)</font><br />";	
				}
				else {
					// price has not changed recently, therefore displayed in black
					mainString = mainString + absolutePriceChange + " (" + absolutePriceChangePercent + "%)<br />";
				}
				
				mainString = mainString + strDividend + strArray[3]  + "<br />"; // display dividend
				mainString = mainString + strMarketCap + strArray[4] + "<br />"; // display market capitalisation
			
				// display name of stock (without quotes)
				mainString = mainString + strStockExchange + strArray[5].substring(1, strArray[5].length()-1) + "<br /><br />";
				
			}
			catch (NumberFormatException e){ // executed if price calculations fail because of lack of data from Yahoo! Quotes
				mainString = mainString + "Data unavailable for " + strArray[0].substring(1,strArray[0].length()-1) + ". <br /><br />";
			}
		}
		else { // Executed if there is an error (according to Yahoo Quotes)
			mainString = mainString + strArray[0].substring(1,strArray[0].length()-1) + " not found. <br /><br />";
		}
		return mainString;
	} // End of ProcessData() definition

	/**
	 * Splitting algorithm that takes into account commas in between quotes, for example retaining 
	 * the comma in "Canon, Inc America"
	 * Works as follows: the data for each company is initially a string consisting of individual pieces of data 
	 * separated by commas.
	 * This method works through the data string starting from the end of the string
	 * and counts the number of commas.
	 * Every time it finds a comma, it adds the data right after that comma but before the comma in front.
	 * By the time seven commas are counted, the method simply adds the rest of the data, which would be the company name.
	 * This keeps any commas in the company name intact.
	 * @param str The string that contains the data retrieved from Yahoo! Quotes
	 * @return Each piece of data related to a single company contained in an array
	 */
	static String[] splitter(String str){
		String[] strArray = new String[8];
		int numberOfCommas = 0;
		int i = str.length()-1;
		while (numberOfCommas < 7){
			if (str.charAt(i) == ','){
				strArray[7-numberOfCommas] = str.substring(i+1,str.length());
				str = str.substring(0,i);
				numberOfCommas++;
			}
			i--;
		}
		strArray[0] = str;
		return strArray;
	} // End of spitter definition
	
	/**
	 * This method determines which currency symbol to use according to the stock exchange the specified company
	 * is listed in.
	 * @param stockExchange the stock symbol, surrounded by quotes. The quotes are removed within the method.
	 * @return the appropriate currency symbol 
	 */
	static String detCurrency(String stockExchange){
		stockExchange = stockExchange.substring(1,stockExchange.length()-1); // removal of quotes
		if (stockExchange.equals("NasdaqNM") || stockExchange.equals("NYSE")) 	return "$";
		if (stockExchange.equals("Brussels") || stockExchange.equals("Paris")) 	return "EUR";
		if (stockExchange.equals("SES")) 										return "S$";
		if (stockExchange.equals("London")) 									return "p";
		if (stockExchange.equals("HKSE")) 										return "HK$";
		if (stockExchange.equals("NCM"))										return "AU$";
		
		return ""; // if the stock exchange the company is listed is not one of the above
	}

	/**
	 * Simple rounding method for absolute price change and price change percent numbers.
	 * @param dbl the number to be rounded
	 * @return the rounded number
	 */
	static double round(double dbl){
		dbl = dbl * 1000;
		double rounded = Math.round(dbl);
		rounded = rounded/1000;
		return rounded;
	} // End of round definition
	
} // End of ReturnButtonLogic definition