package moller.javapeg.program.config.controller.section;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesCategories;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPaths;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPreview;
import moller.util.string.StringUtil;

import org.w3c.dom.Node;

public class TagImagesConfig {

    public static TagImages getTagImagesConfig(Node tagImagesNode, XPath xPath) {
        TagImages tagImages = new TagImages();

        try {
            tagImages.setCategories(getCategories((Node)xPath.evaluate(ConfigElement.CATEGORIES, tagImagesNode, XPathConstants.NODE), xPath));
            tagImages.setImagesPaths(getImagesPaths((Node)xPath.evaluate(ConfigElement.PATHS, tagImagesNode, XPathConstants.NODE), xPath));
            tagImages.setPreview(getPreview((Node)xPath.evaluate(ConfigElement.PREVIEW, tagImagesNode, XPathConstants.NODE), xPath));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tagImages;
    }

    private static TagImagesPreview getPreview(Node previewNode, XPath xPath) {
        TagImagesPreview tagImagesPreview = new TagImagesPreview();

        try {
            tagImagesPreview.setUseEmbeddedThumbnail(Boolean.valueOf((String)xPath.evaluate(ConfigElement.USE_EMBEDDED_THUMBNAIL, previewNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tagImagesPreview;
    }

    private static TagImagesPaths getImagesPaths(Node pathsNode, XPath xPath) {
        TagImagesPaths tagImagesPaths = new TagImagesPaths();

        try {
            tagImagesPaths.setAddToRepositoryPolicy(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.ADD_TO_REPOSITORY_POLICY, pathsNode, XPathConstants.STRING), 1));
            tagImagesPaths.setAutomaticallyRemoveNonExistingImagePath(Boolean.valueOf((String)xPath.evaluate(ConfigElement.AUTOMATICALLY_REMOVE_NON_EXISTING_IMAGE_PATH, pathsNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tagImagesCategories;
    }
}
