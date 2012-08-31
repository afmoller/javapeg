package moller.util.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.util.string.Tab;

public class XMLUtil {

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




}
