package moller.javapeg.program.config.controller.section;

import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.categories.CategoryUserObject;
import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.controller.ConfigHandlerUtil;
import moller.javapeg.program.config.model.categories.ImportedCategories;

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

                DefaultMutableTreeNode root = new DefaultMutableTreeNode(new CategoryUserObject("root", "-1"));

                ConfigHandlerUtil.populateTreeModelFromNode(instanceNode, root, xPath);

                ImportedCategories importedCategories = new ImportedCategories();
                importedCategories.setDisplayName(displayName);
                importedCategories.setRoot(root);

                importedCategoriesConfig.put(javapegClientId, importedCategories);
            }
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return importedCategoriesConfig;
    }
}
