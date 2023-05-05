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

import moller.javapeg.program.config.model.applicationmode.resize.ResizeImages;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;

public class ResizeImagesConfig {

    public static ResizeImages getResizeImagesConfig(Node resizeImagesNode) {
        ResizeImages resizeImages = new ResizeImages();

        NodeList childNodes = resizeImagesNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case HEIGHT:
                resizeImages.setHeight(StringUtil.getIntValue(node.getTextContent(), 100));
                break;
            case WIDTH:
                resizeImages.setWidth(StringUtil.getIntValue(node.getTextContent(), 150));
                break;
            case PATH_DESTINATION:
                String destination = node.getTextContent();
                if (StringUtil.isNotBlank(destination)) {
                    resizeImages.setPathDestination(new File(destination));
                }
                break;
            case SELECTED_QUALITY_INDEX:
                resizeImages.setSelectedQualityIndex(StringUtil.getIntValue(node.getTextContent(), 0));
                break;
            default:
                break;
            }
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
