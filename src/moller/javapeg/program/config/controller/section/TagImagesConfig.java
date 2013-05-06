package moller.javapeg.program.config.controller.section;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesCategories;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPaths;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPreview;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;

public class TagImagesConfig {

    public static TagImages getTagImagesConfig(Node tagImagesNode, XPath xPath) {
        TagImages tagImages = new TagImages();

        try {
            tagImages.setCategories(getCategories((Node)xPath.evaluate(ConfigElement.CATEGORIES, tagImagesNode, XPathConstants.NODE), xPath));
            tagImages.setImagesPaths(getImagesPaths((Node)xPath.evaluate(ConfigElement.PATHS, tagImagesNode, XPathConstants.NODE), xPath));
            tagImages.setPreview(getPreview((Node)xPath.evaluate(ConfigElement.PREVIEW, tagImagesNode, XPathConstants.NODE), xPath));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get tag images config", e);
        }
        return tagImages;
    }

    private static TagImagesPreview getPreview(Node previewNode, XPath xPath) {
        TagImagesPreview tagImagesPreview = new TagImagesPreview();

        try {
            tagImagesPreview.setUseEmbeddedThumbnail(Boolean.valueOf((String)xPath.evaluate(ConfigElement.USE_EMBEDDED_THUMBNAIL, previewNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get tag images preview", e);
        }
        return tagImagesPreview;
    }

    private static TagImagesPaths getImagesPaths(Node pathsNode, XPath xPath) {
        TagImagesPaths tagImagesPaths = new TagImagesPaths();

        try {
            tagImagesPaths.setAddToRepositoryPolicy(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.ADD_TO_REPOSITORY_POLICY, pathsNode, XPathConstants.STRING), 1));
            tagImagesPaths.setAutomaticallyRemoveNonExistingImagePath(Boolean.valueOf((String)xPath.evaluate(ConfigElement.AUTOMATICALLY_REMOVE_NON_EXISTING_IMAGE_PATH, pathsNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get image paths", e);
        }
        return tagImagesPaths;
    }

    private static TagImagesCategories getCategories(Node categoriesNode, XPath xPath) {
        TagImagesCategories tagImagesCategories = new TagImagesCategories();

        try {
            tagImagesCategories.setOrRadioButtonIsSelected(Boolean.valueOf((String)xPath.evaluate(ConfigElement.OR_RADIO_BUTTON_IS_SELECTED, categoriesNode, XPathConstants.STRING)));
            tagImagesCategories.setWarnWhenRemove(Boolean.valueOf((String)xPath.evaluate(ConfigElement.WARN_WHEN_REMOVE, categoriesNode, XPathConstants.STRING)));
            tagImagesCategories.setWarnWhenRemoveWithSubCategories(Boolean.valueOf((String)xPath.evaluate(ConfigElement.WARN_WHEN_REMOVE_WITH_SUB_CATEGORIES, categoriesNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get categories", e);
        }
        return tagImagesCategories;
    }

    public static void writeTagImagesConfig(TagImages tagImages, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  TAG IMAGES start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.TAG_IMAGES, baseIndent, xmlsw);

        writeCategoriesConfig(tagImages.getCategories(), Tab.FOUR, xmlsw);
        writeImagePathsConfig(tagImages.getImagesPaths(), Tab.FOUR, xmlsw);
        writeImagePathsConfig(tagImages.getPreview(), Tab.FOUR, xmlsw);

        //  TAG IMAGES end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

    private static void writeCategoriesConfig(TagImagesCategories tagImagesCategories, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  CATEGORIES start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.CATEGORIES, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.WARN_WHEN_REMOVE, Tab.SIX, Boolean.toString(tagImagesCategories.getWarnWhenRemove()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.WARN_WHEN_REMOVE_WITH_SUB_CATEGORIES, Tab.SIX, Boolean.toString(tagImagesCategories.getWarnWhenRemoveWithSubCategories()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.OR_RADIO_BUTTON_IS_SELECTED, Tab.SIX, Boolean.toString(tagImagesCategories.getOrRadioButtonIsSelected()), xmlsw);

        //  CATEGORIES end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

    private static void writeImagePathsConfig(TagImagesPaths tagImagesPaths, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  PATHS start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.PATHS, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.AUTOMATICALLY_REMOVE_NON_EXISTING_IMAGE_PATH, Tab.SIX, Boolean.toString(tagImagesPaths.getAutomaticallyRemoveNonExistingImagePath()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.ADD_TO_REPOSITORY_POLICY, Tab.SIX, Integer.toString(tagImagesPaths.getAddToRepositoryPolicy()), xmlsw);

        //  PATHS end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

    private static void writeImagePathsConfig(TagImagesPreview tagImagesPreview, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  PREVIEW start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.PREVIEW, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.USE_EMBEDDED_THUMBNAIL, Tab.SIX, Boolean.toString(tagImagesPreview.getUseEmbeddedThumbnail()), xmlsw);

        //  PREVIEW end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
