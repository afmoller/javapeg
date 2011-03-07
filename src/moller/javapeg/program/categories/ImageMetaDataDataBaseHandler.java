package moller.javapeg.program.categories;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.C;
import moller.javapeg.program.contexts.ImageMetaDataDataBaseItemsToUpdateContext;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContext;
import moller.javapeg.program.contexts.imagemetadata.ImagePathAndIndex;
import moller.javapeg.program.datatype.ImageSize;
import moller.javapeg.program.datatype.ShutterSpeed;
import moller.javapeg.program.datatype.ShutterSpeed.ShutterSpeedException;
import moller.javapeg.program.enumerations.Context;
import moller.javapeg.program.enumerations.ImageMetaDataContextAction;
import moller.javapeg.program.logger.Logger;
import moller.util.hash.MD5;
import moller.util.io.StreamUtil;
import moller.util.jpeg.JPEGUtil;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ImageMetaDataDataBaseHandler {

	public static boolean initiateDataBase(File directory) {
		File imageMetaDataDataBase = new File(directory, C.JAVAPEG_IMAGE_META_NAME);

		if(!imageMetaDataDataBase.exists()) {
			if(!createImageMetaDataDataBaseFile(directory)) {
				return false;
			}
		}
		return deserializeImageMetaDataDataBaseFile(imageMetaDataDataBase, Context.IMAGE_META_DATA_DATA_BASE_ITEMS_TO_UPDATE_CONTEXT);
	}

	private static boolean createImageMetaDataDataBaseFile(File imageRepository) {
		Logger logger = Logger.getInstance();

		try {
			List<File> jpegFiles = JPEGUtil.getJPEGFiles(imageRepository);

			Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems = new HashMap<File, ImageMetaDataDataBaseItem>();

			for (File jpegFile : jpegFiles) {
				ImageMetaDataDataBaseItem imddbi = new ImageMetaDataDataBaseItem();

				imddbi.setImage(jpegFile);
				imddbi.setImageExifMetaData(new CategoryImageExifMetaData(jpegFile));
//				TODO: fix hard coded string
				imddbi.setComment("Add Comment Here");
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
		try {
			os = new FileOutputStream(new File(destination, C.JAVAPEG_IMAGE_META_NAME));
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter w = factory.createXMLStreamWriter(os, "UTF8");

			XMLUtil.writeStartDocument("1.0", w);
			XMLUtil.writeComment("This XML file contains meta data information of all JPEG image" + C.LS +
					             "files that exists in the directory where this XML file is to be found." + C.LS +
					             "The content of this file is used and modified by the application JavaPEG", w);
			XMLUtil.writeElementStart("javapeg-image-meta-data-data-base", "version", C.IMAGE_META_DATA_DATA_BASE_VERSION, w);

			for(File image : imageMetaDataDataBaseItems.keySet()) {
				ImageMetaDataDataBaseItem imddbi = imageMetaDataDataBaseItems.get(image);
				CategoryImageExifMetaData ciemd = imddbi.getImageExifMetaData();

				XMLUtil.writeElementStart("image", "file", imddbi.getImage().getName(), w);
				XMLUtil.writeElement("md5", MD5.calculate(image), w);
				XMLUtil.writeElementStart("exif-meta-data", w);
				XMLUtil.writeElement("aperture-value", Double.toString(ciemd.getApertureValue()), w);
				XMLUtil.writeElement("camera-model"  , ciemd.getCameraModel()  , w);
				XMLUtil.writeElement("date-time"     , ciemd.getDateTimeAsString()         , w);
				XMLUtil.writeElement("iso-value"     , Integer.toString(ciemd.getIsoValue())     , w);
				XMLUtil.writeElement("picture-height", Integer.toString(ciemd.getPictureHeight()), w);
				XMLUtil.writeElement("picture-width" , Integer.toString(ciemd.getPictureWidth()) , w);
				XMLUtil.writeElement("shutter-speed" , ciemd.getShutterSpeed().toString() , w);
				XMLUtil.writeElementEnd(w);
				XMLUtil.writeElement("comment", imddbi.getComment(), w);
				XMLUtil.writeElement("rating", Integer.toString(imddbi.getRating()), w);
				XMLUtil.writeElement("categories", imddbi.getCategories().toString(), w);

				XMLUtil.writeElementEnd(w);
			}
			XMLUtil.writeElementEnd(w);
			w.flush();

			switch (imageMetaDataContextAction) {
			case ADD:
				for(File image : imageMetaDataDataBaseItems.keySet()) {
					ImageMetaDataDataBaseItem imddbi = imageMetaDataDataBaseItems.get(image);
					CategoryImageExifMetaData ciemd = imddbi.getImageExifMetaData();
					populateImageMetaDataContext(image, ciemd, imddbi.getComment(), imddbi.getRating(), imddbi.getCategories());
				}
				break;
			case UPDATE:
				for(File image : imageMetaDataDataBaseItems.keySet()) {
					ImageMetaDataDataBaseItem imddbi = imageMetaDataDataBaseItems.get(image);
					updateImageMetaDataContext(image, imddbi.getComment(), imddbi.getRating(), imddbi.getCategories());
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
		return true;
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

			NodeList imageTags = doc.getElementsByTagName("image");

			int nrOfTags = imageTags.getLength();

			Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems = new HashMap<File, ImageMetaDataDataBaseItem>();

			for (int i = 0; i < nrOfTags; i++) {
				Node imageTag = imageTags.item(i);

				NamedNodeMap nnm = imageTag.getAttributes();
				Node file = nnm.getNamedItem("file");

				File image = new File(imageMetaDataDataBase.getParentFile(), file.getNodeValue());

				NodeList content = imageTag.getChildNodes();

				CategoryImageExifMetaData imageExifMetaData = null;
				String comment = "";
				String md5 = "";
				int rating = 0;
				Categories categories = new Categories();

				for (int j = 0; j < content.getLength(); j++) {
					Node node = content.item(j);
					if ("md5".equals(node.getNodeName())) {
						md5 = node.getTextContent();
					} else if ("exif-meta-data".equals(node.getNodeName())) {
						imageExifMetaData = createImageExifMetaData(node.getChildNodes());
					} else if("comment".equals(node.getNodeName())) {
						comment = node.getTextContent();
					} else if("rating".equals(node.getNodeName())) {
						try {
							rating = Integer.parseInt(node.getTextContent());
						} catch (NumberFormatException nfex) {
							logger.logINFO("Could not parse rating value. Rating value is: \"" + node.getTextContent() + "\". Value set to 0 (zero)");
							logger.logINFO(nfex);
							rating = 0;
						}
					} else if("categories".equals(node.getNodeName())) {
						String categoriesString = node.getTextContent();

						if (categoriesString != null && categoriesString.length() > 0) {
							categories.addCategories(categoriesString);
						}
					}
				}

				switch (context) {
				case IMAGE_META_DATA_DATA_BASE_ITEMS_TO_UPDATE_CONTEXT:
					ImageMetaDataDataBaseItem iMDDBI = new ImageMetaDataDataBaseItem(image, md5, imageExifMetaData, comment, rating, categories);
					imageMetaDataDataBaseItems.put(image, iMDDBI);
					break;

				case IMAGE_META_DATA_CONTEXT:
					populateImageMetaDataContext(image, imageExifMetaData, comment, rating, categories);
					break;
				}
			}

			switch (context) {
			case IMAGE_META_DATA_DATA_BASE_ITEMS_TO_UPDATE_CONTEXT:
				ImageMetaDataDataBaseItemsToUpdateContext.getInstance().setImageMetaDataBaseItems(imageMetaDataDataBaseItems);
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
			logger.logERROR("IO exceptrion occurred when parsing file: " + imageMetaDataDataBase.getAbsolutePath());
			logger.logERROR(iox);
			return false;
		}
		return true;
	}

	private static void populateImageMetaDataContext(File image, CategoryImageExifMetaData imageExifMetaData, String comment, int rating, Categories categories) {
		ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();
		final String imagePath = image.getAbsolutePath();

		imdc.addCameraModel(imageExifMetaData.getCameraModel(), imagePath);
		imdc.addDateTime(imageExifMetaData.getDateTime(), imagePath);
		imdc.addIso(imageExifMetaData.getIsoValue(), imagePath);
		imdc.addImageSize(new ImageSize(imageExifMetaData.getPictureHeight(), imageExifMetaData.getPictureWidth()), imagePath);
		imdc.addShutterSpeed(imageExifMetaData.getShutterSpeed(), imagePath);
		imdc.addAperture(imageExifMetaData.getApertureValue(), imagePath);
		imdc.addComment(comment, imagePath);
		imdc.addRating(rating, imagePath);

		for (String category : categories.getCategories()) {
			imdc.addCategory(category, imagePath);
		}
	}

	public static void updateImageMetaDataContext(File image, String newComment, int rating, Categories categories) {
		ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();
		ImagePathAndIndex ipai = ImagePathAndIndex.getInstance();

		updateImageComment(image, newComment, imdc, ipai);
		updateImageRating(image, rating, imdc, ipai);
		updateImageCategories(image, categories, imdc, ipai);
	}

	private static void updateImageComment(File image, String newComment, ImageMetaDataContext imdc, ImagePathAndIndex ipai) {
		Map<String, Set<Integer>> comments = imdc.getComments();

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
						imdc.addComment(newComment, image.getAbsolutePath());
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

	private static void updateImageRating(File image, int rating, ImageMetaDataContext imdc, ImagePathAndIndex ipai) {
		List<Set<Integer>> ratings = imdc.getRatings();

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
						imdc.addRating(rating, image.getAbsolutePath());
						break search;
					}
				}
			}
		}
	}

	private static void updateImageCategories(File image, Categories categories, ImageMetaDataContext imdc, ImagePathAndIndex ipai) {
		Map<String, Set<Integer>> categoriesMap = imdc.getCategories();

		int imageIndex = ipai.getIndexForImagePath(image.getAbsolutePath());

		List<String> categoriesToRemove = new ArrayList<String>();

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
			categoriesMap.remove(categoryToRemove);
		}

		/**
		 * Add any newly selected categories to the ImageMetaDataContext
		 */
		for (String category : categories.getCategories()) {
			Set<Integer> indices = categoriesMap.get(category);

			if (indices == null || !indices.contains(imageIndex)) {
				imdc.addCategory(category, image.getAbsolutePath());
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

			if("aperture-value".equals(nodeName)) {
				imageExifMetaData.setApertureValue(Double.parseDouble(nodeValue));
			} else if("camera-model".equals(nodeName)) {
				imageExifMetaData.setCameraModel(nodeValue);
			} else if("date-time".equals(nodeName)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				try {
					imageExifMetaData.setDateTime(sdf.parse(nodeValue));
				} catch (ParseException pex) {
					imageExifMetaData.setDateTime(null);
					logger.logERROR("Could not parse date string: \"" + nodeValue + "\" with SimpleDateFormat string: \"" + sdf.toPattern() + "\"");
					logger.logERROR(pex);
				}
			} else if("iso-value".equals(nodeName)) {
				imageExifMetaData.setIsoValue(Integer.parseInt(nodeValue));
			} else if("picture-height".equals(nodeName)) {
				imageExifMetaData.setPictureHeight(Integer.parseInt(nodeValue));
			} else if("picture-width".equals(nodeName)) {
				imageExifMetaData.setPictureWidth(Integer.parseInt(nodeValue));
			} else if("shutter-speed".equals(nodeName)) {
				try {
					imageExifMetaData.setShutterSpeed(new ShutterSpeed(nodeValue));
				} catch (ShutterSpeedException spex) {
					imageExifMetaData.setShutterSpeed(null);
					logger.logERROR("Could not create a ShutterSpeed object from string value: " + nodeValue);
					logger.logERROR(spex);
				}
			}
		}
		return imageExifMetaData;
	}
}
