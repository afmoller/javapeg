package moller.javapeg.program.categories;
/**
 * This class was created : 2009-12-25 by Fredrik Möller
 * Latest changed         : 2009-12-28 by Fredrik Möller
 */

import moller.javapeg.program.C;

public class ImageRepositoryUtil {
	
	public static String filterPath(String unfilteredPath) {
		return unfilteredPath.split(C.DIRECTORY_STATUS_DELIMITER, 2)[2];
	}
}