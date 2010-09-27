package moller.util.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * This Class extends the existing JOptionPane and fills a gap that exists in
 * that class. There is no possibility to show an input dialog with custom 
 * title and a default value entered in the input area in the JOptionPane class
 * so this class adds that functionality.
 *  
 * @author Fredrik
 *
 */
public class CustomJOptionPane extends JOptionPane {
	
	private static final long serialVersionUID = 1L;

    public static String showInputDialog(Component parentComponent, Object message, String title, Object initialSelectionValue) {
        return (String)showInputDialog(parentComponent, message, title, QUESTION_MESSAGE, null, null, initialSelectionValue);
    }
}
