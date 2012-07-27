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

import moller.javapeg.program.config.controller.section.CategoriesConfig;
import moller.javapeg.program.config.controller.section.GUIConfig;
import moller.javapeg.program.config.controller.section.ImportedCategoriesConfig;
import moller.javapeg.program.config.controller.section.JavapegClientIdConfig;
import moller.javapeg.program.config.controller.section.LanguageConfig;
import moller.javapeg.program.config.controller.section.LoggingConfig;
import moller.javapeg.program.config.controller.section.RenameImagesConfig;
import moller.javapeg.program.config.controller.section.RepositoryConfig;
import moller.javapeg.program.config.controller.section.TagImagesConfig;
import moller.javapeg.program.config.controller.section.ThumbNailConfig;
import moller.javapeg.program.config.controller.section.ToolTipsConfig;
import moller.javapeg.program.config.controller.section.UpdatesCheckerConfig;
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
            config.setCategories(CategoriesConfig.getCategoriesConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.CATEGORIES, doc, XPathConstants.NODE), xPath));
            config.setImportedCategories(ImportedCategoriesConfig.getImportedCategoriesConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.IMPORTEDCATEGORIES, doc, XPathConstants.NODE), xPath));
            config.setgUI(GUIConfig.getGUIConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.GUI, doc, XPathConstants.NODE), xPath));
            config.setJavapegClientId(JavapegClientIdConfig.getJavapegClientIdConfig((String)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.JAVAPEG_CLIENT_ID, doc, XPathConstants.STRING)));
            config.setLanguage(LanguageConfig.getLanguageConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.LANGUAGE, doc, XPathConstants.NODE), xPath));
            config.setLogging(LoggingConfig.getLoggingConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.LOGGING, doc, XPathConstants.NODE), xPath));
            config.setRenameImages(RenameImagesConfig.getRenameImagesConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.RENAME_IMAGES, doc, XPathConstants.NODE), xPath));
            config.setRepository(RepositoryConfig.getRepositoryConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.REPOSITORY, doc, XPathConstants.NODE), xPath));
            config.setTagImages(TagImagesConfig.getTagImagesConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.TAG_IMAGES, doc, XPathConstants.NODE), xPath));
            config.setThumbNail(ThumbNailConfig.getThumbNailConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.THUMBNAIL, doc, XPathConstants.NODE), xPath));
            config.setToolTips(ToolTipsConfig.getToolTipsConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.TOOL_TIPS, doc, XPathConstants.NODE), xPath));
            config.setUpdatesChecker(UpdatesCheckerConfig.getUpdatesCheckerConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.UPDATES_CHECKER, doc, XPathConstants.NODE), xPath));
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
