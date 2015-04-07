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
package moller.javapeg.program.config.controller;

import moller.javapeg.program.categories.CategoryUserObject;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.string.Tab;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Enumeration;

public class ConfigHandlerUtil {

    public static void populateTreeModelFromNode(Node categoriesElement, DefaultMutableTreeNode root) {

        NodeList childNodes = categoriesElement.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case CATEGORY:
                populateNodeBranch(root, node);
            default:
                break;
            }
        }
    }

    private static void populateNodeBranch(DefaultMutableTreeNode treeNode, Node category) {
        NamedNodeMap attributes = category.getAttributes();
        Node nameAttribute = attributes.getNamedItem("name");
        Node idAttribute = attributes.getNamedItem("id");

        String displayString = nameAttribute.getTextContent();
        String identityString = idAttribute.getTextContent();

        CategoryUserObject cuo = new CategoryUserObject(displayString, identityString);

        DefaultMutableTreeNode mtn = new DefaultMutableTreeNode(cuo);

        treeNode.add(mtn);

        NodeList childNodes = category.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case CATEGORY:
                populateNodeBranch(mtn, node);
            default:
                break;
            }
        }
    }

    /**
     * @param w
     * @throws XMLStreamException
     */
    @SuppressWarnings("unchecked")
    public static void storeChild(DefaultMutableTreeNode node, String indent, XMLStreamWriter w) throws XMLStreamException {
        XMLAttribute[] xmlAttributes = new XMLAttribute[2];

        if (node.getChildCount() > 0) {
            CategoryUserObject cuo = (CategoryUserObject)node.getUserObject();

            xmlAttributes[0] = new XMLAttribute("id", cuo.getIdentity());
            xmlAttributes[1] = new XMLAttribute("name", cuo.getName());
            XMLUtil.writeElementStartWithLineBreak(ConfigElement.CATEGORY, indent, xmlAttributes, w);

            Enumeration<DefaultMutableTreeNode> children = node.children();

            while (children.hasMoreElements()) {
                storeChild(children.nextElement(), indent + Tab.TWO.value(), w);
            }

            XMLUtil.writeElementEndWithLineBreak(w, indent);
        } else {
            CategoryUserObject cuo = (CategoryUserObject)node.getUserObject();

            xmlAttributes[0] = new XMLAttribute("id", cuo.getIdentity());
            xmlAttributes[1] = new XMLAttribute("name", cuo.getName());
            XMLUtil.writeEmptyElementWithIndentAndLineBreak("category", w, indent, xmlAttributes);
        }
    }
}
