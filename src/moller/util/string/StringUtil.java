package moller.util.string;
/**
 * This class was created : 2009-04-06 by Fredrik Möller
 * Latest changed         : 2009-10-15 by Fredrik Möller
 *                        : 2009-12-28 by Fredrik Möller
 */

import java.text.DecimalFormat;

public class StringUtil {

	/**
	 * Formats a byte value depending on the size to "Bytes", "KiBytes",
	 * "MiByte" and "GiByte"
	 * 
	 * @param bytes
	 * @return Formatted {@link String}
	 */
	public static String formatBytes(long bytes, String decimalPattern) {
		DecimalFormat df = new DecimalFormat(decimalPattern);
		
		if (bytes < 1000)
			return Long.toString(bytes) + " Bytes";
		if (bytes < Math.pow(1000, 2))
			return df.format(bytes / 1024d) + " KiByte";
		if (bytes < Math.pow(1000, 3))
			return df.format(bytes / Math.pow(1024, 2)) + " MiByte";
		return df.format(bytes / Math.pow(1024, 3)) + " GiByte";
	}
	
	/**
	 * This method takes a String as input an removes all newline characters
	 * in it and changes any occurrences of the string "<LF>" with the line 
	 * separator for the current system.
	 * 
	 * @param s is the string to format.
	 * @return a formatted string.
	 */
	public static String formatString(String s) {
		s = s.replaceAll("\r", "");
		s = s.replaceAll("\n", "");
		return s.replaceAll("<LF>", System.getProperty("line.separator"));
	}
	
	/**
	 * This method reverses the content of a string. I.E the string: "cat" will
	 * be returned as "tac".
	 * 
	 * @param s contains the string that shall be reversed.
	 * @return the reversed order of the content in the s argument.
	 */
	public static String reverse(String s) {
		return new StringBuilder(s).reverse().toString();		
	}
}