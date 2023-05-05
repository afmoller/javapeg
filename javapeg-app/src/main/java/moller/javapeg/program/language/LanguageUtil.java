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
package moller.javapeg.program.language;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.logger.Logger;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class LanguageUtil {

    /**
     * Loads all embedded languages and puts the list into the
     * {@link ApplicationContext}
     *
     * @param logger
     */
    public static void listEmbeddedLanguages(Logger logger) {

        InputStream languageListFile = StartJavaPEG.class.getResourceAsStream("/lang/list/language.list");

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
