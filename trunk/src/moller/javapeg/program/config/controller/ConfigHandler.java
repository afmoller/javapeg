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
package moller.javapeg.program.config.controller;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.config.ConfigUtil;
import moller.javapeg.program.config.controller.section.CategoriesConfig;
import moller.javapeg.program.config.controller.section.GUIConfig;
import moller.javapeg.program.config.controller.section.ImageSearchResultViewerStateConfig;
import moller.javapeg.program.config.controller.section.ImageViewerStateConfig;
import moller.javapeg.program.config.controller.section.ImportedCategoriesConfig;
import moller.javapeg.program.config.controller.section.JavapegClientIdConfig;
import moller.javapeg.program.config.controller.section.LanguageConfig;
import moller.javapeg.program.config.controller.section.LoggingConfig;
import moller.javapeg.program.config.controller.section.RenameImagesConfig;
import moller.javapeg.program.config.controller.section.RepositoryConfig;
import moller.javapeg.program.config.controller.section.ResizeImagesConfig;
import moller.javapeg.program.config.controller.section.TagImagesConfig;
import moller.javapeg.program.config.controller.section.ThumbNailConfig;
import moller.javapeg.program.config.controller.section.ToolTipsConfig;
import moller.javapeg.program.config.controller.section.UpdatesCheckerConfig;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.schema.SchemaUtil;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.gui.Screen;
import moller.util.io.StreamUtil;
import moller.util.io.ZipUtil;
import moller.util.result.ResultObject;
import moller.util.string.Tab;
import moller.util.xml.XMLAttribute;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigHandler {

    public static Configuration load(File configFile) {

        String configSchemaLocation = C.PATH_SCHEMAS + SchemaUtil.getConfigurationSchemaForVersion(C.JAVAPEG_VERSION).getSchemaName();
        StreamSource configSchema = new StreamSource(StartJavaPEG.class.getResourceAsStream(configSchemaLocation));

        StringBuilder errorMessage = null;

        ResultObject<Exception> validationResult = XMLUtil.validate(configFile, configSchema);
        ResultObject<Exception> storeCorruptConfigurationResult = new ResultObject<Exception>(true, null);

        ResultObject<Exception> restoreResult = null;

        if (!validationResult.getResult()) {

            errorMessage = new StringBuilder();
            errorMessage.append("Configuration file is corrupt:");
            errorMessage.append(C.LS);
            errorMessage.append(validationResult.getObject());
            errorMessage.append(C.LS);

            try {
                ConfigUtil.storeCorruptConfiguration(configFile);
            } catch (IOException iox) {
                storeCorruptConfigurationResult.setResult(false);
                storeCorruptConfigurationResult.setObject(iox);
            }

            errorMessage.append(C.LS);
            if (storeCorruptConfigurationResult.getResult()) {
                errorMessage.append("The corrupt configuration file is stored in the directory: ");
                errorMessage.append(C.LS);
                errorMessage.append(configFile.getParentFile().getAbsolutePath() + C.FS + "corrupt");
            } else {
                errorMessage.append("The corrupt configuration file could not be stored into a \"corrupt\" directory");
                errorMessage.append(C.LS);
                errorMessage.append(storeCorruptConfigurationResult.getObject());
            }

            File configurationBackupFile = new File(configFile.getParentFile(), configFile.getName() + ".zip");

            restoreResult = ConfigUtil.restoreConfigurationFromBackup(configurationBackupFile);

            errorMessage.append(C.LS);
            errorMessage.append(C.LS);
            if (restoreResult.getResult()) {
                errorMessage.append("Configuration restored from backup");
            } else {
                errorMessage.append("Configuration could not be restored from backup:");
                errorMessage.append(C.LS);
                errorMessage.append(restoreResult.getObject());
            }

            if (restoreResult.getResult()) {

                ResultObject<Exception> validationResultForRestoredConfiguration = XMLUtil.validate(configFile, configSchema);

                if (!validationResultForRestoredConfiguration.getResult()) {
                    errorMessage.append(C.LS);
                    try {

                        Files.copy(StartJavaPEG.class.getResourceAsStream("resources/startup/conf.xml"), configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        errorMessage.append("Configuration restored from default configuration");
                    } catch (IOException iox) {
                        errorMessage.append("Could not restore configuration from default configuration:");
                        errorMessage.append(C.LS);
                        errorMessage.append(iox);
                        displayErrorMessage(errorMessage.toString(), "Error - Corrupt configuration");
                        System.exit(1);
                    }
                }
            } else {
                try {
                    Files.copy(StartJavaPEG.class.getResourceAsStream("resources/startup/conf.xml"), configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    errorMessage.append("Configuration restored from default configuration");
                } catch (IOException iox) {
                    errorMessage.append("Could not restore configuration from default configuration:");
                    errorMessage.append(C.LS);
                    errorMessage.append(iox);
                    displayErrorMessage(errorMessage.toString(), "Error - Corrupt configuration");
                    System.exit(1);
                }
            }
        }

        if (errorMessage != null) {
            displayErrorMessage(errorMessage.toString(), "Error");
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc;
        Configuration configuration = null;

        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(configFile);
            doc.getDocumentElement().normalize();

            configuration = new Configuration();

            NodeList configElementsByTagName = doc.getElementsByTagName(ConfigElement.CONFIG);

            if (configElementsByTagName != null && configElementsByTagName.getLength() == 1) {
                Node configNode = configElementsByTagName.item(0);
                NodeList configNodeChildNodes = configNode.getChildNodes();

                for (int i = 0; i < configNodeChildNodes.getLength(); i++) {
                    Node node = configNodeChildNodes.item(i);

                    switch (node.getNodeName()) {
                    case ConfigElement.LOGGING:
                        configuration.setLogging(LoggingConfig.getLoggingConfig(node));
                        break;
                    case ConfigElement.CATEGORIES:
                        configuration.setCategories(CategoriesConfig.getCategoriesConfig(node));
                        break;
                    case ConfigElement.IMPORTEDCATEGORIES:
                        configuration.setImportedCategories(ImportedCategoriesConfig.getImportedCategoriesConfig(node));
                        break;
                    case ConfigElement.GUI:
                        configuration.setgUI(GUIConfig.getGUIConfig(node));
                        break;
                    case ConfigElement.JAVAPEG_CLIENT_ID:
                        configuration.setJavapegClientId(JavapegClientIdConfig.getJavapegClientIdConfig(node.getTextContent()));
                        break;
                    case ConfigElement.LANGUAGE:
                        configuration.setLanguage(LanguageConfig.getLanguageConfig(node));
                        break;
                    case ConfigElement.RENAME_IMAGES:
                        configuration.setRenameImages(RenameImagesConfig.getRenameImagesConfig(node));
                        break;
                    case ConfigElement.RESIZE_IMAGES:
                        configuration.setResizeImages(ResizeImagesConfig.getResizeImagesConfig(node));
                        break;
                    case ConfigElement.IMAGE_VIEWER_STATE:
                        configuration.setImageViewerState(ImageViewerStateConfig.getImageViewerStateConfig(node));
                        break;
                    case ConfigElement.IMAGE_SEARCH_RESULT_VIEWER_STATE:
                        configuration.setImageSearchResultViewerState(ImageSearchResultViewerStateConfig.getImageSearchResultViewerStateConfig(node));
                        break;
                    case ConfigElement.REPOSITORY:
                        configuration.setRepository(RepositoryConfig.getRepositoryConfig(node));
                        break;
                    case ConfigElement.TAG_IMAGES:
                        configuration.setTagImages(TagImagesConfig.getTagImagesConfig(node));
                        break;
                    case ConfigElement.THUMBNAIL:
                        configuration.setThumbNail(ThumbNailConfig.getThumbNailConfig(node));
                        break;
                    case ConfigElement.TOOL_TIPS:
                        configuration.setToolTips(ToolTipsConfig.getToolTipsConfig(node));
                        break;
                    case ConfigElement.UPDATES_CHECKER:
                        configuration.setUpdatesChecker(UpdatesCheckerConfig.getUpdatesCheckerConfig(node));
                        break;
                    default:
                        break;
                    }
                }
            } else {
                throw new RuntimeException("Could not load configuration");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not load configuration", ex);
        }
        return configuration;
    }

    /**
     * This method stores the JavaPEG configuration to an XML file. It does also
     * store a zipped version of the configuration, which is used as a source to
     * restore the last configuration, if the normal XML configuration file has
     * been corrupted (incorrectly manually edited) between two application
     * sessions.
     *
     * @param configuration
     *            is the configuration to store into an XML file
     * @param configurationFile
     *            is the file to which the {@link Configuration} object shall be
     *            stored.
     * @return a boolean value indication whether the store action was
     *         successful or not. True means success and false failure.
     */
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

            // RESIZE IMAGES
            ResizeImagesConfig.writeResizeImagesConfig(configuration.getResizeImages(),Tab.TWO, w);

            // IMAGE VIEWER STATE
            ImageViewerStateConfig.writeImageViewerStateConfig(configuration.getImageViewerState(),Tab.TWO, w);

            // IMAGE SEARCH RESULT VIEWER STATE
            ImageSearchResultViewerStateConfig.writeImageSearchResultViewerStateConfig(configuration.getImageSearchResultViewerState(),Tab.TWO, w);

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

            // Store a restore version, to have as fallback if the
            // configuration by any reason have been corrupted between two
            // application sessions.
            ZipUtil.zip(configurationFile);

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
        } catch (IOException iox) {
            Logger logger = Logger.getInstance();
            logger.logERROR("Could not make a backup of the configuration file.");
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

    /**
     * If there is an error message to display, that is shown in an modal
     * {@link JDialog}
     *
     * @param message
     *            is the error message to display.
     * @param title
     *            contains the string to be used as {@link JDialog} title.
     */
    private static void displayErrorMessage(String message, String title) {
        JTextArea textArea = new JTextArea();
        textArea.setText(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JDialog errorMessageDialog = new JDialog();
        errorMessageDialog.setModalityType(ModalityType.APPLICATION_MODAL);
        errorMessageDialog.getContentPane().add(textArea);
        errorMessageDialog.setSize(new Dimension(600, 300));
        errorMessageDialog.setLocation(Screen.getLeftUpperLocationForCenteredPosition(600, 300));
        errorMessageDialog.setTitle(title);
        errorMessageDialog.setVisible(true);
    }
}
