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

import moller.javapeg.program.C;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.io.FileUtil;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class JavaPEG {

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
            JOptionPane.showMessageDialog(null, lang.get("maingui.tabbedpane.imagelist.imagelistformat.javaPEG.successfullySaved"), "", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, lang.get("maingui.tabbedpane.imagelist.imagelistformat.javaPEG.notSuccessfullySaved"), "", JOptionPane.ERROR_MESSAGE);
        }
    }
}
