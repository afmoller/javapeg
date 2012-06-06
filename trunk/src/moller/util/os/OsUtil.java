package moller.util.os;

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
