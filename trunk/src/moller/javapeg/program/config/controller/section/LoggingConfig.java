package moller.javapeg.program.config.controller.section;

import java.text.SimpleDateFormat;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.Logging;
import moller.javapeg.program.enumerations.Level;

import org.w3c.dom.Node;

public class LoggingConfig {

    public static Logging getLoggingConfig(Node loggingNode, XPath xPath) {
        Logging logging = new Logging();

        try {
            logging.setFileName((String)xPath.evaluate(ConfigElement.FILE_NAME, loggingNode, XPathConstants.STRING));
            logging.setDeveloperMode(Boolean.valueOf((String)xPath.evaluate(ConfigElement.DEVELOPER_MODE, loggingNode, XPathConstants.STRING)));
            logging.setLevel(Level.valueOf((String)xPath.evaluate(ConfigElement.LEVEL, loggingNode, XPathConstants.STRING)));
            logging.setRotate(Boolean.valueOf((String)xPath.evaluate(ConfigElement.ROTATE, loggingNode, XPathConstants.STRING)));
            logging.setRotateSize(new Long((String)xPath.evaluate(ConfigElement.ROTATE_SIZE, loggingNode, XPathConstants.STRING)));
            logging.setRotateZip(Boolean.valueOf((String)xPath.evaluate(ConfigElement.ROTATE_ZIP, loggingNode, XPathConstants.STRING)));
            logging.setTimeStampFormat(new SimpleDateFormat((String)xPath.evaluate(ConfigElement.TIMESTAMP_FORMAT, loggingNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return logging;
    }
}
