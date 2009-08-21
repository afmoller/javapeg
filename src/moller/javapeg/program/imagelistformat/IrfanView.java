package moller.javapeg.program.imagelistformat;
/**
* This class was created : 2009-07-24 by Fredrik Möller
* Latest changed         : 2009-08-21 by Fredrik Möller
*/

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import moller.javapeg.program.language.Language;
import moller.util.io.FileUtil;
import moller.util.logger.Logger;

public class IrfanView {

private static final String NL = System.getProperty("line.separator");
	
	private static Logger  logger = Logger.getInstance();
	
	public static void createAndWriteToFile(DefaultListModel images, File file, Language lang) {
		
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
			JOptionPane.showMessageDialog(null, lang.get("maingui.tabbedpane.imagelist.imagelistformat.irfanView.successfullyCreated"), "", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, lang.get("maingui.tabbedpane.imagelist.imagelistformat.irfanView.notSuccessfullyCreated"), "", JOptionPane.ERROR_MESSAGE);
		}	
	}
}