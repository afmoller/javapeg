package moller.util.io;
/**
 * This class was created : 2009-04-06 by Fredrik M�ller
 * Latest changed         :
 */

public class PathUtil {

	/***
	 * Metod f�r att kontrollera om en str�ng �r giltig. I detta fall kontrolleras str�ngen genom
	 * att s�ka efter tecken som inte �r till�tna i en s�kv�g eller filnamn. hittas otill�tna
	 * tecken s� returnerar metoden ascii-v�rdet f�r det tecken som funnits. Hittas inga otill�tna
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
}