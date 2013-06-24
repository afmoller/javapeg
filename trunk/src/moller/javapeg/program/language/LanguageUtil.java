package moller.javapeg.program.language;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.swing.JOptionPane;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.logger.Logger;

public class LanguageUtil {

    /**
     * Loads all embedded languages and puts the list into the
     * {@link ApplicationContext}
     *
     * @param logger
     */
    public static void listEmbeddedLanguages(Logger logger) {

        InputStream languageListFile = StartJavaPEG.class.getResourceAsStream("resources/lang/list/language.list");

        Properties availableLanguages = new Properties();
        try {
            availableLanguages.load(languageListFile);
        } catch (IOException e) {

            if (logger != null) {
                JOptionPane.showMessageDialog(null, "Could not load file language.list, se log file for details", "Error", JOptionPane.ERROR_MESSAGE);
                logger.logERROR("Could not load file language.list, se stack trace below for details");
                for(StackTraceElement element : e.getStackTrace()) {
                    logger.logERROR(element.toString());
                }
            } else {
                StringBuilder stackTrace = new StringBuilder();

                for(StackTraceElement element : e.getStackTrace()) {
                    stackTrace.append(element.toString());
                    stackTrace.append(C.LS);
                }
                JOptionPane.showMessageDialog(null, "Could not load file language.list, se log file for details" + C.LS + stackTrace.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        Set<String> languageNames = new HashSet<String>();

        for (Object language : availableLanguages.keySet()) {
            languageNames.add((String)language);
        }
        ApplicationContext.getInstance().setJarFileEmbeddedLanguageFiles(languageNames);
    }

}
