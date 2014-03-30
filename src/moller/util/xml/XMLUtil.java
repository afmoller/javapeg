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
package moller.util.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import moller.util.result.ResultObject;
import moller.util.string.Tab;

import org.xml.sax.SAXException;

public class XMLUtil {

    private static SchemaFactory factory;

    static {
        factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    }

	/**
	 * @param encoding
	 * @param version
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeStartDocument(String encoding, String version, XMLStreamWriter xmlsw) throws XMLStreamException {
		xmlsw.writeStartDocument(encoding, version);
	}

	/**
	 * @param element
	 * @param value
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeElement(String element, String value, XMLStreamWriter xmlsw) throws XMLStreamException {
		writeElement(element, value, null, xmlsw);
	}

	/**
	 * @param element
	 * @param value
	 * @param xmlAttributes
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeElement(String element, String value, XMLAttribute[] xmlAttributes, XMLStreamWriter xmlsw) throws XMLStreamException {
		xmlsw.writeStartElement(element);

		if (xmlAttributes != null) {
			for (XMLAttribute xmlAttribute : xmlAttributes) {
				xmlsw.writeAttribute(xmlAttribute.getName(), xmlAttribute.getValue());
			}
		}

		if (value != null) {
		    xmlsw.writeCharacters(value);
		}

		xmlsw.writeEndElement();
	}

	/**
	 * @param element
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeElementStart(String element,  XMLStreamWriter xmlsw) throws XMLStreamException {
		xmlsw.writeStartElement(element);
	}

	/**
	 * @param element
	 * @param attributeName
	 * @param attributeValue
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeElementStart(String element, String attributeName, String attributeValue, XMLStreamWriter xmlsw) throws XMLStreamException {
		xmlsw.writeStartElement(element);
		xmlsw.writeAttribute(attributeName, attributeValue);
	}

	/**
	 * @param element
	 * @param xmlAttributes
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeElementStart(String element, XMLAttribute[] xmlAttributes, XMLStreamWriter xmlsw) throws XMLStreamException {
		xmlsw.writeStartElement(element);
		for (XMLAttribute xmlAttribute : xmlAttributes) {
			xmlsw.writeAttribute(xmlAttribute.getName(), xmlAttribute.getValue());
		}
	}

	/**
	 * @param element
	 * @param indent
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeElementStart(String element, Tab indent, XMLStreamWriter xmlsw) throws XMLStreamException {
        writeIndent(xmlsw, indent.value());
	    writeElementStart(element, xmlsw);
    }

	/**
	 * @param element
	 * @param indent
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeElementStartWithLineBreak(String element, Tab indent, XMLStreamWriter xmlsw) throws XMLStreamException {
	    writeIndent(xmlsw, indent.value());
        writeElementStart(element, xmlsw);
        writeLineBreak(xmlsw);
    }

	/**
     * @param element
     * @param indent
     * @param xmlsw
     * @throws XMLStreamException
     */
    public static void writeElementStartWithLineBreak(String element, XMLAttribute[] xmlAttributes, Tab indent, XMLStreamWriter xmlsw) throws XMLStreamException {
        writeIndent(xmlsw, indent.value());
        writeElementStart(element, xmlAttributes, xmlsw);
        writeLineBreak(xmlsw);
    }

    public static void writeElementStartWithLineBreak(String element, String indent, XMLAttribute[] xmlAttributes, XMLStreamWriter xmlsw) throws XMLStreamException {
        writeIndent(xmlsw, indent);
        writeElementStart(element, xmlAttributes, xmlsw);
        writeLineBreak(xmlsw);
    }

	/**
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeElementEnd(XMLStreamWriter xmlsw) throws XMLStreamException {
		xmlsw.writeEndElement();
	}

	/**
	 * @param xmlsw
	 * @param indent
	 * @throws XMLStreamException
	 */
	public static void writeElementEnd(XMLStreamWriter xmlsw, Tab indent) throws XMLStreamException {
	    writeIndent(xmlsw, indent.value());
	    writeElementEnd(xmlsw);
	}

	public static void writeElementEndWithLineBreak(XMLStreamWriter xmlsw, String indent) throws XMLStreamException {
	    writeIndent(xmlsw, indent);
        writeElementEnd(xmlsw);
        writeLineBreak(xmlsw);
    }

