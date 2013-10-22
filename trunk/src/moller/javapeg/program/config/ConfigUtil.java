package moller.javapeg.program.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.ISO639;
import moller.javapeg.program.language.Language;
import moller.util.io.FileUtil;
import moller.util.io.ZipUtil;
import moller.util.result.ResultObject;

import org.xml.sax.SAXException;

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
     * Utility method which tests if an configuration file (XML) is valid
     * (checked against an Schema).
     *
     * @param configFile
     *            is the configuration file to check the validity of
     * @param configSchemaLocation
     *            is the location of the schema to use for checking the validity
     *            of the XML file specified by the parameter configFile
     * @return a {@link ResultObject} indicating whether or not the confiuration
     *         file is valid against the specified schema. It the configuration
     *         file is not valid the is the cause of invalidity attached as an
     *         exception
     */
    public static ResultObject isConfigValid(File configFile, String configSchemaLocation) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            StreamSource repositorySchema = new StreamSource(StartJavaPEG.class.getResourceAsStream(configSchemaLocation));
            Schema schema = factory.newSchema(repositorySchema);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(configFile));

            return new ResultObject(true, null);
        } catch (SAXException | IOException exception) {
            return new ResultObject(false, exception);
        }
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

    public static ResultObject restoreConfigurationFromBackup(File configurationBackupFile) {
        try {
            ZipUtil.unzip(configurationBackupFile, configurationBackupFile.getParentFile());
        } catch (IOException iox) {
            return new ResultObject(false, iox);
        }
        return new ResultObject(true, null);
    }
}