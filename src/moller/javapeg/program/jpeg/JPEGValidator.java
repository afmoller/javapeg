package moller.javapeg.program.jpeg;
/**
 * This class was created : 2009-03-27 by Fredrik Möller
 * Latest changed         :
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * FileValidation innehåller metoder för att kontrollera filers validitet i
 * något avseende.
 * 
 * @author      Fredrik Möller
 * @version     %I%, %G%
 */
public class JPEGValidator {
	 
	/**
	 * Denna metod kontrollerar om en fil är av jpg-typ eller inte.
	 * Den tar ett filobjekt som inparameter och returnerar ett boleskt
	 * värde.
	 *
	 * @param file Ett File-objekt som innehåller den fil som
	 *             skall kontrolleras	
	 * @return     Ett booleskt värde som talar om ifall filen
	 *             är en jpg-fil eller inte. 
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