package moller.util.image;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import moller.util.jpeg.JPEGScaleAlgorithm;

public class ImageUtil {
	
	/**
	 * @param jpegFile
	 * @param availableWidth
	 * @param availableHeight
	 * @param algorithm
	 * @return
	 * @throws IOException
	 */
	public static Image createThumbNailAdaptedToAvailableSpace(File jpegFile, int availableWidth, int availableHeight, JPEGScaleAlgorithm algorithm) throws IOException {
		
		BufferedImage img = ImageIO.read(jpegFile);
		int imageWidth = img.getWidth();
		int imageHeight = img.getHeight();
		
		Dimension scaledImage = calculateScaledImageWidthAndHeight(availableWidth, availableHeight, imageWidth, imageHeight);
		
		BufferedImage scaledBufferedImage = new BufferedImage(scaledImage.width, scaledImage.height, BufferedImage.TYPE_INT_RGB);
		scaledBufferedImage.createGraphics().drawImage(ImageIO.read(jpegFile).getScaledInstance(scaledImage.width, scaledImage.height, algorithm == JPEGScaleAlgorithm.SMOOTH ? Image.SCALE_SMOOTH : Image.SCALE_FAST),0,0,null);
		
		return scaledBufferedImage;
	}
	
	/**
	 * @param availableWidth
	 * @param availableHeight
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 */
	public static Dimension calculateScaledImageWidthAndHeight(int availableWidth, int availableHeight, int imageWidth, int imageHeight) {
		float ratioWidth  = (float)imageWidth  / (float)availableWidth;
		float ratioHeight = (float)imageHeight / (float)availableHeight;
		
		if (ratioHeight >= ratioWidth) {
			return calculateWidthAndHeight(imageWidth, imageHeight, ratioHeight);
		} else {
			return calculateWidthAndHeight(imageWidth, imageHeight, ratioWidth);
		}
	}
	
	private static Dimension calculateWidthAndHeight(float imageWidth, float imageHeight, float ratio) {
		int scaledImageWidth  = (int)(imageWidth  / ratio);
		int scaledImageHeight = (int)(imageHeight / ratio);
		
		return new Dimension(scaledImageWidth, scaledImageHeight);
	}
}