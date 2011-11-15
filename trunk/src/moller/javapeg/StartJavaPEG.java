package moller.javapeg;

import javax.swing.JOptionPane;

import moller.javapeg.program.ApplicationUncaughtExceptionHandler;
import moller.javapeg.program.MainGUI;
import moller.util.os.OsUtil;

public class StartJavaPEG {

	public static void main (String [] args){
		ApplicationUncaughtExceptionHandler.registerExceptionHandler();

		boolean supportedOS = false;

		String osName = OsUtil.getOsName();

		if (osName.toLowerCase().contains("windows")) {
		    supportedOS = true;
		} else if (osName.toLowerCase().contains("linux")) {
		    supportedOS = true;
		}

		if (supportedOS) {
		    MainGUI mainGUI = new MainGUI();
            mainGUI.setVisible(true);
		} else {
		    JOptionPane.showMessageDialog(null, "Unsupported operating system" + OsUtil.getOsName() + ".\n\nThe supported operating systems are:\nWindows\nLinux", "Error", JOptionPane.ERROR_MESSAGE);
        }
	}
}
