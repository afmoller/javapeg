package moller.javapeg.program.config.controller.section;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.thumbnail.ThumbNail;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCache;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCreation;
import moller.util.jpeg.JPEGScaleAlgorithm;
import moller.util.string.StringUtil;

import org.w3c.dom.Node;

public class ThumbNailConfig {

    public static ThumbNail getThumbNailConfig(Node thumbNailNode, XPath xPath) {
        ThumbNail thumbNail = new ThumbNail();

        try {
            thumbNail.setCache(getCache((Node)xPath.evaluate(ConfigElement.CACHE, thumbNailNode, XPathConstants.NODE), xPath));
            thumbNail.setCreation(getCreation((Node)xPath.evaluate(ConfigElement.CREATION, thumbNailNode, XPathConstants.NODE), xPath));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return thumbNailCreation;
    }

    private static ThumbNailCache getCache(Node cacheNode, XPath xPath) {
        ThumbNailCache thumbNailCache = new ThumbNailCache();

        try {
            thumbNailCache.setEnabled(Boolean.valueOf((String)xPath.evaluate(ConfigElement.ENABLED, cacheNode, XPathConstants.STRING)));
            thumbNailCache.setMaxSize(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.MAX_SIZE, cacheNode, XPathConstants.STRING), 1000));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return thumbNailCache;
    }
}
