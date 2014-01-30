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

import java.text.SimpleDateFormat;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.Logging;
import moller.javapeg.program.enumerations.Level;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;

public class LoggingConfig {

    /**
     * @param loggingNode
     * @param xPath
     * @return
     */
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
            throw new RuntimeException("Could not get logging config", e);
        }
        return logging;
    }

    /**
     * @param logging
     * @param baseIndent
     * @param xmlsw
     * @throws XMLStreamException
     */
    public static void writeLoggingConfig(Logging logging, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.DEVELOPER_MODE, baseIndent, Boolean.toString(logging.getDeveloperMode()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.FILE_NAME, baseIndent, logging.getFileName(), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.LEVEL, baseIndent, logging.getLevel().toString(), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.ROTATE, baseIndent, Boolean.toString(logging.getRotate()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.ROTATE_SIZE, baseIndent, Long.toString(logging.getRotateSize()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.ROTATE_ZIP, baseIndent, Boolean.toString(logging.getRotateZip()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.TIMESTAMP_FORMAT, baseIndent, logging.getTimeStampFormat().toPattern(), xmlsw);
    }
}
