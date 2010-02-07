package moller.util.io;
/**
 * This class was created : 2010-02-07 by Fredrik Möller
 * Latest changed         : 
 */

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XMLUtil {
		
	/**
	 * @param version
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeStartDocument(String version, XMLStreamWriter xmlsw) throws XMLStreamException {
		xmlsw.writeStartDocument(version);
	}

	/**
	 * @param element
	 * @param value
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeElement(String element, String value, XMLStreamWriter xmlsw) throws XMLStreamException {
		xmlsw.writeStartElement(element);
		xmlsw.writeCharacters(value);
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
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeElementEnd(XMLStreamWriter xmlsw) throws XMLStreamException {
		xmlsw.writeEndElement();
	}

	/**
	 * @param comment
	 * @param xmlsw
	 * @throws XMLStreamException
	 */
	public static void writeComment(String comment, XMLStreamWriter xmlsw) throws XMLStreamException {
		xmlsw.writeComment(comment);
	}
}