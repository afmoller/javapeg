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

import java.awt.Component;
import java.awt.Rectangle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.categories.CategoryImageExifMetaData;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.controller.section.CategoriesConfig;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.javapeg.program.config.model.repository.RepositoryExceptions;
import moller.javapeg.program.config.schema.SchemaUtil;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.contexts.ImageMetaDataDataBaseItemsToUpdateContext;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContext;
import moller.javapeg.program.contexts.imagemetadata.ImagePathAndIndex;
import moller.javapeg.program.datatype.ImageSize;
import moller.javapeg.program.enumerations.ImageMetaDataContextAction;
import moller.javapeg.program.gui.dialog.CategoryImportExportPopup;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.io.PathUtil;
import moller.util.io.StreamUtil;
import moller.util.jpeg.JPEGUtil;
import moller.util.result.ResultObject;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ImageMetaDataDataBaseHandler {

    private static DocumentBuilder db;

    static {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
             db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            Logger logger = Logger.getInstance();
            logger.logFATAL("Could not get an DocumentBuilder");
            logger.logFATAL(e);
            System.exit(-1);
        }
    }

    private static Configuration configuration = Config.getInstance().get();

    /**
     * This method will check against the stored add to repository policy
     * whether a path shall be added to the image meta data repository or not.
     *
     * @param path the directory to check policy against.
     *
     * @return a boolean value indicating whether the path shall be added to
     *         the image meta data repository or not. A return value of true
     *         means that the path shall be added to the image meta data
     *         repository.
     */
    public static boolean addPathToRepositoryAccordingToPolicy(Component parentComponent, File path) {

        Integer policy = configuration.getTagImages().getImagesPaths().getAddToRepositoryPolicy();

        RepositoryExceptions repositoryExceptions = configuration.getRepository().getExceptions();

        // The following switch statement will check exceptions to the
        // additions policy.
        switch (policy) {
        case 0:
            for (File neverAdd : repositoryExceptions.getNeverAdd()) {
                if (PathUtil.isChild(path, neverAdd)) {
                    policy = 2;
                    break;
                }
            }
        case 2:
            for (File allwaysAdd : repositoryExceptions.getAllwaysAdd()) {
                if (PathUtil.isChild(path, allwaysAdd)) {
                    policy = 0;
                    break;
                }
            }
        default:
            // Do nothing
            break;
        }

        // Check what to do..
        switch (policy) {
        // Add without a question.
        case 0:
            return true;
        // Ask first if addition shall be done.
        case 1:
            return addPathToImageRepository(parentComponent, path);
        // Do not add.
        case 2:
            return false;
        default:
            return addPathToImageRepository(parentComponent, path);
        }
    }

    private static boolean addPathToImageRepository(Component parentComponent, File directory) {
        Language lang = Language.getInstance();

        JCheckBox rememberSelectionCheckBox = new JCheckBox(lang.get("category.rememberMySelection"));

        Object[] array = { lang.get("category.addToImageRepositoryQuestionPartOne") + "\n" + directory.getAbsolutePath() + "\n" + lang.get("category.addToImageRepositoryQuestionPartTwo") + "\n\n",
                           rememberSelectionCheckBox };

        int result = JOptionPane.showConfirmDialog(parentComponent, array, lang.get("category.addToImageRepositoryHeader"), JOptionPane.YES_NO_OPTION);

        if (rememberSelectionCheckBox.isSelected()) {
           Integer checkBoxState = null;

           if (result == JOptionPane.YES_OPTION) {
               checkBoxState = 0;
           } else if (result == JOptionPane.NO_OPTION) {
               checkBoxState = 2;
           }

           if (checkBoxState != null) {
               configuration.getTagImages().getImagesPaths().setAddToRepositoryPolicy(checkBoxState);
           }
        }

        if (result == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean createImageMetaDataDataBaseFileIn(File imageRepository) {
        Logger logger = Logger.getInstance();
        logger.logDEBUG("Starting to create image meta data file for files in directory: " + imageRepository.getAbsolutePath());

        try {
            logger.logDEBUG("Starting to find JPEG files in directory: " + imageRepository.getAbsolutePath());
            List<File> jpegFiles = JPEGUtil.getJPEGFiles(imageRepository);
            logger.logDEBUG("Finished to find JPEG files in directory: " + imageRepository.getAbsolutePath());

            Map<File, ImageMetaDataItem> imageMetaDataDataBaseItems = new HashMap<File, ImageMetaDataItem>();

            logger.logDEBUG("Starting to store JPEG Meta data for files in directory: " + imageRepository.getAbsolutePath());
            for (File jpegFile : jpegFiles) {
                ImageMetaDataItem imddbi = new ImageMetaDataItem();

                imddbi.setImage(jpegFile);
                imddbi.setImageExifMetaData(new CategoryImageExifMetaData(jpegFile));
                imddbi.setComment(Language.getInstance().get("findimage.comment.defaultCommentText"));
                imddbi.setRating(0);
                imddbi.setCategories(new Categories());

                imageMetaDataDataBaseItems.put(jpegFile, imddbi);
            }
            logger.logDEBUG("Finished to store JPEG Meta data for files in directory: " + imageRepository.getAbsolutePath());

            logger.logDEBUG("Starting to persist JPEG Meta data for files in directory: " + imageRepository.getAbsolutePath());
            boolean result = updateDataBaseFile(imageMetaDataDataBaseItems, imageRepository, ImageMetaDataContextAction.ADD);
            logger.logDEBUG("Finished to persist JPEG Meta data for files in directory: " + imageRepository.getAbsolutePath());
            logger.logDEBUG("Finished to create image meta data file for files in directory: " + imageRepository.getAbsolutePath());

            return result;
        } catch (IOException iox) {
            logger.logERROR("Could not find file: " + imageRepository.getAbsolutePath());
            logger.logERROR(iox);
            return false;
        }
    }

    public static boolean updateDataBaseFile(Map<File, ImageMetaDataItem> imageMetaDataDataBaseItems, File destination, ImageMetaDataContextAction imageMetaDataContextAction) {
        Logger logger = Logger.getInstance();

        OutputStream os = null;

        if (ImageMetaDataDataBaseItemsToUpdateContext.getInstance().isFlushNeeded() || imageMetaDataContextAction == ImageMetaDataContextAction.ADD) {
            try {
                String encoding = C.UTF8;

                os = new FileOutputStream(new File(destination, C.JAVAPEG_IMAGE_META_NAME));
                BufferedOutputStream bos = new BufferedOutputStream(os);
                XMLOutputFactory factory = XMLOutputFactory.newInstance();
                XMLStreamWriter w = factory.createXMLStreamWriter(bos, encoding);

                XMLUtil.writeStartDocument(encoding, "1.0", w);
                XMLUtil.writeComment("This XML file contains meta data information of all JPEG image" + C.LS +
                                     "files that exists in the directory where this XML file is to be found." + C.LS +
                                     "The content of this file is used and modified by the application JavaPEG", w);

                XMLAttribute[] xmlAttributes = new XMLAttribute[4];
                xmlAttributes[0] = new XMLAttribute("version", C.IMAGE_META_DATA_DATA_BASE_VERSION);
                xmlAttributes[1] = new XMLAttribute("xmlns", "http://moller.javapeg.metadata.com");
                xmlAttributes[2] = new XMLAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
                xmlAttributes[3] = new XMLAttribute("xsi:schemaLocation", "http://moller.javapeg.metadata.com metadata.xsd");

                XMLUtil.writeElementStart("javapeg-image-meta-data-data-base", xmlAttributes, w);
                XMLUtil.writeElement(ImageMetaDataDataBaseItemElement.JAVAPEG_ID, configuration.getJavapegClientId(), w);

                logger.logDEBUG("Start writing image elements");
                for(File image : imageMetaDataDataBaseItems.keySet()) {
                    ImageMetaDataItem imddbi = imageMetaDataDataBaseItems.get(image);
                    CategoryImageExifMetaData ciemd = imddbi.getImageExifMetaData();

                    logger.logDEBUG("Start writing image detail elements for image: " + image.getAbsolutePath());
                    XMLUtil.writeElementStart(ImageMetaDataDataBaseItemElement.IMAGE, ImageMetaDataDataBaseItemElement.FILE, imddbi.getImage().getName(), w);
                    XMLUtil.writeElementStart(ImageMetaDataDataBaseItemElement.EXIF_META_DATA, w);
                    XMLUtil.writeElement(ImageMetaDataDataBaseItemElement.F_NUMBER, Double.toString(ciemd.getFNumber()), w);
                    XMLUtil.writeElement(ImageMetaDataDataBaseItemElement.CAMERA_MODEL  , ciemd.getCameraModel()  , w);
                    XMLUtil.writeElement(ImageMetaDataDataBaseItemElement.DATE_TIME     , ciemd.getDateTimeAsString()         , w);
                    XMLUtil.writeElement(ImageMetaDataDataBaseItemElement.ISO_VALUE     , Integer.toString(ciemd.getIsoValue())     , w);
                    XMLUtil.writeElement(ImageMetaDataDataBaseItemElement.PICTURE_HEIGHT, Integer.toString(ciemd.getPictureHeight()), w);
                    XMLUtil.writeElement(ImageMetaDataDataBaseItemElement.PICTURE_WIDTH , Integer.toString(ciemd.getPictureWidth()) , w);
                    XMLUtil.writeElement(ImageMetaDataDataBaseItemElement.EXPOSURE_TIME , ciemd.getExposureTime() == null ? "" : ciemd.getExposureTime().toString() , w);
                    XMLUtil.writeElementEnd(w);
                    XMLUtil.writeElement(ImageMetaDataDataBaseItemElement.COMMENT, imddbi.getComment(), w);
                    XMLUtil.writeElement(ImageMetaDataDataBaseItemElement.RATING, Integer.toString(imddbi.getRating()), w);
                    XMLUtil.writeElement(ImageMetaDataDataBaseItemElement.CATEGORIES, imddbi.getCategories() == null ? "" : imddbi.getCategories().toString(), w);

                    XMLUtil.writeElementEnd(w);
                    logger.logDEBUG("Finished writing image detail elements for image: " + image.getAbsolutePath());

                }
                XMLUtil.writeElementEnd(w);
                logger.logDEBUG("Finished writing image elements");
                logger.logDEBUG("Started xml writer flush");
                w.flush();
                logger.logDEBUG("Finished xml writer flush");
                logger.logDEBUG("File: " + destination.getAbsolutePath() + C.FS + C.JAVAPEG_IMAGE_META_NAME + " has been updated with the changes made to the content");

                switch (imageMetaDataContextAction) {
                case ADD:
                    for(File image : imageMetaDataDataBaseItems.keySet()) {
                        ImageMetaDataItem imddbi = imageMetaDataDataBaseItems.get(image);
                        populateImageMetaDataContext(configuration.getJavapegClientId(), imddbi);
                    }
                    break;
                case UPDATE:
                    for(File image : imageMetaDataDataBaseItems.keySet()) {
                        ImageMetaDataItem imddbi = imageMetaDataDataBaseItems.get(image);
                        if (imddbi.isNeedsToBeSyncedWithImageMetaDataContext()) {
                            updateImageMetaDataContext(configuration.getJavapegClientId(), image, imddbi.getComment(), imddbi.getRating(), imddbi.getCategories());
                            imddbi.setNeedsToBeSyncedWithImageMetaDataContext(false);
                        }
                    }
                    break;
                }
            } catch (XMLStreamException xsex) {
                logger.logERROR("Could not write to XMLStream");
                logger.logERROR(xsex);
                return false;
            } catch (FileNotFoundException fnfex) {
                logger.logERROR("Could not find file: " + destination.getAbsolutePath() + C.FS + C.JAVAPEG_IMAGE_META_NAME);
                logger.logERROR(fnfex);
                return false;
            } finally {
                StreamUtil.close(os, true);
            }
        } else {
            logger.logDEBUG("File: " + destination.getAbsolutePath() + C.FS + C.JAVAPEG_IMAGE_META_NAME + " has not been updated since there was no changes made to the content");
        }
        return true;
    }

    private static void showCategoryImportPopup(File imageMetaDataDataBase, String javaPegIdValue) {
        Logger logger = Logger.getInstance();
        Language lang = Language.getInstance();

        CategoryImportExportPopup ciep = new CategoryImportExportPopup(true, lang.get("categoryimportexport.import.long.title"), new Rectangle(100, 100, 500,200), imageMetaDataDataBase);
        if (ciep.isActionButtonClicked()) {
            ImportedCategories importedCategoriesFromFile = CategoriesConfig.importCategoriesConfig(ciep.getCategoryFileToImportExport());

            if (importedCategoriesFromFile.getJavaPegId().equals(javaPegIdValue)) {
                ImportedCategories importedCategories = new ImportedCategories();
                importedCategories.setDisplayName(ciep.getFileName());
                importedCategories.setRoot(importedCategoriesFromFile.getRoot());

                configuration.getImportedCategoriesConfig().put(javaPegIdValue, importedCategories);
                ApplicationContext.getInstance().setRestartNeeded();
            } else {
                logger.logWARN("The JavaPEG client id (" + importedCategoriesFromFile.getJavaPegId() + ") in the categories file (" + ciep.getCategoryFileToImportExport().getAbsolutePath() + ") does not match the client id (" + javaPegIdValue + ") in the image meta data base file (" + imageMetaDataDataBase.getAbsolutePath() + ")");
                if (0 == JOptionPane.showConfirmDialog(null, lang.get("categoryimportexport.import.wrongCategoriesFile"), lang.get("categoryimportexport.import.wrongCategoriesFile.label"), JOptionPane.YES_OPTION, JOptionPane.ERROR_MESSAGE)) {
                    showCategoryImportPopup(imageMetaDataDataBase, javaPegIdValue);
                }
            }
        }
    }

    /**
     * This method constructs an {@link ImageMetaDataDataBase} object from an
     * the XML file specified by the parameter imageMetaDataDataBase.
     *
     * @param imageMetaDataDataBase
     *            specifies which XML file to create an
     *            {@link ImageMetaDataDataBase} object of.
     * @return an {@link ImageMetaDataDataBase} object from the XML file
     *         specified by the input parameter imageMetaDataDataBase.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static ImageMetaDataDataBase deserializeImageMetaDataDataBaseFile(File imageMetaDataDataBase) throws ParserConfigurationException, SAXException, IOException {
        Logger logger = Logger.getInstance();
        logger.logDEBUG("Image Meta Data File: " + imageMetaDataDataBase.getAbsolutePath() + " START DESERIALIZATION");
        Document doc = db.parse(imageMetaDataDataBase);
        doc.getDocumentElement().normalize();

        /**
         *  Get all the image tags as ImageMetaDataDataBaseItem objects.
         */
        NodeList allImageTags = doc.getElementsByTagName(ImageMetaDataDataBaseItemElement.IMAGE);

        List<ImageMetaDataItem> imageMetaDataDataBaseItemsFromXML = ImageMetaDataDataBaseItemUtil.getImageMetaDataDataBaseItemsFromXML(allImageTags, imageMetaDataDataBase.getParentFile());

        /**
         * Get the JavaPEGId
         */
        String javaPegIdValue = "";

        NodeList javaPegIdValueNodeList = doc.getElementsByTagName(ImageMetaDataDataBaseItemElement.JAVAPEG_ID);
        if (javaPegIdValueNodeList.getLength() > 0) {
            javaPegIdValue = javaPegIdValueNodeList.item(0).getTextContent();
        }

        /**
         * Construct the java representation of the XML file.
         */
        ImageMetaDataDataBase imddb = new ImageMetaDataDataBase();
        imddb.setImageMetaDataItems(imageMetaDataDataBaseItemsFromXML);
        imddb.setJavaPEGId(javaPegIdValue);

        logger.logDEBUG("Image Meta Data File: " + imageMetaDataDataBase.getAbsolutePath() + " FINISHED DESERIALIZATION");
        return imddb;
    }

    /**
     * Displays a category import dialog if the imageMetaDataDataBase file which
     * is given to this method as an input parameter is not created by the
     * current running JavaPEG instance.
     *
     * @param imageMetaDataDataBase
     *            is the file to check if there is a need to do a category
     *            import for.
     * @param javaPegIdValue
     *            specifies which JavaPEG client that has created the filed
     *            defined in the parameter imageMetaDataDataBase.
     */
    public static void showCategoryImportDialogIfNeeded(File imageMetaDataDataBase, String javaPegIdValue) {
        ApplicationContext ac = ApplicationContext.getInstance();
        ac.setImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance(configuration.getJavapegClientId().equals(javaPegIdValue));

        if (!ac.isImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance()) {
            if (!configuration.getImportedCategoriesConfig().containsKey(javaPegIdValue)) {
                showCategoryImportPopup(imageMetaDataDataBase, javaPegIdValue);
            }
        }
    }

    /**
     * @param javaPegIdValue
     * @param imageMetaDataItems
     */
    public static void populateImageMetaDataContext(String javaPegIdValue, List<ImageMetaDataItem> imageMetaDataItems) {
        if (imageMetaDataItems != null && javaPegIdValue != null) {
            for (ImageMetaDataItem imageMetaDataItem : imageMetaDataItems) {
                populateImageMetaDataContext(javaPegIdValue, imageMetaDataItem);
            }
        }
    }

    private static void populateImageMetaDataContext(String javaPegIdValue, ImageMetaDataItem imageMetaDataDataBaseItemFromImageTag) {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();
        final String imagePath = imageMetaDataDataBaseItemFromImageTag.getImage().getAbsolutePath();

        CategoryImageExifMetaData imageExifMetaData = imageMetaDataDataBaseItemFromImageTag.getImageExifMetaData();

        imdc.addCameraModel(javaPegIdValue, imageExifMetaData.getCameraModel(), imagePath);
        imdc.addDateTime(javaPegIdValue, imageExifMetaData.getDateTime(), imagePath);
        imdc.addIso(javaPegIdValue, imageExifMetaData.getIsoValue(), imagePath);
        imdc.addImageSize(javaPegIdValue, new ImageSize(imageExifMetaData.getPictureHeight(), imageExifMetaData.getPictureWidth()), imagePath);
        imdc.addExposureTime(javaPegIdValue, imageExifMetaData.getExposureTime(), imagePath);
        imdc.addFNumber(javaPegIdValue, imageExifMetaData.getFNumber(), imagePath);
        imdc.addComment(javaPegIdValue, imageMetaDataDataBaseItemFromImageTag.getComment(), imagePath);
        imdc.addRating(javaPegIdValue, imageMetaDataDataBaseItemFromImageTag.getRating(), imagePath);

        Categories categories = imageMetaDataDataBaseItemFromImageTag.getCategories();

        for (String category : categories.getCategories()) {
            imdc.addCategory(javaPegIdValue, category, imagePath);
        }
    }

    public static void updateImageMetaDataContext(String javaPegIdValue, File image, String newComment, int rating, Categories categories) {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();
        ImagePathAndIndex ipai = ImagePathAndIndex.getInstance();

        updateImageComment(javaPegIdValue, image, newComment, imdc, ipai);
        updateImageRating(javaPegIdValue, image, rating, imdc, ipai);
        updateImageCategories(image, categories, imdc, ipai);
    }

    private static void updateImageComment(String javaPegIdValue, File image, String newComment, ImageMetaDataContext imdc, ImagePathAndIndex ipai) {
        Map<String, Set<Integer>> comments = imdc.getComments(javaPegIdValue);

        search:
        for (String comment : comments.keySet()) {
            Set<Integer> indices = comments.get(comment);

            for (Integer index : indices) {
                if (image.getAbsolutePath().equals(ipai.getImagePathForIndex(index))) {
                    /**
                     *  Comment changed, deletion of old necessary
                     *  and adding the new comment
                     */
                    if (!newComment.equals(comment)) {
                        indices.remove(index);
                        if(indices.size() == 0) {
                            comments.remove(comment);
                        }
                        imdc.addComment(javaPegIdValue, newComment, image.getAbsolutePath());
                        break search;
                    }
                    /**
                     * Comment not changed, skip the rest of the search.
                     */
                    else {
                        break search;
                    }
                }
            }
        }
    }

    /**
     * This method updates the rating meta data base in the image meta data
     * base. If the rating has changed for an image, then the rating meta data
     * base will be updated to reflect this change.
     *
     * @param javaPegIdValue
     *            defines for which part of the rating meta data base to do an
     *            update .
     * @param image
     *            defines for which image the update shall be done.
     * @param rating
     *            is the rating to set
     * @param imdc
     *            is the {@link ImageMetaDataContext} which holds all meta data
     *            base information
     * @param ipai
     *            is the repository of image paths to index mappings.
     */
    private static void updateImageRating(String javaPegIdValue, File image, int rating, ImageMetaDataContext imdc, ImagePathAndIndex ipai) {
        Map<Integer, Set<Integer>> ratings = imdc.getRatings(javaPegIdValue);

        /**
         * Only do null safe operations.
         */
        if (ratings != null && ratings.get(rating) != null) {
            int imageIndex = ipai.getIndexForImagePath(image.getAbsolutePath());

            /**
             *  Initial test to see whether the rating has changed or not. If no
             *  change do not do anything, otherwise search thru the entire list of
             *  ratings.
             */
            if (!ratings.get(rating).contains(imageIndex)) {
                search:
                for (int index = 0; index < ratings.size(); index++) {
                    /**
                     * Do not search in the already searched index set
                     */
                    if (index != rating) {
                        /**
                         * If a match is found remove that the imageIndex for that
                         * rating and add the new rating value to the
                         * ImageMetaDataContext.
                         */
                        if (ratings.get(index).contains(imageIndex)) {
                            ratings.get(index).remove(imageIndex);
                            imdc.addRating(javaPegIdValue, rating, image.getAbsolutePath());
                            break search;
                        }
                    }
                }
            }
        } else {
            imdc.addRating(javaPegIdValue, rating, image.getAbsolutePath());
        }
    }

    /**
     * This method updates the category image meta data base. It removes any
     * deselected categories and adds newly selected categories for the image
     * that comes to this method as the image parameter into the category meta
     * data base.
     *
     * @param image
     *            is the image to update the categories for.
     * @param categories
     *            are the currently selected categories for the image specifies
     *            by the parameter image
     * @param imdc
     *            is the {@link ImageMetaDataContext} which holds all meta data
     *            base information
     * @param ipai
     *            is the repository of image paths to index mappings.
     */
    private static void updateImageCategories(File image, Categories categories, ImageMetaDataContext imdc, ImagePathAndIndex ipai) {
        Map<String, Map<String, Set<Integer>>> javaPegIdToCategoriesMap = imdc.getCategories();

        int imageIndex = ipai.getIndexForImagePath(image.getAbsolutePath());

        List<String> categoriesToRemove = new ArrayList<String>();

         Map<String, Set<Integer>> categoriesMap = javaPegIdToCategoriesMap.get(configuration.getJavapegClientId());

         /**
          * Only do this if there are any categories defined for this
          * JavapegClientId.
          */
         if (categoriesMap != null) {
             /**
              * Remove any unselected categories from the ImageMetaDataContext
              */
             for (String category : categoriesMap.keySet()) {
                 Set<Integer> indices = categoriesMap.get(category);

                 if (indices.contains(imageIndex)) {
                     if (categories == null || !categories.getCategories().contains(category)) {
                         indices.remove(imageIndex);
                         if(indices.size() == 0) {
                             categoriesToRemove.add(category);
                         }
                     }
                 }
             }

             /**
              *  Remove any empty categories.
              */
             for (String categoryToRemove : categoriesToRemove) {
                 javaPegIdToCategoriesMap.remove(categoryToRemove);
             }
         }

         /**
          * Add any newly selected categories to the ImageMetaDataContext
          */
         if (categories != null) {
             for (String category : categories.getCategories()) {
                 if (categoriesMap != null) {
                     Set<Integer> indices = categoriesMap.get(category);

                     if (indices == null || !indices.contains(imageIndex)) {
                         imdc.addCategory(configuration.getJavapegClientId(), category, image.getAbsolutePath());
                     }
                 } else {
                     /**
                      * This will only happen the first time a meta data file is
                      * created and when categories are added without an
                      * application restart in between.
                      */
                     imdc.addCategory(configuration.getJavapegClientId(), category, image.getAbsolutePath());
                 }
             }
         }
    }

    /**
     * This method checks if an {@link ImageMetaDataDataBase} object which
     * contains an parsed representation of an image meta data XML file is
     * consistent with the files contained in the directory where the file is
     * stored. </p> Consistent means: </p> 1:  That all referenced
     * {@link File} object in the {@link ImageMetaDataItem} objects also
     * exist in the directory where the meta data base XML file is stored.
     *
     * @param imageMetaDataDataBase
     *            is the {@link ImageMetaDataDataBase} object to make a
     *            consistency check on
     * @param directory
     *            is the directory in which the meta data base XML file is
     *            stored and, where the related JPEG files are stored.
     * @return true point 1 above is true, otherwise false.
     */
    public static boolean isConsistent(ImageMetaDataDataBase imageMetaDataDataBase, File directory) {

        List<File> jpegFilesInDirectory = Arrays.asList(directory.listFiles());

        // If not exactly the same files are referenced in the meta data
        // base file as exist on disk, then return false.
        for (ImageMetaDataItem imageMetaDataItem : imageMetaDataDataBase.getImageMetaDataItems()) {
            if (!jpegFilesInDirectory.contains(imageMetaDataItem.getImage())) {
                return false;
            }
        }

        // Everything OK, return true.
        return true;
    }

    /**
     * Utility method which tests if an meta data base file (XML) is valid
     * (checked against an Schema).
     *
     * @param metaDataBaseFile
     *            is the meta data base file to check the validity of
     * @return a {@link ResultObject} indicating whether or not the meta data
     *         base file is valid against the specified schema. If the meta data
     *         base file is not valid then is the cause of invalidity attached
     *         as an exception
     */
    public static ResultObject<Exception> isMetaDataBaseValid(File metaDataBaseFile) {
        String configSchemaLocation = C.PATH_SCHEMA_META_DATA + SchemaUtil.getMetaDataSchemaForVersion(C.JAVAPEG_VERSION).getSchemaName();
        StreamSource configSchema = new StreamSource(StartJavaPEG.class.getResourceAsStream(configSchemaLocation));

        return XMLUtil.validate(metaDataBaseFile, configSchema);
    }
}
