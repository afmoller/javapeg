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
import moller.javapeg.program.config.model.thumbnail.ThumbNail;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCache;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCreation;
import moller.javapeg.program.config.model.thumbnail.ThumbNailGrayFilter;
import moller.util.jpeg.JPEGScaleAlgorithm;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;

public class ThumbNailConfig {

    public static ThumbNail getThumbNailConfig(Node thumbNailNode, XPath xPath) {
        ThumbNail thumbNail = new ThumbNail();

        try {
            thumbNail.setCache(getCache((Node)xPath.evaluate(ConfigElement.CACHE, thumbNailNode, XPathConstants.NODE), xPath));
            thumbNail.setCreation(getCreation((Node)xPath.evaluate(ConfigElement.CREATION, thumbNailNode, XPathConstants.NODE), xPath));
            thumbNail.setGrayFilter(getGrayFilter((Node)xPath.evaluate(ConfigElement.GRAYFILTER, thumbNailNode, XPathConstants.NODE), xPath));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get thumbnail config", e);
        }
        return thumbNail;
    }

    private static ThumbNailCreation getCreation(Node creationNode, XPath xPath) {
        ThumbNailCreation thumbNailCreation = new ThumbNailCreation();

        try {
            thumbNailCreation.setAlgorithm(JPEGScaleAlgorithm.valueOf((String)xPath.evaluate(ConfigElement.ALGORITHM, creationNode, XPathConstants.STRING)));
            thumbNailCreation.setHeight(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.HEIGHT, creationNode, XPathConstants.STRING), 120));
            thumbNailCreation.setIfMissingOrCorrupt(Boolean.valueOf((String)xPath.evaluate(ConfigElement.IF_MISSING_OR_CORRUPT, creationNode, XPathConstants.STRING)));
            thumbNailCreation.setWidth(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.WIDTH, creationNode, XPathConstants.STRING), 160));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get creation", e);
        }
        return thumbNailCreation;
    }

    private static ThumbNailCache getCache(Node cacheNode, XPath xPath) {
        ThumbNailCache thumbNailCache = new ThumbNailCache();

        try {
            thumbNailCache.setEnabled(Boolean.valueOf((String)xPath.evaluate(ConfigElement.ENABLED, cacheNode, XPathConstants.STRING)));
            thumbNailCache.setMaxSize(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.MAX_SIZE, cacheNode, XPathConstants.STRING), 1000));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get cache", e);
        }
        return thumbNailCache;
    }

    private static ThumbNailGrayFilter getGrayFilter(Node grayfilterNode, XPath xPath) {
        ThumbNailGrayFilter thumbNailGrayFilter = new ThumbNailGrayFilter();

        try {
            thumbNailGrayFilter.setPercentage(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.PERCENTAGE, grayfilterNode, XPathConstants.STRING), 35));
            thumbNailGrayFilter.setPixelsBrightened(Boolean.valueOf((String)xPath.evaluate(ConfigElement.PIXELS_BRIGHTENED, grayfilterNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get grayfilter", e);
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
