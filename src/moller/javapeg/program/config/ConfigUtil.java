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
package moller.javapeg.program.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.ISO639;
import moller.javapeg.program.language.Language;
import moller.util.io.FileUtil;
import moller.util.io.ZipUtil;
import moller.util.result.ResultObject;

public class ConfigUtil {

    public static  String [] listLanguagesFiles() {
        Set<String> languageFiles = ApplicationContext.getInstance().getJarFileEmbeddedLanguageFiles();

        List <String> languageNames = new ArrayList<String>(languageFiles.size());

        ISO639 iso639 = ISO639.getInstance();
        String code = "";
        String languageName = "";

        for (String fileName : languageFiles) {
            code = fileName.substring(fileName.lastIndexOf(".") + 1);
            languageName = iso639.getLanguage(code);
            if (languageName != null) {
                languageNames.add(iso639.getLanguage(code));
            }
        }
        Collections.sort(languageNames);

        return languageNames.toArray(new String[0]);
    }

    public static String resolveCodeToLanguageName(String code) {

        String language = ISO639.getInstance().getLanguage(code);

        if (language == null) {
            return Language.getInstance().get("configviewer.language.languageNameNotFound");
        }
        return language;
    }

    public static boolean isClientIdSet(String value) {
        return !value.equals("NOT-DEFINED");
    }

    /**
     * This method generates a unique id to be used as an identifier for an
     * installed JavaPEG instance. The id is used to define which meta data xml
     * files that are created by the running application, to make the
     * "writable".
     *
     * @return a {@link String} representation of an {@link UUID} object.
     */
    public static String generateClientId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Tries to store a copy of the corrupt configuration file in a file called:
     * <CONFIGURATION_FILE_NAME>.corrupt in a folder called "corrupt"
     *
     * @param configFile is the configuration file to make a copy of.
     *
     * @return a boolean value indication whether or not the storage of the
     * configuration file was successful or not.
     */
    public static boolean storeCorruptConfiguration(File configFile) {
        File path = configFile.getParentFile();
        File parentPath = null;

        if (path != null) {
            parentPath = path.getParentFile();
        }

        if (parentPath != null) {
            String fileName = configFile.getName();

            File corruptPath = new File(parentPath, "corrupt");
            File corruptFile = new File(corruptPath, fileName + ".corrupt");

            return FileUtil.copyFile(configFile, corruptFile);
        } else {
            return false;
        }
    }

    public static ResultObject<Exception> restoreConfigurationFromBackup(File configurationBackupFile) {
        try {
            ZipUtil.unzip(configurationBackupFile, configurationBackupFile.getParentFile());
        } catch (IOException iox) {
            return new ResultObject<Exception>(false, iox);
        }
        return new ResultObject<Exception>(true, null);
    }
}