	 /**
	 * @param xmlsw
	 * @param indent
	 * @throws XMLStreamException
	 */
	public static void writeElementEndWithLineBreak(XMLStreamWriter xmlsw, Tab indent) throws XMLStreamException {
	    writeIndent(xmlsw, indent.value());
	    writeElementEnd(xmlsw);
	    writeLineBreak(xmlsw);
	}

	public static void writeEmptyElementWithIndentAndLineBreak(String element, XMLStreamWriter xmlsw, Tab indent) throws XMLStreamException {
	    writeEmptyElementWithIndentAndLineBreak(element, xmlsw, indent.value());
	}

	public static void writeEmptyElementWithIndentAndLineBreak(String element, XMLStreamWriter xmlsw, String indent) throws XMLStreamException {
        writeIndent(xmlsw, indent);
        xmlsw.writeEmptyElement(element);
        writeLineBreak(xmlsw);
    }

	 public static void writeEmptyElementWithIndentAndLineBreak(String element, XMLStreamWriter xmlsw, String indent, XMLAttribute[] xmlAttributes) throws XMLStreamException {
	     writeIndent(xmlsw, indent);
	     xmlsw.writeEmptyElement(element);

	     for (XMLAttribute xmlAttribute : xmlAttributes) {
	         xmlsw.writeAttribute(xmlAttribute.getName(), xmlAttribute.getValue());
	     }
	     writeLineBreak(xmlsw);
	 }

	/**
	 * @param comment
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeComment(String comment, XMLStreamWriter xmlsw) throws XMLStreamException {
		xmlsw.writeComment(comment);
	}

    /**
     * @param element
     * @param baseIndent
     * @param value
     * @param xmlsw
     * @throws XMLStreamException
     */
    public static void writeElementWithIndent(String element, Tab baseIndent, String value, XMLStreamWriter xmlsw) throws XMLStreamException {
        xmlsw.writeCharacters(baseIndent.value());
        writeElement(element, value, xmlsw);
    }

    public static void writeElementWithIndent(String element, Tab baseIndent, XMLAttribute[] attributes, String value, XMLStreamWriter xmlsw) throws XMLStreamException {
        xmlsw.writeCharacters(baseIndent.value());
        writeElement(element, value, attributes, xmlsw);
    }

    public static void writeElementWithIndentAndLineBreak(String element, Tab baseIndent, XMLAttribute[] attributes, String value, XMLStreamWriter xmlsw) throws XMLStreamException {
        writeElementWithIndent(element, baseIndent, attributes, value, xmlsw);
        writeLineBreak(xmlsw);
    }

    public static void writeElementWithIndentAndLineBreak(String element, Tab baseIndent, String value, XMLStreamWriter xmlsw) throws XMLStreamException {
        writeElementWithIndent(element, baseIndent, value, xmlsw);
        writeLineBreak(xmlsw);
    }

    public static void writeElementWithIndentAndLineBreak(String element, String value, String indent, XMLAttribute[] xmlAttributes, XMLStreamWriter xmlsw) throws XMLStreamException {
        writeIndent(xmlsw, indent);
        writeElement(element, value, xmlAttributes, xmlsw);
        writeLineBreak(xmlsw);
    }

    public static void writeLineBreak(XMLStreamWriter xmlsw) throws XMLStreamException {
        xmlsw.writeCharacters("\n");
    }

    public static void writeIndent(XMLStreamWriter xmlsw, String indent) throws XMLStreamException {
        xmlsw.writeCharacters(indent);
    }

    /**
     * Utility method which tests if an configuration file (XML) is valid
     * (checked against an Schema).
     *
     * @param configFile
     *            is the configuration file to check the validity of
     * @param configSchemaLocation
     *            is the location of the schema to use for checking the validity
     *            of the XML file specified by the parameter configFile
     * @return a {@link ResultObject} indicating whether or not the configuration
     *         file is valid against the specified schema. It the configuration
     *         file is not valid the is the cause of invalidity attached as an
     *         exception
     */
    public static ResultObject<Exception> validate(File xmlFileToValidate,  StreamSource schemaToValidateAgainst) {
        try {
            Schema schema = factory.newSchema(schemaToValidateAgainst);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFileToValidate));

            return new ResultObject<Exception>(true, null);
        } catch (SAXException | IOException exception) {
            return new ResultObject<Exception>(false, exception);
        }
    }
}
