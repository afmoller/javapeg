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
