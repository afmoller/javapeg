package moller.javapeg.program.config;
/**
 * This class was created : 2003-12-28 by Fredrik Möller
 * Latest changed         : 2004-03-11 by Fredrik Möller
 *                        : 2004-03-13 by Fredrik Möller
 *                        : 2006-11-09 by Fredrik Möller
 *                        : 2009-02-24 by Fredrik Möller
 *                        : 2009-02-28 by Fredrik Möller
 *                        : 2009-03-01 by Fredrik Möller
 *                        : 2009-03-03 by Fredrik Möller
 *                        : 2009-03-18 by Fredrik Möller
 *                        : 2009-11-11 by Fredrik Möller
 *                        : 2009-11-13 by Fredrik Möller
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import moller.javapeg.program.C;

public class Config {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static Config instance;
	
	private final static String PATH_TO_CONF_FILE = C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION + C.FS + "config" + C.FS + "conf.xml";
	
	private Properties properties;
	
	/**
	 * Private constructor.
	 */
	private Config() {
				
		properties = new Properties();
		
		try {
			properties.loadFromXML(new FileInputStream(PATH_TO_CONF_FILE));
		} catch (FileNotFoundException e) {
			logFatalError(e);
		} catch (IOException e) {
			logFatalError(e);
		}
	}

	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static Config getInstance() {
		if (instance != null)
			return instance;
		synchronized (Config.class) {
			if (instance == null) {
				instance = new Config();
			}
			return instance;
		}
	}

	public void saveSettings() throws FileNotFoundException, IOException {
		properties.storeToXML(new FileOutputStream(PATH_TO_CONF_FILE), null);
	}
	
	public String getStringProperty(String key) {
		return properties.getProperty(key);
	}
	
	public boolean getBooleanProperty(String key) {
		return properties.get(key).equals("true") ? true : false; 
	}
	
	public int getIntProperty(String key) {
		return Integer.parseInt((String)properties.get(key));
	}
		
	public void setStringProperty(String key, String value) {
		properties.put(key, value);
	}
	
	public void setBooleanProperty(String key, boolean value) {
		properties.put(key, value == true ? "true" : "false");
	}	
	
	public void setIntProperty(String key, int value) {
		properties.put(key, Integer.toString(value));
	}
	
	private void logFatalError(Exception e) {
		JOptionPane.showMessageDialog(null,"Configuration file: " + PATH_TO_CONF_FILE + " can not be found, or is invalid\n\n" + e, "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
}