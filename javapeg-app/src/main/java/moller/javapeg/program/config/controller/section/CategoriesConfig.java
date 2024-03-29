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
package moller.javapeg.program.config.controller.section;

import moller.javapeg.program.categories.CategoryUserObject;
import moller.javapeg.program.config.controller.ConfigHandlerUtil;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.string.Tab;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.Enumeration;

public class CategoriesConfig {

    public static TreeNode getCategoriesConfig(Node categoriesNode) {
        NamedNodeMap attributes = categoriesNode.getAttributes();
        Node highestUsedIdAttribute = attributes.getNamedItem(ConfigElement.HIGHEST_USED_ID.getElementValue());

        ApplicationContext.getInstance().setHighestUsedCategoryID(Integer.parseInt(highestUsedIdAttribute.getTextContent()));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new CategoryUserObject("root", "-1"));
        ConfigHandlerUtil.populateTreeModelFromNode(categoriesNode, root);

        return root;
    }

    public static ImportedCategories importCategoriesConfig(File categoryFileToImport) {

        ImportedCategories importedCategories;
        DefaultMutableTreeNode root;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc;

        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(categoryFileToImport);
            doc.getDocumentElement().normalize();

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            Node instanceNode = (Node)xPath.evaluate("/" + ConfigElement.INSTANCE, doc, XPathConstants.NODE);

            root = new DefaultMutableTreeNode(new CategoryUserObject("root", "-1"));
            ConfigHandlerUtil.populateTreeModelFromNode(instanceNode, root);

            importedCategories = new ImportedCategories();
            importedCategories.setJavaPegId((String)xPath.evaluate("@" + ConfigElement.JAVAPEG_CLIENT_ID_ATTRIBUTE, instanceNode, XPathConstants.STRING));
            importedCategories.setHighestUsedId(Integer.parseInt((String)xPath.evaluate("@" + ConfigElement.HIGHEST_USED_ID, instanceNode, XPathConstants.STRING)));
            importedCategories.setRoot(root);

        } catch (Exception e) {
            throw new RuntimeException("Could not create imported categories config", e);
        }
        return importedCategories;
    }

    public static void writeCategoriesConfig(TreeNode root, int highestUsedId, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {

        XMLAttribute[] rootAttributes = new XMLAttribute[1];

        rootAttributes[0] = new XMLAttribute("highest-used-id", Integer.toString(highestUsedId));

        //  CATEGORIES start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.CATEGORIES, rootAttributes, baseIndent, xmlsw);

        Enumeration<? extends TreeNode> children = root.children();

        while (children.hasMoreElements()) {
            ConfigHandlerUtil.storeChild((DefaultMutableTreeNode)children.nextElement(), baseIndent.value() + Tab.TWO.value(), xmlsw);
        }

        //  CATEGORIES end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

    public static void exportCategoriesConfig(TreeNode root, String javaPegClientId, int highestUsedId, XMLStreamWriter xmlsw) throws XMLStreamException {
        XMLAttribute[] rootAttributes = new XMLAttribute[2];

        rootAttributes[0] = new XMLAttribute("javapegclientid", javaPegClientId);
        rootAttributes[1] = new XMLAttribute("highest-used-id", Integer.toString(highestUsedId));

        XMLUtil.writeStartDocument("UTF-8", "1.0", xmlsw);
        XMLUtil.writeLineBreak(xmlsw);

        writeConfig(ConfigElement.INSTANCE, root, rootAttributes, Tab.ZERO, xmlsw);
    }

    private static void writeConfig(ConfigElement element, TreeNode root, XMLAttribute[] rootAttributes, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        // Start
        XMLUtil.writeElementStartWithLineBreak(element, rootAttributes, baseIndent, xmlsw);

        Enumeration<? extends TreeNode> children = root.children();

        while (children.hasMoreElements()) {
            ConfigHandlerUtil.storeChild((DefaultMutableTreeNode)children.nextElement(), baseIndent.value() + Tab.TWO.value(), xmlsw);
        }

        // End
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
