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

import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesCategories;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPaths;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPreview;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class TagImagesConfig {

    public static TagImages getTagImagesConfig(Node tagImagesNode) {
        TagImages tagImages = new TagImages();

        NodeList childNodes = tagImagesNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case CATEGORIES:
                tagImages.setCategories(getCategories(node));
                break;
            case PATHS:
                tagImages.setImagesPaths(getImagesPaths(node));
                break;
            case PREVIEW:
                tagImages.setPreview(getPreview(node));
                break;
            default:
                break;
            }
        }
        return tagImages;
    }

    private static TagImagesPreview getPreview(Node previewNode) {
        TagImagesPreview tagImagesPreview = new TagImagesPreview();

        NodeList childNodes = previewNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case USE_EMBEDDED_THUMBNAIL:
                tagImagesPreview.setUseEmbeddedThumbnail(Boolean.valueOf(node.getTextContent()));
                break;
            default:
                break;
            }
        }
        return tagImagesPreview;
    }

    private static TagImagesPaths getImagesPaths(Node pathsNode) {
        TagImagesPaths tagImagesPaths = new TagImagesPaths();

        NodeList childNodes = pathsNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case ADD_TO_REPOSITORY_POLICY:
                tagImagesPaths.setAddToRepositoryPolicy(StringUtil.getIntValue(node.getTextContent(), 1));
                break;
            default:
                break;
            }
        }
        return tagImagesPaths;
    }

    private static TagImagesCategories getCategories(Node categoriesNode) {
        TagImagesCategories tagImagesCategories = new TagImagesCategories();

        NodeList childNodes = categoriesNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case OR_RADIO_BUTTON_IS_SELECTED:
                tagImagesCategories.setOrRadioButtonIsSelected(Boolean.valueOf(node.getTextContent()));
                break;
            case WARN_WHEN_REMOVE:
                tagImagesCategories.setWarnWhenRemove(Boolean.valueOf(node.getTextContent()));
                break;
            case WARN_WHEN_REMOVE_WITH_SUB_CATEGORIES:
                tagImagesCategories.setWarnWhenRemoveWithSubCategories(Boolean.valueOf(node.getTextContent()));
                break;
            default:
                break;
            }
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
