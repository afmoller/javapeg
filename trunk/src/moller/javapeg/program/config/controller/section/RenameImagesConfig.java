package moller.javapeg.program.config.controller.section;

import java.io.File;
import java.text.SimpleDateFormat;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.util.string.StringUtil;

import org.w3c.dom.Node;

public class RenameImagesConfig {

    public static RenameImages getRenameImagesConfig(Node renameImagesNode, XPath xPath) {
        RenameImages renameImages = new RenameImages();

        try {
            renameImages.setCameraModelNameMaximumLength(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.CAMERA_MODEL_NAME_MAXIMUM_LENGTH, renameImagesNode, XPathConstants.STRING), 0));
            renameImages.setCreateThumbNails(Boolean.valueOf((String)xPath.evaluate(ConfigElement.CREATE_THUMBNAILS, renameImagesNode, XPathConstants.STRING)));
            renameImages.setPathDestination(new File((String)xPath.evaluate(ConfigElement.PATH_DESTINATION, renameImagesNode, XPathConstants.STRING)));
            renameImages.setPathSource(new File((String)xPath.evaluate(ConfigElement.PATH_SOURCE, renameImagesNode, XPathConstants.STRING)));
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
}
