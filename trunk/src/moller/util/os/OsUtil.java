package moller.util.os;
/**
 * This class was created : 2009-10-19 by Fredrik Möller
 * Latest changed         : 
 */

public class OsUtil {
	
	public static String getOsVersion() {
		return System.getProperty("os.version");
	}
	
	public static String getOsArchitecture() {
		return System.getProperty("os.arch");
	}
	
	public static String getOsName() {
		return System.getProperty("os.name");
	}
}
