package moller.javapeg;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import moller.javapeg.program.ApplicationUncaughtExceptionHandler;
import moller.javapeg.program.C;
import moller.javapeg.program.MainGUI;
import moller.javapeg.program.applicationstart.ApplicationBootUtil;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.controller.ConfigHandler;
import moller.javapeg.program.config.importconfig.ConfigImporter;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.Language;
import moller.javapeg.program.firstlaunch.InitialConfigGUI;
import moller.javapeg.program.firstlaunch.InitialConfigGUILanguage;
import moller.javapeg.program.language.ISO639;
import moller.util.os.OsUtil;

public class StartJavaPEG {

    public static void main (String [] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                ApplicationUncaughtExceptionHandler.registerExceptionHandler();

                boolean supportedOS = false;

                String osName = OsUtil.getOsName();

                if (osName.toLowerCase().contains("windows")) {
                    supportedOS = true;
                }

                if (supportedOS) {

                    boolean startApplication = true;

                    if (ApplicationBootUtil.isFirstApplicationLaunch()) {
                        InitialConfigGUI initialConfigGUI = new InitialConfigGUI();
                        initialConfigGUI.setVisible(true);

                        InitialConfigGUILanguage initialLanguage = InitialConfigGUILanguage.getInstance();

                        Object[] options = {initialLanguage.get("button.continue"), initialLanguage.get("button.cancel")};
                        Object initialValue = options[0];

                        int result = JOptionPane.showOptionDialog(null, initialConfigGUI, initialLanguage.get("window.title"), JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, initialValue);

                        if (result == JOptionPane.YES_OPTION) {
                            Configuration config = Config.getInstance().get();

                            if (initialConfigGUI.isImport()) {
                                if (initialConfigGUI.getImportPath() == null) {
                                    JOptionPane.showMessageDialog(null, initialLanguage.get("configuration.file.missing"));
                                    startApplication = false;
                                } else {
                                    Configuration importedConfig = ConfigImporter.doConfigurationImport(initialConfigGUI.getImportPath(), config);
                                    Config.getInstance().set(importedConfig);
                                    ConfigHandler.store(importedConfig, new File(C.PATH_TO_CONFIGURATION_FILE));
                                }
                            } else {
                                Language language = new Language();
                                language.setAutomaticSelection(false);
                                language.setgUILanguageISO6391(ISO639.getInstance().getCode(initialConfigGUI.getLanguage()));

                                config.setLanguage(language);
                            }

                            if (startApplication) {
//                            TODO: Delete the first launch marker file...
                            }
                        } else {
                            startApplication = false;
                        }
                    }

                    if (startApplication) {
                        MainGUI mainGUI = new MainGUI();
                        mainGUI.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Unsupported operating system" + OsUtil.getOsName() + ".\n\nThe supported operating systems are:\nWindows", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
