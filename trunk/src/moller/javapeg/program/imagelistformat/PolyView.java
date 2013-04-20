package moller.javapeg.program.imagelistformat;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import moller.javapeg.program.C;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.io.FileUtil;

public class PolyView {

    private static final String PROLOG = "<randomize 0"    + C.LS +
                                         "<animate 0"      + C.LS +
                                         "<time 10"        + C.LS +
                                         "<absolute 0"     + C.LS +
                                         "<fullpath 1"     + C.LS +
                                         "<displaynames 1" + C.LS +
                                         "<onlyname 0"     + C.LS +
                                         "<notype 0"       + C.LS +
                                         "<color 0"        + C.LS +
                                         "<zoom 0"         + C.LS +
                                         "<fullyerase 0"   + C.LS +
                                         "<effect 0"       + C.LS +
                                         "<dissolve 0"     + C.LS +
                                         "<cycle 1"        + C.LS +
                                         "<windowed 0"     + C.LS;

    private static Logger  logger = Logger.getInstance();

    public static void createAndWriteToFile(DefaultListModel<File> images, File file, Language lang) {

        boolean success = true;

        StringBuilder sb = new StringBuilder(512);

        if(FileUtil.createFile(file)) {
            sb.append(PROLOG);

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
            JOptionPane.showMessageDialog(null, lang.get("maingui.tabbedpane.imagelist.imagelistformat.polyView.successfullyCreated"), "", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, lang.get("maingui.tabbedpane.imagelist.imagelistformat.polyView.notSuccessfullyCreated"), "", JOptionPane.ERROR_MESSAGE);
        }
    }
}