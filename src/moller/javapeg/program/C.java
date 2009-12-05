package moller.javapeg.program;
/**
 * This class was created : 2009-11-13 by Fredrik Möller
 * Latest changed         : 2009-11-14 by Fredrik Möller
 */

import java.io.File;

public class C {
	
	/**
	 * The system dependent file separator char
	 */
	public final static String FS              = File.separator;
	
	public final static String USER_HOME       = System.getProperty("user.home");
	public final static String JAVAPEG_VERSION = "2.5";
	
	/**
	 * This variable  contains a time stamp which is set to when the latest 
	 * release is done. The value is the amount of milliseconds since 
	 * 1970-01-01 with 
	 */
	public final static long VERSION_TIMESTAMP = 1258486103;
}