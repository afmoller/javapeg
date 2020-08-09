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

import moller.javapeg.program.config.model.thumbnail.ThumbNail;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCache;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCreation;
import moller.javapeg.program.config.model.thumbnail.ThumbNailGrayFilter;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.jpeg.JPEGScaleAlgorithm;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class ThumbNailConfig {

    public static ThumbNail getThumbNailConfig(Node thumbNailNode) {
        ThumbNail thumbNail = new ThumbNail();

        NodeList childNodes = thumbNailNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case CACHE:
                thumbNail.setCache(getCache(node));
                break;
            case CREATION:
                thumbNail.setCreation(getCreation(node));
                break;
            case GRAYFILTER:
                thumbNail.setGrayFilter(getGrayFilter(node));
                break;
            default:
                break;
            }
        }
        return thumbNail;
    }

    private static ThumbNailCreation getCreation(Node creationNode) {
        ThumbNailCreation thumbNailCreation = new ThumbNailCreation();

        NodeList childNodes = creationNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case ALGORITHM:
                thumbNailCreation.setAlgorithm(JPEGScaleAlgorithm.valueOf(node.getTextContent()));
                break;
            case HEIGHT:
                thumbNailCreation.setHeight(StringUtil.getIntValue(node.getTextContent(), 120));
                break;
            case IF_MISSING_OR_CORRUPT:
                thumbNailCreation.setIfMissingOrCorrupt(Boolean.valueOf(node.getTextContent()));
                break;
            case WIDTH:
                thumbNailCreation.setWidth(StringUtil.getIntValue(node.getTextContent(), 160));
                break;
            default:
                break;
            }
        }
        return thumbNailCreation;
    }

    private static ThumbNailCache getCache(Node cacheNode) {
        ThumbNailCache thumbNailCache = new ThumbNailCache();

        NodeList childNodes = cacheNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case ENABLED:
                thumbNailCache.setEnabled(Boolean.valueOf(node.getTextContent()));
                break;
            case MAX_SIZE:
                thumbNailCache.setMaxSize(StringUtil.getIntValue(node.getTextContent(), 1000));
                break;
            default:
                break;
            }
        }
        return thumbNailCache;
    }

    private static ThumbNailGrayFilter getGrayFilter(Node grayfilterNode) {
        ThumbNailGrayFilter thumbNailGrayFilter = new ThumbNailGrayFilter();

        NodeList childNodes = grayfilterNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case PERCENTAGE:
                thumbNailGrayFilter.setPercentage(StringUtil.getIntValue(node.getTextContent(), 35));
                break;
            case PIXELS_BRIGHTENED:
                thumbNailGrayFilter.setPixelsBrightened(Boolean.parseBoolean(node.getTextContent()));
                break;
            default:
                break;
            }
        }
        return thumbNailGrayFilter;
    }

    public static void writeThumbNailConfig(ThumbNail thumbNail, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  THUMB NAIL start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.THUMBNAIL, baseIndent, xmlsw);

        writeCacheConfig(thumbNail.getCache(), Tab.FOUR, xmlsw);
        writeCreationConfig(thumbNail.getCreation(), Tab.FOUR, xmlsw);
        writeGrayFilterConfig(thumbNail.getGrayFilter(), Tab.FOUR, xmlsw);

        //  THUMB NAIL end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

    private static void writeCacheConfig(ThumbNailCache thumbNailCache, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  CACHE start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.CACHE, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.ENABLED, Tab.SIX, Boolean.toString(thumbNailCache.getEnabled()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.MAX_SIZE, Tab.SIX, Integer.toString(thumbNailCache.getMaxSize()), xmlsw);

        //  CACHE end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

    private static void writeCreationConfig(ThumbNailCreation thumbNailCreation, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  CREATION start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.CREATION, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.IF_MISSING_OR_CORRUPT, Tab.SIX, Boolean.toString(thumbNailCreation.getIfMissingOrCorrupt()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.ALGORITHM, Tab.SIX, thumbNailCreation.getAlgorithm().name(), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.WIDTH, Tab.SIX, Integer.toString(thumbNailCreation.getWidth()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.HEIGHT, Tab.SIX, Integer.toString(thumbNailCreation.getHeight()), xmlsw);

        //  CREATION end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

    private static void writeGrayFilterConfig(ThumbNailGrayFilter thumbNailGrayFilter, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  GRAYFILTER start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.GRAYFILTER, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.PERCENTAGE, Tab.SIX, Integer.toString(thumbNailGrayFilter.getPercentage()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.PIXELS_BRIGHTENED, Tab.SIX, Boolean.toString(thumbNailGrayFilter.isPixelsBrightened()), xmlsw);

        //  GRAYFILTER end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
