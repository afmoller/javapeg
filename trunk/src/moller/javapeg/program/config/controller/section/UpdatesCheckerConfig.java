package moller.javapeg.program.config.controller.section;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.UpdatesChecker;
import moller.util.string.StringUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Node;

public class UpdatesCheckerConfig {

    public static UpdatesChecker getUpdatesCheckerConfig(Node updatesCheckerNode, XPath xPath) {
        UpdatesChecker updatesChecker = new UpdatesChecker();

        try {
            updatesChecker.setAttachVersionInformation(Boolean.valueOf((String)xPath.evaluate(ConfigElement.ATTACH_VERSION_INFORMATION, updatesCheckerNode, XPathConstants.STRING)));
            updatesChecker.setEnabled(Boolean.valueOf((String)xPath.evaluate(ConfigElement.ENABLED, updatesCheckerNode, XPathConstants.STRING)));
            updatesChecker.setTimeOut(StringUtil.getIntValue((String)xPath.evaluate(ConfigElement.TIMEOUT, updatesCheckerNode, XPathConstants.STRING), 60));
            updatesChecker.setUrlVersion(new URL((String)xPath.evaluate(ConfigElement.URL_VERSION, updatesCheckerNode, XPathConstants.STRING)));
            updatesChecker.setUrlVersionInformation(new URL((String)xPath.evaluate(ConfigElement.URL_VERSION_INFORMATION, updatesCheckerNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return updatesChecker;
    }

    public static void writeUpdatesCheckerConfig(UpdatesChecker updatesChecker, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        //  UPDATES CHECKER start
        XMLUtil.writeElementStartWithLineBreak(ConfigElement.UPDATES_CHECKER, baseIndent, xmlsw);

        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.ENABLED, Tab.FOUR, Boolean.toString(updatesChecker.getEnabled()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.ATTACH_VERSION_INFORMATION, Tab.FOUR, Boolean.toString(updatesChecker.getAttachVersionInformation()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.TIMEOUT, Tab.FOUR, Integer.toString(updatesChecker.getTimeOut()), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.URL_VERSION, Tab.FOUR, updatesChecker.getUrlVersion().toString(), xmlsw);
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.URL_VERSION_INFORMATION, Tab.FOUR, updatesChecker.getUrlVersionInformation().toString(), xmlsw);

        //  UPDATES CHECKER end
        XMLUtil.writeElementEndWithLineBreak(xmlsw, baseIndent);
    }
}