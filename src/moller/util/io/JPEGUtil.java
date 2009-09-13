package moller.util.io;
/**
 * This class was created : 2009-03-27 by Fredrik M�ller
 * Latest changed         : 2009-06-17 by Fredrik M�ller
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JPEGUtil {

	/**
	 * Denna metod kontrollerar om en fil �r av jpg-typ eller inte.
	 * Den tar ett filobjekt som inparameter och returnerar ett boleskt
	 * v�rde.
	 *
	 * @param file Ett File-objekt som inneh�ller den fil som
	 *             skall kontrolleras	
	 * @return     Ett booleskt v�rde som talar om ifall filen
	 *             �r en jpg-fil eller inte. 
	 * @throws FileNotFoundException, IOException 
	 */
	public static boolean isJPEG(File file) throws FileNotFoundException, IOException{
		if (file.isFile() && file.canRead()) {
			byte[]mB = new byte[2];

			FileInputStream fis = new FileInputStream(file);
			fis.read(mB);

			String fName = file.getName();
			String ext = fName.substring(fName.indexOf(".") + 1);

			/**
			 * H�r kontrolleras s� att de tv� f�rsta byten i filen har v�rdet 255 (FF)
			 * och 216 (D8) vilket �r det som identifierar en JPEG/JFIF-fil. I if-satsen
			 * har v�rdet r�knats om till unsigned genom att &:a med FF. Vidare testas ifall
			 * filnamnet slutar p� JPG eller JPEG s� att inte tumnaglar eller liknande med
			 * annan fil�ndelse slinker igenom. 
			 */
			return ((mB[0] & 0xFF) == 255 && 
					(mB[1] & 0xFF) == 216) && 
					((ext.equalsIgnoreCase("jpg") || 
					  ext.equalsIgnoreCase("jpeg")));
		} else {
			return false;
		}
	}	
}