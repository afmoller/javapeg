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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.categories.CategoryUserObject;
import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.controller.ConfigHandlerUtil;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.util.string.Tab;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ImportedCategoriesConfig {

    public static Map<String, ImportedCategories> getImportedCategoriesConfig(Node importedCategoriesNode) {
        Map<String, ImportedCategories> importedCategoriesConfig = new HashMap<String, ImportedCategories>();

        NodeList childNodes = importedCategoriesNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (node.getNodeName()) {
            case ConfigElement.INSTANCE:
                NamedNodeMap attributes = node.getAttributes();

                Node javaPegClientAttribute = attributes.getNamedItem(ConfigElement.JAVAPEG_CLIENT_ID_ATTRIBUTE);
                Node displayNameAttribute = attributes.getNamedItem(ConfigElement.DISPLAY_NAME);
                Node highestUsedIdAttribute = attributes.getNamedItem(ConfigElement.HIGHEST_USED_ID);

                String javapegClientId = javaPegClientAttribute.getTextContent();
                String displayName = displayNameAttribute.getTextContent();
                Integer highestUsedId = Integer.parseInt(highestUsedIdAttribute.getTextContent());

                DefaultMutableTreeNode root = new DefaultMutableTreeNode(new CategoryUserObject("root", "-1"));

                ConfigHandlerUtil.populateTreeModelFromNode(node, root);

                ImportedCategories importedCategories = new ImportedCategories();
                importedCategories.setDisplayName(displayName);
                importedCategories.setHighestUsedId(highestUsedId);
                importedCategories.setJavaPegId(javapegClientId);
                importedCategories.setRoot(root);

                importedCategoriesConfig.put(javapegClientId, importedCategories);
                break;
            default:
                break;
            }
        }
        return importedCategoriesConfig;
    }

    public static void writeImportedCategoriesConfig(Map<String, ImportedCategories> javaPEGIdToImportedCategoriesMappings, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {

        //  IMPORTED CATEGORIES start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.IMPORTEDCATEGORIES, baseIndent, xmlsw);

        for (String javaPEGId : javaPEGIdToImportedCategoriesMappings.keySet()) {

            XMLAttribute[] xmlAttributes = new XMLAttribute[3];

            ImportedCategories importedCategories = javaPEGIdToImportedCategoriesMappings.get(javaPEGId);

            xmlAttributes[0] = new XMLAttribute(ConfigElement.DISPLAY_NAME, importedCategories.getDisplayName());
            xmlAttributes[1] = new XMLAttribute(ConfigElement.JAVAPEG_CLIENT_ID_ATTRIBUTE, javaPEGId);
            xmlAttributes[2] = new XMLAttribute(ConfigElement.HIGHEST_USED_ID, importedCategories.getHighestUsedId());

            //  INSTANCE start
            XMLUtil.writeElementStartWithLineBreak(ConfigElement.INSTANCE, xmlAttributes, Tab.FOUR, xmlsw);

            @SuppressWarnings("unchecked")
            Enumeration<DefaultMutableTreeNode> children = importedCategories.getRoot().children();

            while (children.hasMoreElements()) {
                ConfigHandlerUtil.storeChild(children.nextElement(), Tab.FOUR.value() + Tab.TWO.value(), xmlsw);
            }

            //  INSTANCE end
            XMLUtil.writeElementEndWithLineBreak(xmlsw, Tab.FOUR);
        }

        //  IMPORTED CATEGORIES end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
