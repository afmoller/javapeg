package moller.javapeg.program;

import java.io.File;

public class C {
	
	/**
	 * The system dependent file separator char
	 */
	public final static String FS              = File.separator;
	
	/**
	 * The system dependent line separator
	 */
	public final static String LS              = System.getProperty("line.separator");
	
	public final static String USER_HOME       = System.getProperty("user.home");
	public final static String JAVAPEG_VERSION = "2.5";
	
	/**
	 * This variable  contains a time stamp which is set to when the latest 
	 * release is done. The value is the amount of milliseconds since 
	 * 1970-01-01 with 
	 */
	public final static long VERSION_TIMESTAMP = 1258486103;
	
	public final static String DIRECTORY_STATUS_DELIMITER = "-";
	
	/**
	 * The name of the file containing meta data of images in the same 
	 * directory as this file is stored in.
	 */
	public final static String JAVAPEG_IMAGE_META_NAME = "javapeg-image-meta.xml";
	
	public final static String IMAGE_META_DATA_DATA_BASE_VERSION = "1";
}