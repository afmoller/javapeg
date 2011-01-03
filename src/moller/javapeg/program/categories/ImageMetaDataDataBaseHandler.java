package moller.javapeg.program.categories;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.C;
import moller.javapeg.program.contexts.ImageMetaDataDataBaseItemsToUpdateContext;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContext;
import moller.javapeg.program.datatype.ImageSize;
import moller.javapeg.program.datatype.ShutterSpeed;
import moller.javapeg.program.datatype.ShutterSpeed.ShutterSpeedException;
import moller.javapeg.program.enumerations.Context;
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
		try {
			List<File> jpegFiles = JPEGUtil.getJPEGFiles(imageRepository);
			
			Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems = new HashMap<File, ImageMetaDataDataBaseItem>();
			
			for (File jpegFile : jpegFiles) {
				ImageMetaDataDataBaseItem imddbi = new ImageMetaDataDataBaseItem();
				
				imddbi.setImage(jpegFile);
				imddbi.setImageExifMetaData(new CategoryImageExifMetaData(jpegFile));
				imddbi.setComment("Add Comment Here");
				imddbi.setRating(0);
				imddbi.setCategories(new Categories());
				
				imageMetaDataDataBaseItems.put(jpegFile, imddbi);
			}
			return updateDataBaseFile(imageMetaDataDataBaseItems, imageRepository);
		} catch (FileNotFoundException e) {
		 // TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
		 // TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
		
	public static boolean updateDataBaseFile(Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems, File destination) {
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
				XMLUtil.writeElement("date"          , ciemd.getDateAsString()         , w);
				XMLUtil.writeElement("iso-value"     , Integer.toString(ciemd.getIsoValue())     , w);
				XMLUtil.writeElement("picture-height", Integer.toString(ciemd.getPictureHeight()), w);
				XMLUtil.writeElement("picture-width" , Integer.toString(ciemd.getPictureWidth()) , w);
				XMLUtil.writeElement("shutter-speed" , ciemd.getShutterSpeed().toString() , w);
				XMLUtil.writeElement("time"          , ciemd.getTimeAsString()         , w);
				XMLUtil.writeElementEnd(w);
				XMLUtil.writeElement("comment", imddbi.getComment(), w);
				XMLUtil.writeElement("rating", Integer.toString(imddbi.getRating()), w);
				XMLUtil.writeElement("categories", imddbi.getCategories().toString(), w);
				
				XMLUtil.writeElementEnd(w);
			}
			XMLUtil.writeElementEnd(w);
			w.flush();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			return false;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static void populateImageMetaDataContext(File image, CategoryImageExifMetaData imageExifMetaData, String comment, int rating, Categories categories) {
		ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();
		
		imdc.addCameraModel(imageExifMetaData.getCameraModel(), image.getAbsolutePath());
		imdc.addDateTime(imageExifMetaData.getDate(), image.getAbsolutePath());
		imdc.addIso(imageExifMetaData.getIsoValue(), image.getAbsolutePath());
		imdc.addImageSize(new ImageSize(imageExifMetaData.getPictureHeight(), imageExifMetaData.getPictureWidth()), image.getAbsolutePath());
		imdc.addShutterSpeed(imageExifMetaData.getShutterSpeed(), image.getAbsolutePath());
		imdc.addAperture(imageExifMetaData.getApertureValue(), image.getAbsolutePath());
		imdc.addComment(comment, image.getAbsolutePath());
		imdc.addRating(rating, image.getAbsolutePath());
		
		for (String category : categories.getCategories()) {
			imdc.addCategory(category, image.getAbsolutePath());	
		}
	}

	private static CategoryImageExifMetaData createImageExifMetaData(NodeList exifMetaData) {
		CategoryImageExifMetaData imageExifMetaData = new CategoryImageExifMetaData();
		
		for (int index = 0; index < 8; index++) {
			String nodeName  = exifMetaData.item(index).getNodeName();
			String nodeValue = exifMetaData.item(index).getTextContent();
			
			if("aperture-value".equals(nodeName)) {
				imageExifMetaData.setApertureValue(Double.parseDouble(nodeValue));
			} else if("camera-model".equals(nodeName)) {
				imageExifMetaData.setCameraModel(nodeValue);
			} else if("date".equals(nodeName)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
				try {
					imageExifMetaData.setDate(sdf.parse(nodeValue));
				} catch (ParseException e) {
					imageExifMetaData.setDate(null);
//					TODO: Add logging about impossible to create a Date
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
//					TODO: Add logging about impossible to create a ShutterSpeed
				}
			} else if("time".equals(nodeName)) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				try {
					imageExifMetaData.setTime(sdf.parse(nodeValue));
				} catch (ParseException e) {
					imageExifMetaData.setTime(null);
//					TODO: Add logging about impossible to create a Date				
				}
			}
		}
		return imageExifMetaData;
	}
}
