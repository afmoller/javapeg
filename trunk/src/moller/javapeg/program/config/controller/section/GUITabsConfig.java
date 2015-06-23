package moller.javapeg.program.config.controller.section;

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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.List;

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
                    guiTab.setTextColor(node.getTextContent());
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

        writeTabs(guiTabs.getGuiTabs(), Tab.SIX, xmlsw);

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

            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.TEXT_COLOR, Tab.EIGHT, guiTab.getTextColor(), xmlsw);
            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.POSITION, Tab.EIGHT, guiTab.getPosition().toString(), xmlsw);

            // Tab end
            XMLUtil.writeElementEnd(xmlsw);
        }
    }
}
