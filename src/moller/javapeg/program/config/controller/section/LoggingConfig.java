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

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.Logging;
import moller.javapeg.program.enumerations.Level;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoggingConfig {

    /**
     * @param loggingNode
     * @return
     */
    public static Logging getLoggingConfig(Node loggingNode) {
        Logging logging = new Logging();

        NodeList childNodes = loggingNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (node.getNodeName()) {
            case ConfigElement.FILE_NAME:
                logging.setFileName(node.getTextContent());
                break;
            case ConfigElement.DEVELOPER_MODE:
                logging.setDeveloperMode(Boolean.valueOf(node.getTextContent()));
                break;
            case ConfigElement.LEVEL:
                logging.setLevel(Level.valueOf(node.getTextContent()));
                break;
            case ConfigElement.ROTATE:
                logging.setRotate(Boolean.valueOf(node.getTextContent()));
                break;
            case ConfigElement.ROTATE_SIZE:
                logging.setRotateSize(Long.valueOf(node.getTextContent()));
                break;
            case ConfigElement.ROTATE_ZIP:
                logging.setRotateZip(Boolean.valueOf(node.getTextContent()));
                break;
            case ConfigElement.TIMESTAMP_FORMAT:
                logging.setTimeStampFormat(new SimpleDateFormat(node.getTextContent()));
                break;
            default:
                break;
            }
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
