package moller.javapeg.program;
/**
 * This class was created : 2007-01-18 by Fredrik Möller
 * Latest changed         : 2009-02-03 by Fredrik Möller
 *                        : 2009-03-10 by Fredrik Möller
 *                        : 2009-06-17 by Fredrik Möller
 *                        : 2009-07-20 by Fredrik Möller
 *                        : 2009-08-21 by Fredrik Möller
 *                        : 2009-09-11 by Fredrik Möller
 *                        : 2009-09-13 by Fredrik Möller
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.enumerations.Action;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.jpeg.JPEGUtil;

public class FileRetriever {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static FileRetriever instance;
	
	/**
	 * This Map contains a File name and File object mapping. In other words:
	 * the key in the Map is a file name and the value is the actual file
	 * object which have the file name denoted by the key
	 */
	private Map<String, File> jpegFileNameFileObjectMap;
	
	/**
	 * This Map contains a File name and File object mapping. In other words:
	 * the key in the Map is a file name and the value is the actual file
	 * object which have the file name denoted by the key
	 */
	private Map<String, File> nonJpegFileNameFileObjectMap;
	
	private int nrOfJpegImages;
	
	private static Language lang;
	private static Logger   logger;
	
	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static FileRetriever getInstance() {
		if (instance != null)
			return instance;
		synchronized (FileRetriever.class) {
			if (instance == null) {
				instance = new FileRetriever();
			}
			return instance;
		}
	}
	
	private FileRetriever() {
		lang   = Language.getInstance();
		logger = Logger.getInstance();
		
		nrOfJpegImages = 0;
		
		jpegFileNameFileObjectMap    = new HashMap<String, File>(128);
		nonJpegFileNameFileObjectMap = new HashMap<String, File>(128);
	}

	/**
	 * This method will list all files in a directory on disk and put
	 * them into two different Map structures, one with JPEG files and 
	 * the second one with all potentially existing non JPEG files and
	 * directories.
	 *  
	 * @param directoryPath is the path to the directory to load files
	 *        from.
	 */
	public void loadFilesFromDisk (File directoryPath) {

		nrOfJpegImages = 0;
		jpegFileNameFileObjectMap.clear();
		nonJpegFileNameFileObjectMap.clear();
		
		ApplicationContext ac = ApplicationContext.getInstance();

		try {
			if(directoryPath.isDirectory()){
				for (File file : directoryPath.listFiles()) {
					try {
						if(JPEGUtil.isJPEG(file)) {
							jpegFileNameFileObjectMap.put(file.getName(), file);
							handleNrOfJpegImages(Action.SET);
							ac.handleJpegFileLoadBuffer(file, Action.ADD);
						} else {
							nonJpegFileNameFileObjectMap.put(file.getName(), file);
						}
					} catch (FileNotFoundException fnfex) {
						JOptionPane.showMessageDialog(null, lang.get("fileretriever.canNotFindFile") + "\n(" + file.getAbsolutePath() + ")", lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
						logger.logERROR("Could not find file:");
						logger.logERROR(fnfex);
					} catch (IOException iox) {
						JOptionPane.showMessageDialog(null, lang.get("fileretriever.canNotReadFromFile") + "\n(" + file.getAbsolutePath() + ")", lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
						logger.logERROR("Can not read from file:");
						logger.logERROR(iox);
					}
				}
			} 
		} catch (Throwable sex) {
			logger.logERROR("Can not list files in directory: " + directoryPath);
			logger.logERROR(sex);
//			TODO: Hard coded string
			JOptionPane.showMessageDialog(null, "Can not list files in directory: " + directoryPath, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public synchronized int handleNrOfJpegImages(Action action) {
		switch (action) {
		case RETRIEVE:
			return nrOfJpegImages;
		case SET:
			nrOfJpegImages++;
			return -1;
		default:
			throw new RuntimeException("Unsupported Action");
		}
	}

	/**
	 * Get a collection of JPEG File objects.
	 * 
	 * @return A collection of File objects.
	 */
	public Collection <File> getJPEGFiles() {
		return new TreeSet<File>(jpegFileNameFileObjectMap.values());
	}
		
	/**
	 * Get a Map consisting of a key - value pair of String - File
	 * objects, where the String object represents the name of the
	 * file and the File object the actual file.
	 * 
	 * @return A Map with file name and file pairs.
	 */
	public Map<String, File> getJPEGFileNameFileObjectMap() {
		return jpegFileNameFileObjectMap;
	}
	
	/**
	 * Get a collection of non JPEG File objects.
	 * 
	 * @return A collection of File objects.
	 */
	public Collection <File> getNonJPEGFiles() {
		return nonJpegFileNameFileObjectMap.values();
	}
	
	/**
	 * Get a Map consisting of a key - value pair of String - File
	 * objects, where the String object represents the name of the
	 * file and the File object the actual file.
	 * 
	 * @return A Map with file name and file pairs.
	 */
	public Map<String, File> getNonJPEGFileNameFileObjectMap() {
		return nonJpegFileNameFileObjectMap;
	}
}