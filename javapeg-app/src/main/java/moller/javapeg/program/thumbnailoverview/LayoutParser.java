/******************************************************import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.io.StreamUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
u.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program.thumbnailoverview;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.io.StreamUtil;
import moller.util.java.SystemProperties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LayoutParser {

    /**
     * The static singleton instance of this class.
     */
    private static LayoutParser instance;

    private static final String FS = File.separator;

    private static String rowClass;
    private static String columnClass;
    private static int columnAmount;
    private static String imageClass;
    private static String metaClass;
    private static List<LayoutMetaItem> metaItems;
    private static String documentTitle;

    /**
     * Private constructor.
     */
    private LayoutParser() {
        init();
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static LayoutParser getInstance() {
        if (instance != null) {
            init();
            return instance;
        }
        synchronized (LayoutParser.class) {
            if (instance == null) {
                instance = new LayoutParser();
            }
            return instance;
        }
    }

    private static void init() {
        rowClass      = "";
        columnClass   = "";
        columnAmount  = 0;
        imageClass    = "";
        metaClass     = "";
        documentTitle = "";
        metaItems     = new ArrayList<LayoutMetaItem>();
    }

    public void parse() {

        Language lang = Language.getInstance();

        Logger logger = Logger.getInstance();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc;

        InputStream layoutSource = null;

        try {

            try {
                layoutSource = new FileInputStream(SystemProperties.getUserDir() + FS + "resources" + FS + "thumb" + FS + "layout.xml");
            } catch (FileNotFoundException e1) {
                layoutSource = StartJavaPEG.class.getResourceAsStream("resources/thumbnailoverview/layout.xml");
            }

            db = dbf.newDocumentBuilder();
            doc = db.parse(layoutSource);
            doc.getDocumentElement().normalize();

            NodeList documenttitle = doc.getElementsByTagName("documenttitle");

            if(documenttitle.getLength() == 1) {
                if((documenttitle.item(0)).getNodeType() == Node.ELEMENT_NODE) {
                    documentTitle = documenttitle.item(0).getTextContent();
                }
            } else {
                JOptionPane.showMessageDialog(null, lang.get("thumbnailoverview.LayoutParser.wrongElementAmount") + " documenttitle" , lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                logger.logERROR("Wrong number of element for tag: \"documenttitle\". Allowed amount is: 1. Found in file is: " + documenttitle.getLength());
            }

            NodeList row = doc.getElementsByTagName("row");

            if(row.getLength() == 1) {
                if((row.item(0)).getNodeType() == Node.ELEMENT_NODE) {
                    String [] attributeNames = {"class"};
                    List<String> attributeValues =     parseAttributes((Element)row.item(0), attributeNames);

                    rowClass = attributeValues.get(0);
                }
            } else {
                JOptionPane.showMessageDialog(null, lang.get("thumbnailoverview.LayoutParser.wrongElementAmount") + " row" , lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                logger.logERROR("Wrong number of element for tag: \"row\". Allowed amount is: 1. Found in file is: " + row.getLength());
            }

            NodeList column = doc.getElementsByTagName("column");

            if(column.getLength() == 1) {
                if((column.item(0)).getNodeType() == Node.ELEMENT_NODE) {
                    String [] attributeNames = {"class", "amount"};
                    List<String> attributeValues = parseAttributes((Element)column.item(0), attributeNames);

                    columnClass  = attributeValues.get(0);
                    columnAmount = Integer.parseInt(attributeValues.get(1));
                }
            } else {
                JOptionPane.showMessageDialog(null, lang.get("thumbnailoverview.LayoutParser.wrongElementAmount") + " column" , lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                logger.logERROR("Wrong number of element for tag: \"column\". Allowed amount is: 1. Found in file is: " + column.getLength());
            }

            NodeList image = doc.getElementsByTagName("image");

            if(image.getLength() == 1) {
                if((image.item(0)).getNodeType() == Node.ELEMENT_NODE) {
                    String [] attributeNames = {"class"};
                    List<String> attributeValues =     parseAttributes((Element)image.item(0), attributeNames);

                    imageClass = attributeValues.get(0);
                }
            } else {
                JOptionPane.showMessageDialog(null, lang.get("thumbnailoverview.LayoutParser.wrongElementAmount") + " image" , lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                logger.logERROR("Wrong number of element for tag: \"image\". Allowed amount is: 1. Found in file is: " + image.getLength());
            }

            NodeList meta = doc.getElementsByTagName("meta");

            if(meta.getLength() == 1) {
                if((meta.item(0)).getNodeType() == Node.ELEMENT_NODE) {
                    String [] attributeNames = {"class"};
                    List<String> attributeValues =     parseAttributes((Element)meta.item(0), attributeNames);

                    metaClass = attributeValues.get(0);
                }
            } else {
                JOptionPane.showMessageDialog(null, lang.get("thumbnailoverview.LayoutParser.wrongElementAmount") + " meta" , lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                logger.logERROR("Wrong number of element for tag: \"meta\". Allowed amount is: 1. Found in file is: " + meta.getLength());
            }

            NodeList metaItem = doc.getElementsByTagName("metaitem");

            for (int i=0; i<metaItem.getLength(); i++) {
                if((metaItem.item(i)).getNodeType() == Node.ELEMENT_NODE) {
                    String [] attributeNames = {"class", "label", "meta"};
                    List<String> attributeValues =     parseAttributes((Element)metaItem.item(i), attributeNames);
                    metaItems.add(new LayoutMetaItem(attributeValues.get(0), attributeValues.get(1), attributeValues.get(2)));
                }
            }
        } catch (ParserConfigurationException pce) {
            JOptionPane.showMessageDialog(null, lang.get("thumbnailoverview.LayoutParser.parseError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            logStackTrace(pce);
        } catch (SAXException sx) {
            JOptionPane.showMessageDialog(null, lang.get("thumbnailoverview.LayoutParser.parseError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            logStackTrace(sx);
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, lang.get("thumbnailoverview.LayoutParser.parseError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            logStackTrace(io);
        } finally {
            StreamUtil.close(layoutSource, true);
        }
    }

    private List<String> parseAttributes (Element element, String [] attributeNames) {
        List<String> attributeValues = new ArrayList<String>(attributeNames.length);

        for (String attribute : attributeNames) {
            attributeValues.add(element.getAttribute(attribute));
        }
        return attributeValues;
    }

    private void logStackTrace(Exception e) {

        Logger logger = Logger.getInstance();
        logger.logERROR("Error when parsing layout.xml. See stack trace below for details.");

        for(StackTraceElement element : e.getStackTrace()) {
            logger.logERROR(element.toString());
        }
    }

    public String getRowClass() {
        return rowClass;
    }

    public String getColumnClass() {
        return columnClass;
    }

    public int getColumnAmount() {
        return columnAmount;
    }

    public String getImageClass() {
        return imageClass;
    }

    public String getMetaClass() {
        return metaClass;
    }

    public List<LayoutMetaItem> getMetaItems() {
        return metaItems;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }
}