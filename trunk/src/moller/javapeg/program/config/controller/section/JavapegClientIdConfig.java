package moller.javapeg.program.config.controller.section;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.config.controller.ConfigElement;
import moller.util.string.Tab;
import moller.util.xml.XMLUtil;

public class JavapegClientIdConfig {

    public static String getJavapegClientIdConfig(String javapegClientId) {
        return javapegClientId;
    }

    public static void writeJavapegClientIdConfig(String javapegClientId, Tab baseIndent, XMLStreamWriter xmlsw) throws XMLStreamException {
        XMLUtil.writeElementWithIndentAndLineBreak(ConfigElement.JAVAPEG_CLIENT_ID, baseIndent, javapegClientId, xmlsw);
    }
}
