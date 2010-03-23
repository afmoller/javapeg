package moller.javapeg.program.categories;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
import moller.javapeg.program.contexts.ImageRepositoryContext;
import moller.javapeg.program.logger.Logger;
import moller.util.io.StreamUtil;
import moller.util.io.XMLUtil;
import moller.util.jpeg.JPEGUtil;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ImageMetaDataDataBaseHandler {
	
	public static boolean initiateDataBase(File directory) {
		File imageMetaDataDataBase = new File(directory, C.JAVAPEG_IMAGE_META_NAME);
	
		if(!imageMetaDataDataBase.exists()) {
			if(!createImageMetaDataDataBaseFile(directory, imageMetaDataDataBase)) {
				return false;
			}
		} 
		return deserializeImageMetaDataDataBaseFile(imageMetaDataDataBase);
	}
	
	private static boolean createImageMetaDataDataBaseFile(File imageRepository, File destination) {
		try {
			List<File> jpegFiles = JPEGUtil.getJPEGFiles(imageRepository);
			
			Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems = new HashMap<File, ImageMetaDataDataBaseItem>();
			
			for (File jpegFile : jpegFiles) {
				ImageMetaDataDataBaseItem imddbi = new ImageMetaDataDataBaseItem();
				
				imddbi.setImage(jpegFile);
				imddbi.setImageExifMetaData(new ImageExifMetaData(jpegFile));
				imddbi.setComment("Add Comment Here");
				imddbi.setRating(-1);
				imddbi.setTags(new ArrayList<Tag>());
				
				imageMetaDataDataBaseItems.put(jpegFile, imddbi);
			}
			updateDataBaseFile(imageMetaDataDataBaseItems, destination);
		} catch (FileNotFoundException e) {
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
		
	public static boolean updateDataBaseFile(Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems, File destination) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(destination);
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter w = factory.createXMLStreamWriter(os);
			
			XMLUtil.writeStartDocument("1.0", w);
			XMLUtil.writeComment("This XML file contains meta data information of all JPEG image\n" +
					             "files that exists in the directory where this XML file is to be found.\n" + 
					             "The content of this file is used and modified by the application JavaPEG", w);
			XMLUtil.writeElementStart("javapeg-image-meta-data-data-base", "version", C.IMAGE_META_DATA_DATA_BASE_VERSION, w);
								
			for(File image : imageMetaDataDataBaseItems.keySet()) {
				ImageMetaDataDataBaseItem imddbi = imageMetaDataDataBaseItems.get(image);
				ImageExifMetaData iemd = imddbi.getImageExifMetaData();
				
				XMLUtil.writeElementStart("image", "file", imddbi.getImage().getAbsolutePath(), w);
				XMLUtil.writeElementStart("exif-meta-data", w);
					XMLUtil.writeElement("aperture-value", iemd.getApertureValue(), w);
					XMLUtil.writeElement("camera-model"  , iemd.getCameraModel()  , w);
					XMLUtil.writeElement("date"          , iemd.getDate()         , w);
					XMLUtil.writeElement("iso-value"     , iemd.getIsoValue()     , w);
					XMLUtil.writeElement("picture-height", iemd.getPictureHeight(), w);
					XMLUtil.writeElement("picture-width" , iemd.getPictureWidth() , w);
					XMLUtil.writeElement("shutter-speed" , iemd.getShutterSpeed() , w);
					XMLUtil.writeElement("time"          , iemd.getTime()         , w);
				XMLUtil.writeElementEnd(w);
				XMLUtil.writeElement("comment", imddbi.getComment(), w);
				XMLUtil.writeElement("rating", Integer.toString(imddbi.getRating()), w);
				XMLUtil.writeElementStart("tags", w);
				XMLUtil.writeElementEnd(w);
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
	
	private static boolean deserializeImageMetaDataDataBaseFile(File imageMetaDataDataBase ) {
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
				
				File image = new File(file.getNodeValue());
				
				NodeList content = imageTag.getChildNodes();
				
				ImageExifMetaData imageExifMetaData = null;
				String comment = "";
				int rating = 0;
				List<Tag> tags = new ArrayList<Tag>();
				
				for (int j = 0; j < content.getLength(); j++) {
					Node node = content.item(j);
					if ("exif-meta-data".equals(node.getNodeName())) {
						imageExifMetaData = createImageExifMetaData(node.getChildNodes());
					} else if("comment".equals(node.getNodeName())) {
						comment = node.getTextContent();
					} else if("rating".equals(node.getNodeName())) {
						try {
							rating = Integer.parseInt(node.getTextContent());
						} catch (NumberFormatException nfex) {
							logger.logINFO("Could not parse rating value. Rating value is: \"" + node.getTextContent() + "\". Value set to -1");
							logger.logINFO(nfex);
							rating = -1;
						}
					} else if("tags".equals(node.getNodeName())) {
//						TODO: Fix real implementation
					}
				}
				ImageMetaDataDataBaseItem iMDDBI = new ImageMetaDataDataBaseItem(image, imageExifMetaData, comment, rating, tags);
				imageMetaDataDataBaseItems.put(image, iMDDBI);
				
				ImageRepositoryContext.getInstance().setImageMetaDataBaseItems(imageMetaDataDataBaseItems);
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
	
	private static ImageExifMetaData createImageExifMetaData(NodeList exifMetaData) {
		ImageExifMetaData imageExifMetaData = new ImageExifMetaData();
		
		for (int index = 0; index < 8; index++) {
			String nodeName  = exifMetaData.item(index).getNodeName();
			String nodeValue = exifMetaData.item(index).getTextContent();
			
			if("aperture-value".equals(nodeName)) {
				imageExifMetaData.setApertureValue(nodeValue);
			} else if("camera-model".equals(nodeName)) {
				imageExifMetaData.setCameraModel(nodeValue);
			} else if("date".equals(nodeName)) {
				imageExifMetaData.setDate(nodeValue);
			} else if("iso-value".equals(nodeName)) {
				imageExifMetaData.setIsoValue(nodeValue);
			} else if("picture-height".equals(nodeName)) {
				imageExifMetaData.setPictureHeight(nodeValue);
			} else if("picture-width".equals(nodeName)) {
				imageExifMetaData.setPictureWidth(nodeValue);
			} else if("time".equals(nodeName)) {
				imageExifMetaData.setTime(nodeValue);
			}
		}
		return imageExifMetaData;
	}
}
