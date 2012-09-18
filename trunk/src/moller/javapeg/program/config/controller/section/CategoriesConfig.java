package moller.javapeg.program.config.controller.section;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.categories.CategoryUserObject;
import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.controller.ConfigHandlerUtil;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.util.string.Tab;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;

public class CategoriesConfig {

    public static TreeNode getCategoriesConfig(Node categoriesNode, XPath xPath) {

        DefaultMutableTreeNode root = null;

        try {
            String id = (String)xPath.evaluate("@" + ConfigElement.HIGHEST_USED_ID, categoriesNode, XPathConstants.STRING);
            ApplicationContext.getInstance().setHighestUsedCategoryID(Integer.parseInt(id));

            root = new DefaultMutableTreeNode(new CategoryUserObject("root", "-1"));
            ConfigHandlerUtil.populateTreeModelFromNode(categoriesNode, root, xPath);
        } catch (XPathExpressionException xpee) {
            ConfigHandlerUtil.displayErrorMessage("Error", "Could not create Categories Model: " + xpee.getMessage());
            System.exit(1);
        }

        return root;
    }

    public static void writeCategoriesConfig(TreeNode root, int highestUsedId, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {

        XMLAttribute[] rootAttributes = new XMLAttribute[1];

        rootAttributes[0] = new XMLAttribute("highest-used-id", Integer.toString(highestUsedId));

        //  CATEGORIES start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.CATEGORIES, rootAttributes, baseIndent, xmlsw);

        @SuppressWarnings("unchecked")
        Enumeration<DefaultMutableTreeNode> children = root.children();

        while (children.hasMoreElements()) {
            ConfigHandlerUtil.storeChild(children.nextElement(), baseIndent.value() + Tab.TWO.value(), xmlsw);
        }

        //  CATEGORIES end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
