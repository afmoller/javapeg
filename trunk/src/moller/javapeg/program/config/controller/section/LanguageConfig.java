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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.Language;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;

public class LanguageConfig {

    public static Language getLanguageConfig(Node languageNode, XPath xPath) {
        Language language = new Language();

        try {
            language.setAutomaticSelection(Boolean.valueOf((String)xPath.evaluate(ConfigElement.AUTOMATIC_SELECTION, languageNode, XPathConstants.STRING)));
            language.setgUILanguageISO6391((String)xPath.evaluate(ConfigElement.GUI_LANGUAGE_ISO_6391, languageNode, XPathConstants.STRING));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not get language config", e);
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
