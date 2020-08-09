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

import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.TreeSet;

public class RenameImagesConfig {

    public static RenameImages getRenameImagesConfig(Node renameImagesNode) {
        RenameImages renameImages = new RenameImages();

        NodeList childNodes = renameImagesNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case CAMERA_MODEL_NAME_MAXIMUM_LENGTH:
                renameImages.setCameraModelNameMaximumLength(StringUtil.getIntValue(node.getTextContent(), 0));
                break;
            case CREATE_THUMBNAILS:
                renameImages.setCreateThumbNails(Boolean.valueOf(node.getTextContent()));
                break;
            case PATH_DESTINATION:
                String destination = node.getTextContent();

                if (StringUtil.isNotBlank(destination)) {
                    renameImages.setPathDestination(new File(destination));
                }
                break;
            case PATH_SOURCE:
                String source = node.getTextContent();

                if (StringUtil.isNotBlank(source)) {
                    renameImages.setPathSource(new File(source));
                }
                break;
            case PROGRESS_LOG_TIMESTAMP_FORMAT:
                renameImages.setProgressLogTimestampFormat(new SimpleDateFormat(node.getTextContent()));
                break;
            case TEMPLATE_FILE_NAME:
                renameImages.setTemplateFileName(node.getTextContent());
                break;
            case TEMPLATE_SUB_DIRECTORY_NAME:
                renameImages.setTemplateSubDirectoryName(node.getTextContent());
                break;
            case TEMPLATES_FILE_NAME:
                renameImages.setTemplateFileNameNames(getTemplates(node));
                break;
            case TEMPLATES_SUB_DIRECTORY_NAME:
                renameImages.setTemplateSubDirectoryNames(getTemplates(node));
                break;
            case USE_LAST_MODIFIED_DATE:
                renameImages.setUseLastModifiedDate(Boolean.valueOf(node.getTextContent()));
                break;
            case USE_LAST_MODIFIED_TIME:
                renameImages.setUseLastModifiedTime(Boolean.valueOf(node.getTextContent()));
                break;
            default:
                break;
            }
        }
        return renameImages;
    }

    private static Set<String> getTemplates(Node templatesNode) {
        Set<String> templates = new TreeSet<>();

        NodeList childNodes = templatesNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case TEMPLATE:
                String template = node.getTextContent();
                if (StringUtil.isNotBlank(template)) {
                    templates.add(template);
                }
                break;
            default:
                break;
            }
        }
        return templates;
    }

    public static void writeRenameImagesConfig(RenameImages renameImages, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  RENAME IMAGES start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.RENAME_IMAGES, baseIndent, xmlsw);

        if (renameImages.getPathSource() != null) {
            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.PATH_SOURCE, Tab.FOUR, renameImages.getPathSource().getAbsolutePath(), xmlsw);
        } else {
            XMLUtil.writeEmptyElementWithIndentAndLineBreak(ConfigElement.PATH_SOURCE, xmlsw, Tab.FOUR);
        }
        if (renameImages.getPathDestination() != null) {
            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.PATH_DESTINATION, Tab.FOUR, renameImages.getPathDestination().getAbsolutePath(), xmlsw);
        } else {
            XMLUtil.writeEmptyElementWithIndentAndLineBreak(ConfigElement.PATH_DESTINATION, xmlsw, Tab.FOUR);
        }

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.TEMPLATE_SUB_DIRECTORY_NAME, Tab.FOUR, renameImages.getTemplateSubDirectoryName(), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.TEMPLATE_FILE_NAME, Tab.FOUR, renameImages.getTemplateFileName(), xmlsw);

        // Write File Name Templates section
        writeTemplates(renameImages.getTemplateFileNameNames(), ConfigElement.TEMPLATES_FILE_NAME, Tab.FOUR, xmlsw);

        // Write Sub Directory Name Templates section
        writeTemplates(renameImages.getTemplateSubDirectoryNames(), ConfigElement.TEMPLATES_SUB_DIRECTORY_NAME, Tab.FOUR, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.CREATE_THUMBNAILS, Tab.FOUR, Boolean.toString(renameImages.getCreateThumbNails()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.USE_LAST_MODIFIED_DATE, Tab.FOUR, Boolean.toString(renameImages.getUseLastModifiedDate()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.USE_LAST_MODIFIED_TIME, Tab.FOUR, Boolean.toString(renameImages.getUseLastModifiedTime()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.CAMERA_MODEL_NAME_MAXIMUM_LENGTH, Tab.FOUR, Integer.toString(renameImages.getCameraModelNameMaximumLength()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.PROGRESS_LOG_TIMESTAMP_FORMAT, Tab.FOUR, renameImages.getProgressLogTimestampFormat().toPattern(), xmlsw);

        //  RENAME IMAGES end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }

    private static void writeTemplates(Set<String> templates, ConfigElement configElement, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        // TEMPLATE SECTION START
        XMLUtil.writeElementStartWithLineBreak(configElement, baseIndent, xmlsw);

        for (String template : templates) {
            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.TEMPLATE, Tab.SIX, template, xmlsw);
        }

        // TEMPLATE SECTION END
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
