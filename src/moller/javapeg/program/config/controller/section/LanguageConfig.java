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

import moller.javapeg.program.config.model.Language;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class LanguageConfig {

    public static Language getLanguageConfig(Node languageNode) {
        Language language = new Language();

        NodeList childNodes = languageNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            switch (ConfigElement.getEnum(node.getNodeName())) {
            case AUTOMATIC_SELECTION:
                language.setAutomaticSelection(Boolean.valueOf(node.getTextContent()));
                break;
            case GUI_LANGUAGE_ISO_6391:
                language.setgUILanguageISO6391(node.getTextContent());
                break;
            default:
                break;
            }
        }
        return language;
    }

    public static void writeLanguageConfig(Language language, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  LANGUAGE start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.LANGUAGE, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.AUTOMATIC_SELECTION, Tab.FOUR, Boolean.toString(language.getAutomaticSelection()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.GUI_LANGUAGE_ISO_6391, Tab.FOUR, language.getgUILanguageISO6391(), xmlsw);

        //  LANGUAGE end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}
