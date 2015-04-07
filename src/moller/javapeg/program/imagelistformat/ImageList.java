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

import moller.javapeg.program.language.Language;
import moller.util.io.FileUtil;

import javax.swing.*;
import java.io.File;

public class ImageList {

    /**
     * The static singleton instance of this class.
     */
    private static ImageList instance;

    /**
     * Language instance, to be able to have localization
     */
    private static Language lang;

    /**
     * Private constructor.
     */
    private ImageList() {
        lang = Language.getInstance();
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static ImageList getInstance() {
        if (instance != null)
            return instance;
        synchronized (ImageList.class) {
            if (instance == null) {
                instance = new ImageList();
            }
            return instance;
        }
    }

    public void createList(DefaultListModel<File> images, File destination, String fileExtension, String fileDescription) {

        /**
         * This check is done to see if an file extension has been entered in
         * the JFileChooser. If it is missing, the extension will be added here
         */
        if(!FileUtil.isOfType(fileExtension, destination)) {
            destination = new File(destination.getAbsolutePath() + "." + fileExtension);
        }

        if(destination.exists()) {
            int returnValue = JOptionPane.showConfirmDialog(null, destination.getAbsolutePath() + " " + lang.get("maingui.tabbedpane.imagelist.imagelistformat.imageList.listAlreadyExists"));

            /**
             * 0 indicates a yes answer, and then the file shall be overwritten
             * and that is accomplished by first delete the old file and the
             * create a new.
             */
            if(returnValue == 0) {
                destination.delete();
            }
        }

        if (fileDescription.equals("IrfanView")) {
            IrfanView.createAndWriteToFile(images, destination, lang);
        } else if (fileDescription.equals("PolyView")) {
            PolyView.createAndWriteToFile(images, destination, lang);
        } else if (fileDescription.equals("XnView")) {
            XnView.createAndWriteToFile(images, destination, lang);
        } else if (fileDescription.equals("JavaPEG Image List")) {
            JavaPEG.createAndWriteToFile(images, destination, lang);
        }
    }
}