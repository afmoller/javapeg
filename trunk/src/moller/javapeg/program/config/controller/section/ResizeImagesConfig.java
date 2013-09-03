package moller.javapeg.program.config.controller.section;

import java.io.File;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.applicationmode.resize.ResizeImages;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;

public class ResizeImagesConfig {

    public static ResizeImages getResizeImagesConfig(Node resizeImagesNode, XPath xPath) {
        ResizeImages resizeImages = new ResizeImages();

        try {
            resizeImages.setHeight(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.HEIGHT, resizeImagesNode, XPathConstants.STRING), 100));
            resizeImages.setWidth(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.WIDTH, resizeImagesNode, XPathConstants.STRING), 150));

            String destination = (String)xPath.evaluate(ConfigElement.PATH_DESTINATION, resizeImagesNode, XPathConstants.STRING);

            if (StringUtil.isNotBlank(destination)) {
                resizeImages.setPathDestination(new File(destination));
            }

            resizeImages.setSelectedQualityIndex(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.SELECTED_QUALITY_INDEX, resizeImagesNode, XPathConstants.STRING), 0));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get resize images config", e);
        }
        return resizeImages;
    }

    public static void writeResizeImagesConfig(ResizeImages resizeImages, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  RESIZE IMAGES start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.RESIZE_IMAGES, baseIndent, xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.HEIGHT, Tab.FOUR, Integer.toString(resizeImages.getHeight()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.WIDTH, Tab.FOUR, Integer.toString(resizeImages.getWidth()), xmlsw);

        if (resizeImages.getPathDestination() != null) {
            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.PATH_DESTINATION, Tab.FOUR, resizeImages.getPathDestination().getAbsolutePath(), xmlsw);
        } else {
            XMLUtil.writeEmptyElementWithIndentAndLineBreak(ConfigElement.PATH_DESTINATION, xmlsw, Tab.FOUR);
        }

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.SELECTED_QUALITY_INDEX, Tab.FOUR, Integer.toString(resizeImages.getSelectedQualityIndex()), xmlsw);

        //  RESIZE IMAGES end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

}
