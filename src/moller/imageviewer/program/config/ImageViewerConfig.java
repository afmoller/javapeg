package moller.imageviewer.program.config;

/**
 * This class was created : 2009-08-16 by Fredrik Möller
 * Latest changed         : 2009-08-19 by Fredrik Möller
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class ImageViewerConfig {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static ImageViewerConfig instance;
	
	private final static String PATH_TO_CONF_FILE = System.getProperty("user.dir") + File.separator + "config" + File.separator + "conf_imageviewer.xml";
	
	private Properties properties;
	
	/**
	 * Private constructor.
	 */
	private ImageViewerConfig() {
				
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
	public static ImageViewerConfig getInstance() {
		if (instance != null)
			return instance;
		synchronized (ImageViewerConfig.class) {
			if (instance == null) {
				instance = new ImageViewerConfig();
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
		JOptionPane.showMessageDialog(null,"Configuration file: " + PATH_TO_CONF_FILE + " can not be found", "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
}
