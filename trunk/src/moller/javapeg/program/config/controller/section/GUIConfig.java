package moller.javapeg.program.config.controller.section;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPane;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GUIConfig {

    public static GUI getGUIConfig(Node gUINode, XPath xPath) {
        GUI gui = new GUI();

        try {
            gui.setConfigViewer(createGUIWindow((Node)xPath.evaluate(ConfigElement.CONFIG_VIEWER, gUINode, XPathConstants.NODE), xPath));
            gui.setHelpViewer(createGUIWindow((Node)xPath.evaluate(ConfigElement.HELP_VIEWER, gUINode, XPathConstants.NODE), xPath));
            gui.setImageResizer(createGUIWindow((Node)xPath.evaluate(ConfigElement.IMAGE_RESIZER, gUINode, XPathConstants.NODE), xPath));
            gui.setImageSearchResultViewer(createGUIWindow((Node)xPath.evaluate(ConfigElement.IMAGE_SEARCH_RESULT_VIEWER, gUINode, XPathConstants.NODE), xPath));
            gui.setImageViewer(createGUIWindow((Node)xPath.evaluate(ConfigElement.IMAGE_VIEWER, gUINode, XPathConstants.NODE), xPath));
            gui.setMain(createGUIWindow((Node)xPath.evaluate(ConfigElement.MAIN, gUINode, XPathConstants.NODE), xPath));
            gui.setImageConflictViewer(createGUIWindow((Node)xPath.evaluate(ConfigElement.IMAGE_CONFLICT_VIEWER, gUINode, XPathConstants.NODE), xPath));

        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get gui config", e);
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
            throw new RuntimeException("Could not create gui window", e);
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
                throw new RuntimeException("Could not create gui window split pane config", e);
            }
        }
        return guiWindowSplitPanes;
    }

    public static void writeGUIConfig(GUI gUI, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  GUI start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.GUI, baseIndent, xmlsw);

        //    MAIN start
        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.MAIN, Tab.TWO, xmlsw);

        writeSizeAndLocation(gUI.getMain().getSizeAndLocation(), Tab.SIX, xmlsw);
        writeSplitPanes(gUI.getMain().getGuiWindowSplitPane(), Tab.SIX, xmlsw);

        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
        //    MAIN end

        //    IMAGE VIEWER start
        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.IMAGE_VIEWER, Tab.TWO, xmlsw);

        writeSizeAndLocation(gUI.getImageViewer().getSizeAndLocation(), Tab.SIX, xmlsw);
        writeSplitPanes(gUI.getImageViewer().getGuiWindowSplitPane(), Tab.SIX, xmlsw);

        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
        //    IMAGE VIEWER end

        //    IMAGE SEARCH RESULT VIEWER start
        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.IMAGE_SEARCH_RESULT_VIEWER, Tab.TWO, xmlsw);

        writeSizeAndLocation(gUI.getImageSearchResultViewer().getSizeAndLocation(), Tab.SIX, xmlsw);

        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
        //    IMAGE SEARCH RESULT VIEWER end

        //    CONFIG VIEWER start
        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.CONFIG_VIEWER, Tab.TWO, xmlsw);

        writeSizeAndLocation(gUI.getConfigViewer().getSizeAndLocation(), Tab.SIX, xmlsw);

        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
        //    CONFIG VIEWER end

        //    HELP VIEWER start
        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.HELP_VIEWER, Tab.TWO, xmlsw);

        writeSizeAndLocation(gUI.getHelpViewer().getSizeAndLocation(), Tab.SIX, xmlsw);

        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
        //    HELP VIEWER end

        //    IMAGE RESIZER start
        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.IMAGE_RESIZER, Tab.TWO, xmlsw);

        writeSizeAndLocation(gUI.getImageResizer().getSizeAndLocation(), Tab.SIX, xmlsw);
        writeSplitPanes(gUI.getImageResizer().getGuiWindowSplitPane(), Tab.SIX, xmlsw);

        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
        //    IMAGE RESIZER end

        //    IMAGE CONFLICT VIEWER start
        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.IMAGE_CONFLICT_VIEWER, Tab.TWO, xmlsw);

        writeSizeAndLocation(gUI.getImageConflictViewer().getSizeAndLocation(), Tab.SIX, xmlsw);

        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
        //    IMAGE CONFLICT VIEWER end

        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
        //  GUI end
    }

    private static void writeSizeAndLocation(Rectangle sizeAndLocation, Tab indent, XMLStreamWriter xmlsw) throws XMLStreamException {
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.X_LOCATION, indent, Integer.toString(sizeAndLocation.x), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.Y_LOCATION, indent, Integer.toString(sizeAndLocation.y), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.WIDTH, indent, Integer.toString(sizeAndLocation.width), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.HEIGHT, indent, Integer.toString(sizeAndLocation.height), xmlsw);
    }

    private static void writeSplitPanes(List<GUIWindowSplitPane> guiWindowSplitPanes, Tab indent, XMLStreamWriter xmlsw) throws XMLStreamException {
        for (GUIWindowSplitPane guiWindowSplitPane : guiWindowSplitPanes) {

            String id = guiWindowSplitPane.getName();
            Integer width = guiWindowSplitPane.getWidth();

            XMLAttribute idAttribute = new XMLAttribute(ConfigElement.ID, id);
            XMLAttribute widthAttribute;

            XMLAttribute[] attributes;

            if (width != null) {
                widthAttribute = new XMLAttribute(ConfigElement.WIDTH, Integer.toString(width));

                attributes = new XMLAttribute[2];
                attributes[0] = idAttribute;
                attributes[1] = widthAttribute;
            } else {
                attributes = new XMLAttribute[1];
                attributes[0] = idAttribute;
            }

            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.SPLIT_PANE, indent, attributes, Integer.toString(guiWindowSplitPane.getLocation()), xmlsw);
        }
    }
}
