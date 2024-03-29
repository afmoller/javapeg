/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;


import moller.javapeg.program.metadata.MetaDataUtil;
import moller.javapeg.program.metadata.MetaData;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.imgscalr.Scalr.Rotation;

public class ImageUtil {

	/**
	 * This method rotates an image according to the associated Exif meta data
	 * orientation value given in the @metaData parameter.
	 *
	 * @param imageIcon
	 * @param metaData
	 * @return
	 */
	public static ImageIcon rotateIfNeeded(ImageIcon imageIcon, MetaData metaData) {
		int exifOrientation = metaData.getExifOrientation();

		double rotation;
		switch (exifOrientation) {
			case 180:
				rotation = 180;
				break;
			case 270:
				rotation = -90;
				break;
			case 90:
				rotation = 90;
				break;
			default:
				return imageIcon;
		}

		BufferedImage bi = new BufferedImage(
				imageIcon.getIconWidth(),
				imageIcon.getIconHeight(),
				BufferedImage.TYPE_INT_RGB);

		Graphics g = bi.createGraphics();
		imageIcon.paintIcon(null, g, 0,0);
		g.dispose();

		BufferedImage bufferedImage = ImageUtil.rotateImage(bi, rotation);
		imageIcon.setImage(bufferedImage);

		return imageIcon;
	}

	/**
	 * @param jpegFile
	 * @param availableWidth
	 * @param availableHeight
	 * @return
	 * @throws IOException
	 */
	public static Image createThumbNailAdaptedToAvailableSpace(File jpegFile, int availableWidth, int availableHeight) throws IOException {

		BufferedImage img = ImageIO.read(jpegFile);
		int imageWidth = img.getWidth();
		int imageHeight = img.getHeight();

		Dimension scaledImage = calculateScaledImageWidthAndHeight(availableWidth, availableHeight, imageWidth, imageHeight);

		return Scalr.resize(img, Method.SPEED, Mode.FIT_EXACT, scaledImage.width, scaledImage.height);
	}

	/**
	 * @param availableWidth
	 * @param availableHeight
	 * @return
	 * @throws IOException
	 */
	public static Image createThumbNailAdaptedToAvailableSpace(byte[] jpegData, int availableWidth, int availableHeight) throws IOException {

		InputStream in = new ByteArrayInputStream(jpegData);
		BufferedImage img = ImageIO.read(in);
		int imageWidth = img.getWidth();
		int imageHeight = img.getHeight();

		Dimension scaledImage = calculateScaledImageWidthAndHeight(availableWidth, availableHeight, imageWidth, imageHeight);

		return Scalr.resize(img, Method.SPEED, Mode.FIT_EXACT, scaledImage.width, scaledImage.height);
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

	public static BufferedImage rotateImage(BufferedImage img,double degree){
		double angle = Math.toRadians(degree);
		return tilt(img,angle);
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

	/**
	 * This method resizes an image and stores the resized image to a specified
	 * directory. If the
	 *
	 * @param fileToResize is the image file to resize.
	 * @param width is the desired width of the resized image
	 * @param height is the desired height of the resized image
	 * @param destinationDirectory is to which directory the resized image
	 *        shall be stored.
	 * @param keepAspectRatio specifies if the aspect ratio shall be respected.
	 * @param resizeMethod specifies which {@link Scalr.Method} to be used when
	 *        the image is resized.
	 * @throws IOException
	 */
	public static void resizeAndStoreImage(File fileToResize, int width, int height, File destinationDirectory, float quality, boolean keepAspectRatio) throws IOException {

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

	    // When both width and height are given then respect those values,
	    // otherwise let Scalr calculate the missing value according to the
	    // images aspect ratio. If the height and width are given and the
	    // keepAspectRatio is true, then the resulting image will have the
	    // size in which it fits into the "box" defined by the width and
	    // height and with the aspect ratio of the image kept intact.
	    Scalr.Mode mode;

	    if (width > 0 && height > 0) {
	        if (keepAspectRatio) {
	            mode = Mode.AUTOMATIC;
	        } else {
	            mode = Mode.FIT_EXACT;
	        }
	    } else {
	        if (width == 0) {
	            mode = Mode.FIT_TO_HEIGHT;
	        } else {
	            mode = Mode.FIT_TO_WIDTH;
	        }
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

	    Scalr.Rotation rotation = null;

	    int orientationTag = MetaDataUtil.getOrientationTag(fileToResize);

	    switch (orientationTag) {
	    case 3:
            rotation = Rotation.CW_180;
            break;
        case 6:
            rotation = Rotation.CW_90;
            break;
        case 8:
            rotation = Rotation.CW_270;
            break;
        default:
            break;
        }

	    if (rotation != null) {
	        resizedBufferedImage = Scalr.rotate(resizedBufferedImage, rotation);
	    }

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
	    try {
	        writer = ImageIO.getImageWritersByFormatName("jpeg").next();
	        ImageWriteParam param = writer.getDefaultWriteParam();
	        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	        param.setCompressionQuality(quality);
	        try (FileImageOutputStream output = new FileImageOutputStream(destFile)) {
	            writer.setOutput(output);
	            IIOImage iioImage = new IIOImage(image, null, null);
	            writer.write(null, iioImage, param);
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (writer != null) writer.dispose();
	    }
	}
}
