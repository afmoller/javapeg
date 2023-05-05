package moller.javapeg.program.config.controller.section;

import java.awt.Color;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.config.controller.section.helper.ColorHelper;
import moller.javapeg.program.config.model.GUI.tab.GUITab;
import moller.javapeg.program.config.model.GUI.tab.GUITabs;
import moller.javapeg.program.enumerations.TabPosition;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.string.Tab;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by Fredrik on 2015-06-21.
 */
public class GUITabsConfig {

    public static GUITabs getGUITabsConfig(Node gUITabsNode) {
        GUITabs guiTabs = new GUITabs();

        NodeList childNodes = gUITabsNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
                case TAB:
                    guiTabs.addTab(createTab(node));
                    break;
                default:
                    break;
            }
        }
        return guiTabs;
    }

    private static GUITab createTab(Node tabNode) {

        GUITab guiTab = new GUITab();

        NamedNodeMap attributes = tabNode.getAttributes();
        Node idAttribute = attributes.getNamedItem(ConfigElement.ID.getElementValue());
        String id = idAttribute.getTextContent();

        guiTab.setId(id);

        NodeList childNodes = tabNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
                case TEXT_COLOR:
                    guiTab.setTextColor(ColorHelper.createBackgroundColor(node));
                    break;
                case POSITION:
                    guiTab.setPosition(TabPosition.valueOf(node.getTextContent()));
                    break;
                default:
                    break;
            }
        }
        return guiTab;
    }

    public static void writeGUITabsConfig(GUITabs guiTabs, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  GUITabs start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.GUI_TABS, baseIndent, xmlsw);

        writeTabs(guiTabs.getGuiTabs(), Tab.FOUR, xmlsw);

        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
        //  GUITabs end
    }

    private static void writeTabs(List<GUITab> guiTabs, Tab indent, XMLStreamWriter xmlsw) throws XMLStreamException {
        for (GUITab guiTab : guiTabs) {
            XMLAttribute idAttribute = new XMLAttribute(ConfigElement.ID.getElementValue(), guiTab.getId());

            XMLAttribute[] attributes = new XMLAttribute[1];
            attributes[0] = idAttribute;

            // Tab start
            XMLUtil.writeElementStartWithLineBreak(ConfigElement.TAB, attributes, indent, xmlsw);
            writeTextColor(guiTab.getTextColor(), Tab.SIX, xmlsw);
            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.POSITION, Tab.SIX, guiTab.getPosition().toString(), xmlsw);

            // Tab end
            XMLUtil.writeElementEndWithLineBreak(xmlsw, indent);
        }
    }

    private static void writeTextColor(Color textColor, Tab indent, XMLStreamWriter xmlsw) throws XMLStreamException {
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.TEXT_COLOR, indent, xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.RED, Tab.EIGHT, Integer.toString(textColor.getRed()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.GREEN, Tab.EIGHT, Integer.toString(textColor.getGreen()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.BLUE, Tab.EIGHT, Integer.toString(textColor.getBlue()), xmlsw);
        XMLUtil.writeElementEndWithLineBreak(xmlsw, indent);
    }
}
