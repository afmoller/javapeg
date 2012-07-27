package moller.javapeg.program.config.controller.section;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.Language;

import org.w3c.dom.Node;

public class LanguageConfig {

    public static Language getLanguageConfig(Node languageNode, XPath xPath) {
        Language language = new Language();

        try {
            language.setAutomaticSelection(Boolean.valueOf((String)xPath.evaluate(ConfigElement.AUTOMATIC_SELECTION, languageNode, XPathConstants.STRING)));
            language.setgUILanguageISO6391((String)xPath.evaluate(ConfigElement.GUI_LANGUAGE_ISO_6391, languageNode, XPathConstants.STRING));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return language;
    }
}
