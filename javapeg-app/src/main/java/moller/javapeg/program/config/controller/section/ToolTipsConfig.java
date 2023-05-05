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

import moller.javapeg.program.config.model.ToolTips;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class ToolTipsConfig {

    public static ToolTips getToolTipsConfig(Node toolTipsNode) {
        ToolTips toolTips = new ToolTips();

        NodeList childNodes = toolTipsNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case OVERVIEW_STATE:
                toolTips.setOverviewState(node.getTextContent());
                break;
            case IMAGE_SEARCH_RESULT_STATE:
                toolTips.setImageSearchResultState(node.getTextContent());
                break;
            case OVERVIEW_IMAGE_VIEWER_STATE:
                toolTips.setOverviewImageViewerState(node.getTextContent());
                break;
            default:
                break;
            }
        }
        return toolTips;
    }

    public static void writeToolTipsConfig(ToolTips toolTips, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  TOOL TIPS start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.TOOL_TIPS, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.OVERVIEW_STATE, Tab.FOUR, toolTips.getOverviewState(), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.IMAGE_SEARCH_RESULT_STATE, Tab.FOUR, toolTips.getImageSearchResultState(), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.OVERVIEW_IMAGE_VIEWER_STATE, Tab.FOUR, toolTips.getOverviewImageViewerState(), xmlsw);

        //  TOOL TIPS end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
