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
package moller.javapeg.program.imagemetadata;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.categories.CategoryImageExifMetaData;
import moller.javapeg.program.datatype.ExposureTime;
import moller.javapeg.program.datatype.ExposureTime.ExposureTimeException;
import moller.javapeg.program.logger.Logger;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class contains various utility methods related to the
 * {@link ImageMetaDataItem} object.
 *
 * @author Fredrik
 *
 */
public class ImageMetaDataDataBaseItemUtil {

    /**
     * Private constructor, do not instantiate this utility class.
     */
    private ImageMetaDataDataBaseItemUtil() {
        // do nothing here
    }

    /**
     * This method creates and returns an {@link ImageMetaDataItem}
     * object with the input from a meta data base XML file. This object
     * corresponds to the image tag in the XML file.
     *
     * @param imageTag
     *            is the corresponding XML tag to unmarshal
     * @param imageDirectory
     *            is in which directory the XML file resides.
     * @param xPath
     *            is the XPath object to use, when querying the XML
     * @return
     * @throws XPathExpressionException
     */
    public static ImageMetaDataItem createFromXML(Node imageTag, File imageDirectory, XPath xPath) throws XPathExpressionException {
        // Fetch the values from the XML section
        Categories categories = getCategories(xPath, imageTag);
        String comment = getComment(xPath, imageTag);
        File image = getFile(xPath, imageTag, imageDirectory);
        CategoryImageExifMetaData imageExifMetaData = getCategoryImageExifMetaData(xPath, imageTag);
        int rating = getRating(xPath, imageTag);

        // Create and populate the ImageMetaDataDataBaseItem with the values
        // fetched from the XML section.
        ImageMetaDataItem imddbi = new ImageMetaDataItem();

        imddbi.setCategories(categories);
        imddbi.setComment(comment);
        imddbi.setImage(image);
        imddbi.setImageExifMetaData(imageExifMetaData);
        imddbi.setRating(rating);

        return imddbi;
    }

    /**
     * Get the image {@link File} object from the XML tag
     *
     * @param imageTag
     *            is the corresponding XML tag to unmarshal
     * @param imageDirectory
     *            is in which directory the XML file resides. Used to construct
     *            the absolute file path
     * @return the referenced image as a {@link File} object.
     * @throws XPathExpressionException
     */
    private static File getFile(XPath xPath, Node imageTag, File imageDirectory) throws XPathExpressionException {
        String fileName = (String)xPath.evaluate("@" + ImageMetaDataDataBaseItemElement.FILE, imageTag, XPathConstants.STRING);
        return new File(imageDirectory, fileName);
    }

    /**
     * Get the image Exif meta data as a {@link CategoryImageExifMetaData}
     * object from the XML tag
     *
     * @param xPath
     *            is the XPath object to use, when querying the XML
     * @param imageTag
     *            is the corresponding XML tag to unmarshal
     * @return
     * @throws NumberFormatException
     * @throws XPathExpressionException
     */
    private static CategoryImageExifMetaData getCategoryImageExifMetaData(XPath xPath, Node imageTag) throws NumberFormatException, XPathExpressionException {
        return createImageExifMetaData((Node)xPath.evaluate(ImageMetaDataDataBaseItemElement.EXIF_META_DATA, imageTag, XPathConstants.NODE), xPath);
    }

    /**
     * Get the image comment as a {@link String} object from the XML tag
     *
     * @param xPath
     *            is the XPath object to use, when querying the XML
     * @param imageTag
     *            is the corresponding XML tag to unmarshal
     * @return
     * @throws XPathExpressionException
     */
    private static String getComment(XPath xPath, Node imageTag) throws XPathExpressionException {
        return (String)xPath.evaluate(ImageMetaDataDataBaseItemElement.COMMENT, imageTag, XPathConstants.STRING);
    }

    /**
     * Get the assigned image categories as a {@link Categories} object from the
     * XML tag
     *
     * @param xPath
     *            is the XPath object to use, when querying the XML
     * @param imageTag
     *            is the corresponding XML tag to unmarshal
     * @return
     * @throws XPathExpressionException
     */
    private static Categories getCategories(XPath xPath, Node imageTag) throws XPathExpressionException {
        String categoriesString = (String)xPath.evaluate(ImageMetaDataDataBaseItemElement.CATEGORIES, imageTag, XPathConstants.STRING);
        Categories categories = new Categories();
        categories.addCategories(categoriesString);

        return categories;
    }

    /**
     * Get the assigned image rating as an int value from the XML tag
     *
     * @param xPath
     *            is the XPath object to use, when querying the XML
     * @param imageTag
     *            is the corresponding XML tag to unmarshal
     * @return
     * @throws XPathExpressionException
     */
    private static int getRating(XPath xPath, Node imageTag) throws XPathExpressionException {
        int rating = 0;
        try {
            rating = Integer.parseInt((String)xPath.evaluate(ImageMetaDataDataBaseItemElement.RATING, imageTag, XPathConstants.STRING));
        } catch (NumberFormatException nfex) {
            Logger logger = Logger.getInstance();
            logger.logINFO("Could not parse rating value. Rating value is: \"" + (String)xPath.evaluate(ImageMetaDataDataBaseItemElement.RATING, imageTag, XPathConstants.STRING) + "\". Value set to 0 (zero)");
            logger.logINFO(nfex);
        }
        return rating;
    }

