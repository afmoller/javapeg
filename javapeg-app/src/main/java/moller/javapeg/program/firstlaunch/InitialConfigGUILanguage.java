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
package moller.javapeg.program.firstlaunch;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.util.java.SystemProperties;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class InitialConfigGUILanguage {

    /**
     * The static singleton instance of this class.
     */
    private static InitialConfigGUILanguage instance;

    /**
     * The data structure containing all the key value pairs for the selected
     * language.
     */
    private final Properties properties;



    /**
     * Private constructor.
     */
    private InitialConfigGUILanguage() {
        properties = new Properties();

        loadLanguageFile();
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static InitialConfigGUILanguage getInstance() {
        if (instance != null)
            return instance;
        synchronized (InitialConfigGUILanguage.class) {
            if (instance == null) {
                instance = new InitialConfigGUILanguage();
            }
            return instance;
        }
    }

    /**
     * This method tries to load the desired language file according to the
     * application configuration. If automatic language selection is set in the
     * configuration, then the selection of language file is dependent on the
     * current language used by the system running this application, otherwise
     * the language according to the configuration is loaded. If, for some
     * reason, the desired files are non existing, then the default language
     * file, English, is loaded.
     *
     * @return if neither the desired language file exists nor the default
     *         file then this method return false, otherwise true.
     */
    private void loadLanguageFile() {

        String languageCode = "";

        languageCode = SystemProperties.getUserLanguage();

        /**
         * If the assigned language code does not exist, then fall back to
         * English.
         */
        if (StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/initialgui." + languageCode) == null) {
            languageCode = "en";
        }

        try (InputStreamReader initialConfigGUILanguage = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/initialgui." + languageCode), C.UTF8)) {

            properties.load(initialConfigGUILanguage);

        } catch (IOException e) {
        }
    }

    /**
     * This method returns a language specific String associated to a key in a
     * Properties data structure
     *
     * @param key is the parameter name for which language specific string
     *        that shall be returned.
     *
     * @return a language specific string or null if the key does not exist in
     *         the Properties data structure.
     */
    public String get(String key) {
        return properties.getProperty(key).trim();
    }
}
