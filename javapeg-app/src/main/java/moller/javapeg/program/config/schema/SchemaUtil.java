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
package moller.javapeg.program.config.schema;

import moller.javapeg.program.enumerations.ConfigurationSchema;
import moller.javapeg.program.enumerations.MetaDataSchema;

import java.util.ArrayList;
import java.util.List;

public class SchemaUtil {

    public static ConfigurationSchema getConfigurationSchemaForVersion(String javapegVersion) {

        switch (javapegVersion) {
        case "3.0":
            return ConfigurationSchema.CONFIG_001_XSD;

        default:
            throw new IllegalArgumentException("Unsupported version: " + javapegVersion);
        }
    }

    public static List<String> getTransformersForSchema(ConfigurationSchema configurationSchema) {

        List<String> transformers = new ArrayList<>();

        switch (configurationSchema) {
        case CONFIG_001_XSD:

            break;

        default:
            break;
        }

        return transformers;
    }

    public static MetaDataSchema getMetaDataSchemaForVersion(String javapegVersion) {
        switch (javapegVersion) {
        case "3.0":
            return MetaDataSchema.META_DATA_001_XSD;

        default:
            throw new IllegalArgumentException("Unsupported version: " + javapegVersion);
        }
    }
}
