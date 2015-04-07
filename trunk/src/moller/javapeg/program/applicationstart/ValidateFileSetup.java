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
package moller.javapeg.program.applicationstart;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ValidateFileSetup {

    /**
     * Performs an existence check of the, for JavaPEG, necessary files. If an
     * file is missing. An attempt is done to replace the missing file with an
     * default file. If this process fails, then an error message is displayed
     * for the user and JavaPEG is exited.
     */
    public static void check() {

        File javaPEGuserHome = new File(C.JAVAPEG_HOME);

        File logDirectory = new File(javaPEGuserHome, "logs");
        File configFile   = new File(javaPEGuserHome, "config" + C.FS + "conf.xml");
        File layoutInfo   = new File(javaPEGuserHome, "resources" + C.FS + "thumb" + C.FS + "layout.info");
        File styleInfo    = new File(javaPEGuserHome, "resources" + C.FS + "thumb" + C.FS + "style.info");

        boolean firstApplicationLaunch = ApplicationBootUtil.isFirstApplicationLaunch();

        checkFileObject(logDirectory, firstApplicationLaunch);
        checkFileObject(configFile, firstApplicationLaunch);
        checkFileObject(layoutInfo, firstApplicationLaunch);
        checkFileObject(styleInfo, firstApplicationLaunch);
    }

    /**
     * Performs a check of an file object. If the {@link File} object to check
     * does not exist and can not be created (restored from an default file)
     * then that error is displayed to the user in an {@link JOptionPane} and
     * thereafter is JavaPEG exited.
     *
     * @param fileToCheck
     *            is the {@link File} object to check the existence for.
     */
    private static void checkFileObject (File fileToCheck, boolean silentCreate) {

        String fileNameToCheck = fileToCheck.getName();

        if (!fileToCheck.exists()) {

            File parent = fileToCheck.getParentFile();

            // If the parent is missing, then create parent first...
            if (!parent.exists()) {
                if (!parent.mkdirs()) {
                    displayErrorMessage("Could not create parent directory: " + parent.getAbsolutePath());
                    System.exit(1);
                } else if (!silentCreate) {
                    displayInformationMessage("Missing parent directory: " + parent.getAbsolutePath() + " has been created");
                }
            }

            // ... then create the actually missing resource.
            if (fileNameToCheck.equals("logs")) {
                if(!fileToCheck.mkdir()) {
                    displayErrorMessage("Could not create directory: " + fileToCheck.getAbsolutePath());
                    System.exit(1);
                } else if (!silentCreate) {
                    displayInformationMessage("Directory: " + fileToCheck.getAbsolutePath() + " has been created");
                }
            } else {
                if(!createFile(fileToCheck)) {
                    displayErrorMessage("Could not create file: " + fileToCheck.getAbsolutePath());
                    System.exit(1);
                } else if (!silentCreate) {
                    displayInformationMessage("File: " + fileToCheck.getAbsolutePath() + " has been created");
                }
            }
        }
    }

    /**
     * Tries to create an missing file by "replacing" the missing file with an
     * application bundled default file.
     *
     * @param fileToCreate
     *            is the file to replace with an application default file.
     * @return true if it was possible to replace the missing file with an
     *         application default file.
     */
    private static boolean createFile(File fileToCreate) {
        try {
            Files.copy(StartJavaPEG.class.getResourceAsStream("resources/startup/" + fileToCreate.getName()), fileToCreate.toPath());
        } catch (IOException iox) {
            displayErrorMessage("Could not create file: " + fileToCreate + " (" + iox.getMessage() + ")");
            return false;
        }
        return true;
    }

    /**
     * Displays an {@link JOptionPane} dialog with an error message.
     *
     * @param message is the message to display.
     */
    private static void displayErrorMessage(String message) {
        displayMessageWithType(message, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an {@link JOptionPane} dialog with an information message.
     *
     * @param message is the message to display.
     */
    private static void displayInformationMessage(String message) {
        displayMessageWithType(message, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays an {@link JOptionPane} dialog with an specified message and
     * title.
     *
     * @param message
     *            is the message to display.
     * @param type
     *            is the type of the {@link JOptionPane} to display.
     */
    private static void displayMessageWithType(String message, int type) {
        JOptionPane.showMessageDialog(null, message, type == JOptionPane.ERROR_MESSAGE ? "Error" : "Information", type);
    }
}
