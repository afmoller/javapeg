package moller.javapeg.program.jpeg;
/**
 * This class was created : 2009-03-27 by Fredrik M�ller
 * Latest changed         :
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * FileValidation inneh�ller metoder f�r att kontrollera filers validitet i
 * n�got avseende.
 * 
 * @author      Fredrik M�ller
 * @version     %I%, %G%
 */
public class JPEGValidator {
	 
	/**
	 * Denna metod kontrollerar om en fil �r av jpg-typ eller inte.
	 * Den tar ett filobjekt som inparameter och returnerar ett boleskt
	 * v�rde.
	 *
	 * @param file Ett File-objekt som inneh�ller den fil som
	 *             skall kontrolleras	
	 * @return     Ett booleskt v�rde som talar om ifall filen
	 *             �r en jpg-fil eller inte. 
	 */
	public static boolean isJPEG(File file){
		if (file.isFile() && file.canRead()) {
			byte[]magicByte = new byte[2];
			
			try {
				FileInputStream fis = new FileInputStream(file);
				fis.read(magicByte);
			} catch(FileNotFoundException fnfex) {
				throw new RuntimeException(fnfex);
			} catch(IOException iox) {
				throw new RuntimeException(iox);
			}
						
			String fileName = file.getName();
			String extension = fileName.substring(fileName.indexOf(".") + 1);
					
			/**
			 * H�r kontrolleras s� att de tv� f�rsta byten i filen har v�rdet 255 (FF)
			 * och 216 (D8) vilket �r det som identifierar en JPEG/JFIF-fil. I if-satsen
			 * har v�rdet r�knats om till unsigned genom att &:a med FF. Vidare testas ifall
			 * filnamnet slutar p� JPG eller JPEG s� att inte tumnaglar eller liknande med
			 * annan fil�ndelse slinker igenom. 
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