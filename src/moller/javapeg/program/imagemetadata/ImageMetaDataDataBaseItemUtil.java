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

import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.categories.CategoryImageExifMetaData;
import moller.javapeg.program.datatype.ExposureTime;
import moller.javapeg.program.datatype.ExposureTime.ExposureTimeException;
import moller.javapeg.program.enumerations.xml.ImageMetaDataDataBaseItemElement;
import moller.javapeg.program.logger.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains various utility methods related to the
 * {@link ImageMetaDataItem} object.
 *
 * @author Fredrik
 *
 */
public class ImageMetaDataDataBaseItemUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");

    private static Logger logger = Logger.getInstance();
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
     * @return
     */
    public static ImageMetaDataItem createFromXML(Node imageTag, File imageDirectory) {
        NodeList childNodes = imageTag.getChildNodes();

        CategoryImageExifMetaData imageExifMetaData = null;
        String comment = null;
        int rating = 0;
        Categories categories = null;

        // Fetch the values from the XML section
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ImageMetaDataDataBaseItemElement.getEnum(node.getNodeName())) {
            case EXIF_META_DATA:
                imageExifMetaData = createImageExifMetaData(node);
                break;
            case COMMENT:
                comment = node.getTextContent();
                break;
            case RATING:
                rating = getRating(node);
                break;
            case CATEGORIES:
                categories = getCategories(node);
                break;
            default:
                break;
            }
        }

        File image = getFile(imageTag, imageDirectory);

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
     */
    private static File getFile(Node imageTag, File imageDirectory) {
        NamedNodeMap attributes = imageTag.getAttributes();
        Node fileAttribute = attributes.getNamedItem(ImageMetaDataDataBaseItemElement.FILE.getElementValue());
        String fileName = fileAttribute.getTextContent();

        return new File(imageDirectory, fileName);
    }

    /**
     * Get the assigned image categories as a {@link Categories} object from the
     * XML tag
     *
     * @param categoriesTag
     *            is the corresponding XML tag to unmarshal
     * @return
     */
    private static Categories getCategories(Node categoriesTag) {
        String categoriesString = categoriesTag.getTextContent();
        Categories categories = new Categories();
        categories.addCategories(categoriesString);

        return categories;
    }

    /**
     * Get the assigned image rating as an int value from the XML tag
     *
     * @param ratingNode
     *            is the corresponding XML tag to unmarshal
     * @return
     */
    private static int getRating(Node ratingNode){
        int rating = 0;
        String ratingString = "";
        try {
            ratingString = ratingNode.getTextContent();
            rating = Integer.parseInt(ratingString);
        } catch (NumberFormatException nfex) {
            Logger logger = Logger.getInstance();
            logger.logINFO("Could not parse rating value. Rating value is: \"" + ratingString + "\". Value set to 0 (zero)");
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
     * @return a {@link CategoryImageExifMetaData} object constructed from the
     *         information found in the given {@link Node} object.
     * @throws NumberFormatException
     */
    private static CategoryImageExifMetaData createImageExifMetaData(Node exifMetaData) throws NumberFormatException {

        CategoryImageExifMetaData imageExifMetaData = new CategoryImageExifMetaData();

        NodeList childNodes = exifMetaData.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ImageMetaDataDataBaseItemElement.getEnum(node.getNodeName())) {
            case F_NUMBER:
                imageExifMetaData.setFNumber(Double.parseDouble(node.getTextContent()));
                break;
            case CAMERA_MODEL:
                imageExifMetaData.setCameraModel(node.getTextContent());
                break;
            case DATE_TIME:
                String dateTime = node.getTextContent();

                try {
                    imageExifMetaData.setDateTime(sdf.parse(dateTime));
                } catch (ParseException pex) {
                    // This can happen if there is a missing date or a date in
                    // an incorrect format in the image meta data information.
                    imageExifMetaData.setDateTime(null);
                    logger.logDEBUG("Could not parse date string: \"" + dateTime + "\" with SimpleDateFormat string: \"" + sdf.toPattern() + "\"");
                    logger.logDEBUG(pex);
                }
                break;
            case ISO_VALUE:
                imageExifMetaData.setIsoValue(Integer.parseInt(node.getTextContent()));
                break;
            case PICTURE_HEIGHT:
                imageExifMetaData.setPictureHeight(Integer.parseInt(node.getTextContent()));
                break;
            case PICTURE_WIDTH:
                imageExifMetaData.setPictureWidth(Integer.parseInt(node.getTextContent()));
                break;
            case EXPOSURE_TIME:
                String exposureTime = node.getTextContent();

                try {
                    imageExifMetaData.setExposureTime(new ExposureTime(exposureTime));
                } catch (ExposureTimeException etex) {
                    // This can happen if there is a missing exposure time or
                    // an exposure time in an incorrect format in the image
                    // meta data information.
                    imageExifMetaData.setExposureTime(null);
                    logger.logDEBUG("Could not create a ExposureTime object from string value: " + exposureTime);
                    logger.logDEBUG(etex);
                }
                break;
            default:
                break;
            }
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
     * @return a {@link List} with {@link ImageMetaDataItem} objects
     *         which are constructed from the content found in an image meta
     *         data base XML file.
     */
    public static List<ImageMetaDataItem> getImageMetaDataDataBaseItemsFromXML(NodeList imageTags, File imageDirectory) {
        List<ImageMetaDataItem> imageMetaDataDataBaseItems = new ArrayList<ImageMetaDataItem>();

        int nrOfTags = imageTags.getLength();
        for (int i = 0; i < nrOfTags; i++) {
            imageMetaDataDataBaseItems.add(createFromXML(imageTags.item(i), imageDirectory));
        }
        return imageMetaDataDataBaseItems;
    }

    /**
     * Tests whether or not a list of files exists or not.
     *
     * @param imageMetaDataDataBaseItems
     *            is the list with XML nodes which contain a file attribute.
     * @return a list with {@link File} objects that does not exist. If all
     *         {@link File} objects exist the an empty {@link List} is returned.
     */
    public static List<File> checkReferencedFilesExistence(List<ImageMetaDataItem> imageMetaDataDataBaseItems) {
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
