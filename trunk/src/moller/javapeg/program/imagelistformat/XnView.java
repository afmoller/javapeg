package moller.javapeg.program.imagelistformat;
/**
* This class was created : 2009-08-11 by Fredrik Möller
* Latest changed         : 2009-08-21 by Fredrik Möller
*/

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.io.FileUtil;

public class XnView {
	
	private static final String NL = System.getProperty("line.separator");
	
	private static final String PROLOG = "# Slide Show Sequence"        + NL +
                                         "Timer = 5.000000"             + NL +
                                         "Loop = 1"                     + NL +
                                         "FullScreen = 1"               + NL +
                                         "MouseNav = 0"                 + NL +
                                         "TitleBar = 0"                 + NL +
                                         "Fit = 0"                      + NL +
                                         "StrechVideo = 0"              + NL +
                                         "High = 1"                     + NL +
                                         "CenterWindow = 1"             + NL +
                                         "OnTop = 0"                    + NL +
                                         "Frame = 1"                    + NL +
                                         "ReadErrors = 1"               + NL +
                                         "RandomOrder = 0"              + NL +
                                         "ShowText = 1"                 + NL +
                                         "Text = <Directory><Filename>" + NL +
                                         "BackgroundColor = 0"          + NL +
                                         "FontName = \"Courier\""       + NL +
                                         "FontBold = 0"                 + NL +
                                         "FontItalic = 0"               + NL +
                                         "FontHeight = -13"             + NL +
                                         "TextOpacity = 255"            + NL +
                                         "TextColor = ffffff"           + NL +
                                         "TextPosition = 0"             + NL +
                                         "Effect = 1"                   + NL +
                                         "EffectMask = 268435455"       + NL;
		
	private static Logger  logger = Logger.getInstance();
	
	public static void createAndWriteToFile(DefaultListModel images, File file, Language lang) {
		
		boolean success = true;
		
		StringBuilder sb = new StringBuilder(512);
		
		if(FileUtil.createFile(file)) {
			sb.append(PROLOG);
			
			for (int i = 0; i < images.size(); i++) {
				sb.append("\"" + ((File)images.get(i)).getAbsolutePath() + "\"" + NL);
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
			JOptionPane.showMessageDialog(null, lang.get("maingui.tabbedpane.imagelist.imagelistformat.xnView.successfullyCreated"), "", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, lang.get("maingui.tabbedpane.imagelist.imagelistformat.xnView.notSuccessfullyCreated"), "", JOptionPane.ERROR_MESSAGE);
		}	
	}
}