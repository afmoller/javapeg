package moller.util.io;
/**
 * This class was created : 2009-03-27 by Fredrik Möller
 * Latest changed         : 2009-06-17 by Fredrik Möller
 *                        : 2010-01-02 by Fredrik Möller
 *                        : 2010-01-03 by Fredrik Möller
 *                        : 2010-01-04 by Fredrik Möller
 */

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

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
			
			byte [] ffd8 = new byte [2];
			byte [] ffd9 = new byte [2];
			
			FileInputStream fis = null;
			
			try {
				fis = new FileInputStream(file);
				
				fis.read(ffd8);
				fis.skip(file.length() - 2 - 2);
				fis.read(ffd9);
			} finally {
				 if(fis != null) {
					 fis.close();
				 }
			}
			
			String fileName = file.getName();
			
			/**
			 * Här kontrolleras så att de två första byten i filen har värdet 255 (FF)
			 * och 216 (D8) vilket är det som identifierar en JPEG/JFIF-fil. I if-satsen
			 * har värdet räknats om till unsigned genom att &:a med FF. Vidare testas ifall
			 * filnamnet slutar på JPG eller JPEG så att inte tumnaglar eller liknande med
			 * annan filändelse slinker igenom. 
			 */
			return (startsWithFFD8(ffd8) && endsWithFFD9(ffd9) && hasJFIFExtension(fileName));
		} else {
			return false;
		}
	}
	
	/**
	 * @param jpegFile
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static ByteArrayOutputStream createThumbNail(File jpegFile, int width, int height) throws IOException {
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		img.createGraphics().drawImage(ImageIO.read(jpegFile).getScaledInstance(width, height, Image.SCALE_SMOOTH),0,0,null);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", baos);
		
		return baos;
	}
	
	/**
	 * @param data
	 * @return
	 */
	public static boolean isJPEG(byte [] data) {
		return startsWithFFD8(data) && endsWithFFD9(data);
	}
	
	/**
	 * @param data
	 * @return
	 */
	private static boolean startsWithFFD8(byte [] data) {
		return (data[0] & 0xFF) == 255 && (data[1] & 0xFF) == 216;
	}
	
	private static boolean endsWithFFD9(byte [] data) {
		return (data[data.length - 2] & 0xFF) == 255 && (data[data.length - 1] & 0xFF) == 217;
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	private static boolean hasJFIFExtension(String fileName) {
		String ext = fileName.substring(fileName.indexOf(".") + 1).toLowerCase();
		return ext.equals("jpg") || ext.equals("jpeg");
	}
}