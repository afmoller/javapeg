package moller.javapeg.program.config.controller.section;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.ToolTips;

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
}
