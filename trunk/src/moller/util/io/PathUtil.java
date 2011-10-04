package moller.util.io;

import java.io.File;

public class PathUtil {

	/***
	 * Metod för att kontrollera om en sträng är giltig. I detta fall kontrolleras strängen genom
	 * att söka efter tecken som inte är tillåtna i en sökväg eller filnamn. hittas otillåtna
	 * tecken så returnerar metoden ascii-värdet för det tecken som funnits. Hittas inga otillåtna
	 * tecken returneras -1
	 **/
	public static int validateString(String theStringToValidate, boolean isPath){

		int isValid = -1;

		if(!isPath){
			if(theStringToValidate.indexOf(47) > -1) {  // "/"
				isValid = 47;
			}
			if(theStringToValidate.indexOf(92) > -1) {  // "\"
				isValid = 92;
			}
			if(theStringToValidate.indexOf(58) > -1) {  // ":"
				isValid = 58;
			}
		}
		if(theStringToValidate.indexOf(60) > -1) {  // "<"
			isValid = 60;
		}
		if(theStringToValidate.indexOf(62) > -1) {  // ">"
			isValid = 62;
		}
		if(theStringToValidate.indexOf(124) > -1) { // "|"
			isValid = 124;
		}
		if(theStringToValidate.indexOf(42) > -1) {  // "*"
			isValid = 42;
		}
		if(theStringToValidate.indexOf(63) > -1) {  // "?"
			isValid = 63;
		}
		if(theStringToValidate.indexOf(34) > -1) {  // """
			isValid = 34;
		}

		return isValid;
	}

	public static String getTotalWindowsPathAsString(Object[] pathArray) {
	    StringBuilder worker = new StringBuilder();

        int index = 0;

        for (Object path : pathArray) {
            if (index < 2 || index == pathArray.length-1) {
                worker.append(path.toString());
            } else{
                worker.append(path.toString() + File.separator);
            }
            index++;
        }
        return worker.toString();
	}


	/**
	 * This method tests whether a path is a child to a reference path.
	 *
	 * @param pathToTest is the path to be investigated whether it is a child
	 *        or not to a reference path.
	 *
	 * @param reference is the path against which the pathToTest parameter is
	 *        compared.
	 *
	 * @return a boolean value indicating whether the parameter pathToTest is a
	 *         child of the parameter reference or not.
	 */
	public static boolean isChild(File pathToTest, File reference) {
	    if (pathToTest.getAbsolutePath().equals(reference.getAbsolutePath())) {
	        return false;
	    } else {
	        return pathToTest.getAbsolutePath().startsWith(reference.getAbsolutePath());
	    }
	}

	/**
     * This method tests whether a path is a parent to a reference path.
     *
     * @param pathToTest is the path to be investigated whether it is a parent
     *        or not to a reference path.
     *
     * @param reference is the path against which the pathToTest parameter is
     *        compared.
     *
     * @return a boolean value indicating whether the parameter pathToTest is a
     *         parent of the parameter reference or not.
     */
	public static boolean isParent(File pathToTest, File reference) {
	    return isChild(reference, pathToTest);
	}
}