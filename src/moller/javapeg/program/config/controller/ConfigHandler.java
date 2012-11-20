package moller.javapeg.program.config.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
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
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.io.StreamUtil;
import moller.util.string.Tab;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ConfigHandler {

    public static Configuration load(File configFile) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc;
        Configuration configuration = null;

        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(configFile);
            doc.getDocumentElement().normalize();

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            configuration = new Configuration();
            configuration.setLogging(LoggingConfig.getLoggingConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.LOGGING, doc, XPathConstants.NODE), xPath));
            configuration.setCategories(CategoriesConfig.getCategoriesConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.CATEGORIES, doc, XPathConstants.NODE), xPath));
            configuration.setImportedCategories(ImportedCategoriesConfig.getImportedCategoriesConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.IMPORTEDCATEGORIES, doc, XPathConstants.NODE), xPath));
            configuration.setgUI(GUIConfig.getGUIConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.GUI, doc, XPathConstants.NODE), xPath));
            configuration.setJavapegClientId(JavapegClientIdConfig.getJavapegClientIdConfig((String)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.JAVAPEG_CLIENT_ID, doc, XPathConstants.STRING)));
            configuration.setLanguage(LanguageConfig.getLanguageConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.LANGUAGE, doc, XPathConstants.NODE), xPath));
            configuration.setRenameImages(RenameImagesConfig.getRenameImagesConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.RENAME_IMAGES, doc, XPathConstants.NODE), xPath));
            configuration.setRepository(RepositoryConfig.getRepositoryConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.REPOSITORY, doc, XPathConstants.NODE), xPath));
            configuration.setTagImages(TagImagesConfig.getTagImagesConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.TAG_IMAGES, doc, XPathConstants.NODE), xPath));
            configuration.setThumbNail(ThumbNailConfig.getThumbNailConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.THUMBNAIL, doc, XPathConstants.NODE), xPath));
            configuration.setToolTips(ToolTipsConfig.getToolTipsConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.TOOL_TIPS, doc, XPathConstants.NODE), xPath));
            configuration.setUpdatesChecker(UpdatesCheckerConfig.getUpdatesCheckerConfig((Node)xPath.evaluate("/" + ConfigElement.CONFIG + "/" + ConfigElement.UPDATES_CHECKER, doc, XPathConstants.NODE), xPath));
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
        return configuration;
    }

    public static boolean store(Configuration configuration, File configurationFile) {

        OutputStream os = null;
        boolean displayErrorMessage = false;

        try {
            String encoding = "UTF-8";

            os = new FileOutputStream(configurationFile);
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter w = factory.createXMLStreamWriter(os, encoding);

            XMLUtil.writeStartDocument(encoding, "1.0", w);
            XMLUtil.writeLineBreak(w);
            XMLUtil.writeComment("The content of this file, application configuration, is used and modified by the" + C.LS +
                    "application JavaPEG and should not be edited manually, since any change might be" + C.LS +
                    "overwritten by the JavaPEG application or corrupt the file if the change is invalid" + C.LS, w);
            XMLUtil.writeLineBreak(w);

            XMLAttribute[] xmlAttributes = new XMLAttribute[3];
            xmlAttributes[0] = new XMLAttribute("xmlns", "http://moller.javapeg.config.com");
            xmlAttributes[1] = new XMLAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            xmlAttributes[2] = new XMLAttribute("xsi:schemaLocation", "http://moller.javapeg.config.com config.xsd");

            XMLUtil.writeElementStart(ConfigElement.CONFIG, xmlAttributes, w);
            XMLUtil.writeLineBreak(w);

            // LOGGING
            XMLUtil.writeElementStart(ConfigElement.LOGGING, Tab.TWO, w);
            XMLUtil.writeLineBreak(w);
            LoggingConfig.writeLoggingConfig(configuration.getLogging(), Tab.FOUR, w);

            XMLUtil.writeElementEnd(w, Tab.TWO);
            XMLUtil.writeLineBreak(w);

            // JAVAPEG CLIENT ID
            JavapegClientIdConfig.writeJavapegClientIdConfig(configuration.getJavapegClientId(), Tab.TWO, w);

            // GUI
            GUIConfig.writeGUIConfig(configuration.getgUI(), Tab.TWO, w);

            // UPDATES CHECKER
            UpdatesCheckerConfig.writeUpdatesCheckerConfig(configuration.getUpdatesChecker(), Tab.TWO, w);

            // LANGUAGE
            LanguageConfig.writeLanguageConfig(configuration.getLanguage(), Tab.TWO, w);

            // RENAME IMAGES
            RenameImagesConfig.writeRenameImagesConfig(configuration.getRenameImages(), Tab.TWO, w);

            // TAG IMAGES
            TagImagesConfig.writeTagImagesConfig(configuration.getTagImages(), Tab.TWO, w);

            // THUMB NAIL
            ThumbNailConfig.writeThumbNailConfig(configuration.getThumbNail(), Tab.TWO, w);

            // TOOL TIPS
            ToolTipsConfig.writeToolTipsConfig(configuration.getToolTips(), Tab.TWO, w);

            // CATEGORIES
            CategoriesConfig.writeCategoriesConfig(configuration.getCategories(), ApplicationContext.getInstance().getHighestUsedCategoryID(), Tab.TWO, w);

            // IMPORTED CATEGORIES
            ImportedCategoriesConfig.writeImportedCategoriesConfig(configuration.getImportedCategoriesConfig(), Tab.TWO, w);

            // REPOSITORY
            RepositoryConfig.writeRepositoryConfig(configuration.getRepository(), Tab.TWO, w);

            XMLUtil.writeElementEnd(w);
            w.flush();
        } catch (XMLStreamException xse) {
            Logger logger = Logger.getInstance();
            logger.logFATAL("Could not store content of repository file (" + configurationFile.getAbsolutePath() + "). See stacktrace below for details");
            logger.logFATAL(xse);
            displayErrorMessage = true;
        } catch (FileNotFoundException fnex) {
            Logger logger = Logger.getInstance();
            logger.logFATAL("Could not store content of repository file (" + configurationFile.getAbsolutePath() + "). See stacktrace below for details");
            logger.logFATAL(fnex);
            displayErrorMessage = true;
        } finally {
            StreamUtil.close(os, true);

            if (displayErrorMessage) {
                Language lang = Language.getInstance();
                displayErrorMessage(lang.get("imagerepository.model.store.error"), lang.get("errormessage.maingui.errorMessageLabel"));
                System.exit(1);
            }
        }

        return true;
    }

    private static void displayErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

//    TODO: Create config file schema validation
    private boolean validateAgainstSchema(File repositoryFile, String repositorySchemaLocation) {
        Logger logger = Logger.getInstance();

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            StreamSource repositorySchema = new StreamSource(StartJavaPEG.class.getResourceAsStream(repositorySchemaLocation));
            Schema schema = factory.newSchema(repositorySchema);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(repositoryFile));
            return true;
        } catch (SAXParseException spex) {
            logger.logERROR("Invalid content of repository file: " + repositoryFile.getAbsolutePath() + " see stacktrace below for details");
            logger.logERROR(spex);
            return false;
        } catch (SAXException sex) {
            logger.logERROR("Invalid content of repository file: " + repositoryFile.getAbsolutePath() + " see stacktrace below for details");
            logger.logERROR(sex);
            return false;
        } catch (IOException iox) {
            logger.logERROR("Invalid content of repository file: " + repositoryFile.getAbsolutePath() + " see stacktrace below for details");
            logger.logERROR(iox);
            return false;
        }
    }
}
