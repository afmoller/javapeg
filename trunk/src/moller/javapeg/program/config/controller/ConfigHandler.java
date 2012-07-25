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
import org.w3c.dom.Node;
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
            config.setCategories(ConfigHandlerUtil.getCategoriesConfig((Node)xPath.evaluate("/config/categories", doc, XPathConstants.NODE), xPath));
            config.setImportedCategories(ConfigHandlerUtil.getImportedCategoriesConfig((Node)xPath.evaluate("/config/importedcategories", doc, XPathConstants.NODE), xPath));
            config.setgUI(ConfigHandlerUtil.getGUIConfig((Node)xPath.evaluate("/config/gui", doc, XPathConstants.NODE), xPath));
            config.setJavapegClientId((String)xPath.evaluate("/config/javapegClientId", doc, XPathConstants.STRING));
            config.setLanguage(ConfigHandlerUtil.getLanguageConfig((Node)xPath.evaluate("/config/language", doc, XPathConstants.NODE), xPath));
            config.setLogging(ConfigHandlerUtil.getLoggingConfig((Node)xPath.evaluate("/config/logging", doc, XPathConstants.NODE), xPath));
            config.setRenameImages(ConfigHandlerUtil.getRenameImagesConfig((Node)xPath.evaluate("/config/renameImages", doc, XPathConstants.NODE), xPath));
            config.setRepository(ConfigHandlerUtil.getRepositoryConfig((Node)xPath.evaluate("/config/repository", doc, XPathConstants.NODE), xPath));
            config.setTagImages(ConfigHandlerUtil.getTagImagesConfig((Node)xPath.evaluate("/config/tagImages", doc, XPathConstants.NODE), xPath));
            config.setThumbNail(ConfigHandlerUtil.getThumbNailConfig((Node)xPath.evaluate("/config/thumbNail", doc, XPathConstants.NODE), xPath));
            config.setToolTips(ConfigHandlerUtil.getToolTipsConfig((Node)xPath.evaluate("/config/toolTips", doc, XPathConstants.NODE), xPath));
            config.setUpdatesChecker(ConfigHandlerUtil.getUpdatesCheckerConfig((Node)xPath.evaluate("/config/updatesChecker", doc, XPathConstants.NODE), xPath));
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

    public static boolean store(Config config, File configFile) {
        return true;
    }
}
