package moller.javapeg.program.config.controller.section.helper;

import java.awt.Color;

import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.string.StringUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ColorHelper {
    
    public static Color createBackgroundColor(Node backgroundColorNode) {
        NodeList childNodes = backgroundColorNode.getChildNodes();

        int red = 0;
        int green = 0;
        int blue = 0;

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            int nodeValue = StringUtil.getIntValue(node.getTextContent(), 0);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case RED:
                red = nodeValue;
                break;
            case GREEN:
                green = nodeValue;
                break;
            case BLUE:
                blue = nodeValue;
                break;
            default:
                break;
            }
        }

        return new Color(red, green, blue);
    }

}
