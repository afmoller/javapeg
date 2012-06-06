package moller.javapeg.program.config.controller;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import moller.javapeg.program.config.model.Config;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigHandler {

    public static Config load(File configFile) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc;
        Config config = null;

        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(configFile);
            doc.getDocumentElement().normalize();

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            config = new Config();
//            config.setCategories(ConfigHandlerUtil.getCategoriesConfig(doc.getElementsByTagName("categories"), xPath));
            config.setgUI(ConfigHandlerUtil.getGUIConfig((NodeList)xPath.evaluate("/config/gui/*", doc, XPathConstants.NODESET), xPath));
            config.setJavapegClientId((String)xPath.evaluate("/config/javapegClientId", doc, XPathConstants.STRING));
            config.setLanguage(ConfigHandlerUtil.getLanguageConfig(doc.getElementsByTagName("language"), xPath));
            config.setLogging(ConfigHandlerUtil.getLoggingConfig(doc.getElementsByTagName("logging"), xPath));
            config.setRenameImages(ConfigHandlerUtil.getRenameImagesConfig(doc.getElementsByTagName("renameImages"), xPath));
            config.setRepository(ConfigHandlerUtil.getRepositoryConfig(doc.getElementsByTagName("repository"), xPath));
            config.setTagImages(ConfigHandlerUtil.getTagImagesConfig(doc.getElementsByTagName("tagImages"), xPath));
            config.setThumbNail(ConfigHandlerUtil.getThumbNailConfig(doc.getElementsByTagName("thumbNail"), xPath));
            config.setToolTips(ConfigHandlerUtil.getToolTipsConfig(doc.getElementsByTagName("toolTips"), xPath));
            config.setUpdatesChecker(ConfigHandlerUtil.getUpdatesCheckerConfig(doc.getElementsByTagName("updatesChecker"), xPath));
        } catch (ParserConfigurationException pcex) {
//            TODO: Fix error logging
        } catch (SAXException sex) {
//          TODO: Fix error logging
        } catch (IOException iox) {
//          TODO: Fix error logging
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return config;
    }

    public static boolean store(Config config) {
        return true;
    }
}
