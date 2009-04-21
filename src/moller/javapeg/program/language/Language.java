package moller.javapeg.program.language;
/**
 * This class was created : 2009-01-25 by Fredrik M�ller
 * Latest changed         : 2009-02-20 by Fredrik M�ller
 *                        : 2009-02-24 by Fredrik M�ller
 *                        : 2009-03-01 by Fredrik M�ller
 *                        : 2009-03-23 by Fredrik M�ller
 *                        : 2009-03-24 by Fredrik M�ller
 *                        : 2009-04-15 by Fredrik M�ller
 *                        : 2009-04-21 by Fredrik M�ller
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.swing.JOptionPane;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.ApplicationContext;
import moller.javapeg.program.Config;
import moller.javapeg.program.logger.Logger;

public class Language {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static Language instance;
	
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
	private Language() {
		properties = new Properties();
		logger = Logger.getInstance();
		
		listEmbeddedLanguages();
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
	 * file, english, is loaded.
	 * 
	 * @return if neither the desired language file exists nor the default 
	 *         file then this method return false, otherwise true.
	 */
	public boolean loadLanguageFile() {
		
		Config conf = Config.getInstance();
		
		try {
			InputStream langFile = null;
			String languageToLoad = "";
			
			if (conf.getBooleanProperty("automaticLanguageSelection")) {	
				languageToLoad = "language." + System.getProperty("user.language");
			} else {
				languageToLoad = "language." + conf.getStringProperty("gUILanguageISO6391");
			}
			
			try {
				langFile = new FileInputStream(LANGUAGE_FILE_BASE + FS + languageToLoad);
				logger.logDEBUG("Language file: \"" + languageToLoad + "\" found in directroy: " + LANGUAGE_FILE_BASE);
			} catch (FileNotFoundException fnfe1) {
				logger.logDEBUG("Could not find Language file: \"" + languageToLoad + "\" in directroy: " + LANGUAGE_FILE_BASE);
				logger.logDEBUG("Try to load language file: " + languageToLoad + " from JAR file instead");				
				langFile = StartJavaPEG.class.getResourceAsStream("resources/lang/languages/" + languageToLoad);
								
				if(langFile == null) {
					logger.logDEBUG("Could not find Language file: \"" + languageToLoad + "\" in JAR file either.");
					try {
						logger.logDEBUG("Try to load default language file: language.en  from directory: " +  LANGUAGE_FILE_BASE);
						langFile = new FileInputStream(LANGUAGE_FILE_BASE + FS + "language.en");
					} catch (FileNotFoundException fnfe2) {
						logger.logDEBUG("Could not find language file: \"language.en\" in directroy: " + LANGUAGE_FILE_BASE);
						logger.logDEBUG("Try to load language file: \"language.en\" from JAR file instead");
						langFile = StartJavaPEG.class.getResourceAsStream("resources/lang/languages/language.en");
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