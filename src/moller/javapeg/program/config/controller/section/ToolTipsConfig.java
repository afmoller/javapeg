package moller.javapeg.program.config.controller.section;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.ToolTips;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;

public class ToolTipsConfig {

    public static ToolTips getToolTipsConfig(Node toolTipsNode, XPath xPath) {
        ToolTips toolTips = new ToolTips();

        try {
            toolTips.setOverviewState((String)xPath.evaluate(ConfigElement.OVERVIEW_STATE, toolTipsNode, XPathConstants.STRING));
            toolTips.setImageSearchResultState((String)xPath.evaluate(ConfigElement.IMAGE_SEARCH_RESULT_STATE, toolTipsNode, XPathConstants.STRING));
            toolTips.setOverviewImageViewerState((String)xPath.evaluate(ConfigElement.OVERVIEW_IMAGE_VIEWER_STATE, toolTipsNode, XPathConstants.STRING));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get tooltips config", e);
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
