package moller.javapeg.program.categories;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.C;
import moller.javapeg.program.model.XMLTreeNode;
import moller.util.io.StreamUtil;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CategoryUtil {
	
	/**
	 * This method checks whether a category with a specified name already 
	 * exists. The scope for "exists" is in the same sub category or in the top
	 * level. In other words the same category name may exist several times but
	 * not in the same scope.
	 *  
	 * @param document is the content to search for potential matches of an 
	 *                 already existing category.
	 *                 
	 * @param selectedPath if a sub category is selected, then that is 
	 *        specified by this parameter, otherwise it is null and that 
	 *        indicates that the top (root) level shall be examined.
	 *        
	 * @param categoryName is the name of the category to search for.
	 *  
	 * @return a boolean value indicating whether the category name to search 
	 *         for exists in the specified scope or not. True is returned if
	 *         the specified category name in the parameter categoryName is 
	 *         found in the scope specified by the parameter selectedPath.
	 */
	public static boolean alreadyExists(Document document, TreePath selectedPath, String categoryName) {
		
		// If the top level categories is to be examined ...
		if (selectedPath == null || selectedPath.getPathCount() == 1) {
			Node categoriesNode = document.getElementsByTagName("categories").item(0);
			
			NodeList children = categoriesNode.getChildNodes();
			
			if (existsIn(categoryName, children)) {
				return true;
			}
		} 
		// ... or the categories of a sub category
		else {
			XMLTreeNode xmlTreeNode = ((XMLTreeNode)selectedPath.getLastPathComponent());
			String searchForID = xmlTreeNode.getAttribute("id");
			
			NodeList allCategories = document.getElementsByTagName("category");
			
			for (int i = 0; i < allCategories.getLength(); i++) {
				Element category = (Element)allCategories.item(i);
				
				if (category.getAttribute("id").equals(searchForID)) {
					if (existsIn(categoryName, category.getChildNodes())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * @param value
	 * @param nodes
	 * @return
	 */
	private static boolean existsIn(String value, NodeList nodes) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node child = nodes.item(i);
			if (child.getNodeName().equals("category")) {
				NamedNodeMap attributes = child.getAttributes();
				Node valueAttribute = attributes.getNamedItem("value");
				
				if (valueAttribute.getTextContent().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param category
	 * @return
	 */
	public static boolean isValid(String category) {
		return category.length() > 0 && !category.startsWith(" ");
	}
	
	/**
	 * @param document
	 * @return
	 */
	public static int getNextIdToUse(Document document) {
		NodeList categories = document.getElementsByTagName("categories");
		
		if (categories.getLength() != 1) {
			throw new RuntimeException("Invalid xml");
//			TODO: Add error message and logging
		} else {
			Element categoriesNode = (Element)categories.item(0);
			
			int idNext = Integer.parseInt(categoriesNode.getAttribute("highest-used-id")) + 1;
			categoriesNode.setAttribute("highest-used-id", Integer.toString(idNext));
			
			return idNext;
		}
	}
	
	/**
	 * @param categories
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document parse(File categories) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document document = builder.parse(categories);
		document.normalize();

		return document;
	}
	
	/**
	 * @param categoriesFile
	 * @param document
	 */
	public static void store (File categoriesFile, Document document) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(categoriesFile);
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter w = factory.createXMLStreamWriter(os, "UTF8");
			
			XMLUtil.writeStartDocument("1.0", w);
			XMLUtil.writeComment("This XML file contains the available categories and the logical structure" + C.LS +
					             "of the categories. The content of this file is used and modified by the" + C.LS +
					             "application JavaPEG and should not be edited manually, since any change might be" + C.LS +
					             "overwritten by the JavaPEG application or corrupt the file if the change is invalid" + C.LS, w);
			
			NodeList categories = document.getElementsByTagName("categories");
			Element categoriesElement = (Element)categories.item(0);
			
			if (categoriesElement == null) {
//				TODO: Display error message and log error
			} else {
				XMLUtil.writeElementStart("categories", "highest-used-id", categoriesElement.getAttribute("highest-used-id"), w);
				
				NodeList children = categoriesElement.getChildNodes();
				
				for (int i = 0; i < children.getLength(); i++) {
					if (children.item(i).getNodeName().equals("category")) {
						Element child = (Element)children.item(i);
						storeChild(child, w);	
					}
				}
				
				XMLUtil.writeElementEnd(w);
			}
			w.flush();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
		} finally {
			StreamUtil.close(os, true);
		}
	}
	
	/**
	 * @param child
	 * @param w
	 * @throws XMLStreamException
	 */
	private static void storeChild(Element child, XMLStreamWriter w) throws XMLStreamException {
		XMLAttribute[] xmlAttributes = new XMLAttribute[2];
		
		if (child.hasChildNodes()) {
			
			xmlAttributes[0] = new XMLAttribute("id", child.getAttribute("id"));
			xmlAttributes[1] = new XMLAttribute("value", child.getAttribute("value"));
			XMLUtil.writeElementStart("category", xmlAttributes, w);
			
			NodeList childNodes = child.getChildNodes();
			for (int i = 0 ; i < childNodes.getLength(); i++) {
				if (childNodes.item(i).getNodeName().equals("category")) {
					storeChild((Element)childNodes.item(i), w);
				}
			}
	
			XMLUtil.writeElementEnd(w);
		} else {
			xmlAttributes[0] = new XMLAttribute("id", child.getAttribute("id"));
			xmlAttributes[1] = new XMLAttribute("value", child.getAttribute("value"));
			
			XMLUtil.writeElement("category", null, xmlAttributes, w);
		}
	}
}
