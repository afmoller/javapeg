package moller.javapeg.program.config.controller.section;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.categories.CategoryUserObject;
import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.controller.ConfigHandlerUtil;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.util.string.Tab;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ImportedCategoriesConfig {

    public static Map<String, ImportedCategories> getImportedCategoriesConfig(Node importedCategoriesNode, XPath xPath) {

        Map<String, ImportedCategories> importedCategoriesConfig = null;

        try {
            NodeList instancesNodeList = (NodeList)xPath.evaluate(ConfigElement.INSTANCE, importedCategoriesNode, XPathConstants.NODESET);

            importedCategoriesConfig = new HashMap<String, ImportedCategories>();

            for (int index = 0; index < instancesNodeList.getLength(); index++) {
                Node instanceNode = instancesNodeList.item(index);
                String javapegClientId = (String)xPath.evaluate("@" + ConfigElement.JAVAPEG_CLIENT_ID_ATTRIBUTE, instanceNode, XPathConstants.STRING);
                String displayName = (String)xPath.evaluate("@" + ConfigElement.DISPLAY_NAME, instanceNode, XPathConstants.STRING);
                Integer highestUsedId = Integer.parseInt((String)xPath.evaluate("@" + ConfigElement.HIGHEST_USED_ID, instanceNode, XPathConstants.STRING));

                DefaultMutableTreeNode root = new DefaultMutableTreeNode(new CategoryUserObject("root", "-1"));

                ConfigHandlerUtil.populateTreeModelFromNode(instanceNode, root, xPath);

                ImportedCategories importedCategories = new ImportedCategories();
                importedCategories.setDisplayName(displayName);
                importedCategories.setHighestUsedId(highestUsedId);
                importedCategories.setJavaPegId(javapegClientId);
                importedCategories.setRoot(root);

                importedCategoriesConfig.put(javapegClientId, importedCategories);
            }
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
