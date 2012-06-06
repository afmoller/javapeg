package moller.javapeg.program.config.controller;



import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.model.Language;
import moller.javapeg.program.config.model.Logging;
import moller.javapeg.program.config.model.ToolTips;
import moller.javapeg.program.config.model.UpdatesChecker;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPane;
import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesCategories;
import moller.javapeg.program.config.model.repository.Repository;
import moller.javapeg.program.config.model.thumbnail.ThumbNail;
import moller.javapeg.program.enumerations.Level;
import moller.util.string.StringUtil;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigHandlerUtil {

    public static Logging getLoggingConfig(NodeList loggingNodeList, XPath xPath) {
        Logging logging = new Logging();

        try {
            logging.setFileName((String)xPath.evaluate("fileName", loggingNodeList, XPathConstants.STRING));
            logging.setDeveloperMode((Boolean)xPath.evaluate("developerMode", loggingNodeList, XPathConstants.BOOLEAN));
            logging.setLevel(Level.valueOf((String)xPath.evaluate("level", loggingNodeList, XPathConstants.STRING)));
            logging.setRotate((Boolean)xPath.evaluate("rotate", loggingNodeList, XPathConstants.BOOLEAN));
            logging.setRotateSize(new Long((String)xPath.evaluate("rotateSize", loggingNodeList, XPathConstants.STRING)));
            logging.setRotateZip((Boolean)xPath.evaluate("rotateZip", loggingNodeList, XPathConstants.BOOLEAN));
            logging.setTimeStampFormat(new SimpleDateFormat((String)xPath.evaluate("timestampFormat", loggingNodeList, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return logging;
    }

    public static TagImagesCategories getCategoriesConfig(NodeList categoriesNodeList, XPath xPath) {
        // TODO Auto-generated method stub
        return null;
    }

    public static GUI getGUIConfig(NodeList gUINodeList, XPath xPath) {
        GUI gui = new GUI();

int l = gUINodeList.getLength();

for (int i=0; i<l; i++) {
    Node n = gUINodeList.item(i);
    System.out.println(n.getNodeName());
}


        try {
            gui.setConfigViewer(createGUIWindow((NodeList)xPath.evaluate("gui/configViewer/*", gUINodeList, XPathConstants.NODESET), xPath));
//            gui.setHelpViewer(createGUIWindow((NodeList)xPath.evaluate("helpViewer", gUINodeList, XPathConstants.NODESET), xPath));
//            gui.setImageSearchResultViewer(createGUIWindow((NodeList)xPath.evaluate("imageSearchResultViewer", gUINodeList, XPathConstants.NODESET), xPath));
//            gui.setImageViewer(createGUIWindow((NodeList)xPath.evaluate("imageViewer", gUINodeList, XPathConstants.NODESET), xPath));
//            gui.setMain(createGUIWindow((NodeList)xPath.evaluate("main", gUINodeList, XPathConstants.NODESET), xPath));

        } catch (XPathExpressionException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return gui;
    }

    private static GUIWindow createGUIWindow(NodeList windowNodeList, XPath xPath) {
        GUIWindow guiWindow = new GUIWindow();

        Rectangle sizeAndLocation = new Rectangle();

        try {
            sizeAndLocation.x = StringUtil.getIntValue((String)xPath.evaluate("xLocation", windowNodeList, XPathConstants.STRING), 1);
            sizeAndLocation.y = StringUtil.getIntValue((String)xPath.evaluate("yLocation", windowNodeList, XPathConstants.STRING), 1);
            sizeAndLocation.width = StringUtil.getIntValue((String)xPath.evaluate("width", windowNodeList, XPathConstants.STRING), 100);
            sizeAndLocation.height = StringUtil.getIntValue((String)xPath.evaluate("height", windowNodeList, XPathConstants.STRING), 100);

            guiWindow.setSizeAndLocation(sizeAndLocation);
            guiWindow.setGuiWindowSplitPane(createGUIWindowSplitPane((NodeList)xPath.evaluate("splitPane", windowNodeList, XPathConstants.NODESET)));
        } catch (XPathExpressionException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return guiWindow;
    }

    private static List<GUIWindowSplitPane> createGUIWindowSplitPane(NodeList splitPaneNodeList) {
        List<GUIWindowSplitPane> guiWindowSplitPanes = new ArrayList<GUIWindowSplitPane>();

        for (int index = 0; index < splitPaneNodeList.getLength(); index++) {

            GUIWindowSplitPane guiWindowSplitPane = new GUIWindowSplitPane();

            Node splitPaneNode = splitPaneNodeList.item(index);

            guiWindowSplitPane.setLocation(StringUtil.getIntValue(splitPaneNode.getTextContent(), 1));

            NamedNodeMap attributes = splitPaneNode.getAttributes();

            Node idAttribute = attributes.getNamedItem("id");
            Node widthAttribute = attributes.getNamedItem("width");

            guiWindowSplitPane.setId(idAttribute.getTextContent());

            if (null != widthAttribute) {
                if (StringUtil.isInt(widthAttribute.getTextContent())) {
                    guiWindowSplitPane.setWidth(Integer.parseInt(widthAttribute.getTextContent()));
                }
            }

            guiWindowSplitPanes.add(guiWindowSplitPane);
        }
        return guiWindowSplitPanes;
    }

    public static String getJavapegClientIdConfig(NodeList javapegClientIdNodeList) {

        Node javapegClientIdNode = javapegClientIdNodeList.item(0);

        NodeList children = javapegClientIdNode.getChildNodes();

        for (int index = 0; index < children.getLength(); index++) {

            Node child = children.item(index);

            if ("javapegClientId".equals(child.getNodeName())) {
                return child.getTextContent();
            }
        }
        return "NOT-DEFINED";
    }

    public static Language getLanguageConfig(NodeList languageNodeList, XPath xPath) {

        Language language = new Language();

        try {
            language.setAutomaticSelection((Boolean)xPath.evaluate("automaticSelection", languageNodeList, XPathConstants.BOOLEAN));
            language.setgUILanguageISO6391((String)xPath.evaluate("gUILanguageISO6391", languageNodeList, XPathConstants.STRING));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return language;
    }

    public static RenameImages getRenameImagesConfig(NodeList elementsByTagName, XPath xPath) {
        // TODO Auto-generated method stub
        return null;
    }

    public static Repository getRepositoryConfig(NodeList elementsByTagName, XPath xPath) {
        // TODO Auto-generated method stub
        return null;
    }

    public static TagImages getTagImagesConfig(NodeList elementsByTagName, XPath xPath) {
        // TODO Auto-generated method stub
        return null;
    }

    public static ThumbNail getThumbNailConfig(NodeList elementsByTagName, XPath xPath) {
        // TODO Auto-generated method stub
        return null;
    }

    public static ToolTips getToolTipsConfig(NodeList elementsByTagName, XPath xPath) {
        // TODO Auto-generated method stub
        return null;
    }

    public static UpdatesChecker getUpdatesCheckerConfig(
            NodeList elementsByTagName, XPath xPath) {
        // TODO Auto-generated method stub
        return null;
    }
}
