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

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.UpdatesChecker;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UpdatesCheckerConfig {

    public static UpdatesChecker getUpdatesCheckerConfig(Node updatesCheckerNode) {
        UpdatesChecker updatesChecker = new UpdatesChecker();

        NodeList childNodes = updatesCheckerNode.getChildNodes();

        try {
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);

                switch (node.getNodeName()) {
                case ConfigElement.ATTACH_VERSION_INFORMATION:
                    updatesChecker.setAttachVersionInformation(Boolean.valueOf(node.getTextContent()));
                    break;
                case ConfigElement.ENABLED:
                    updatesChecker.setEnabled(Boolean.valueOf(node.getTextContent()));
                    break;
                case ConfigElement.TIMEOUT:
                    updatesChecker.setTimeOut(StringUtil.getIntValue(node.getTextContent(), 60));
                    break;
                case ConfigElement.URL_VERSION:
                    updatesChecker.setUrlVersion(new URL(node.getTextContent()));
                    break;
                case ConfigElement.URL_VERSION_INFORMATION:
                    updatesChecker.setUrlVersionInformation(new URL(node.getTextContent()));
                    break;
                default:
                    break;
                }
            }
        } catch (MalformedURLException mURLE) {
            throw new RuntimeException("Could not get updates checker config", mURLE);
        }
        return updatesChecker;
    }

    public static void writeUpdatesCheckerConfig(UpdatesChecker updatesChecker, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  UPDATES CHECKER start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.UPDATES_CHECKER, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.ENABLED, Tab.FOUR, Boolean.toString(updatesChecker.isEnabled()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.ATTACH_VERSION_INFORMATION, Tab.FOUR, Boolean.toString(updatesChecker.getAttachVersionInformation()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.TIMEOUT, Tab.FOUR, Integer.toString(updatesChecker.getTimeOut()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.URL_VERSION, Tab.FOUR, updatesChecker.getUrlVersion().toString(), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.URL_VERSION_INFORMATION, Tab.FOUR, updatesChecker.getUrlVersionInformation().toString(), xmlsw);

        //  UPDATES CHECKER end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
