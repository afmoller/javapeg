package moller.javapeg.program.language;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.logger.Logger;
import moller.util.io.StreamUtil;
import moller.util.java.SystemProperties;

public class Language {

    private static final String RESOURCES_LANG_LANGUAGES = "resources/lang/languages/";

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

        LanguageUtil.listEmbeddedLanguages(logger);
        logger.logDEBUG("Start loading language files");
        loadLanguageFile();
        logger.logDEBUG("Finished loading language files");
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

        InputStreamReader langFileJavaPEG              = null;
        InputStreamReader langFileImageViewer          = null;
        InputStreamReader langFileCommon               = null;
        InputStreamReader langFileConfigViewer         = null;
        InputStreamReader langFileCategory             = null;
        InputStreamReader langFileImageRepository      = null;
        InputStreamReader langFileImageResizer         = null;
        InputStreamReader langFileCategoryImportExport = null;
        InputStreamReader langFileImageMerge           = null;

        try {
            String languageCode = "";

            Config conf = Config.getInstance();
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
            if (StartJavaPEG.class.getResourceAsStream(RESOURCES_LANG_LANGUAGES + languageCode + "/javapeg." + languageCode) == null) {
                languageCode = "en";
            }

            langFileJavaPEG              = new InputStreamReader(StartJavaPEG.class.getResourceAsStream(RESOURCES_LANG_LANGUAGES + languageCode + "/javapeg."              + languageCode), C.UTF8);
            langFileImageViewer          = new InputStreamReader(StartJavaPEG.class.getResourceAsStream(RESOURCES_LANG_LANGUAGES + languageCode + "/imageviewer."          + languageCode), C.UTF8);
            langFileCommon               = new InputStreamReader(StartJavaPEG.class.getResourceAsStream(RESOURCES_LANG_LANGUAGES + languageCode + "/common."               + languageCode), C.UTF8);
            langFileConfigViewer         = new InputStreamReader(StartJavaPEG.class.getResourceAsStream(RESOURCES_LANG_LANGUAGES + languageCode + "/configviewer."         + languageCode), C.UTF8);
            langFileCategory             = new InputStreamReader(StartJavaPEG.class.getResourceAsStream(RESOURCES_LANG_LANGUAGES + languageCode + "/category."             + languageCode), C.UTF8);
            langFileImageRepository      = new InputStreamReader(StartJavaPEG.class.getResourceAsStream(RESOURCES_LANG_LANGUAGES + languageCode + "/imagerepository."      + languageCode), C.UTF8);
            langFileImageResizer         = new InputStreamReader(StartJavaPEG.class.getResourceAsStream(RESOURCES_LANG_LANGUAGES + languageCode + "/imageresizer."         + languageCode), C.UTF8);
            langFileCategoryImportExport = new InputStreamReader(StartJavaPEG.class.getResourceAsStream(RESOURCES_LANG_LANGUAGES + languageCode + "/categoryimportexport." + languageCode), C.UTF8);
            langFileImageMerge           = new InputStreamReader(StartJavaPEG.class.getResourceAsStream(RESOURCES_LANG_LANGUAGES + languageCode + "/imagemerge."           + languageCode), C.UTF8);

            loadAndLog("javapeg." + languageCode, langFileJavaPEG);
            loadAndLog("imageviewer." + languageCode, langFileImageViewer);
            loadAndLog("common." + languageCode, langFileCommon);
            loadAndLog("configviewer." + languageCode, langFileConfigViewer);
            loadAndLog("category." + languageCode, langFileCategory);
            loadAndLog("imagerepository." + languageCode, langFileImageRepository);
            loadAndLog("imageresizer." + languageCode, langFileImageResizer);
            loadAndLog("categoryimportexport." + languageCode, langFileCategoryImportExport);
            loadAndLog("imagemerge." + languageCode, langFileImageMerge);

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
            StreamUtil.close(langFileImageResizer, true);
            StreamUtil.close(langFileImageMerge, true);
        }
    }

    private void loadAndLog(String fileToLoad, InputStreamReader inputStream) throws IOException {
        logger.logDEBUG("Start loading language file: " + fileToLoad);

        Properties loader = new Properties();
        loader.load(inputStream);

        properties.putAll(loader);
        logger.logDEBUG("Finished loading language file: " + fileToLoad);
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