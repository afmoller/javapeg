package moller.javapeg.program.imagerepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.model.ModelInstanceLibrary;
import moller.util.io.FileUtil;
import moller.util.io.StreamUtil;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ImageRepositoryUtil {

    /**
     * The static singleton instance of this class.
     */
    private static ImageRepositoryUtil instance;

    private final File repositoryFile;

    private final TreeSet<String> imageRepositoryPaths;
    private final Set<String> allwaysAdd;
    private final Set<String> neverAdd;

    /**
     * Private constructor.
     */
    private ImageRepositoryUtil() {
        repositoryFile = new File(C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION + C.FS + "config" + C.FS + "repository.xml");

        imageRepositoryPaths = new TreeSet<String>();
        allwaysAdd = new HashSet<String>();
        neverAdd = new HashSet<String>();
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static ImageRepositoryUtil getInstance() {
        if (instance != null)
            return instance;
        synchronized (ImageRepositoryUtil.class) {
            if (instance == null) {
                instance = new ImageRepositoryUtil();
            }
            return instance;
        }
    }

    /**
     * @param repositoryFile
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws ImageRepositoryContentException
     */
    private Document parse(File repositoryFile) throws ParserConfigurationException, SAXException, IOException {

        Language lang = Language.getInstance();

        String repositorySchemaLocation = "resources/schema/repository.xsd";

        StringBuilder errorMessage = new StringBuilder();

        if (!validateAgainstSchema(repositoryFile, repositorySchemaLocation)) {
            Logger logger = Logger.getInstance();


            errorMessage.append("The repository file is corrupt");

            // Try to make a backup of the existing corrupt repository file...
            File backupFile = new File(repositoryFile.getParentFile(), "repository.xml.backup");
            if (FileUtil.copyFile(repositoryFile, backupFile)) {
//                TODO: Fix hard coded string
                errorMessage.append("\nThe corrupt repository file was succesfully backed up to the file: " + backupFile.getAbsolutePath());
            }
            // ... if it was not possible to make a backup the content of the
            // corrupted repository file will be put into the log file.
            else {
//              TODO: Fix hard coded string
                errorMessage.append("\nCould not back up the repository file. Content is written to JavaPEG log file");

                logger.logERROR("Could not make backup of repository file: " + repositoryFile.getAbsolutePath() + " content is logged below:");
                logger.logERROR("Start of repository file content");
                logger.logERROR("================================");

                for (String rowInFile :  FileUtil.readFromFile(repositoryFile)) {
                    logger.logERROR(rowInFile);
                }

                logger.logERROR("==============================");
                logger.logERROR("End of repository file content");
            }

            if (FileUtil.copy(StartJavaPEG.class.getResourceAsStream("resources/startup/repository.xml"), repositoryFile)) {
//              TODO: Fix hard coded string
                errorMessage.append("\nRepository file restored to default");
            } else {
//              TODO: Fix hard coded string
                errorMessage.append("\nCould not restore the repository file to default");
                errorMessage.append("\nPlease add the following content manually to the repository file: " + repositoryFile.getAbsolutePath());
                errorMessage.append("Content start:");
//                TODO: Fix content
                errorMessage.append("Content end:");

                logger.logFATAL("Could not restore corrupted repository file.");
                System.exit(1);
            }

//          TODO: Fix hard coded string
            errorMessage.append("\nSee JavaPEG log file for details");
        }

        if (errorMessage.length() > 0) {
            JOptionPane.showMessageDialog(null, errorMessage.toString(), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbFactory.newDocumentBuilder();
        Document document = builder.parse(repositoryFile);
        document.normalize();

        return document;
    }

    /**
     * @param repositoryFile
     * @param document
     */
    public void store () {
        SortedSet<Object> imageRepositoryList = ModelInstanceLibrary.getInstance().getImageRepositoryListModel().getModel();
        SortedSet<Object> addDirectoriesAutomatically = ModelInstanceLibrary.getInstance().getAddDirectoriesAutomaticallyModel().getModel();
        SortedSet<Object> doNotAddDirectoriesAutomatically = ModelInstanceLibrary.getInstance().getDoNotAddDirectoriesAutomaticallyModel().getModel();

        boolean pathsChanged = pathsContentChanged(imageRepositoryList);
        boolean neverAddChanged = contentChanged(neverAdd, doNotAddDirectoriesAutomatically);
        boolean allwaysAddChanged = contentChanged(allwaysAdd, addDirectoriesAutomatically);

        if (pathsChanged || allwaysAddChanged || neverAddChanged) {
            OutputStream os = null;
            boolean displayErrorMessage = false;

            try {
                String encoding = "UTF-8";

                os = new FileOutputStream(repositoryFile);
                XMLOutputFactory factory = XMLOutputFactory.newInstance();
                XMLStreamWriter w = factory.createXMLStreamWriter(os, encoding);

                XMLUtil.writeStartDocument(encoding, "1.0", w);
                XMLUtil.writeComment("This XML file contains the definition of an image repository, the content" + C.LS +
                        "of it and exceptions. The content of this file is used and modified by the" + C.LS +
                        "application JavaPEG and should not be edited manually, since any change might be" + C.LS +
                        "overwritten by the JavaPEG application or corrupt the file if the change is invalid" + C.LS, w);

                XMLUtil.writeElementStart("repository", w);
                XMLUtil.writeElementStart("paths", w);

                for (Object imageRepository : imageRepositoryList) {
                    XMLUtil.writeElement("path", ((ImageRepositoryItem)imageRepository).getPath(), w);
                }

                XMLUtil.writeElementEnd(w);
                XMLUtil.writeElementStart("exceptions", w);
                XMLUtil.writeElementStart("allwaysAdd", w);

                for (Object addDirectoryAutomatically : addDirectoriesAutomatically) {
                    XMLUtil.writeElement("path", addDirectoryAutomatically.toString(), w);
                }

                XMLUtil.writeElementEnd(w);
                XMLUtil.writeElementStart("neverAdd", w);

                for (Object doNotAddDirectoryAutomatically : doNotAddDirectoriesAutomatically) {
                    XMLUtil.writeElement("path", doNotAddDirectoryAutomatically.toString(), w);
                }

                XMLUtil.writeElementEnd(w);
                XMLUtil.writeElementEnd(w);
                XMLUtil.writeElementEnd(w);
                w.flush();
            } catch (XMLStreamException xse) {
                Logger logger = Logger.getInstance();
                logger.logFATAL("Could not store content of repository file (" + repositoryFile.getAbsolutePath() + "). See stacktrace below for details");
                logger.logFATAL(xse);
                displayErrorMessage = true;
            } catch (FileNotFoundException fnex) {
                Logger logger = Logger.getInstance();
                logger.logFATAL("Could not store content of repository file (" + repositoryFile.getAbsolutePath() + "). See stacktrace below for details");
                logger.logFATAL(fnex);
                displayErrorMessage = true;
            } finally {
                StreamUtil.close(os, true);

                if (displayErrorMessage) {
                    Language lang = Language.getInstance();
//                    TODO: Fix correct error message
                    displayErrorMessage(lang.get("category.categoriesModel.store.error"), lang.get("errormessage.maingui.errorMessageLabel"));
                    System.exit(1);
                }
            }
        }
    }

    public ImageRepository createImageRepositoryModel() {

        Document document = null;
        ImageRepository ir = null;
        boolean createRepositoryModelSuccess = false;

        Logger logger = Logger.getInstance();

        try {
            document = this.parse(repositoryFile);

            ir = new ImageRepository();
            ir.setAllwaysAdd(this.populateImageRepository("allwaysAdd", document));
            ir.setNeverAdd(this.populateImageRepository("neverAdd", document));
            ir.setPaths(this.populateImageRepositoryPaths("paths", document));

            createRepositoryModelSuccess = true;
        } catch (ParserConfigurationException pce) {
            logger.logFATAL("Could not parse the repository file. See stacktrace below for details.");
            logger.logFATAL(pce);
        } catch (SAXException se) {
            logger.logFATAL("Could not parse the repository file. See stacktrace below for details.");
            logger.logFATAL(se);
        } catch (IOException iox) {
            logger.logFATAL("Could not parse the repository file. See stacktrace below for details.");
            logger.logFATAL(iox);
        }

        if (!createRepositoryModelSuccess) {
            Language lang = Language.getInstance();
//            TODO: Fix error messsage to a correct one
            displayErrorMessage(lang.get("category.categoriesModel.create.error"), lang.get("errormessage.maingui.errorMessageLabel"));
            System.exit(1);
        }
        return ir;
    }

    private Set<Object> populateImageRepository(String tagName, Document document) {
        Set<Object> paths = new HashSet<Object>();

        NodeList tagNameElementAsNodeList = document.getElementsByTagName(tagName);

        Node tagNameElement = tagNameElementAsNodeList.item(0);

        NodeList pathElements = tagNameElement.getChildNodes();

        if (pathElements.getLength() > 0) {
            for (int i = 0; i < pathElements.getLength(); i++) {
                Element path = (Element)pathElements.item(i);
                paths.add(new File(path.getTextContent()));

                if (tagName.equals("allwaysAdd")) {
                    allwaysAdd.add(path.getTextContent());
                } else {
                    neverAdd.add(path.getTextContent());
                }
            }
        }
        return paths;
    }

    private Set<Object> populateImageRepositoryPaths(String tagName, Document document) {
        Set<Object> paths = new HashSet<Object>();

        NodeList tagNameElementAsNodeList = document.getElementsByTagName(tagName);

        Node tagNameElement = tagNameElementAsNodeList.item(0);

        NodeList pathElements = tagNameElement.getChildNodes();

        if (pathElements.getLength() > 0) {
            for (int i = 0; i < pathElements.getLength(); i++) {
                Element path = (Element)pathElements.item(i);
                paths.add(path.getTextContent());
                imageRepositoryPaths.add(path.getTextContent());
            }
        }
        return paths;
    }

    private void displayErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method decides whether the image repository has changed while
     * JavaPEG has been running. If a change is found then the image repository
     * file needs to be updated by flushing the current content to persistent
     * storage.
     *
     * @param directoryPaths contains the current image repository paths.
     *
     * @return a boolean value indicating whether the image repository file
     *         need to be updated or not.
     */
    private boolean pathsContentChanged(SortedSet<Object> directoryPaths) {

        if (directoryPaths.size() != imageRepositoryPaths.size()) {
            return true;
        }

        for (String imageRepositoryPath : imageRepositoryPaths) {
            boolean found = false;

            for (Object directoryPath : directoryPaths) {
                if (((ImageRepositoryItem)directoryPath).getPath().equals(imageRepositoryPath)) {
                    found = true;
                }
            }

            if (!found) {
                return true;
            }
        }
        return false;
    }

    private boolean contentChanged(Set<String> initialSet, SortedSet<Object> addDirectoriesAutomatically) {

        for (Object addDirectoryAutomatically : addDirectoriesAutomatically) {
            if (!initialSet.contains(addDirectoryAutomatically)) {
                return true;
            }
        }
        return false;
    }

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