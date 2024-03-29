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

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.config.controller.section.helper.ColorHelper;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.GUI.splitpane.GUIWindowSplitPane;
import moller.javapeg.program.config.model.GUI.tab.SelectedMainGUITab;
import moller.javapeg.program.enumerations.SplitPaneDividerSize;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GUIConfig {

    public static GUI getGUIConfig(Node gUINode) {
        GUI gui = new GUI();

        NodeList childNodes = gUINode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case CONFIG_VIEWER:
                gui.setConfigViewer(createGUIWindow(node));
                break;
            case HELP_VIEWER:
                gui.setHelpViewer(createGUIWindow(node));
                break;
            case IMAGE_RESIZER:
                gui.setImageResizer(createGUIWindow(node));
                break;
            case IMAGE_SEARCH_RESULT_VIEWER:
                gui.setImageSearchResultViewer(createGUIWindow(node));
                break;
            case IMAGE_VIEWER:
                gui.setImageViewer(createGUIWindow(node));
                break;
            case MAIN:
                gui.setMain(createGUIWindow(node));
                break;
            case IMAGE_CONFLICT_VIEWER:
                gui.setImageConflictViewer(createGUIWindow(node));
                break;
            case IMAGE_REPOSITORY_STATISTICS_VIEWER:
                gui.setImageRepositoryStatisticsViewer(createGUIWindow(node));
                break;
            case SELECTED_MAIN_GUI_TAB:
                gui.setSelectedMainGUITab(SelectedMainGUITab.valueOf(node.getTextContent()));
                break;
            default:
                break;
            }
        }
        return gui;
    }

    private static GUIWindow createGUIWindow(Node windowNode) {
        GUIWindow guiWindow = new GUIWindow();
        List<GUIWindowSplitPane> guiWindowSplitPanes = new ArrayList<>();

        Rectangle sizeAndLocation = new Rectangle();

        NodeList childNodes = windowNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case X_LOCATION:
                sizeAndLocation.x = StringUtil.getIntValue(node.getTextContent(), 1);
                break;
            case Y_LOCATION:
                sizeAndLocation.y = StringUtil.getIntValue(node.getTextContent(), 1);
                break;
            case WIDTH:
                sizeAndLocation.width = StringUtil.getIntValue(node.getTextContent(), 100);
                break;
            case HEIGHT:
                sizeAndLocation.height = StringUtil.getIntValue(node.getTextContent(), 100);
                break;
            case SPLIT_PANE:
                guiWindowSplitPanes.add(createGUIWindowSplitPane(node));
                break;
            case BACKGROUND_COLOR:
                guiWindow.setBackgroundColor(ColorHelper.createBackgroundColor(node));
            default:
                break;
            }
        }

        guiWindow.setSizeAndLocation(sizeAndLocation);
        guiWindow.setGuiWindowSplitPane(guiWindowSplitPanes);

        return guiWindow;
    }

    private static GUIWindowSplitPane createGUIWindowSplitPane(Node splitPaneNode) {

        GUIWindowSplitPane guiWindowSplitPane = new GUIWindowSplitPane();

        guiWindowSplitPane.setLocation(StringUtil.getIntValue(splitPaneNode.getTextContent(), 1));

        NamedNodeMap attributes = splitPaneNode.getAttributes();
        Node idAttribute = attributes.getNamedItem(ConfigElement.ID.getElementValue());
        String id = idAttribute.getTextContent();

        Node widthAttribute = attributes.getNamedItem(ConfigElement.WIDTH.getElementValue());
        String width = "";

        // The width attribute is an optional attribute, therefore it is
        // necessary with an null check.
        if (widthAttribute != null) {
            width = widthAttribute.getTextContent();
        }

        guiWindowSplitPane.setId(id);
        guiWindowSplitPane.setDividerSize(SplitPaneDividerSize.valueOf(width));

        return guiWindowSplitPane;
    }

    public static void writeGUIConfig(GUI gUI, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  GUI start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.GUI, baseIndent, xmlsw);

        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.SELECTED_MAIN_GUI_TAB, Tab.TWO, gUI.getSelectedMainGUITab().name(), xmlsw);

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
        writeBackgroundColor(gUI.getImageViewer().getBackgroundColor(), Tab.SIX, xmlsw);

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

        //    IMAGE REPOSITORY STATISTICS VIEWER start
        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.IMAGE_REPOSITORY_STATISTICS_VIEWER, Tab.TWO, xmlsw);

        writeSizeAndLocation(gUI.getImageRepositoryStatisticsViewer().getSizeAndLocation(), Tab.SIX, xmlsw);

        XMLUtil.writeIndent(xmlsw, baseIndent.value());
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
        //    IMAGE REPOSITORY STATISTICS end

        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
        //  GUI end
    }

    private static void writeBackgroundColor(Color backgroundColor, Tab indent, XMLStreamWriter xmlsw) throws XMLStreamException {
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.BACKGROUND_COLOR, indent, xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.RED, Tab.EIGHT, Integer.toString(backgroundColor.getRed()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.GREEN, Tab.EIGHT, Integer.toString(backgroundColor.getGreen()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.BLUE, Tab.EIGHT, Integer.toString(backgroundColor.getBlue()), xmlsw);
        XMLUtil.writeElementEndWithLineBreak(xmlsw, indent);
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
            SplitPaneDividerSize width = guiWindowSplitPane.getDividerSize();

            XMLAttribute idAttribute = new XMLAttribute(ConfigElement.ID.getElementValue(), id);
            XMLAttribute widthAttribute;

            XMLAttribute[] attributes;

            if (width != null) {
                widthAttribute = new XMLAttribute(ConfigElement.WIDTH.getElementValue(), width.name());

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
