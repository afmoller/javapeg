package moller.util.image;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import moller.util.io.StreamUtil;
import moller.util.jpeg.JPEGScaleAlgorithm;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;

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
	 * @param jpegFile
	 * @param availableWidth
	 * @param availableHeight
	 * @param algorithm
	 * @return
	 * @throws IOException
	 */
	public static Image createThumbNailAdaptedToAvailableSpace(byte[] jpegData, int availableWidth, int availableHeight, JPEGScaleAlgorithm algorithm) throws IOException {

		InputStream in = new ByteArrayInputStream(jpegData);
		BufferedImage img = ImageIO.read(in);
		int imageWidth = img.getWidth();
		int imageHeight = img.getHeight();

		Dimension scaledImage = calculateScaledImageWidthAndHeight(availableWidth, availableHeight, imageWidth, imageHeight);

		BufferedImage scaledBufferedImage = new BufferedImage(scaledImage.width, scaledImage.height, BufferedImage.TYPE_INT_RGB);
		scaledBufferedImage.createGraphics().drawImage(img.getScaledInstance(scaledImage.width, scaledImage.height, algorithm == JPEGScaleAlgorithm.SMOOTH ? Image.SCALE_SMOOTH : Image.SCALE_FAST),0,0,null);

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

	public static Image rotateImage(Image img,double degree){
		BufferedImage bufImg = toBufferedImage(img);
		double angle = Math.toRadians(degree);

		return tilt(bufImg,angle);
	}

	public static BufferedImage tilt(BufferedImage image, double angle) {
		double sin = Math.abs(Math.sin(angle));
		double cos = Math.abs(Math.cos(angle));

		int w = image.getWidth();
		int h = image.getHeight();

		int neww = (int)Math.floor(w*cos+h*sin);
		int newh = (int)Math.floor(h*cos+w*sin);

		GraphicsConfiguration gc = getDefaultConfiguration();
		BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
		Graphics2D g = result.createGraphics();
		g.translate((neww-w)/2, (newh-h)/2);
		g.rotate(angle, w/2, h/2);
		g.drawRenderedImage(image, null);
		g.dispose();
		return result;
	}

	public static GraphicsConfiguration getDefaultConfiguration() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		return gd.getDefaultConfiguration();
	}

	// http://www.exampledepot.com/egs/java.awt.image/Image2Buf.html
	// An Image object cannot be converted to a BufferedImage object.
	// The closest equivalent is to create a buffered image and then draw the image on the buffered image.
	// This example defines a method that does this.

	// This method returns a buffered image with the contents of an image
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage)image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent Pixels
		boolean hasAlpha = hasAlpha(image);

		// Create a buffered image with a format that's compatible with the screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			if (hasAlpha) {
				transparency = Transparency.BITMASK;
			}

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			if (hasAlpha) {
				type = BufferedImage.TYPE_INT_ARGB;
			}
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	// http://www.exampledepot.com/egs/java.awt.image/HasAlpha.html
	// This method returns true if the specified image has transparent pixels
	public static boolean hasAlpha(Image image) {
		// If buffered image, the color model is readily available
		if (image instanceof BufferedImage) {
			BufferedImage bimage = (BufferedImage)image;
			return bimage.getColorModel().hasAlpha();
		}

		// Use a pixel grabber to retrieve the image's color model;
		// grabbing a single pixel is usually sufficient
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		// Get the image's color model
		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}

	/**
	 * This method returns an Icon from an InputStream.
	 *
	 * @param imageStream is the resource to create the Icon from.
	 * @param silent is a flag indicating whether this method should propagate
	 *        exceptions that might occur when the InputStream is closed.
	 *
	 * @return an Icon with the content specified by the supplied InputStream
	 *
	 * @throws IOException is thrown if an error occurs during reading.
	 */
	public static Icon getIcon(InputStream imageStream, boolean silent) throws IOException {
		ImageIcon imageIcon = new ImageIcon();

		try {
			imageIcon.setImage(ImageIO.read(imageStream));
		}  finally {
			StreamUtil.close(imageStream, true);
		}
		return imageIcon;
	}

	/**
	 * This method resizes an image and stores the resized image to a specified
	 * directory. If the
	 *
	 * @param fileToResize is the image file to resize.
	 * @param width is the desired width of the resized image
	 * @param height is the desired height of the resized image
	 * @param destinationDirectory is to which directory the resized image
	 *        shall be stored.
	 * @param resizeMethod specifies which {@link Scalr.Method} to be used when
	 *        the image is resized.
	 * @throws IOException
	 */
	public static void resizeAndStoreImage(File fileToResize, int width, int height, File destinationDirectory, float quality) throws IOException {

	    // Check size values
	    if (width < 1 && height < 1) {
	        throw new IllegalArgumentException("Both width and height argument may not be lower than 1 (one): " + width + " (width) and " + height + " (height)");
	    }

	    // Ensure no negative value is sent to Scalr
	    if (width < 0) {
	        width = 0;
	    }

	    // Ensure no negative value is sent to Scalr
	    if (height < 0) {
	        height = 0;
	    }

	    BufferedImage fileToResizeBufferedImage = ImageIO.read(fileToResize);

	    Scalr.Mode mode;

	    if (width == 0) {
	        mode = Mode.FIT_TO_HEIGHT;
	    } else {
	        mode = Mode.FIT_TO_WIDTH;
	    }

	    // Set best quality as default method
	    Method resizeMethod = Method.ULTRA_QUALITY;

	    if (quality == 0.25f) {
	        resizeMethod = Method.SPEED;
	    } else if (quality == 0.5f) {
	        resizeMethod = Method.BALANCED;
	    } else if (quality == 0.75f) {
            resizeMethod = Method.QUALITY;
        } else if (quality == 1.0f) {
            resizeMethod = Method.ULTRA_QUALITY;
        }

	    BufferedImage resizedBufferedImage = Scalr.resize(fileToResizeBufferedImage, resizeMethod, mode, width, height);
	    fileToResizeBufferedImage.flush();

	    writeJpeg(resizedBufferedImage, new File(destinationDirectory, fileToResize.getName()), quality);

	}

	/**
	 * Write a JPEG file setting the compression quality.
	 *
	 * @param image
	 *                a BufferedImage to be saved
	 * @param destFile
	 *                destination file (absolute or relative path)
	 * @param quality
	 *                a float between 0 and 1, where 1 means uncompressed.
	 * @throws IOException
	 *                 in case of problems writing the file
	 */
	public static void writeJpeg(BufferedImage image, File destFile, float quality)
	throws IOException {
	    ImageWriter writer = null;
	    FileImageOutputStream output = null;
	    try {
	        writer = ImageIO.getImageWritersByFormatName("jpeg").next();
	        ImageWriteParam param = writer.getDefaultWriteParam();
	        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	        param.setCompressionQuality(quality);
	        output = new FileImageOutputStream(destFile);
	        writer.setOutput(output);
	        IIOImage iioImage = new IIOImage(image, null, null);
	        writer.write(null, iioImage, param);
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (writer != null) writer.dispose();
	        if (output != null) output.close();
	    }
	}
}
