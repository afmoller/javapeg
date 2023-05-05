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
package moller.javapeg;

import moller.javapeg.program.ApplicationUncaughtExceptionHandler;
import moller.javapeg.program.C;
import moller.javapeg.program.applicationstart.ApplicationBootUtil;
import moller.javapeg.program.applicationstart.ValidateFileSetup;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.controller.ConfigHandler;
import moller.javapeg.program.config.importconfig.ConfigImporter;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.Language;
import moller.javapeg.program.firstlaunch.InitialConfigGUI;
import moller.javapeg.program.firstlaunch.InitialConfigGUILanguage;
import moller.javapeg.program.gui.frames.MainGUI;
import moller.javapeg.program.language.ISO639;
import moller.util.os.OsUtil;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * This is the entry point class that starts the entire JavaPEG application.
 *
 * @author Fredrik
 *
 */
public class StartJavaPEG {

    /**
     * The main method of this application, the entry point to the application.
     *
     * @param args
     */
    public static void main (String [] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                ApplicationUncaughtExceptionHandler.registerExceptionHandler();

                if (!isSupportedOS()) {
                    JOptionPane.showMessageDialog(null, "Unsupported operating system" + OsUtil.getOsName() + ".\n\nThe supported operating systems are:\nWindows", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean startApplication = true;

                // Make an initial application configuration if it is the first
                // application start after the installation.
                if (ApplicationBootUtil.isFirstApplicationLaunch()) {

                    InitialConfigGUI initialConfigGUI = new InitialConfigGUI();
                    initialConfigGUI.setVisible(true);

                    if (initialConfigGUI.isContinueButtonClicked()) {

                        ValidateFileSetup.check();

                        if (initialConfigGUI.isImport()) {
                            startApplication = performConfigurationImport(initialConfigGUI.getImportPath());
                        } else {
                            setLanguage(initialConfigGUI.getLanguage());
                        }

                        if (startApplication) {
                            try {
                                ApplicationBootUtil.removeFirstApplicationLaunchMarkerFile();
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, "Could not delete first application launch marker file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        startApplication = false;
                    }
                }

                // Start the application if the configuration import was
                // successfully performed or if it was not the first application
                // start after the installation.
                if (startApplication) {
                    MainGUI mainGUI = new MainGUI();
                    mainGUI.setVisible(true);
                }
            }
        });
    }

    /**
     * Determines if the JavaPEG application has been started on a supported
     * operating system.
     *
     * @return true if the JavaPEG application has been started on a supported
     *         operating system, otherwise false.
     */
    private static boolean isSupportedOS() {
        String osName = OsUtil.getOsName();
        return osName.toLowerCase().contains("windows");
    }

    /**
     * This method makes an configuration import or displays an error message if
     * the path to the configuration to import is null.
     *
     * @param importPath
     *            specifies which configuration file to import.
     * @return a boolean value indication whether the configuration import was
     *         successful or not. True == success, false == failure.
     */
    private static boolean performConfigurationImport(File importPath) {
        if (importPath == null) {
            InitialConfigGUILanguage initialLanguage = InitialConfigGUILanguage.getInstance();
            JOptionPane.showMessageDialog(null, initialLanguage.get("configuration.file.missing"));
            return false;
        } else {
            Configuration importedConfig = ConfigImporter.doConfigurationImport(importPath);
            Config.getInstance().set(importedConfig);
            ConfigHandler.store(importedConfig, new File(C.PATH_TO_CONFIGURATION_FILE));
            return true;
        }
    }

    /**
     * This method configures the language to use as initial language by the
     * JavaPEG application.
     *
     * @param languageString
     *            is a {@link String} representation of the language to
     *            configure in the {@link Configuration}.
     */
    private static void setLanguage(String languageString) {
        Language language = new Language();
        language.setAutomaticSelection(false);
        language.setgUILanguageISO6391(ISO639.getInstance().getCode(languageString));

        Config.getInstance().get().setLanguage(language);
    }
}
