package moller.util.io;
/**
 * This class was created : 2009-03-27 by Fredrik Möller
 * Latest changed         : 2009-06-17 by Fredrik Möller
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JPEGUtil {

	/**
	 * Denna metod kontrollerar om en fil är av jpg-typ eller inte.
	 * Den tar ett filobjekt som inparameter och returnerar ett boleskt
	 * värde.
	 *
	 * @param file Ett File-objekt som innehåller den fil som
	 *             skall kontrolleras	
	 * @return     Ett booleskt värde som talar om ifall filen
	 *             är en jpg-fil eller inte. 
	 * @throws FileNotFoundException, IOException 
	 */
	public static boolean isJPEG(File file) throws FileNotFoundException, IOException{
		if (file.isFile() && file.canRead()) {
			byte[]magicByte = new byte[2];

			FileInputStream fis = new FileInputStream(file);
			fis.read(magicByte);

			String fileName = file.getName();
			String extension = fileName.substring(fileName.indexOf(".") + 1);

			/**
			 * Här kontrolleras så att de två första byten i filen har värdet 255 (FF)
			 * och 216 (D8) vilket är det som identifierar en JPEG/JFIF-fil. I if-satsen
			 * har värdet räknats om till unsigned genom att &:a med FF. Vidare testas ifall
			 * filnamnet slutar på JPG eller JPEG så att inte tumnaglar eller liknande med
			 * annan filändelse slinker igenom. 
			 */
			return ((magicByte[0] & 0xFF) == 255 &&	
					(magicByte[1] & 0xFF) == 216) && 
					(extension.equalsIgnoreCase("jpg") || 
                     extension.equalsIgnoreCase("jpeg"));	
		} else {
			return false;
		}
	}	
}