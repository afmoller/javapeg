package moller.util.string;

import java.text.DecimalFormat;

public class StringUtil {

    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

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

	/**
	 * This method tests whether the value of a string is possible to parse
	 * as an int.
	 *
	 * @param s contains the string which value will be tested if it is
	 *        possible to cast it to an integer.
	 *
	 * @return a boolean value indicating whether the string s contains a value
	 *         that is possible to cast to an integer.
	 */
	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException nfex) {
			 return false;
		}
		return true;
	}

	/**
	 * This method tests whether the value of a string is possible to parse
	 * as an int.
	 *
	 * @param s contains the string which value will be tested if it is
	 *        possible to cast it to an integer.
	 *
	 * @return a boolean value indicating whether the string s contains a value
	 *         that is possible to cast to an integer.
	 */
	public static boolean isInt(String s, boolean isPositive) {
		try {
			int number = Integer.parseInt(s);
			return number > -1 ? true : false;
		} catch (NumberFormatException nfex) {
			 return false;
		}
	}

	/**
	 * This method will return the int value of the String parameter. If the
	 * value of the String parameter is not an valid integer, then the default
	 * value specified by the defaultValue parameter will be returned.
	 *
	 * @param s contains the integer in String form.
	 *
	 * @param defaultValue is the value to return if the s parameter does not
	 *        contain a valid integer.
	 *
	 * @return either an int with the value of the s parameter or the value of
	 *         the defaultValue parameter if the s parameter does not contain
	 *         an valid integer.
	 */
	public static int getIntValue(String s, int defaultValue) {
	    try {
            return Integer.parseInt(s);
        } catch (NumberFormatException nfex) {
             return defaultValue;
        }
	}

	/**
	 * This method removes the first character in a String.
	 *
	 * @param s contains the string in which the first character will be
	 *        removed.
	 *
	 * @return the string contained in the parameter s except for the first
	 *         character.
	 */
	public static String removeFirstCharacter(String s) {
		return s.substring(1);
	}

	/**
	 * This method removes the last character in a String.
	 *
	 * @param s contains the string in which the last character will be
	 *        removed.
	 *
	 * @return the string contained in the parameter s except for the last
	 *         character.
	 */
	public static String removeLastCharacter(String s) {
		return s.substring(0, s.length() - 1);
	}

	/**
	 * This method returns the first character in a String.
	 *
	 * @param s contains the string in which the first character will be
	 *        returned.
	 *
	 * @return the first character in the string contained in the parameter
	 *         s.
	 */
	public static String getFirstCharacter(String s) {
		return s.substring(0,1);
	}

	/**
	 * @param string
	 * @param stringToRemove
	 * @return
	 */
	public static String removeStringFromEnd(String string, String stringToRemove) {
		if (string.endsWith(stringToRemove)) {
			return string.substring(0,string.length() - stringToRemove.length());
		} else {
			return string;
		}
	}

	/**
	 * @param stringValue
	 * @return
	 */
	public static String removeAnyTrailingNonIntegerCharacters(String stringValue) {
		stringValue = stringValue.trim();

		String subString = "";

		int index = -1;

		if (stringValue.length() > 0) {
			for (int i = stringValue.length(); i >= 0 ; i--) {
				subString = stringValue.substring(i - 1, i);
				try {
					Integer.parseInt(subString);
					index = i;
					break;
				} catch (Exception e) {
				}
			}
		}

		if (index > -1) {
			return stringValue.substring(0, index);
		} else {
			return stringValue;
		}
	}

	/**
	 * @param stringValue
	 * @return
	 */
	public static String removeAnyPrecedingNonIntegerCharacters(String stringValue) {
		stringValue = stringValue.trim();

		String subString = "";

		int index = -1;

		if (stringValue.length() > 0) {
			for (int i = 0; i < stringValue.length(); i++) {
				subString = stringValue.substring(i, i + 1);
				try {
					Integer.parseInt(subString);
					index = i;
					break;
				} catch (Exception e) {
				}
			}
		}

		if (index > -1) {
			return stringValue.substring(index);
		} else {
			return stringValue;
		}
	}

	public static String removeAnyPrecedingAndTrailingNonIntegerCharacters(String stringValue) {
		return removeAnyTrailingNonIntegerCharacters(removeAnyPrecedingNonIntegerCharacters(stringValue));
	}

	public static String convertToHexString(byte[] buf) {
        char[] chars = new char[2 * buf.length];

        for (int i = 0; i < buf.length; ++i) {
            chars[2 * i]     = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i]  & 0x0F];
        }
        return new String(chars);
    }

    /**
     * This method checks whether the argument is null or empty.
     *
     * @param s contains the string to check
     *
     * @return true if the parameter s is not null or empty, otherwise false.
     */
    public static boolean isNotBlank(String s) {
        if (s == null) {
            return false;
        }

        if (s.length() == 0) {
            return false;
        }

        return true;
    }

    /**
     * This method removes all non digits from a string.
     *
     * @param s is the String to clean from non digits.
     * @return
     */
    public static String removeAllNonDigits(String s) {
        if (s == null) {
            return null;
        }

        if (s.length() == 0) {
            return s;
        }

        return s.replaceAll("[^\\d]", "");
    }
}
