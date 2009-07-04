package moller.javapeg.program;
/**
 * This class was created : 2007-01-18 by Fredrik Möller
 * Latest changed         : 2009-02-03 by Fredrik Möller
 *                        : 2009-03-10 by Fredrik Möller
 *                        : 2009-06-17 by Fredrik Möller
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import moller.util.io.JPEGUtil;

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

		jpegFileNameFileObjectMap.clear();
		nonJpegFileNameFileObjectMap.clear();

		if(directoryPath.isDirectory()){
			for (File file : directoryPath.listFiles()) {
				try {
					if(JPEGUtil.isJPEG(file)) {
						jpegFileNameFileObjectMap.put(file.getName(), file);
					} else {
						nonJpegFileNameFileObjectMap.put(file.getName(), file);
					}
				} catch (FileNotFoundException e) {
					// TODO Fix notification about file not found
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Fix notification about can not read from file.
					e.printStackTrace();
				}
			}
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