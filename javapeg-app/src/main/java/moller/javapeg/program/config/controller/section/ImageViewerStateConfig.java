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

import moller.javapeg.program.config.model.ImageViewerState;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;
import org.imgscalr.Scalr.Method;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class ImageViewerStateConfig {

    public static ImageViewerState getImageViewerStateConfig(Node imageViewerStateNode) {
        ImageViewerState imageViewerState = new ImageViewerState();

        NodeList childNodes = imageViewerStateNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case AUTOMATICALLY_RESIZE_IMAGES:
                imageViewerState.setAutomaticallyResizeImages(Boolean.parseBoolean(node.getTextContent()));
                break;
            case AUTOMATICALLY_ROTATE_IMAGES:
                imageViewerState.setAutomaticallyRotateImages(Boolean.parseBoolean(node.getTextContent()));
                break;
            case SHOW_NAVIGATION_IMAGE:
                imageViewerState.setShowNavigationImage(Boolean.parseBoolean(node.getTextContent()));
                break;
            case RESIZE_QUALITY:
                imageViewerState.setResizeQuality(getMethodFromResizeQualityString(node.getTextContent()));
                break;
            case SLIDE_SHOW_DELAY_IN_SECONDS:
                imageViewerState.setSlideShowDelay(Integer.parseInt(node.getTextContent()));
                break;
            default:
                break;
            }
        }
        return imageViewerState;
    }

    public static void writeImageViewerStateConfig(ImageViewerState imageViewerState, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  IMAGE VIEWER STATE start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.IMAGE_VIEWER_STATE, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.AUTOMATICALLY_RESIZE_IMAGES, Tab.FOUR, Boolean.toString(imageViewerState.isAutomaticallyResizeImages()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.AUTOMATICALLY_ROTATE_IMAGES, Tab.FOUR, Boolean.toString(imageViewerState.isAutomaticallyRotateImages()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.SHOW_NAVIGATION_IMAGE, Tab.FOUR, Boolean.toString(imageViewerState.isShowNavigationImage()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.RESIZE_QUALITY, Tab.FOUR, imageViewerState.getResizeQuality().name(), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.SLIDE_SHOW_DELAY_IN_SECONDS, Tab.FOUR, Integer.toString(imageViewerState.getSlideShowDelayInSeconds()), xmlsw);

        //  IMAGE VIEWER STATE end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

    private static Method getMethodFromResizeQualityString(String resizeQualityString) {
        return Method.valueOf(resizeQualityString);
    }
}
