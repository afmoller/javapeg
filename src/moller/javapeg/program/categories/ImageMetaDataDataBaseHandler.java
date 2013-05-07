package moller.javapeg.program.categories;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import moller.javapeg.program.C;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.controller.section.CategoriesConfig;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.javapeg.program.config.model.repository.RepositoryExceptions;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.contexts.ImageMetaDataDataBaseItemsToUpdateContext;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContext;
import moller.javapeg.program.contexts.imagemetadata.ImagePathAndIndex;
import moller.javapeg.program.datatype.ExposureTime;
import moller.javapeg.program.datatype.ExposureTime.ExposureTimeException;
import moller.javapeg.program.datatype.ImageSize;
import moller.javapeg.program.enumerations.Context;
import moller.javapeg.program.enumerations.ImageMetaDataContextAction;
import moller.javapeg.program.gui.CategoryImportExportPopup;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.hash.MD5Calculator;
import moller.util.io.StreamUtil;
import moller.util.jpeg.JPEGUtil;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ImageMetaDataDataBaseHandler {

    private static final String FILE = "file";
    private static final String IMAGE = "image";
    private static final String JAVAPEG_ID = "javapeg-id";
    private static final String CATEGORIES = "categories";
    private static final String RATING = "rating";
    private static final String COMMENT = "comment";
    private static final String EXPOSURE_TIME = "exposure-time";
    private static final String PICTURE_WIDTH = "picture-width";
    private static final String PICTURE_HEIGHT = "picture-height";
    private static final String ISO_VALUE = "iso-value";
    private static final String DATE_TIME = "date-time";
    private static final String CAMERA_MODEL = "camera-model";
    private static final String F_NUMBER = "f-number";
    private static final String EXIF_META_DATA = "exif-meta-data";
    private static final String MD5 = "md5";
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
    public static boolean addPathToRepositoryAccordingToPolicy(File path) {

        Integer policy = configuration.getTagImages().getImagesPaths().getAddToRepositoryPolicy();

        RepositoryExceptions repositoryExceptions = configuration.getRepository().getExceptions();

        // The following switch statement will check exceptions to the
        // additions policy.
        switch (policy) {
        case 0:
            if (repositoryExceptions.getNeverAdd().contains(path)) {
                policy = 2;
            }
            break;
        case 2:
            if (repositoryExceptions.getAllwaysAdd().contains(path)) {
                policy = 0;
            }
            break;
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
            return addPathToImageRepository(path);
        // Do not add.
        case 2:
            return false;
        default:
            return addPathToImageRepository(path);
        }
    }

    private static boolean addPathToImageRepository(File directory) {
        Language lang = Language.getInstance();

        JCheckBox rememberSelectionCheckBox = new JCheckBox(lang.get("category.rememberMySelection"));

        Object[] array = { lang.get("category.addToImageRepositoryQuestionPartOne") + "\n" + directory.getAbsolutePath() + "\n" + lang.get("category.addToImageRepositoryQuestionPartTwo") + "\n\n",
                           rememberSelectionCheckBox };

        int result = JOptionPane.showConfirmDialog(null, array, lang.get("category.addToImageRepositoryHeader"), JOptionPane.YES_NO_OPTION);

        if (rememberSelectionCheckBox.isSelected()) {
           Integer checkBoxState = null;

           if (result == 0) {
               checkBoxState = 0;
           } else if (result == 1) {
               checkBoxState = 2;
           }

           if (checkBoxState != null) {
               configuration.getTagImages().getImagesPaths().setAddToRepositoryPolicy(checkBoxState);
           }
        }

        if (result == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean createImageMetaDataDataBaseFileIn(File imageRepository) {
        Logger logger = Logger.getInstance();

        try {
            List<File> jpegFiles = JPEGUtil.getJPEGFiles(imageRepository);

            Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems = new HashMap<File, ImageMetaDataDataBaseItem>();

            for (File jpegFile : jpegFiles) {
                ImageMetaDataDataBaseItem imddbi = new ImageMetaDataDataBaseItem();

                imddbi.setImage(jpegFile);
                imddbi.setImageExifMetaData(new CategoryImageExifMetaData(jpegFile));
                imddbi.setComment(Language.getInstance().get("findimage.comment.defaultCommentText"));
                imddbi.setRating(0);
                imddbi.setCategories(new Categories());

                imageMetaDataDataBaseItems.put(jpegFile, imddbi);
            }
            return updateDataBaseFile(imageMetaDataDataBaseItems, imageRepository, ImageMetaDataContextAction.ADD);
        } catch (IOException iox) {
            logger.logERROR("Could not find file: " + imageRepository.getAbsolutePath());
            logger.logERROR(iox);
            return false;
        }
    }

    public static boolean updateDataBaseFile(Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems, File destination, ImageMetaDataContextAction imageMetaDataContextAction) {
        Logger logger = Logger.getInstance();

        OutputStream os = null;

        if (ImageMetaDataDataBaseItemsToUpdateContext.getInstance().isFlushNeeded() || imageMetaDataContextAction == ImageMetaDataContextAction.ADD) {
            try {
                String encoding = C.UTF8;

                os = new FileOutputStream(new File(destination, C.JAVAPEG_IMAGE_META_NAME));
                XMLOutputFactory factory = XMLOutputFactory.newInstance();
                XMLStreamWriter w = factory.createXMLStreamWriter(os, encoding);

                XMLUtil.writeStartDocument(encoding, "1.0", w);
                XMLUtil.writeComment("This XML file contains meta data information of all JPEG image" + C.LS +
                                     "files that exists in the directory where this XML file is to be found." + C.LS +
                                     "The content of this file is used and modified by the application JavaPEG", w);
                XMLUtil.writeElementStart("javapeg-image-meta-data-data-base", "version", C.IMAGE_META_DATA_DATA_BASE_VERSION, w);
                XMLUtil.writeElement(JAVAPEG_ID, configuration.getJavapegClientId(), w);

                for(File image : imageMetaDataDataBaseItems.keySet()) {
                    ImageMetaDataDataBaseItem imddbi = imageMetaDataDataBaseItems.get(image);
                    CategoryImageExifMetaData ciemd = imddbi.getImageExifMetaData();

                    XMLUtil.writeElementStart(IMAGE, FILE, imddbi.getImage().getName(), w);
                    XMLUtil.writeElement(MD5, MD5Calculator.calculate(image), w);
                    XMLUtil.writeElementStart(EXIF_META_DATA, w);
                    XMLUtil.writeElement(F_NUMBER, Double.toString(ciemd.getFNumber()), w);
                    XMLUtil.writeElement(CAMERA_MODEL  , ciemd.getCameraModel()  , w);
                    XMLUtil.writeElement(DATE_TIME     , ciemd.getDateTimeAsString()         , w);
                    XMLUtil.writeElement(ISO_VALUE     , Integer.toString(ciemd.getIsoValue())     , w);
                    XMLUtil.writeElement(PICTURE_HEIGHT, Integer.toString(ciemd.getPictureHeight()), w);
                    XMLUtil.writeElement(PICTURE_WIDTH , Integer.toString(ciemd.getPictureWidth()) , w);
                    XMLUtil.writeElement(EXPOSURE_TIME , ciemd.getExposureTime().toString() , w);
                    XMLUtil.writeElementEnd(w);
                    XMLUtil.writeElement(COMMENT, imddbi.getComment(), w);
                    XMLUtil.writeElement(RATING, Integer.toString(imddbi.getRating()), w);
                    XMLUtil.writeElement(CATEGORIES, imddbi.getCategories().toString(), w);

                    XMLUtil.writeElementEnd(w);
                }
                XMLUtil.writeElementEnd(w);
                w.flush();

                logger.logDEBUG("File: " + destination.getAbsolutePath() + C.FS + C.JAVAPEG_IMAGE_META_NAME + " has been updated with the changes made to the content");

                switch (imageMetaDataContextAction) {
                case ADD:
                    for(File image : imageMetaDataDataBaseItems.keySet()) {
                        ImageMetaDataDataBaseItem imddbi = imageMetaDataDataBaseItems.get(image);
                        CategoryImageExifMetaData ciemd = imddbi.getImageExifMetaData();
                        populateImageMetaDataContext(configuration.getJavapegClientId(), image, ciemd, imddbi.getComment(), imddbi.getRating(), imddbi.getCategories());
                    }
                    break;
                case UPDATE:
                    for(File image : imageMetaDataDataBaseItems.keySet()) {
                        ImageMetaDataDataBaseItem imddbi = imageMetaDataDataBaseItems.get(image);
                        updateImageMetaDataContext(configuration.getJavapegClientId(), image, imddbi.getComment(), imddbi.getRating(), imddbi.getCategories());
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
//             TODO: Remove hard coded string
                if (0 == JOptionPane.showConfirmDialog(null, "Wrong categories file.\n\nThe JavaPEG client id in the selected categories file does not match the client id\nin the image meta data base file. See log file for details\n\nSelect another categories file?", "Wrong categories file", JOptionPane.YES_OPTION, JOptionPane.ERROR_MESSAGE)) {
                    showCategoryImportPopup(imageMetaDataDataBase, javaPegIdValue);
                }
            }
        }
    }

    public static boolean deserializeImageMetaDataDataBaseFile(File imageMetaDataDataBase, Context context) {
        Logger logger = Logger.getInstance();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc;

        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(imageMetaDataDataBase);
            doc.getDocumentElement().normalize();

            NodeList javaPegId = doc.getElementsByTagName(JAVAPEG_ID);
            Node javaPegIdNode = javaPegId.item(0);
            String javaPegIdValue = javaPegIdNode.getTextContent();

            ApplicationContext ac = ApplicationContext.getInstance();
            ac.setImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance(configuration.getJavapegClientId().equals(javaPegIdValue));

            if (!ac.isImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance()) {

                if (!configuration.getImportedCategoriesConfig().containsKey(javaPegIdValue)) {
                    showCategoryImportPopup(imageMetaDataDataBase, javaPegIdValue);
                }
            }

            NodeList imageTags = doc.getElementsByTagName(IMAGE);

            int nrOfTags = imageTags.getLength();

            Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems = new HashMap<File, ImageMetaDataDataBaseItem>();

            for (int i = 0; i < nrOfTags; i++) {
                Node imageTag = imageTags.item(i);

                NamedNodeMap nnm = imageTag.getAttributes();
                Node file = nnm.getNamedItem(FILE);

                File image = new File(imageMetaDataDataBase.getParentFile(), file.getNodeValue());

                NodeList content = imageTag.getChildNodes();

                CategoryImageExifMetaData imageExifMetaData = null;
                String comment = "";
                String md5 = "";
                int rating = 0;
                Categories categories = new Categories();

                for (int j = 0; j < content.getLength(); j++) {
                    Node node = content.item(j);
                    if (MD5.equals(node.getNodeName())) {
                        md5 = node.getTextContent();
                    } else if (EXIF_META_DATA.equals(node.getNodeName())) {
                        imageExifMetaData = createImageExifMetaData(node.getChildNodes());
                    } else if(COMMENT.equals(node.getNodeName())) {
                        comment = node.getTextContent();
                    } else if(RATING.equals(node.getNodeName())) {
                        try {
                            rating = Integer.parseInt(node.getTextContent());
                        } catch (NumberFormatException nfex) {
                            logger.logINFO("Could not parse rating value. Rating value is: \"" + node.getTextContent() + "\". Value set to 0 (zero)");
                            logger.logINFO(nfex);
                            rating = 0;
                        }
                    } else if(CATEGORIES.equals(node.getNodeName())) {
                        String categoriesString = node.getTextContent();
                        categories.addCategories(categoriesString);

                        switch (context) {
                        case IMAGE_META_DATA_CONTEXT:
                            // Do nothing here.
                            break;

                        case IMAGE_META_DATA_DATA_BASE_ITEMS_TO_UPDATE_CONTEXT:
                            // If there are no categories set to the current image,
                            // then there is no need to do anything from here...
                            if (categoriesString != null && categoriesString.length() > 0) {
                                ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

                                Map<String, Map<String, Set<Integer>>> javaPegIdToCategories = imdc.getCategories();

                                Map<String, Set<Integer>> categoryIdsAndImageIdsForJavaPegIdValue = javaPegIdToCategories.get(javaPegIdValue);

                                // If there are no categories defined for current
                                // JavaPeg Id, then do not do anything here...
                                if (categoryIdsAndImageIdsForJavaPegIdValue != null) {

                                    Set<String> categoryIdsForJavePegId = categoryIdsAndImageIdsForJavaPegIdValue.keySet();

                                    for (String categoryId : categories.getCategories()) {
                                        if (!categoryIdsForJavePegId.contains(categoryId)) {
                                            //                                TODO: Remove hard coded string
                                            if (0 == JOptionPane.showConfirmDialog(null, "A newer version exist for the categories file for meta data base file: " + imageMetaDataDataBase.getAbsolutePath() + "\nMake the import now?", "Newer version exists", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE)) {
                                                showCategoryImportPopup(imageMetaDataDataBase, javaPegIdValue);
                                            }
                                        }
                                    }
                                }
                            }

                            break;
                        }

                    }
                }

                switch (context) {
                case IMAGE_META_DATA_DATA_BASE_ITEMS_TO_UPDATE_CONTEXT:
                    ImageMetaDataDataBaseItem iMDDBI = new ImageMetaDataDataBaseItem(image, md5, imageExifMetaData, comment, rating, categories);
                    imageMetaDataDataBaseItems.put(image, iMDDBI);
                    break;

                case IMAGE_META_DATA_CONTEXT:
                    populateImageMetaDataContext(javaPegIdValue, image, imageExifMetaData, comment, rating, categories);
                    break;
                }
            }

            switch (context) {
            case IMAGE_META_DATA_DATA_BASE_ITEMS_TO_UPDATE_CONTEXT:
                ImageMetaDataDataBaseItemsToUpdateContext imddbituc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();
                imddbituc.setImageMetaDataBaseItems(imageMetaDataDataBaseItems);
                break;
            case IMAGE_META_DATA_CONTEXT:
                break;
            }
        } catch (ParserConfigurationException pcex) {
            logger.logERROR("Could not create a DocumentBuilder");
            logger.logERROR(pcex);
            return false;
        } catch (SAXException sex) {
            logger.logERROR("Could not parse file: " + imageMetaDataDataBase.getAbsolutePath());
            logger.logERROR(sex);
            return false;
        } catch (IOException iox) {
            logger.logERROR("IO exception occurred when parsing file: " + imageMetaDataDataBase.getAbsolutePath());
            logger.logERROR(iox);
            return false;
        }
        return true;
    }

    private static void populateImageMetaDataContext(String javaPegIdValue, File image, CategoryImageExifMetaData imageExifMetaData, String comment, int rating, Categories categories) {
        ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();
        final String imagePath = image.getAbsolutePath();

        imdc.addCameraModel(javaPegIdValue, imageExifMetaData.getCameraModel(), imagePath);
        imdc.addDateTime(javaPegIdValue, imageExifMetaData.getDateTime(), imagePath);
        imdc.addIso(javaPegIdValue, imageExifMetaData.getIsoValue(), imagePath);
        imdc.addImageSize(javaPegIdValue, new ImageSize(imageExifMetaData.getPictureHeight(), imageExifMetaData.getPictureWidth()), imagePath);
        imdc.addExposureTime(javaPegIdValue, imageExifMetaData.getExposureTime(), imagePath);
        imdc.addFNumber(javaPegIdValue, imageExifMetaData.getFNumber(), imagePath);
        imdc.addComment(javaPegIdValue, comment, imagePath);
        imdc.addRating(javaPegIdValue, rating, imagePath);

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

    private static void updateImageRating(String javaPegIdValue, File image, int rating, ImageMetaDataContext imdc, ImagePathAndIndex ipai) {
        Map<Integer, Set<Integer>> ratings = imdc.getRatings(javaPegIdValue);

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
    }

    private static void updateImageCategories(File image, Categories categories, ImageMetaDataContext imdc, ImagePathAndIndex ipai) {
        Map<String, Map<String, Set<Integer>>> javaPegIdToCategoriesMap = imdc.getCategories();

        int imageIndex = ipai.getIndexForImagePath(image.getAbsolutePath());

        List<String> categoriesToRemove = new ArrayList<String>();

         Map<String, Set<Integer>> categoriesMap = javaPegIdToCategoriesMap.get(configuration.getJavapegClientId());

         // Only do this if there are any categories defined for this
         // JavapegClientId.
         if (categoriesMap != null) {
             /**
              * Remove any unselected categories from the ImageMetaDataContext
              */
             for (String category : categoriesMap.keySet()) {
                 Set<Integer> indices = categoriesMap.get(category);

                 if (indices.contains(imageIndex)) {
                     if (!categories.getCategories().contains(category)) {
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

             /**
              * Add any newly selected categories to the ImageMetaDataContext
              */
             for (String category : categories.getCategories()) {
                 Set<Integer> indices = categoriesMap.get(category);

                 if (indices == null || !indices.contains(imageIndex)) {
                     imdc.addCategory(configuration.getJavapegClientId(), category, image.getAbsolutePath());
                 }
             }
         }
    }

    private static CategoryImageExifMetaData createImageExifMetaData(NodeList exifMetaData) {
        Logger logger = Logger.getInstance();

        CategoryImageExifMetaData imageExifMetaData = new CategoryImageExifMetaData();

        int listLength = exifMetaData.getLength();

        for (int index = 0; index < listLength; index++) {
            String nodeName  = exifMetaData.item(index).getNodeName();
            String nodeValue = exifMetaData.item(index).getTextContent();

            if(F_NUMBER.equals(nodeName)) {
                imageExifMetaData.setFNumber(Double.parseDouble(nodeValue));
            } else if(CAMERA_MODEL.equals(nodeName)) {
                imageExifMetaData.setCameraModel(nodeValue);
            } else if(DATE_TIME.equals(nodeName)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                try {
                    imageExifMetaData.setDateTime(sdf.parse(nodeValue));
                } catch (ParseException pex) {
                    imageExifMetaData.setDateTime(null);
                    logger.logERROR("Could not parse date string: \"" + nodeValue + "\" with SimpleDateFormat string: \"" + sdf.toPattern() + "\"");
                    logger.logERROR(pex);
                }
            } else if(ISO_VALUE.equals(nodeName)) {
                imageExifMetaData.setIsoValue(Integer.parseInt(nodeValue));
            } else if(PICTURE_HEIGHT.equals(nodeName)) {
                imageExifMetaData.setPictureHeight(Integer.parseInt(nodeValue));
            } else if(PICTURE_WIDTH.equals(nodeName)) {
                imageExifMetaData.setPictureWidth(Integer.parseInt(nodeValue));
            } else if(EXPOSURE_TIME.equals(nodeName)) {
                try {
                    imageExifMetaData.setExposureTime(new ExposureTime(nodeValue));
                } catch (ExposureTimeException etex) {
                    imageExifMetaData.setExposureTime(null);
                    logger.logERROR("Could not create a ExposureTime object from string value: " + nodeValue);
                    logger.logERROR(etex);
                }
            }
        }
        return imageExifMetaData;
    }
}
