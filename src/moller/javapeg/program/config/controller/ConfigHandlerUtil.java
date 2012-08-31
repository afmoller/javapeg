package moller.javapeg.program.config.controller;

import java.util.Enumeration;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.categories.CategoryUserObject;
import moller.util.string.Tab;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigHandlerUtil {

    public static void populateTreeModelFromNode(Node categoriesElement, DefaultMutableTreeNode root, XPath xPath) {

        try {
            NodeList categoryElements = (NodeList)xPath.evaluate("category", categoriesElement, XPathConstants.NODESET);
            if (categoryElements.getLength() > 0) {
                for (int i = 0; i < categoryElements.getLength(); i++) {
                    populateNodeBranch(root, categoryElements.item(i), xPath);
                }
            }
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void populateNodeBranch(DefaultMutableTreeNode node, Node category, XPath xPath ) {

        try {
            String displayString = (String)xPath.evaluate("@name", category, XPathConstants.STRING);
            String identityString = (String)xPath.evaluate("@id", category, XPathConstants.STRING);

            CategoryUserObject cuo = new CategoryUserObject(displayString, identityString);

            DefaultMutableTreeNode mtn = new DefaultMutableTreeNode(cuo);

            node.add(mtn);

            NodeList categoryChildren = (NodeList)xPath.evaluate("category", category, XPathConstants.NODESET);

            if (categoryChildren.getLength() > 0) {
                for (int i = 0; i < categoryChildren.getLength(); i++) {
                    populateNodeBranch(mtn, categoryChildren.item(i), xPath);
                }
            }
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param child
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
            XMLUtil.writeElementStartWithLineBreak("category", indent, xmlAttributes, w);

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

    public static void displayErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