    /**
     * This helper method constructs a {@link CategoryImageExifMetaData} object
     * from an {@link Node} object containing such kind of data.
     *
     * @param exifMetaData
     *            is the {@link Node} object that contains the data that shall
     *            be transfered into the {@link CategoryImageExifMetaData}
     *            object
     * @param xPath
     *            is used to query the {@link Node} object.
     * @return a {@link CategoryImageExifMetaData} object constructed from the
     *         information found in the given {@link Node} object.
     * @throws NumberFormatException
     * @throws XPathExpressionException
     */
    private static CategoryImageExifMetaData createImageExifMetaData(Node exifMetaData, XPath xPath) throws NumberFormatException, XPathExpressionException {
        Logger logger = Logger.getInstance();

        CategoryImageExifMetaData imageExifMetaData = new CategoryImageExifMetaData();

        imageExifMetaData.setFNumber(Double.parseDouble((String)xPath.evaluate(ImageMetaDataDataBaseItemElement.F_NUMBER, exifMetaData, XPathConstants.STRING)));
        imageExifMetaData.setCameraModel((String)xPath.evaluate(ImageMetaDataDataBaseItemElement.CAMERA_MODEL, exifMetaData, XPathConstants.STRING));

        String dateTime = (String)xPath.evaluate(ImageMetaDataDataBaseItemElement.DATE_TIME, exifMetaData, XPathConstants.STRING);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        try {
            imageExifMetaData.setDateTime(sdf.parse(dateTime));
        } catch (ParseException pex) {
            imageExifMetaData.setDateTime(null);
            logger.logERROR("Could not parse date string: \"" + dateTime + "\" with SimpleDateFormat string: \"" + sdf.toPattern() + "\"");
            logger.logERROR(pex);
        }

        imageExifMetaData.setIsoValue(Integer.parseInt((String)xPath.evaluate(ImageMetaDataDataBaseItemElement.ISO_VALUE, exifMetaData, XPathConstants.STRING)));
        imageExifMetaData.setPictureHeight(Integer.parseInt((String)xPath.evaluate(ImageMetaDataDataBaseItemElement.PICTURE_HEIGHT, exifMetaData, XPathConstants.STRING)));
        imageExifMetaData.setPictureWidth(Integer.parseInt((String)xPath.evaluate(ImageMetaDataDataBaseItemElement.PICTURE_WIDTH, exifMetaData, XPathConstants.STRING)));

        String exposureTime = (String)xPath.evaluate(ImageMetaDataDataBaseItemElement.EXPOSURE_TIME, exifMetaData, XPathConstants.STRING);

        try {
            imageExifMetaData.setExposureTime(new ExposureTime(exposureTime));
        } catch (ExposureTimeException etex) {
            imageExifMetaData.setExposureTime(null);
            logger.logERROR("Could not create a ExposureTime object from string value: " + exposureTime);
            logger.logERROR(etex);
        }
        return imageExifMetaData;
    }

    /**
     * This method creates and returns a {@link List} of
     * {@link ImageMetaDataItem} objects which are unmarshalled from the
     * image tags found in an image metadata data base XML file.
     *
     * @param imageTags
     *            contains a list of all image tags contained in a image meta
     *            data base file.
     * @param imageDirectory
     *            is the directory where the image meta data base file is to be
     *            found.
     * @param xPath
     *            used to query the {@link Node} objects
     * @return a {@link List} with {@link ImageMetaDataItem} objects
     *         which are constructed from the content found in an image meta
     *         data base XML file.
     * @throws XPathExpressionException
     */
    public static List<ImageMetaDataItem> getImageMetaDataDataBaseItemsFromXML(NodeList imageTags, File imageDirectory, XPath xPath) throws XPathExpressionException {
        List<ImageMetaDataItem> imageMetaDataDataBaseItems = new ArrayList<ImageMetaDataItem>();

        int nrOfTags = imageTags.getLength();
        for (int i = 0; i < nrOfTags; i++) {
            imageMetaDataDataBaseItems.add(createFromXML(imageTags.item(i), imageDirectory, xPath));
        }
        return imageMetaDataDataBaseItems;
    }

    /**
     * Tests whether or not a list of files exists or not.
     *
     * @param imageTags
     *            are the list with XML nodes which contain a file attribute.
     * @param xPath
     *            the object used to query the XML node
     * @param parentDirectory
     *            is the directory to which the file name in the imageTag
     *            element belongs-
     * @return a list with {@link File} objects that does not exist. If all
     *         {@link File} objects exist the an empty {@link List} is returned.
     * @throws XPathExpressionException
     */
    public static List<File> checkReferencedFilesExistence(List<ImageMetaDataItem> imageMetaDataDataBaseItems) {
        Logger logger = Logger.getInstance();
        logger.logDEBUG("Start of checking file consistency for meta data XML content");

        List<File> nonExistingFiles = new ArrayList<File>();

        for (ImageMetaDataItem imageMetaDataDataBaseItem : imageMetaDataDataBaseItems) {

            File referencedFile = imageMetaDataDataBaseItem.getImage();
            if (!referencedFile.exists()) {
                nonExistingFiles.add(referencedFile);
            }
        }
        logger.logDEBUG("Ended the checking file consistency. Found: " + nonExistingFiles.size() + " inconsistencies (missing files) in the total of " + imageMetaDataDataBaseItems.size() + " referenced files");
        return nonExistingFiles;
    }

}
