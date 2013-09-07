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

        Config conf = Config.getInstance();

        InputStreamReader langFileJavaPEG              = null;
        InputStreamReader langFileImageViewer          = null;
        InputStreamReader langFileCommon               = null;
        InputStreamReader langFileConfigViewer         = null;
        InputStreamReader langFileCategory             = null;
        InputStreamReader langFileImageRepository      = null;
        InputStreamReader langFileImageResizer         = null;
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
            langFileImageViewer          = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/imageviewer."     + languageCode), C.UTF8);
            langFileCommon               = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/common."          + languageCode), C.UTF8);
            langFileConfigViewer         = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/configviewer."    + languageCode), C.UTF8);
            langFileCategory             = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/category."        + languageCode), C.UTF8);
            langFileImageRepository      = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/imagerepository." + languageCode), C.UTF8);
            langFileImageResizer         = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/imageresizer."    + languageCode), C.UTF8);
            langFileCategoryImportExport = new InputStreamReader(StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageCode + "/categoryimportexport." + languageCode), C.UTF8);

            Properties loader = new Properties();

            logger.logDEBUG("Start loading language file: javapeg." + languageCode);
            loader.load(langFileJavaPEG);

            properties.putAll(loader);

            loader.clear();

            logger.logDEBUG("Finished loading language file: javapeg." + languageCode);
            logger.logDEBUG("Start loading language file: imageviewer." + languageCode);

            loader.load(langFileImageViewer);

            properties.putAll(loader);

            loader.clear();

            logger.logDEBUG("Finished loading language file: imageviewer." + languageCode);
            logger.logDEBUG("Start loading language file: common." + languageCode);

            loader.load(langFileCommon);

            properties.putAll(loader);

            loader.clear();

            logger.logDEBUG("Finished loading language file: common." + languageCode);
            logger.logDEBUG("Start loading language file: configviewer." + languageCode);

            loader.load(langFileConfigViewer);

            properties.putAll(loader);

            loader.clear();

            logger.logDEBUG("Finished loading language file: configviewer." + languageCode);
            logger.logDEBUG("Start loading language file: category." + languageCode);

            loader.load(langFileCategory);

            properties.putAll(loader);

            loader.clear();

            logger.logDEBUG("Finished loading language file: category." + languageCode);
            logger.logDEBUG("Start loading language file: imagerepository." + languageCode);

            loader.load(langFileImageRepository);

            properties.putAll(loader);

            loader.clear();

            logger.logDEBUG("Finished loading language file: imagerepository." + languageCode);
            logger.logDEBUG("Start loading language file: imageresizer." + languageCode);

            loader.load(langFileImageResizer);

            properties.putAll(loader);

            loader.clear();

            logger.logDEBUG("Finished loading language file: imageresizer." + languageCode);
            logger.logDEBUG("Start loading language file: categoryimportexport." + languageCode);

            loader.load(langFileCategoryImportExport);

            properties.putAll(loader);
            logger.logDEBUG("Finished loading language file: categoryimportexport." + languageCode);

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
            StreamUtil.close(langFileImageResizer, true);
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