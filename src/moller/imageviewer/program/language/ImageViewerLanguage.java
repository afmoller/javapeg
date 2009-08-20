package moller.imageviewer.program.language;

/**
 * This class was created : 2009-08-20 by Fredrik Möller
 * Latest changed         : 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import moller.imageviewer.StartImageViewer;
import moller.javapeg.program.logger.Logger;

public class ImageViewerLanguage {
			
	/**
	 * The static singleton instance of this class.
	 */
	private static ImageViewerLanguage instance;
	
	/**
	 * The system dependent file separator char
	 */
	private final static String FS = File.separator;
	
	/**
	 * The base path to the language files including the common "language" part
	 * of the file name.
	 */
	private final static String LANGUAGE_FILE_BASE = System.getProperty("user.dir") + FS + "resources" + FS + "lang";
	
	/**
	 * The data structure containing all the key value pairs for the selected
	 * language. 
	 */
	private Properties properties;
	
	private Logger logger;
	
	/**
	 * Private constructor.
	 */
	private ImageViewerLanguage() {
		properties = new Properties();
		logger = Logger.getInstance();	
	}

	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static ImageViewerLanguage getInstance() {
		if (instance != null)
			return instance;
		synchronized (ImageViewerLanguage.class) {
			if (instance == null) {
				instance = new ImageViewerLanguage();
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
	 * file, english, is loaded.
	 * 
	 * @return if neither the desired language file exists nor the default 
	 *         file then this method return false, otherwise true.
	 */
	public boolean loadLanguageFile(String iso639) {
		
		try {
			InputStream langFile = null;
			String languageToLoad = "language." + iso639;
			
			try {
				langFile = new FileInputStream(LANGUAGE_FILE_BASE + FS + iso639 + FS + "imageviewer" + FS + languageToLoad);
				logger.logDEBUG("Language file: \"" + languageToLoad + "\" found in directroy: " + LANGUAGE_FILE_BASE + FS + iso639 + FS + "imageviewer");
			} catch (FileNotFoundException fnfe1) {
				logger.logDEBUG("Could not find Language file: \"" + languageToLoad + "\" in directroy: " + LANGUAGE_FILE_BASE + FS + iso639 + FS + "imageviewer");
				logger.logDEBUG("Try to load language file: " + languageToLoad + " from JAR file instead");				
				langFile = StartImageViewer.class.getResourceAsStream("resources/lang/languages/" + iso639 + "/" + languageToLoad);
												
				if(langFile == null) {
					logger.logDEBUG("Could not find Language file: \"" + languageToLoad + "\" in JAR file either.");
					try {
						logger.logDEBUG("Try to load default language file: language.en  from directory: " +  LANGUAGE_FILE_BASE + FS + iso639 + FS + "imageviewer");
						langFile = new FileInputStream(LANGUAGE_FILE_BASE + FS + iso639 + FS + "imageviewer" + FS + "language.en");
					} catch (FileNotFoundException fnfe2) {
						logger.logDEBUG("Could not find language file: \"language.en\" in directroy: " + LANGUAGE_FILE_BASE + FS + iso639 + FS + "imageviewer");
						logger.logDEBUG("Try to load language file: \"language.en\" from JAR file instead");
						langFile = StartImageViewer.class.getResourceAsStream("resources/lang/languages/en/language.en");
					}
				}	
			}
            properties.load(langFile);
        } catch (IOException e) {
			logger.logFATAL("No language file found");
			for(StackTraceElement element : e.getStackTrace()) {
				logger.logFATAL(element.toString());	
			}
        	return false;
        }
        return true;
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