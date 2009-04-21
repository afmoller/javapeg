package moller.javapeg.program.language;
/**
 * This class was created : 2009-02-24 by Fredrik Möller
 * Latest changed         : 2009-02-27 by Fredrik Möller
 *                        : 2009-03-03 by Fredrik Möller
 *                        : 2009-03-05 by Fredrik Möller
 *                        : 2009-04-21 by Fredrik Möller
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.logger.Logger;

public class ISO639 {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static ISO639 instance;
	
	private Properties properties;
	
	private Map<String, String> languageCode;
	
	/**
	 * Private constructor.
	 */
	private ISO639() {
		
		properties = new Properties();
		
		try {
			properties.load(StartJavaPEG.class.getResourceAsStream("resources/lang/iso/iso639.dat"));
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