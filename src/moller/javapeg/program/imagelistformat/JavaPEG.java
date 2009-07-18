package moller.javapeg.program.imagelistformat;
/**
* This class was created : 2009-07-16 by Fredrik Möller
* Latest changed         : 
*/

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import moller.javapeg.program.logger.Logger;
import moller.util.io.FileUtil;

public class JavaPEG {

	private static final String NL = System.getProperty("line.separator");
	
	private static Logger  logger = Logger.getInstance();
	
	public static void createAndWriteToFile(DefaultListModel images, File file) {
		
		boolean success = true;
		
		StringBuilder sb = new StringBuilder(512);
		
		if(FileUtil.createFile(file)) {
			for (int i = 0; i < images.size(); i++) {
				sb.append(((File)images.get(i)).getAbsolutePath() + NL);
			}
	
			try {
				FileUtil.writeToFile(file, sb.toString(), true);
			} catch (IOException e) {
				success = false;
				logger.logERROR("Could not write to file: " + file.getAbsolutePath());
				logger.logERROR(e);
			}
		} else {
			success = false;
			logger.logERROR("Could not create file: " + file.getAbsolutePath());
		}
		
		if(success) {
//			TODO: Remove hard coded string
			JOptionPane.showMessageDialog(null, "The image list was successfully saved.", "", JOptionPane.INFORMATION_MESSAGE);
		} else {
//			TODO: Remove hard coded string
			JOptionPane.showMessageDialog(null, "The image list could not be saved, see log file for details.", "", JOptionPane.ERROR_MESSAGE);
		}	
	}
}