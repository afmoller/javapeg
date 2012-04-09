package moller.javapeg.program.imagelistformat;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import moller.javapeg.program.C;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.io.FileUtil;

public class XnView {

	private static final String PROLOG = "# Slide Show Sequence"        + C.LS +
                                         "Timer = 5.000000"             + C.LS +
                                         "Loop = 1"                     + C.LS +
                                         "FullScreen = 1"               + C.LS +
                                         "MouseNav = 0"                 + C.LS +
                                         "TitleBar = 0"                 + C.LS +
                                         "Fit = 0"                      + C.LS +
                                         "StrechVideo = 0"              + C.LS +
                                         "High = 1"                     + C.LS +
                                         "CenterWindow = 1"             + C.LS +
                                         "OnTop = 0"                    + C.LS +
                                         "Frame = 1"                    + C.LS +
                                         "ReadErrors = 1"               + C.LS +
                                         "RandomOrder = 0"              + C.LS +
                                         "ShowText = 1"                 + C.LS +
                                         "Text = <Directory><Filename>" + C.LS +
                                         "BackgroundColor = 0"          + C.LS +
                                         "FontName = \"Courier\""       + C.LS +
                                         "FontBold = 0"                 + C.LS +
                                         "FontItalic = 0"               + C.LS +
                                         "FontHeight = -13"             + C.LS +
                                         "TextOpacity = 255"            + C.LS +
                                         "TextColor = ffffff"           + C.LS +
                                         "TextPosition = 0"             + C.LS +
                                         "Effect = 1"                   + C.LS +
                                         "EffectMask = 268435455"       + C.LS;

	private static Logger  logger = Logger.getInstance();

	public static void createAndWriteToFile(DefaultListModel<File> images, File file, Language lang) {

		boolean success = true;

		StringBuilder sb = new StringBuilder(512);

		if(FileUtil.createFile(file)) {
			sb.append(PROLOG);

			for (int i = 0; i < images.size(); i++) {
				sb.append("\"" + images.get(i).getAbsolutePath() + "\"" + C.LS);
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
