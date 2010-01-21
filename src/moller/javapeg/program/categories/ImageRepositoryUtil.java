package moller.javapeg.program.categories;
/**
 * This class was created : 2009-12-25 by Fredrik Möller
 * Latest changed         : 2009-12-28 by Fredrik Möller
 *                        : 2010-01-20 by Fredrik Möller
 *                        : 2010-01-21 by Fredrik Möller
 */

import moller.javapeg.program.C;
import moller.util.string.StringUtil;

public class ImageRepositoryUtil {
	
	public static ImageRepositoryItem parseImageRepositoryItem(String imageRepositoryStringItem) {
		imageRepositoryStringItem =  StringUtil.reverse(imageRepositoryStringItem);
						
		String [] stringValueParts = imageRepositoryStringItem.split(C.DIRECTORY_STATUS_DELIMITER, 2);
		
		ImageRepositoryItem iri = new ImageRepositoryItem();
		
		iri.setPath(StringUtil.reverse(stringValueParts[1]));
		iri.setPathStatus(StringUtil.reverse(stringValueParts[0]));
		
		return iri;
	}
}