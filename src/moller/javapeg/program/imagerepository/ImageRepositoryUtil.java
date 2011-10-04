package moller.javapeg.program.imagerepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.C;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.model.ModelInstanceLibrary;
import moller.util.io.StreamUtil;
import moller.util.xml.XMLUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ImageRepositoryUtil {

    private static final File exceptionsFile = new File(C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION + C.FS + "repository" + C.FS +  "exceptions.xml");

    /**
     * @param exceptionsFile
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws ImageRepositoryContentException
     */
    private static Document parse(File exceptionsFile) throws ParserConfigurationException, SAXException, IOException, ImageRepositoryContentException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbFactory.newDocumentBuilder();
        Document document = builder.parse(exceptionsFile);
        document.normalize();

        NodeList exceptions = document.getElementsByTagName("exceptions");

        if (exceptions.getLength() != 1) {
            throw new ImageRepositoryContentException("Invalid xml. It is only valid to have  exactly 1 element \"exceptions\", But there is: " + exceptions.getLength() + " \"exceptions\" elements in the file: " + exceptionsFile.getAbsolutePath());
        }
        return document;
    }

    /**
     * @param exceptionsFile
     * @param document
     */
    public static void store () {
        OutputStream os = null;
        boolean displayErrorMessage = false;
        try {
            String encoding = "UTF-8";

            os = new FileOutputStream(exceptionsFile);
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter w = factory.createXMLStreamWriter(os, encoding);

            XMLUtil.writeStartDocument(encoding, "1.0", w);
            XMLUtil.writeComment("This XML file contains the available categories and the logical structure" + C.LS +
                    "of the categories. The content of this file is used and modified by the" + C.LS +
                    "application JavaPEG and should not be edited manually, since any change might be" + C.LS +
                    "overwritten by the JavaPEG application or corrupt the file if the change is invalid" + C.LS, w);

            XMLUtil.writeElementStart("exceptions", w);

            SortedSet<Object> addDirectoriesAutomatically = ModelInstanceLibrary.getInstance().getAddDirectoriesAutomaticallyModel().getModel();

            XMLUtil.writeElementStart("allwaysAdd", w);

            for (Object addDirectoryAutomatically : addDirectoriesAutomatically) {
                XMLUtil.writeElement("path", addDirectoryAutomatically.toString(), w);
            }

            XMLUtil.writeElementEnd(w);


            SortedSet<Object> doNotAddDirectoriesAutomatically = ModelInstanceLibrary.getInstance().getDoNotAddDirectoriesAutomaticallyModel().getModel();

            XMLUtil.writeElementStart("neverAdd", w);

            for (Object doNotAddDirectoryAutomatically : doNotAddDirectoriesAutomatically) {
                XMLUtil.writeElement("path", doNotAddDirectoryAutomatically.toString(), w);
            }

            XMLUtil.writeElementEnd(w);

            XMLUtil.writeElementEnd(w);
            w.flush();
        } catch (XMLStreamException xse) {
            Logger logger = Logger.getInstance();
            logger.logFATAL("Could not store content of exceptions file (" + exceptionsFile.getAbsolutePath() + "). See stacktrace below for details");
            logger.logFATAL(xse);
            displayErrorMessage = true;
        } catch (FileNotFoundException fnex) {
            Logger logger = Logger.getInstance();
            logger.logFATAL("Could not store content of exceptions file (" + exceptionsFile.getAbsolutePath() + "). See stacktrace below for details");
            logger.logFATAL(fnex);
            displayErrorMessage = true;
        } finally {
            StreamUtil.close(os, true);

            if (displayErrorMessage) {
                Language lang = Language.getInstance();
                displayErrorMessage(lang.get("category.categoriesModel.store.error"), lang.get("errormessage.maingui.errorMessageLabel"));
                System.exit(1);
            }
        }
    }

    public static ImageRepositoryContentEceptions createImageRepositoryModel() {

        Document document = null;
        ImageRepositoryContentEceptions irce = null;
        boolean createExceptionsModelSuccess = false;

        Logger logger = Logger.getInstance();

        try {
            document = ImageRepositoryUtil.parse(exceptionsFile);

            irce = new ImageRepositoryContentEceptions();
            irce.setAllwaysAdd(ImageRepositoryUtil.populateImageRepositoryExceptions("allwaysAdd", document));
            irce.setNeverAdd(ImageRepositoryUtil.populateImageRepositoryExceptions("neverAdd", document));

            createExceptionsModelSuccess = true;
        } catch (ParserConfigurationException pce) {
            logger.logFATAL("Could not parse the categories file. See stacktrace below for details.");
            logger.logFATAL(pce);
        } catch (SAXException se) {
            logger.logFATAL("Could not parse the categories file. See stacktrace below for details.");
            logger.logFATAL(se);
        } catch (IOException iox) {
            logger.logFATAL("Could not parse the categories file. See stacktrace below for details.");
            logger.logFATAL(iox);
        } catch (ImageRepositoryContentException cce) {
            logger.logFATAL("Invalid content in categories file. See stacktrace below for details.");
            logger.logFATAL(cce);
        }

        if (!createExceptionsModelSuccess) {
            Language lang = Language.getInstance();
//            TODO: Fix error messsage to a correct one
            displayErrorMessage(lang.get("category.categoriesModel.create.error"), lang.get("errormessage.maingui.errorMessageLabel"));
            System.exit(1);
        }
        return irce;
    }

    private static Set<Object> populateImageRepositoryExceptions(String tagName, Document document) throws ImageRepositoryContentException {
        Set<Object> paths = new HashSet<Object>();

        NodeList tagNameElementAsNodeList = document.getElementsByTagName(tagName);

        if (tagNameElementAsNodeList.getLength() != 1) {
            throw new ImageRepositoryContentException("Invalid xml. It is only valid to have  exactly 1 element \"" + tagName + "\", But there is: " + tagNameElementAsNodeList.getLength() + " \"" + tagName + "\" elements in the loaded XML document");
        } else {
            Node tagNameElement = tagNameElementAsNodeList.item(0);

            NodeList pathElements = tagNameElement.getChildNodes();

            if (pathElements.getLength() > 0) {
                for (int i = 0; i < pathElements.getLength(); i++) {
                    Element path = (Element)pathElements.item(i);
                    paths.add(new File(path.getTextContent()));
                }
            }
        }
        return paths;
    }

    private static void displayErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
