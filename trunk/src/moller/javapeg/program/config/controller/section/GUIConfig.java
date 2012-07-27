package moller.javapeg.program.config.controller.section;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPane;
import moller.util.string.StringUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GUIConfig {

    public static GUI getGUIConfig(Node gUINode, XPath xPath) {
        GUI gui = new GUI();

        try {
            gui.setConfigViewer(createGUIWindow((Node)xPath.evaluate(ConfigElement.CONFIG_VIEWER, gUINode, XPathConstants.NODE), xPath));
            gui.setHelpViewer(createGUIWindow((Node)xPath.evaluate(ConfigElement.HELP_VIEWER, gUINode, XPathConstants.NODE), xPath));
            gui.setImageSearchResultViewer(createGUIWindow((Node)xPath.evaluate(ConfigElement.IMAGE_SEARCH_RESULT_VIEWER, gUINode, XPathConstants.NODE), xPath));
            gui.setImageViewer(createGUIWindow((Node)xPath.evaluate(ConfigElement.IMAGE_VIEWER, gUINode, XPathConstants.NODE), xPath));
            gui.setMain(createGUIWindow((Node)xPath.evaluate(ConfigElement.MAIN, gUINode, XPathConstants.NODE), xPath));

        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return gui;
    }

    private static GUIWindow createGUIWindow(Node windowNode, XPath xPath) {
        GUIWindow guiWindow = new GUIWindow();

        Rectangle sizeAndLocation = new Rectangle();

        try {
            sizeAndLocation.x = StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.X_LOCATION, windowNode, XPathConstants.STRING), 1);
            sizeAndLocation.y = StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.Y_LOCATION, windowNode, XPathConstants.STRING), 1);
            sizeAndLocation.width = StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.WIDTH, windowNode, XPathConstants.STRING), 100);
            sizeAndLocation.height = StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.HEIGHT, windowNode, XPathConstants.STRING), 100);

            guiWindow.setSizeAndLocation(sizeAndLocation);
            guiWindow.setGuiWindowSplitPane(createGUIWindowSplitPane((NodeList)xPath.evaluate(ConfigElement.SPLIT_PANE, windowNode, XPathConstants.NODESET), xPath));
        } catch (XPathExpressionException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return guiWindow;
    }

    private static List<GUIWindowSplitPane> createGUIWindowSplitPane(NodeList splitPaneNodeList, XPath xPath) {
        List<GUIWindowSplitPane> guiWindowSplitPanes = new ArrayList<GUIWindowSplitPane>();

        for (int index = 0; index < splitPaneNodeList.getLength(); index++) {
            try {
                GUIWindowSplitPane guiWindowSplitPane = new GUIWindowSplitPane();

                Node splitPaneNode = splitPaneNodeList.item(index);

                guiWindowSplitPane.setLocation(StringUtil.getIntValue(splitPaneNode.getTextContent(), 1));

                String id = (String)xPath.evaluate("@" + ConfigElement.ID, splitPaneNode, XPathConstants.STRING);
                String width = (String)xPath.evaluate("@" + ConfigElement.WIDTH, splitPaneNode, XPathConstants.STRING);

                guiWindowSplitPane.setId(id);
                guiWindowSplitPane.setWidth(width.equals("") ? null : Integer.parseInt(width));

                guiWindowSplitPanes.add(guiWindowSplitPane);
            } catch (XPathExpressionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return guiWindowSplitPanes;
    }
}
