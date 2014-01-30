/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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