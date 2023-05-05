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
import moller.javapeg.program.logger.Logger;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ISO639 {

    /**
     * The static singleton instance of this class.
     */
    private static ISO639 instance;

    private final Properties properties;

    private final Map<String, String> languageCode;

    /**
     * Private constructor.
     */
    private ISO639() {

        properties = new Properties();

        try {
            properties.load(StartJavaPEG.class.getResourceAsStream("/lang/iso/iso639.dat"));
        } catch (FileNotFoundException e) {
            logFatalError(e);
        } catch (IOException e) {
            logFatalError(e);
        } catch (NullPointerException e) {
            logFatalError(e);
        }

        languageCode = new HashMap<String, String>(properties.size());

        for (Object code : properties.keySet()) {
            languageCode.put(properties.getProperty((String)code), (String)code);
        }
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static ISO639 getInstance() {
        if (instance != null)
            return instance;
        synchronized (ISO639.class) {
            if (instance == null) {
                instance = new ISO639();
            }
            return instance;
        }
    }

    public String getLanguage(String code) {
        return properties.getProperty(code);
    }

    public String getCode(String language) {
        return languageCode.get(language);
    }

    private void logFatalError(Exception e) {
        Logger logger = Logger.getInstance();

        JOptionPane.showMessageDialog(null,"File iso639.dat can not be loaded.\nApplication will exit.", "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
        logger.logFATAL("Language Codes file iso639.dat can not be found");
        logger.logFATAL("Below is the generated StackTrace");

        for(StackTraceElement element : e.getStackTrace()) {
            logger.logFATAL(element.toString());
        }
        logger.flush();
        System.exit(1);
    }
}