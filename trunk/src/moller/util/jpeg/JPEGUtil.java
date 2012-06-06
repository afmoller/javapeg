package moller.util.jpeg;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import moller.util.io.FileUtil;
import moller.util.io.StreamUtil;

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
	public static byte[] createThumbNail(File jpegFile, int width, int height, JPEGScaleAlgorithm algorithm) throws IOException {

	    BufferedImage fullsizeImage = ImageIO.read(jpegFile);

	    float fullSizeHeight = fullsizeImage.getHeight();
	    float fullSizeWidth = fullsizeImage.getWidth();

	    float ratioBetweenHeightAndWidth = fullSizeHeight / fullSizeWidth;

	    if (ratioBetweenHeightAndWidth > 1) {
	        width = Math.round((height / ratioBetweenHeightAndWidth));
	    } else {
	        height = Math.round((width / ratioBetweenHeightAndWidth));
	    }

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		img.createGraphics().drawImage(fullsizeImage.getScaledInstance(width, height, algorithm == JPEGScaleAlgorithm.SMOOTH ? Image.SCALE_SMOOTH : Image.SCALE_FAST),0,0,null);

		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			ImageIO.write(img, "jpg", baos);
			return baos.toByteArray();
		} finally {
			StreamUtil.closeStream(baos);
		}
	}

	/**
	 * @param jpegFile
	 * @return
	 * @throws IOException
	 */
	public static byte [] searchForThumbnail(File jpegFile) throws IOException {
		byte [] content = FileUtil.getBytesFromFile(jpegFile);

		int thumbStartIndex = -1;
		int thumbEndIndex   = -1;

		for (int i = 2; i < content.length - 3; i++) {

			if ((content[i] & 0xFF) == 255 && (content[i + 1] & 0xFF) == 216) {
				thumbStartIndex = i;
			} else if ((content[i] & 0xFF) == 255 && (content[i + 1] & 0xFF) == 217) {
				thumbEndIndex = i;
			}

			if (thumbStartIndex > -1 && thumbEndIndex > thumbStartIndex) {
				return Arrays.copyOfRange(content, thumbStartIndex, thumbEndIndex);
			}
		}
		// No thumb nail found in image, return null.
		return null;
	}

	/**
	 * @param data
	 * @return
	 */
	public static boolean isJPEG(byte [] data) {
		if (data == null || data.length == 0) {
			return false;
		} else {
			return startsWithFFD8(data) && endsWithFFD9(data);
		}
	}

	/**
	 * This method return all JPEG files in a directory.
	 *
	 * @param directory is the directory to search for JPEG files
	 *
	 * @return a list containing all found JPEG files, as File objects, in the
	 *         directory defined by the directory parameter.
	 *
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<File> getJPEGFiles(File directory) throws FileNotFoundException, IOException {
		List<File> jpegFiles = new ArrayList<File>();

		for(File file : directory.listFiles()) {
			if(isJPEG(file)){
				jpegFiles.add(file);
			}
		}
		return jpegFiles;
	}


	/**
	 * This method returns a boolean value indicating whether a directory
	 * contains JPEG files or not.
	 *
	 * @param directory is the directory to search for JPEG files
	 *
	 * @return a boolean value indicating whether there is JPEG files in the
	 *         directory specified by the directory parameter.
	 *
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean containsJPEGFiles(File directory) throws FileNotFoundException, IOException {
	    File[] files = directory.listFiles();
	    List<File> potentialJPEGFiles = new ArrayList<File>();
	    List<File> otherFiles = new ArrayList<File>();


	    for (File file : files) {
	        if (file.getName().toLowerCase().contains("jpg") || file.getName().toLowerCase().contains("jpeg")) {
	            potentialJPEGFiles.add(file);
	        } else {
	            otherFiles.add(file);
	        }
	    }

	    for(File file : potentialJPEGFiles) {
            if(isJPEG(file)){
                return true;
            }
        }

	    for(File file : otherFiles) {
            if(isJPEG(file)){
                return true;
            }
        }

        return false;
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
