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
package moller.javapeg.program.enumerations;

/**
 * This {@link Enum} lists all the meta data base schemas used by the JavaPEG
 * application for all their version. It basically maps, for each entry in this
 * {@link Enum}, a name as an {@link String} to an XSD schema file.
 *
 * @author Fredrik
 *
 */
public enum MetaDataSchema {

    META_DATA_001_XSD("meta-data-001.xsd");

    private String schemaName;

    MetaDataSchema(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSchemaName() {
        return schemaName;
    }
}
