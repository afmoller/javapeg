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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RenameImagesConfig {

    public static RenameImages getRenameImagesConfig(Node renameImagesNode, XPath xPath) {
        RenameImages renameImages = new RenameImages();

        try {
            renameImages.setCameraModelNameMaximumLength(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.CAMERA_MODEL_NAME_MAXIMUM_LENGTH, renameImagesNode, XPathConstants.STRING), 0));
            renameImages.setCreateThumbNails(Boolean.valueOf((String)xPath.evaluate(ConfigElement.CREATE_THUMBNAILS, renameImagesNode, XPathConstants.STRING)));

            String destination = (String)xPath.evaluate(ConfigElement.PATH_DESTINATION, renameImagesNode, XPathConstants.STRING);

            if (StringUtil.isNotBlank(destination)) {
                renameImages.setPathDestination(new File(destination));
            }

            String source = (String)xPath.evaluate(ConfigElement.PATH_SOURCE, renameImagesNode, XPathConstants.STRING);

            if (StringUtil.isNotBlank(destination)) {
                renameImages.setPathSource(new File(source));
            }

            renameImages.setProgressLogTimestampFormat(new SimpleDateFormat((String)xPath.evaluate(ConfigElement.PROGRESS_LOG_TIMESTAMP_FORMAT, renameImagesNode, XPathConstants.STRING)));
            renameImages.setTemplateFileName((String)xPath.evaluate(ConfigElement.TEMPLATE_FILE_NAME, renameImagesNode, XPathConstants.STRING));
            renameImages.setTemplateSubDirectoryName((String)xPath.evaluate(ConfigElement.TEMPLATE_SUB_DIRECTORY_NAME, renameImagesNode, XPathConstants.STRING));
            renameImages.setTemplateFileNameNames(getTemplates((Node)xPath.evaluate(ConfigElement.TEMPLATES_FILE_NAME, renameImagesNode, XPathConstants.NODE), xPath));
            renameImages.setTemplateSubDirectoryNames(getTemplates((Node)xPath.evaluate(ConfigElement.TEMPLATES_SUB_DIRECTORY_NAME, renameImagesNode, XPathConstants.NODE), xPath));
            renameImages.setUseLastModifiedDate(Boolean.valueOf((String)xPath.evaluate(ConfigElement.USE_LAST_MODIFIED_DATE, renameImagesNode, XPathConstants.STRING)));
            renameImages.setUseLastModifiedTime(Boolean.valueOf((String)xPath.evaluate(ConfigElement.USE_LAST_MODIFIED_TIME, renameImagesNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get rename images config", e);
        }
        return renameImages;
    }

    private static Set<String> getTemplates(Node renameImagesNode, XPath xPath) {
        Set<String> templates = new TreeSet<String>();

        try {
            NodeList templateNodeList = (NodeList)xPath.evaluate(ConfigElement.TEMPLATE, renameImagesNode, XPathConstants.NODESET);

            for (int index = 0; index < templateNodeList.getLength(); index++) {
                String template = templateNodeList.item(index).getTextContent();

                if (StringUtil.isNotBlank(template)) {
                    templates.add(template);
                }
            }
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get templates", e);
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

    private static void writeTemplates(Set<String> templates, String configElement, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        // TEMPLATE SECTION START
        XMLUtil.writeElementStartWithLineBreak(configElement, baseIndent, xmlsw);

        for (String template : templates) {
            XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.TEMPLATE, Tab.SIX, template, xmlsw);
        }

        // TEMPLATE SECTION END
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
