package moller.javapeg;
/**
 * This class was created : 2009-02-25 by Fredrik M�ller
 * Latest changed         : 2009-09-03 by Fredrik M�ller
 */

import moller.javapeg.program.MainGUI;
import moller.javapeg.program.ApplicationUncaughtExceptionHandler;

public class StartJavaPEG {
	
	public static void main (String [] args){
	
		ApplicationUncaughtExceptionHandler.registerExceptionHandler();
		
		MainGUI mainGUI = new MainGUI();
		mainGUI.setVisible(true);
	}
}