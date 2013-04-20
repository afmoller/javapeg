package moller.javapeg.program.language;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.swing.JOptionPane;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.logger.Logger;
import moller.util.io.StreamUtil;
import moller.util.java.SystemProperties;

public class Language {

    /**
     * The static singleton instance of this class.
     */
    private static Language instance;

    /**
     * The data structure containing all the key value pairs for the selected
     * language.
     */
    private final Properties properties;

    private final Logger logger;

    /**
     * Private constructor.
     */
    private Language() {
        properties = new Properties();
        logger = Logger.getInstance();

        listEmbeddedLanguages();
        loadLanguageFile();
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static Language getInstance() {
        if (instance != null)
            return instance;
        synchronized (Language.class) {
            if (instance == null) {
                instance = new Language();
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
    private boolean loadLanguageFile() {

        Config conf = Config.getInstance();

        InputStreamReader langFileJavaPEG              = null;
        InputStreamReader langFileImageViewer          = null;
        InputStreamReader langFileCommon               = null;
        InputStreamReader langFileConfigViewer         = null;
        InputStreamReader langFileCategory             = null;
        InputStreamReader langFileImageRepository      = null;
        InputStreamReader langFileCategoryImportExport = null;

        try {
            String languageCode = "";

            moller.javapeg.program.config.model.Language language = conf.get().getLanguage();

            if (language.getAutomaticSelection()) {
                languageCode = SystemProperties.getUserLanguage();
            } else {
                languageCode = language.getgUILanguageISO6391();
            }

            /**
             * If the assigned language code does not exist, then fall back to
             * English.
             */
            if (StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/javapeg." + languageCode) == null) {
                languageCode = "en";
            }

            langFileJavaPEG              = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/javapeg."         + languageCode), C.UTF8);
            langFileImageViewer          = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/imageviewer."     + languageCode), C.UTF8) ;
            langFileCommon               = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/common."          + languageCode), C.UTF8);
            langFileConfigViewer         = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/configviewer."    + languageCode), C.UTF8);
            langFileCategory             = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/category."        + languageCode), C.UTF8);
            langFileImageRepository      = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/imagerepository." + languageCode), C.UTF8);
            langFileCategoryImportExport = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/categoryimportexport." + languageCode), C.UTF8);

            Properties loader = new Properties();
            loader.load(langFileJavaPEG);

            properties.putAll(loader);

            loader.clear();
            loader.load(langFileImageViewer);

            properties.putAll(loader);

            loader.clear();
            loader.load(langFileCommon);

            properties.putAll(loader);

            loader.clear();
            loader.load(langFileConfigViewer);

            properties.putAll(loader);

            loader.clear();
            loader.load(langFileCategory);

            properties.putAll(loader);

            loader.clear();
            loader.load(langFileImageRepository);

            properties.putAll(loader);

            loader.clear();
            loader.load(langFileCategoryImportExport);

            properties.putAll(loader);

            loader = null;

            return true;
        } catch (IOException e) {
            logger.logFATAL("No language file found");
            for(StackTraceElement element : e.getStackTrace()) {
                logger.logFATAL(element.toString());
            }
            return false;
        } finally {
            StreamUtil.close(langFileJavaPEG, true);
            StreamUtil.close(langFileImageViewer, true);
            StreamUtil.close(langFileCommon, true);
            StreamUtil.close(langFileConfigViewer, true);
            StreamUtil.close(langFileCategory, true);
            StreamUtil.close(langFileImageRepository, true);
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

    private void listEmbeddedLanguages() {

        InputStream languageListFile = StartJavaPEG.class.getResourceAsStream("resources/lang/list/language.list");

        Properties availableLanguages = new Properties();
        try {
            availableLanguages.load(languageListFile);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not load file language.list, se log file for details", "Error", JOptionPane.ERROR_MESSAGE);

            logger.logERROR("Could not load file language.list, se stack trace below for details");
            for(StackTraceElement element : e.getStackTrace()) {
                logger.logERROR(element.toString());
            }
        }

        Set<String> languageNames = new HashSet<String>();

        for (Object language : availableLanguages.keySet()) {
            languageNames.add((String)language);
        }
        ApplicationContext.getInstance().setJarFileEmbeddedLanguageFiles(languageNames);
    }
}