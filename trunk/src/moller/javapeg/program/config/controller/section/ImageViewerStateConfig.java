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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.ImageViewerState;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.imgscalr.Scalr.Method;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ImageViewerStateConfig {

    public static ImageViewerState getImageViewerStateConfig(Node imageViewerStateNode) {
        ImageViewerState imageViewerState = new ImageViewerState();

        NodeList childNodes = imageViewerStateNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (node.getNodeName()) {
            case ConfigElement.AUTOMATICALLY_RESIZE_IMAGES:
                imageViewerState.setAutomaticallyResizeImages(Boolean.valueOf(node.getTextContent()));
                break;
            case ConfigElement.AUTOMATICALLY_ROTATE_IMAGES:
                imageViewerState.setAutomaticallyRotateImages(Boolean.valueOf(node.getTextContent()));
                break;
            case ConfigElement.SHOW_NAVIGATION_IMAGE:
                imageViewerState.setShowNavigationImage(Boolean.valueOf(node.getTextContent()));
                break;
            case ConfigElement.RESIZE_QUALITY:
                imageViewerState.setResizeQuality(getMethodFromResizeQualityString(node.getTextContent()));
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

        //  IMAGE VIEWER STATE end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

    private static Method getMethodFromResizeQualityString(String resizeQualityString) {
        Method valueOf = Method.valueOf(resizeQualityString);
        return valueOf;
    }
}
