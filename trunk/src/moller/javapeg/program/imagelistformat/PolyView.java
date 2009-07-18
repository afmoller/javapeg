package moller.javapeg.program.imagelistformat;
/**
* This class was created : 2009-07-18 by Fredrik Möller
* Latest changed         : 
*/

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import moller.javapeg.program.logger.Logger;
import moller.util.io.FileUtil;

public class PolyView {
	
	private static final String NL = System.getProperty("line.separator");
	
	private static final String PROLOG = "<randomize 0"    + NL +
                                         "<animate 0"      + NL +
                                         "<time 10"        + NL +
                                         "<absolute 0"     + NL +
                                         "<fullpath 1"     + NL +
                                         "<displaynames 1" + NL +
                                         "<onlyname 0"     + NL +
                                         "<notype 0"       + NL +
                                         "<color 0"        + NL +
                                         "<zoom 0"         + NL +
                                         "<fullyerase 0"   + NL +
                                         "<effect 0"       + NL +
                                         "<dissolve 0"     + NL +
                                         "<cycle 1"        + NL +
                                         "<windowed 0"     + NL;
	
	private static Logger  logger = Logger.getInstance();
	
	public static void createAndWriteToFile(DefaultListModel images, File file) {
		
		boolean success = true;
		
		StringBuilder sb = new StringBuilder(512);
		
		if(FileUtil.createFile(file)) {
			sb.append(PROLOG);
			
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
			JOptionPane.showMessageDialog(null, "The Image list in PolyView format was successfully created.", "", JOptionPane.INFORMATION_MESSAGE);
		} else {
//			TODO: Remove hard coded string
			JOptionPane.showMessageDialog(null, "The Image list in PolyView format could not be created, see log file for details.", "", JOptionPane.ERROR_MESSAGE);
		}	
	}

}
