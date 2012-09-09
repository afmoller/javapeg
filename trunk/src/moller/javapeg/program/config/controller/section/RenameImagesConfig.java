package moller.javapeg.program.config.controller.section;

import java.io.File;
import java.text.SimpleDateFormat;

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
            renameImages.setUseLastModifiedDate(Boolean.valueOf((String)xPath.evaluate(ConfigElement.USE_LAST_MODIFIED_DATE, renameImagesNode, XPathConstants.STRING)));
            renameImages.setUseLastModifiedTime(Boolean.valueOf((String)xPath.evaluate(ConfigElement.USE_LAST_MODIFIED_TIME, renameImagesNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return renameImages;
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
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.CREATE_THUMBNAILS, Tab.FOUR, Boolean.toString(renameImages.getCreateThumbNails()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.USE_LAST_MODIFIED_DATE, Tab.FOUR, Boolean.toString(renameImages.getUseLastModifiedDate()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.USE_LAST_MODIFIED_TIME, Tab.FOUR, Boolean.toString(renameImages.getUseLastModifiedTime()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.CAMERA_MODEL_NAME_MAXIMUM_LENGTH, Tab.FOUR, Integer.toString(renameImages.getCameraModelNameMaximumLength()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.PROGRESS_LOG_TIMESTAMP_FORMAT, Tab.FOUR, renameImages.getProgressLogTimestampFormat().toPattern(), xmlsw);

        //  RENAME IMAGES end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
