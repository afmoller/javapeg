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
            toolTips.setState((String)xPath.evaluate(ConfigElement.STATE, toolTipsNode, XPathConstants.STRING));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return toolTips;
    }

    public static void writeToolTipsConfig(ToolTips toolTips, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  TOOL TIPS start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.TOOL_TIPS, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.STATE, Tab.FOUR, toolTips.getState(), xmlsw);

        //  TOOL TIPS end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
