package moller.javapeg.program.categories;

import javax.swing.tree.TreePath;

import moller.javapeg.program.model.XMLTreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	public static int getHighestID(Document document) {
		int highest = -1;
		
		NodeList allCategories = document.getElementsByTagName("category");
		
		for (int i = 0; i < allCategories.getLength(); i++) {
			Element category = (Element)allCategories.item(i);
			
			int current = Integer.parseInt(category.getAttribute("id")); 
			if (current > highest) {
				highest = current;
			}
		}
		return highest;
	}
}
