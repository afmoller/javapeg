package moller.javapeg.program.imagelistformat;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import moller.javapeg.program.C;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.io.FileUtil;

public class IrfanView {

	private static Logger  logger = Logger.getInstance();

	public static void createAndWriteToFile(DefaultListModel<File> images, File file, Language lang) {

		boolean success = true;

		StringBuilder sb = new StringBuilder(512);

		if(FileUtil.createFile(file)) {
			for (int i = 0; i < images.size(); i++) {
				sb.append(images.get(i).getAbsolutePath() + C.LS);
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
