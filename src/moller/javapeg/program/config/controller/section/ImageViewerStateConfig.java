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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.ImageViewerState;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;

public class ImageViewerStateConfig {

    public static ImageViewerState getImageViewerStateConfig(Node imageViewerStateNode, XPath xPath) {
        ImageViewerState imageViewerState = new ImageViewerState();

        try {
            imageViewerState.setAutomaticallyResizeImages(Boolean.valueOf((String)xPath.evaluate(ConfigElement.AUTOMATICALLY_RESIZE_IMAGES, imageViewerStateNode, XPathConstants.STRING)));
            imageViewerState.setAutomaticallyRotateImages(Boolean.valueOf((String)xPath.evaluate(ConfigElement.AUTOMATICALLY_ROTATE_IMAGES, imageViewerStateNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get image viewer state config", e);
        }
        return imageViewerState;
    }

    public static void writeImageViewerStateConfig(ImageViewerState imageViewerState, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  IMAGE VIEWER STATE start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.IMAGE_VIEWER_STATE, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.AUTOMATICALLY_RESIZE_IMAGES, Tab.FOUR, Boolean.toString(imageViewerState.isAutomaticallyResizeImages()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.AUTOMATICALLY_ROTATE_IMAGES, Tab.FOUR, Boolean.toString(imageViewerState.isAutomaticallyRotateImages()), xmlsw);

        //  IMAGE VIEWER STATE end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
